package fileExplorer.controller;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

public class MainController {
    public MainController(MainView mainView) {
        DirectoryManagementModel directoryModel = new DirectoryManagementModel(mainView);
        FileManipulationModel fileModel = new FileManipulationModel(mainView);

        new FileManipulationController(mainView, directoryModel, fileModel);
        new DirectoryManagementController(mainView, directoryModel);
        new SidebarController(mainView, directoryModel, fileModel);
    }
}
