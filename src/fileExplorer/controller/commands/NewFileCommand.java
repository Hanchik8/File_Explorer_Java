package fileExplorer.controller.commands;

import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

import java.io.File;

public class NewFileCommand implements Command {
    private FileManipulationModel fileModel;
    private MainView mainView;
    public NewFileCommand(FileManipulationModel fileModel, MainView mainView) {
        this.fileModel = fileModel;
        this.mainView = mainView;
    }

    @Override
    public void execute() {
        String newFileType = (String) mainView.getEditPanel().getNewComboBox().getSelectedItem();
        File parentDirectory = new File(mainView.getTopMenu().getCurrentPath());
        fileModel.createFile(parentDirectory, newFileType);
        mainView.getEditPanel().getNewComboBox().setSelectedIndex(0); // Сбрасываем выбор
    }
}
