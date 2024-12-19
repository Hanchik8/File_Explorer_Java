package fileExplorer.controller.listeners;

import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class JTreeMouseListener extends MouseAdapter {
    private MainView mainView;
    private FileManipulationModel fileModel;
    public JTreeMouseListener(MainView mainView, FileManipulationModel fileModel) {
        this.mainView = mainView;
        this.fileModel = fileModel;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = mainView.getSidebarPanel().getjTreePanel().getFileTree().getClosestRowForLocation(e.getX(), e.getY());
        if (row == -1) return;

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) mainView.getSidebarPanel().getjTreePanel().getFileTree().getPathForRow(row).getLastPathComponent();
        File selectedFile = new File(String.valueOf(node.getUserObject()));
        if (node.isLeaf() && e.getClickCount() == 2) {
            fileModel.openFile(selectedFile);
        }

        mainView.updateFileDetails(selectedFile, fileModel.getFileExtension(selectedFile.getName()));
    }
}
