package fileExplorer.controller.commands;

import fileExplorer.controller.FileManipulationController;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

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
        fileModel.createFile(parentDirectory, newFileType);
        mainView.getToolbarPanel().getNewComboBox().setSelectedIndex(0); // Сбрасываем выбор
        fileController.updateView();
    }
}
