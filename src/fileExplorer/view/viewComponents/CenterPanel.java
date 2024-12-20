package fileExplorer.view.viewComponents;

import fileExplorer.utils.iconProviders.FileIconProvider;
import fileExplorer.utils.iconRenderers.FileListRenderer;

import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.JList;

import java.awt.BorderLayout;

public class CenterPanel extends JPanel {
    private DefaultListModel<String> fileListModel;
    private JList<String> fileList;
    private final FileListRenderer fileListRenderer;

    public CenterPanel() {
        setLayout(new BorderLayout());
        fileListRenderer = new FileListRenderer(new FileIconProvider());
        add(createFileList());
    }

    private JList<String> createFileList() {
        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        fileList.setCellRenderer(fileListRenderer);
        return fileList;
    }

    public void updateFileListModel(String[] fileNamesList) {
        fileListModel.clear();
        for (String filename : fileNamesList) {
            fileListModel.addElement(filename);
        }

        fileList.revalidate();
        fileList.repaint();
    }

    public JList<String> getFileList() {
        return fileList;
    }
}
