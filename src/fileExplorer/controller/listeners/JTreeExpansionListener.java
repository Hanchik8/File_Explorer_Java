package fileExplorer.controller.listeners;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.view.MainView;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;

public class JTreeExpansionListener extends MouseAdapter implements TreeExpansionListener {
    private DirectoryManagementModel directoryModel;
    private MainView mainView;
    public JTreeExpansionListener(MainView mainView, DirectoryManagementModel directoryModel) {
        this.directoryModel = directoryModel;
        this.mainView = mainView;
    }
    @Override
    public void treeExpanded(TreeExpansionEvent event) {
        TreePath path = event.getPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        if (node.getChildCount() == 1) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(0);
            if ("Loading...".equals(childNode.getUserObject())) {
                node.removeAllChildren();
                String directoryName = node.getUserObject().toString();
                mainView.getSidebarPanel().getjTreePanel().addFilesToNode(node, directoryModel.updateDirectory(directoryName));
            }
        }
    }

    @Override
    public void treeCollapsed(TreeExpansionEvent event) {

    }
}
