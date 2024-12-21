package fileExplorer.controller.listeners;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import java.io.File;

public class JTreeMouseListener implements TreeSelectionListener {
    private MainView mainView;
    private FileManipulationModel fileModel;
    DirectoryManagementModel directoryModel;
    public JTreeMouseListener(MainView mainView, FileManipulationModel fileModel, DirectoryManagementModel directoryModel) {
        this.mainView = mainView;
        this.fileModel = fileModel;
        this.directoryModel = directoryModel;
    }

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
            if (selectedFile.getName().equals("Root")) {
                directoryModel.updateDirectory("Root");
            }
            else {
                directoryModel.updateDirectory(selectedFile.getAbsolutePath());
            }
        }
    }
}
