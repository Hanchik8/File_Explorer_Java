package fileExplorer.utils.iconRenderers;

import fileExplorer.utils.iconProviders.FileIconProvider;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.Icon;
import javax.swing.DefaultListCellRenderer;
import java.awt.Component;

public class FileListRenderer extends DefaultListCellRenderer {
    private final FileIconProvider fileIconProvider;

    public FileListRenderer(FileIconProvider iconProvider) {
        this.fileIconProvider = iconProvider;
    }

    @Override
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value != null) {
            String filename = value.toString();
            Icon icon = fileIconProvider.getIconForFile(filename);
            label.setIcon(icon);
        } else {
            label.setIcon(null);
        }
        return label;
    }
}
