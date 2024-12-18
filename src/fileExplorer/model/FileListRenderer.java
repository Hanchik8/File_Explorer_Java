package fileExplorer.model;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.Icon;
import javax.swing.DefaultListCellRenderer;
import java.awt.Component;

/**
 * This class provides custom rendering for file list items.
 * It associates appropriate icons with file names using a FileIconProvider.
 */
public class FileListRenderer extends DefaultListCellRenderer {
    private final FileIconProvider fileIconProvider; // Provides icons for files based on their type

    /**
     * Constructor for initializing the file list renderer with an icon provider.
     * 
     * @param iconProvider an object responsible for providing icons for files
     */
    public FileListRenderer(FileIconProvider iconProvider) {
        this.fileIconProvider = iconProvider;
    }

    /**
     * Overrides the method to customize how each list item is rendered.
     * Adds an icon to the label if the file name is not null.
     * 
     * @param list         the JList being rendered
     * @param value        the value to be displayed (file name)
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

        // Check if the file name is not null before proceeding
        if (value != null) {
            String filename = value.toString(); // Convert the object to a string
            Icon icon = fileIconProvider.getIconForFile(filename); // Get icon based on file name
            label.setIcon(icon); // Set the icon for the label
        } else {
            label.setIcon(null); // If the value is null, clear the icon
        }

        return label; // Return the customized label for rendering
    }
}
