package fileExplorer.controller;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

/**
 * Главный контроллер приложения, который инициализирует все компоненты контроллеров,
 * необходимых для взаимодействия с интерфейсом и моделями.
 */
public class MainController {
    /**
     * Конструктор главного контроллера.
     * Инициализирует модели и контроллеры, связывая их с представлением.
     * @param mainView основное представление, через которое пользователь взаимодействует с приложением.
     */
    public MainController(MainView mainView) {
        DirectoryManagementModel directoryModel = new DirectoryManagementModel(mainView);
        FileManipulationModel fileModel = new FileManipulationModel(mainView);

        new FileManipulationController(mainView, directoryModel, fileModel);
        new DirectoryManagementController(mainView, directoryModel);
        new SidebarController(mainView, directoryModel, fileModel);
    }
}
