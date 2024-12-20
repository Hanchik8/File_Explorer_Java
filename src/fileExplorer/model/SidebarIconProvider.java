package fileExplorer.model;

import fileExplorer.utils.ImageUtils;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Provides icons for sidebar items in the file explorer.
 */
public class SidebarIconProvider {
    private final Map<String, Icon> sidebarIcons;

    /**
     * Constructor for initializing the SidebarIconProvider.
     * Loads the icons for common sidebar categories.
     */
    public SidebarIconProvider() {
        sidebarIcons = new HashMap<>();
        loadSidebarIcons();
    }

    private void loadSidebarIcons() {
        // Load standard icons for known sidebar categories
        sidebarIcons.put("Documents", ImageUtils.getImagePreview("resources/images/sidebarIcons/documentsIcon.png", 15));
        sidebarIcons.put("Downloads", ImageUtils.getImagePreview("resources/images/sidebarIcons/downloads.png", 15));
        sidebarIcons.put("Images", ImageUtils.getImagePreview("resources/images/sidebarIcons/imagesIcon.png", 15));
        sidebarIcons.put("Music", ImageUtils.getImagePreview("resources/images/sidebarIcons/musicIcon.png", 15));
        sidebarIcons.put("Videos", ImageUtils.getImagePreview("resources/images/sidebarIcons/videosIcon.png", 15));
        }

    /**
     * Gets the icon associated with a specific category.
     *
     * @param category the category name
     * @return the icon for the category, or a default icon if not found
     */
    public Icon getIconForCategory(String category) {
        return sidebarIcons.getOrDefault(category,
                sidebarIcons.get("Default")); // Return default icon if category not found
    }
}
