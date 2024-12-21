package fileExplorer.view.viewComponents;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.io.File;
import java.io.Serializable;

public class JTreePanel implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public void updateDirectoryNode(File directory, File[] fileList) {
        DefaultMutableTreeNode targetNode = findNode(mainRoot, directory);
        if (targetNode != null) {
            targetNode.removeAllChildren();
            addFilesToNode(targetNode, fileList);
        }
    }

    private DefaultMutableTreeNode findNode(DefaultMutableTreeNode rootNode, File directory) {
        if (rootNode.getUserObject().equals(directory.getAbsolutePath())) {
            return rootNode;
        }

        for (int i = 0; i < rootNode.getChildCount(); i++) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) rootNode.getChildAt(i);
            DefaultMutableTreeNode result = findNode(childNode, directory);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    public JTree getFileTree() {
        return fileTree;
    }
}
