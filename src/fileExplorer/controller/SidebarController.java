package fileExplorer.controller;

import fileExplorer.controller.listeners.JTreeExpansionListener;
import fileExplorer.controller.listeners.JTreeSelectionListener;
import fileExplorer.controller.listeners.SideBarSelectionListener;
import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

import java.io.File;

/**
 * Контроллер для работы с боковой панелью (Sidebar) в файловом менеджере.
 * Управляет взаимодействием с элементами на боковой панели, такими как дерево каталогов и список категорий.
 */
public class SidebarController {
    private DirectoryManagementModel directoryModel;

    /**
     * Конструктор класса.
     * Инициализирует контроллер и связывает действия с компонентами боковой панели.
     * @param mainView Основное окно приложения.
     * @param directoryModel Модель для управления директорией.
     * @param fileModel Модель для манипуляций с файлами.
     */
    public SidebarController(MainView mainView, DirectoryManagementModel directoryModel, FileManipulationModel fileModel) {
        this.directoryModel = directoryModel;

        mainView.getSidebarPanel().getCategoryList().addListSelectionListener(new SideBarSelectionListener(mainView.getSidebarPanel(), this));
        mainView.getSidebarPanel().getjTreePanel().generateRoots(directoryModel.updateDirectory());
        mainView.getSidebarPanel().getjTreePanel().getFileTree().addTreeExpansionListener(new JTreeExpansionListener(mainView, directoryModel));
        mainView.getSidebarPanel().getjTreePanel().getFileTree().addTreeSelectionListener(new JTreeSelectionListener(mainView, fileModel, directoryModel));
    }

    /**
     * Обновляет основную панель на основе выбранной директории.
     * Вызывается при изменении пути на боковой панели.
     * @param directory Директория для обновления.
     */
    public void updateMainPanel(File directory) {
        directoryModel.updateDirectory(directory.getAbsolutePath());
    }

    /**
     * Обновляет основную панель на основе строки с путем к директории.
     * Используется, если путь передается как строка.
     * @param directory Путь к директории в виде строки.
     */
    public void updateMainPanel(String directory) {directoryModel.updateDirectory(directory);}
}
