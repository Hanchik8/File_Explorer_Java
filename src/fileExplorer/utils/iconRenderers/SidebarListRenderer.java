package fileExplorer.utils.iconRenderers;

import fileExplorer.utils.iconProviders.SidebarIconProvider;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.JLabel;
import javax.swing.Icon;

import java.awt.Component;

/**
 * This class provides custom rendering for sidebar items.
 * It associates appropriate icons with item names using a SidebarIconProvider.
 */
public class SidebarListRenderer extends DefaultListCellRenderer {
    private static final long serialVersionUID = 1L;
    private transient final SidebarIconProvider sidebarIconProvider;

    /**
     * Constructor for initializing the sidebar list renderer with an icon provider.
     *
     * @param iconProvider an object responsible for providing icons for sidebar items
     */
    public SidebarListRenderer(SidebarIconProvider iconProvider) {
        this.sidebarIconProvider = iconProvider;
    }

    /**
     * Overrides the method to customize how each list item is rendered.
     * Adds an icon to the label if the item name is not null.
     *
     * @param list         the JList being rendered
     * @param value        the value to be displayed (sidebar item name)
     * @param index        the index of the item in the list
     * @param isSelected   whether the item is selected
     * @param cellHasFocus whether the cell has focus
     * @return the component used for rendering the list item
     */
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
