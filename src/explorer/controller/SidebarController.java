package explorer.controller;

import explorer.model.DirectoryManagement;
import explorer.model.FileManipulation;
import explorer.view.ExplorerView;
import explorer.view.viewComponents.SidebarPanel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.io.File;

public class SidebarController {
    private SidebarPanel sidebarPanel;
    private ExplorerView explorerView;
    private DirectoryManagement directoryManagement;
    private FileManipulation fileManipulation;

    public SidebarController(SidebarPanel sidebarPanel, ExplorerView explorerView) {
        this.sidebarPanel = sidebarPanel;
        this.explorerView = explorerView;
        this.directoryManagement = new DirectoryManagement(explorerView);
        this.fileManipulation = new FileManipulation(explorerView);

        // Инициализация слушателей для компонентов
        initListeners();
    }

    // Инициализация слушателей
    private void initListeners() {
        // Слушатель для изменений в списке категорий
        sidebarPanel.getCategoryList().addListSelectionListener(new CategoryListSelectionListener());

        // Слушатель для изменений в дереве файлов
        sidebarPanel.getFileTree().addTreeSelectionListener(new FileTreeSelectionListener());
    }

    // Обновляем правую панель с содержимым папки
    private void updateMainPanel(File directory) {
        directoryManagement.updateDirectory(directory.getAbsolutePath());
    }

    // Обработчик выбора в списке категорий
    private class CategoryListSelectionListener implements ListSelectionListener {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                String selectedCategory = sidebarPanel.getCategoryList().getSelectedValue();
                File selectedFolder = null;

                // Определяем путь в зависимости от выбранной категории
                switch (selectedCategory) {
                    case "Загрузки":
                        selectedFolder = new File(System.getProperty("user.home") + "/Downloads");
                        break;
                    case "Музыка":
                        selectedFolder = new File(System.getProperty("user.home") + "/Music");
                        break;
                    case "Изображения":
                        selectedFolder = new File(System.getProperty("user.home") + "/Gallery");
                        break;
                    case "Документы":
                        selectedFolder = new File(System.getProperty("user.home") + "/Documents");
                        break;
                    case "Видео":
                        selectedFolder = new File(System.getProperty("user.home") + "/Videos");
                        break;
                }

                // Обновляем правую панель с содержимым выбранной папки
                if (selectedFolder != null && selectedFolder.exists()) {
                    updateMainPanel(selectedFolder);
                }
            }
        }
    }

    // Обработчик выбора узлов в дереве файлов
    private class FileTreeSelectionListener implements javax.swing.event.TreeSelectionListener {
        @Override
        public void valueChanged(TreeSelectionEvent e) {
            TreePath selectedPath = sidebarPanel.getFileTree().getSelectionPath();

            if (selectedPath != null) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                Object userObject = selectedNode.getUserObject();

                if (userObject instanceof File) {
                    File selectedFile = (File) userObject;

                    if (selectedFile.isDirectory()) {
                        // Если это директория, то раскрываем её содержимое в правой панели
                        updateMainPanel(selectedFile);

                        // Если узел еще не загружен, подгружаем дочерние узлы
                        if (selectedNode.getChildCount() == 1 &&
                                selectedNode.getChildAt(0).toString().equals("Загрузка...")) {
                            updateChildNodes(selectedNode);
                        }
                    } else {
                        // Если это файл, открываем его с помощью FileManipulation
                        fileManipulation.openFile(selectedFile);
                    }
                }
            }
        }
    }

    // Обновляем дочерние узлы при раскрытии
    private void updateChildNodes(DefaultMutableTreeNode parentNode) {
        parentNode.removeAllChildren();
        loadChildNodes(parentNode);

        // Обновляем модель дерева
        ((DefaultTreeModel) sidebarPanel.getFileTree().getModel()).reload(parentNode);
    }

    // Загружаем дочерние узлы для заданного узла
    private void loadChildNodes(DefaultMutableTreeNode parentNode) {
        File parentFile = (File) parentNode.getUserObject();
        File[] files = parentFile.listFiles();

        if (files != null) {
            for (File file : files) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(file);
                parentNode.add(childNode);

                // Добавляем "пустой" узел для папок, чтобы их можно было раскрывать
                if (file.isDirectory()) {
                    childNode.add(new DefaultMutableTreeNode("Загрузка..."));
                }
            }
        }
    }
}
