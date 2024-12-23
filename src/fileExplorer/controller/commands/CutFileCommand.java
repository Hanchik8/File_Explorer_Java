package fileExplorer.controller.commands;

import fileExplorer.controller.FileManipulationController;
import fileExplorer.model.FileManipulationModel;

/**
 * Реализация команды "Вырезать файл".
 * Этот класс инкапсулирует запрос на вырезание (перемещение) выбранного файла,
 * делая его выполнение независимым от контекста, из которого он был вызван.
 */
public class CutFileCommand implements Command {
    private final FileManipulationModel fileModel;
    private final FileManipulationController fileController;

    /**
     * Конструктор для создания команды вырезания файла.
     * @param fileModel модель файловой системы, предоставляющая логику перемещения файлов.
     * @param fileController контроллер, управляющий взаимодействием с пользователем.
     */
    public CutFileCommand(FileManipulationModel fileModel, FileManipulationController fileController) {
        this.fileModel = fileModel;
        this.fileController = fileController;
    }

    /**
     * Выполняет команду вырезания выбранного файла.
     * Команда вызывает метод {@link FileManipulationModel#cutFile} для вырезания (перемещения) файла,
     * который был выбран в {@link FileManipulationController}.
     */
    @Override
    public void execute() {
        fileModel.cutFile(fileController.getSelectedFile());
    }
}
