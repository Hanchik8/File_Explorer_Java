package fileExplorer.controller;

import fileExplorer.controller.listeners.JTreeExpansionListener;
import fileExplorer.controller.listeners.JTreeSelectionListener;
import fileExplorer.controller.listeners.SideBarSelectionListener;
import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

import java.io.File;

public class SidebarController {
    private DirectoryManagementModel directoryModel;

    public SidebarController(MainView mainView, DirectoryManagementModel directoryModel, FileManipulationModel fileModel) {
        this.directoryModel = directoryModel;

        mainView.getSidebarPanel().getCategoryList().addListSelectionListener(new SideBarSelectionListener(mainView.getSidebarPanel(), this));
        mainView.getSidebarPanel().getjTreePanel().generateRoots(directoryModel.updateDirectory());
        mainView.getSidebarPanel().getjTreePanel().getFileTree().addTreeExpansionListener(new JTreeExpansionListener(mainView, directoryModel));
        mainView.getSidebarPanel().getjTreePanel().getFileTree().addTreeSelectionListener(new JTreeSelectionListener(mainView, fileModel, directoryModel));
    }

    public void updateMainPanel(File directory) {
        directoryModel.updateDirectory(directory.getAbsolutePath());
    }
    public void updateMainPanel(String directory) {directoryModel.updateDirectory(directory);}
}
