package fileExplorer.controller.commands;

import fileExplorer.controller.FileManipulationController;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

import javax.swing.JOptionPane;
import java.io.File;

public class NewFileCommand implements Command {
    private FileManipulationModel fileModel;
    private FileManipulationController fileController;
    private MainView mainView;
    public NewFileCommand(FileManipulationModel fileModel, MainView mainView, FileManipulationController fileController) {
        this.fileModel = fileModel;
        this.mainView = mainView;
        this.fileController = fileController;
    }

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

        fileModel.createFile(parentDirectory, fileName, fileModel.getFileExtension(newFileType));
        mainView.getToolbarPanel().getNewComboBox().setSelectedIndex(0);
        fileController.updateView();
    }
}
