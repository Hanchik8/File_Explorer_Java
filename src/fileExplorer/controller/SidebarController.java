package fileExplorer.controller;

import fileExplorer.controller.listeners.JTreeExpansionListener;
import fileExplorer.controller.listeners.JTreeMouseListener;
import fileExplorer.controller.listeners.SideBarSelectionListener;
import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

import java.io.File;

public class SidebarController {
    private MainView mainView;
    private DirectoryManagementModel directoryModel;
    private FileManipulationModel fileModel;

    public SidebarController(MainView mainView, DirectoryManagementModel directoryModel, FileManipulationModel fileModel) {
        this.mainView = mainView;
        this.directoryModel = directoryModel;
        this.fileModel = fileModel;

        // Initialize listeners for the components
        mainView.getSidebarPanel().getCategoryList().addListSelectionListener(new SideBarSelectionListener(mainView.getSidebarPanel(), this));
        mainView.getSidebarPanel().getjTreePanel().generateRoots(directoryModel.updateDirectory());
        mainView.getSidebarPanel().getjTreePanel().getFileTree().addTreeExpansionListener(new JTreeExpansionListener(mainView, directoryModel));
        mainView.getSidebarPanel().getjTreePanel().getFileTree().addMouseListener(new JTreeMouseListener(mainView, fileModel));
    }

    // Update the main panel with the contents of the selected directory
    public void updateMainPanel(File directory) {
        directoryModel.updateDirectory(directory.getAbsolutePath());
    }
}
