package fileExplorer.controller.commands;

import fileExplorer.controller.FileManipulationController;
import fileExplorer.model.FileManipulationModel;

public class PasteFileCommand implements Command {
    private final FileManipulationModel fileModel;
    private final FileManipulationController fileController;

    public PasteFileCommand(FileManipulationModel fileModel, FileManipulationController fileController) {
        this.fileModel = fileModel;
        this.fileController = fileController;
    }

    @Override
    public void execute() {
        fileModel.pasteFile(fileController.getCurrentDirectory());
        fileController.updateView();
    }
}
