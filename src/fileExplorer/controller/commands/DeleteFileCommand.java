package fileExplorer.controller.commands;

import fileExplorer.controller.FileManipulationController;
import fileExplorer.model.FileManipulationModel;

/**
 * Реализация команды "Удалить файл".
 * Этот класс инкапсулирует запрос на удаление выбранного файла,
 * делая его выполнение независимым от контекста, из которого он был вызван.
 */
public class DeleteFileCommand implements Command {
    private final FileManipulationModel fileModel;
    private final FileManipulationController fileController;

    /**
     * Конструктор для создания команды удаления файла.
     * @param fileModel модель файловой системы, предоставляющая логику удаления файлов.
     * @param fileController контроллер, управляющий взаимодействием с пользователем.
     */
    public DeleteFileCommand(FileManipulationModel fileModel, FileManipulationController fileController) {
        this.fileModel = fileModel;
        this.fileController = fileController;
    }

    /**
     * Выполняет команду удаления выбранного файла.
     * Команда вызывает метод {@link FileManipulationModel#deleteFile} для удаления файла,
     * который был выбран в {@link FileManipulationController}.
     * После удаления обновляются представления: обновляется главный экран и дерево файлов.
     */
    @Override
    public void execute() {
        fileModel.deleteFile(fileController.getSelectedFile());
        fileController.updateView();
        fileController.updateJTree();
    }
}
