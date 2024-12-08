package fileExplorer.model;

import javax.swing.*;
import java.awt.*;

public class FileListRenderer extends DefaultListCellRenderer {
    private final FileIconProvider iconProvider;

    public FileListRenderer(FileIconProvider iconProvider) {
        this.iconProvider = iconProvider;
    }

    @Override
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        String filename = value.toString();

        Icon icon = iconProvider.getIconForFile(filename);
        label.setIcon(icon);
        return label;
    }
}
