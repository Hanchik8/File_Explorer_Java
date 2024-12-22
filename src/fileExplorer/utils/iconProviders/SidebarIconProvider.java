package fileExplorer.utils.iconProviders;

import fileExplorer.utils.ImageUtils;

import javax.swing.ImageIcon;
import java.util.HashMap;
import java.util.Map;

public class SidebarIconProvider {
    private final Map<String, ImageIcon> sidebarIcons;

    public SidebarIconProvider() {
        sidebarIcons = new HashMap<>();
        loadSidebarIcons();
    }

    private void loadSidebarIcons() {
        sidebarIcons.put("Desktop", ImageUtils.getImagePreview("resources/images/sidebarIcons/desktop.png"));
        sidebarIcons.put("Documents", ImageUtils.getImagePreview("resources/images/sidebarIcons/documentsIcon.png"));
        sidebarIcons.put("Downloads", ImageUtils.getImagePreview("resources/images/sidebarIcons/downloads.png"));
        sidebarIcons.put("Pictures", ImageUtils.getImagePreview("resources/images/sidebarIcons/picturesIcon.png"));
        sidebarIcons.put("Music", ImageUtils.getImagePreview("resources/images/sidebarIcons/musicIcon.png"));
        sidebarIcons.put("Videos", ImageUtils.getImagePreview("resources/images/sidebarIcons/videosIcon.png"));
        sidebarIcons.put("My Computer", ImageUtils.getImagePreview("resources/images/sidebarIcons/myComputer.png"));
    }

    public ImageIcon getIconForCategory(String category) {
        return ImageUtils.scaleImage(sidebarIcons.get(category), 20, 20);
    }
}
