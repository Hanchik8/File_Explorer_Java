package fileExplorer.utils.iconRenderers;

import fileExplorer.utils.iconProviders.SidebarIconProvider;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.Icon;

import java.awt.Component;

public class SidebarListRenderer extends DefaultListCellRenderer {
    private static final long serialVersionUID = 1L;
    private transient final SidebarIconProvider sidebarIconProvider;

    public SidebarListRenderer(SidebarIconProvider iconProvider) {
        this.sidebarIconProvider = iconProvider;
    }

    @Override
    public Component getListCellRendererComponent(
            JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value != null) {
            String itemName = value.toString();
            Icon icon = sidebarIconProvider.getIconForCategory(itemName);
            label.setIcon(icon);
        } else {
            label.setIcon(null);
        }

        return label;
    }
}
