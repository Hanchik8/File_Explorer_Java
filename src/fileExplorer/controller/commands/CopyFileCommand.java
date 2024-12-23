package fileExplorer.controller.commands;

import fileExplorer.controller.FileManipulationController;
import fileExplorer.model.FileManipulationModel;

/**
 * Реализация команды "Копирование файла".
 * Этот класс инкапсулирует запрос на копирование выбранного файла,
 * делая его выполнение независимым от контекста, из которого он был вызван.
 */
public class CopyFileCommand implements Command {
    private final FileManipulationModel fileModel;
    private final FileManipulationController fileController;

    /**
     * Конструктор для создания команды копирования файла.
     * @param fileModel модель файловой системы, предоставляющая логику копирования файлов.
     * @param fileController контроллер, управляющий взаимодействием с пользователем.
     */
    public CopyFileCommand(FileManipulationModel fileModel, FileManipulationController fileController) {
        this.fileModel = fileModel;
        this.fileController = fileController;
    }

    /**
     * Выполняет команду копирования выбранного файла.
     * Команда вызывает метод {@link FileManipulationModel#copyFile} для копирования файла,
     * который был выбран в {@link FileManipulationController}.
     */
    @Override
    public void execute() {
        fileModel.copyFile(fileController.getSelectedFile());
    }
}
