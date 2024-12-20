package fileExplorer.model;

import javax.swing.*;
import java.awt.*;

/**
 * This class provides custom rendering for sidebar items.
 * It associates appropriate icons with item names using a SidebarIconProvider.
 */
public class SidebarListRenderer extends DefaultListCellRenderer {
    private final SidebarIconProvider sidebarIconProvider; // Provides icons for sidebar items

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

        // Call the superclass to get the default rendering component
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        // Check if the item name is not null before proceeding
        if (value != null) {
            String itemName = value.toString(); // Convert the object to a string
            Icon icon = sidebarIconProvider.getIconForCategory(itemName); // Get icon based on item name
            label.setIcon(icon); // Set the icon for the label
        } else {
            label.setIcon(null); // If the value is null, clear the icon
        }

        return label; // Return the customized label for rendering
    }
}
