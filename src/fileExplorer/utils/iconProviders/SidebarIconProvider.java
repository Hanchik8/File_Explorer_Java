package fileExplorer.utils.iconProviders;

import fileExplorer.utils.ImageUtils;

import javax.swing.Icon;
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
        sidebarIcons.put("Desktop", ImageUtils.getImagePreview("resources/images/sidebarIcons/desktop.png", 20));
        sidebarIcons.put("Documents", ImageUtils.getImagePreview("resources/images/sidebarIcons/documentsIcon.png", 20));
        sidebarIcons.put("Downloads", ImageUtils.getImagePreview("resources/images/sidebarIcons/downloads.png", 20));
        sidebarIcons.put("Pictures", ImageUtils.getImagePreview("resources/images/sidebarIcons/picturesIcon.png", 20));
        sidebarIcons.put("Music", ImageUtils.getImagePreview("resources/images/sidebarIcons/musicIcon.png", 20));
        sidebarIcons.put("Videos", ImageUtils.getImagePreview("resources/images/sidebarIcons/videosIcon.png", 20));
        sidebarIcons.put("My Computer", ImageUtils.getImagePreview("resources/images/sidebarIcons/myComputer.png", 20));
    }

    /**
     * Gets the icon associated with a specific category.
     *
     * @param category the category name
     * @return the icon for the category, or a default icon if not found
     */
    public Icon getIconForCategory(String category) {
        return sidebarIcons.getOrDefault(category,
                sidebarIcons.get("Default"));
    }
}
