package fileExplorer.controller.commands;

import fileExplorer.controller.FileManipulationController;
import fileExplorer.model.FileManipulationModel;

public class DeleteFileCommand implements Command {
    private final FileManipulationModel fileModel;
    private final FileManipulationController fileController;

    public DeleteFileCommand(FileManipulationModel fileModel, FileManipulationController fileController) {
        this.fileModel = fileModel;
        this.fileController = fileController;
    }

    @Override
    public void execute() {
        fileModel.deleteFile(fileController.getSelectedFile());
        fileController.updateView();
    }
}
