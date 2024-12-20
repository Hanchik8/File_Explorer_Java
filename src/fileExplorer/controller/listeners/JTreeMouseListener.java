package fileExplorer.controller.listeners;

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
    public JTreeMouseListener(MainView mainView, FileManipulationModel fileModel) {
        this.mainView = mainView;
        this.fileModel = fileModel;
    }

    @Override
    public void valueChanged(TreeSelectionEvent event) {
        TreePath selectedPath = event.getPath();

        if (selectedPath == null) return;

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
        File selectedFile = new File(String.valueOf(node.getUserObject()));

        mainView.updateFileDetails(selectedFile, fileModel.getFileExtension(selectedFile.getName()));

        if (node.isLeaf()) {
            fileModel.openFile(selectedFile);
        }
    }
}
