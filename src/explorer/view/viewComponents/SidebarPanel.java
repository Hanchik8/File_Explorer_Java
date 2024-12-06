package explorer.view.viewComponents;

import explorer.controller.SidebarController;
import explorer.model.DirectoryManagement;
import explorer.view.ExplorerView;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;

public class SidebarPanel extends JPanel {
    private JTree fileTree;
    private JList<String> categoryList;

    public SidebarPanel(ExplorerView explorerView) {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, getHeight())); // Ширина 250px, высота не изменяется

        // Создаем панель с категориями
        JPanel categoryPanel = createCategoryPanel();

        // Создаем корневой узел для дерева файлов
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Мой компьютер");
        File[] roots = File.listRoots();
        for (File root : roots) {
            DefaultMutableTreeNode driveNode = new DefaultMutableTreeNode(root);
            rootNode.add(driveNode);
            loadChildNodes(driveNode);
        }

        // Создаем модель дерева и JTree для нижней части
        DefaultTreeModel treeModel = new DefaultTreeModel(rootNode);
        fileTree = new JTree(treeModel);
        fileTree.setRootVisible(true);

        // Разделим боковую панель на 2 части с помощью JSplitPane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(200);
        splitPane.setTopComponent(categoryPanel);
        splitPane.setBottomComponent(new JScrollPane(fileTree));

        add(splitPane, BorderLayout.CENTER);

        // Создаем контроллер
        new SidebarController(this, explorerView);
    }

    // Получаем список категорий
    public JList<String> getCategoryList() {
        return categoryList;
    }

    // Получаем дерево файлов
    public JTree getFileTree() {
        return fileTree;
    }

    // Создаем панель с категориями (Загрузки, Музыка, Изображения, Документы, Видео)
    private JPanel createCategoryPanel() {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BorderLayout());

        // Категории для списка
        String[] categories = {"Загрузки", "Музыка", "Изображения", "Документы", "Видео"};

        // Создаем JList для категорий
        categoryList = new JList<>(categories);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Оборачиваем в JScrollPane
        categoryPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER);

        return categoryPanel;
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
