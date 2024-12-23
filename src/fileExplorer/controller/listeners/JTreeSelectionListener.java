package fileExplorer.controller.listeners;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import java.io.File;

/**
 * Слушатель для событий выбора узлов в дереве (JTree).
 * Обрабатывает выбор файлов и директорий, обновляя информацию о файле и переключая директорию в модели.
 */
public class JTreeSelectionListener implements TreeSelectionListener {
    private MainView mainView;
    private FileManipulationModel fileModel;
    DirectoryManagementModel directoryModel;

    /**
     * Конструктор для создания слушателя событий выбора узлов дерева.
     * @param mainView       основное окно приложения.
     * @param fileModel      модель для работы с файлами.
     * @param directoryModel модель для работы с директориями.
     */
    public JTreeSelectionListener(MainView mainView, FileManipulationModel fileModel, DirectoryManagementModel directoryModel) {
        this.mainView = mainView;
        this.fileModel = fileModel;
        this.directoryModel = directoryModel;
    }

    /**
     * Метод, вызываемый при изменении выбора в дереве.
     * Обновляет детали файла, открывает файл или обновляет текущую директорию.
     * @param event событие изменения выбора узла дерева.
     */
    @Override
    public void valueChanged(TreeSelectionEvent event) {
        TreePath selectedPath = event.getPath();

        if (selectedPath == null) return;

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
        File selectedFile = new File(String.valueOf(node.getUserObject()));

        mainView.updateFileDetails(selectedFile, fileModel.getFileExtension(selectedFile));

        if (node.isLeaf()) {
            fileModel.openFile(selectedFile);
        } else {
            if (selectedFile.getName().equals("My Computer")) {
                directoryModel.updateDirectory("Root");
            }
            else {
                directoryModel.updateDirectory(selectedFile.getAbsolutePath());
            }
        }
    }
}
