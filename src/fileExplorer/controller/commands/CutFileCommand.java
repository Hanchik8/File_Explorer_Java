package fileExplorer.controller.commands;

import fileExplorer.controller.FileManipulationController;
import fileExplorer.model.FileManipulationModel;

public class CutFileCommand implements Command {
    private final FileManipulationModel fileModel;
    private final FileManipulationController fileController;

    public CutFileCommand(FileManipulationModel fileModel, FileManipulationController fileController) {
        this.fileModel = fileModel;
        this.fileController = fileController;
    }

    @Override
    public void execute() {
        fileModel.cutFile(fileController.getSelectedFile());
    }
}
