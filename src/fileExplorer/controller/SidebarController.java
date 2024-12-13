package fileExplorer.controller;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.view.MainView;
import fileExplorer.view.viewComponents.SidebarPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.File;

public class SidebarController {
    private SidebarPanel sidebarPanel;
    private MainView mainView;
    private DirectoryManagementModel directoryManagement;

    public SidebarController(SidebarPanel sidebarPanel, MainView mainView) {
        this.sidebarPanel = sidebarPanel;
        this.mainView = mainView;
        this.directoryManagement = new DirectoryManagementModel(mainView);

        // Initialize listeners for the components
        initListeners();
    }

    // Initialize listeners
    private void initListeners() {
        // Listener for category list selection
        sidebarPanel.getCategoryList().addListSelectionListener(new CategoryListSelectionListener());
    }

    // Update the main panel with the contents of the selected directory
    private void updateMainPanel(File directory) {
        directoryManagement.updateDirectory(directory.getAbsolutePath());
    }

    // Listener for category list selection
    private class CategoryListSelectionListener implements ListSelectionListener {
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
                    updateMainPanel(selectedFolder);
                }
            }
        }
    }
}
