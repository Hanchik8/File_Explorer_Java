package fileExplorer.controller.listeners;

import fileExplorer.controller.SidebarController;
import fileExplorer.view.viewComponents.SidebarPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.File;

public class SideBarSelectionListener implements ListSelectionListener {
    private final SidebarPanel sidebarPanel;
    private final SidebarController sidebarController;

    public SideBarSelectionListener(SidebarPanel sidebarPanel, SidebarController sidebarController) {
        this.sidebarPanel = sidebarPanel;
        this.sidebarController = sidebarController;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String selectedCategory = sidebarPanel.getCategoryList().getSelectedValue();
            File selectedFolder = null;

            // Determine the path based on the selected category
            switch (selectedCategory) {
                case "Downloads":
                    selectedFolder = new File(System.getProperty("user.home") + "/Downloads");
                    break;
                case "Music":
                    selectedFolder = new File(System.getProperty("user.home") + "/Music");
                    break;
                case "Images":
                    selectedFolder = new File(System.getProperty("user.home") + "/Pictures");
                    break;
                case "Documents":
                    selectedFolder = new File(System.getProperty("user.home") + "/Documents");
                    break;
                case "Videos":
                    selectedFolder = new File(System.getProperty("user.home") + "/Videos");
                    break;
            }

            // Update the main panel with the contents of the selected folder
            if (selectedFolder != null && selectedFolder.exists()) {
                sidebarController.updateMainPanel(selectedFolder);
            }
        }
    }
}
