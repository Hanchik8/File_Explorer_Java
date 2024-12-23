package fileExplorer.controller.commands;

import fileExplorer.controller.FileManipulationController;
import fileExplorer.model.FileManipulationModel;

/**
 * Реализация команды для вставки файла.
 * Этот класс используется для обработки команды вставки файла в файловом менеджере.
 */
public class PasteFileCommand implements Command {
    private final FileManipulationModel fileModel;
    private final FileManipulationController fileController;

    /**
     * Конструктор для создания команды вставки файла.
     * @param fileModel модель, отвечающая за операции с файлами.
     * @param fileController контроллер, который управляет текущей директорией и обновляет представление.
     */
    public PasteFileCommand(FileManipulationModel fileModel, FileManipulationController fileController) {
        this.fileModel = fileModel;
        this.fileController = fileController;
    }

    /**
     * Выполняет команду вставки файла.
     * Этот метод вызывает соответствующий метод в модели для вставки файла в текущую директорию.
     */
    @Override
    public void execute() {
        fileModel.pasteFile(fileController.getCurrentDirectory());
        fileController.updateView();
        fileController.updateJTree();
    }
}
