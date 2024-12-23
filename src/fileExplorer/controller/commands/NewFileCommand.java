package fileExplorer.controller.commands;

import fileExplorer.controller.FileManipulationController;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

import javax.swing.JOptionPane;
import java.io.File;

/**
 * Реализация команды для создания нового файла.
 * Этот класс используется для обработки команды создания нового файла в файловом менеджере.
 */
public class NewFileCommand implements Command {
    private FileManipulationModel fileModel;
    private FileManipulationController fileController;
    private MainView mainView;

    /**
     * Конструктор для создания команды создания нового файла.
     * @param fileModel модель, отвечающая за операции с файлами.
     * @param mainView  основной вид, содержащий элементы управления для создания файла.
     * @param fileController контроллер, который обновляет представление и дерево файлов.
     */
    public NewFileCommand(FileManipulationModel fileModel, MainView mainView, FileManipulationController fileController) {
        this.fileModel = fileModel;
        this.mainView = mainView;
        this.fileController = fileController;
    }

    /**
     * Выполняет команду создания нового файла.
     * Пользователь выбирает тип создаваемого файла и вводит имя файла.
     * После этого файл создается в текущей директории.
     */
    @Override
    public void execute() {
        String newFileType = (String) mainView.getToolbarPanel().getNewComboBox().getSelectedItem();
        File parentDirectory = new File(mainView.getNavigationPanel().getCurrentPath());

        String fileName = (String) JOptionPane.showInputDialog(
                null,
                "Enter the file name:",
                "Create File",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                fileModel.getFileNameWithoutExtension(newFileType)
        );

        fileModel.createFile(parentDirectory, fileName, fileModel.getFileExtension(new File(newFileType)));
        mainView.getToolbarPanel().getNewComboBox().setSelectedIndex(0);
        fileController.updateView();
        fileController.updateJTree();
    }
}
