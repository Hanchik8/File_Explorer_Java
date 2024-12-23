package fileExplorer.controller.commands;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.view.MainView;

import java.io.File;

/**
 * Реализация команды для обработки ввода пути в файловом менеджере.
 * Этот класс используется для изменения текущей директории на основе пути, введенного пользователем.
 */
public class PathInputCommand implements Command {
    private final DirectoryManagementModel directoryModel;
    private final MainView mainView;

    /**
     * Конструктор для создания команды ввода пути.
     * @param directoryModel модель, отвечающая за управление текущей директорией.
     * @param mainView представление, которое обновляет отображение в файловом менеджере.
     */
    public PathInputCommand(DirectoryManagementModel directoryModel, MainView mainView) {
        this.directoryModel = directoryModel;
        this.mainView = mainView;
    }

    /**
     * Выполняет команду изменения текущей директории на основе введенного пути.
     * Проверяет, является ли введенный путь директорией, и если да, обновляет текущую директорию.
     */
    @Override
    public void execute() {
        File selectedFile = new File(mainView.getNavigationPanel().getDirectoryField().getText());
        if (selectedFile.isDirectory()) {
            directoryModel.updateDirectory(selectedFile.getAbsolutePath());
            mainView.updateBtnState(false);
        }
    }
}
