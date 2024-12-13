package fileExplorer.controller;

import fileExplorer.controller.listeners.SideBarSelectionListener;
import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.view.MainView;
import fileExplorer.view.viewComponents.SidebarPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.File;

public class SidebarController {
    private SidebarPanel sidebarPanel;
    private DirectoryManagementModel directoryModel;

    public SidebarController(SidebarPanel sidebarPanel, DirectoryManagementModel directoryModel) {
        this.sidebarPanel = sidebarPanel;
        this.directoryModel = directoryModel;

        // Initialize listeners for the components
        sidebarPanel.getCategoryList().addListSelectionListener(new SideBarSelectionListener(sidebarPanel, this));
    }

    // Update the main panel with the contents of the selected directory
    public void updateMainPanel(File directory) {
        directoryModel.updateDirectory(directory.getAbsolutePath());
    }
}
