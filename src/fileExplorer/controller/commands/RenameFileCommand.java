package fileExplorer.controller.commands;

import fileExplorer.controller.FileManipulationController;
import fileExplorer.model.FileManipulationModel;

import javax.swing.JOptionPane;
import java.io.File;

/**
 * Реализация команды для переименования файла.
 * Эта команда используется для изменения имени выбранного файла.
 */
public class RenameFileCommand implements Command{
    private final FileManipulationModel fileModel;
    private final FileManipulationController fileController;

    /**
     * Конструктор для создания команды "Переименовать файл" (Rename).
     * @param fileModel модель, отвечающая за управление операциями с файлами.
     * @param fileController контроллер, отвечающий за выбор и обработку файлов.
     */
    public RenameFileCommand(FileManipulationModel fileModel, FileManipulationController fileController) {
        this.fileModel = fileModel;
        this.fileController = fileController;
    }

    /**
     * Выполняет команду "Переименовать файл", запрашивая новое имя файла у пользователя и
     * переименовывая выбранный файл.
     */
    @Override
    public void execute() {
        File originFile = fileController.getSelectedFile();
        String newName = (String) JOptionPane.showInputDialog(
                null,
                "Enter the new file name:",
                "Rename File",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                fileModel.getFileNameWithoutExtension(originFile.getName())
        );
        fileModel.renameFile(originFile, newName);
        fileController.updateView();
        fileController.updateJTree();
    }
}
