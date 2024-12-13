package fileExplorer.controller.commands;

import fileExplorer.controller.FileManipulationController;
import fileExplorer.model.FileManipulationModel;

public class CopyFileCommand implements Command {
    private final FileManipulationModel fileModel;
    private final FileManipulationController fileController;

    public CopyFileCommand(FileManipulationModel fileModel, FileManipulationController fileController) {
        this.fileModel = fileModel;
        this.fileController = fileController;
    }

    @Override
    public void execute() {
        fileModel.copyFile(fileController.getSelectedFile());
    }
}
