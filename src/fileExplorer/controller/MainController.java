package fileExplorer.controller;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

public class MainController {
    private final MainView mainView;

    public MainController(MainView mainView) {
        this.mainView = mainView;

        DirectoryManagementModel directoryModel = new DirectoryManagementModel(mainView);
        FileManipulationModel fileModel = new FileManipulationModel(mainView);

        // Инициализация дочерних контроллеров
        new FileManipulationController(mainView, directoryModel, fileModel);
        new DirectoryManagementController(mainView, directoryModel);
        new SidebarController(mainView.getSidebarPanel(), directoryModel);
    }
}
