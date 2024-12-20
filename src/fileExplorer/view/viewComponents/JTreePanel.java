package fileExplorer.view.viewComponents;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.io.File;

public class JTreePanel {
    private JTree fileTree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode mainRoot;

    public JTreePanel() {
        mainRoot = new DefaultMutableTreeNode("Root");
        treeModel = new DefaultTreeModel(mainRoot);
        fileTree = new JTree(treeModel);
    }

    public void generateRoots(File[] initialDirectories) {
        for (File directory : initialDirectories) {
            DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(directory.getAbsoluteFile());
            mainRoot.add(rootNode);

            rootNode.add(new DefaultMutableTreeNode("Loading..."));
        }
    }

    public void addFilesToNode(DefaultMutableTreeNode parentNode, File[] directoryContent) {
        if (directoryContent != null) {
            for (File file : directoryContent) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(file.getAbsolutePath());
                parentNode.add(childNode);
                if (file.isDirectory()) {
                    childNode.add(new DefaultMutableTreeNode("Loading..."));
                }
            }
        }
        treeModel.reload(parentNode);
    }

    public JTree getFileTree() {
        return fileTree;
    }
}
