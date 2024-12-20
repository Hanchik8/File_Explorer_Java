package fileExplorer.controller.commands;

import fileExplorer.controller.FileManipulationController;
import fileExplorer.model.FileManipulationModel;

import javax.swing.JOptionPane;
import java.io.File;

public class RenameFileCommand implements Command{
    private final FileManipulationModel fileModel;
    private final FileManipulationController fileController;

    public RenameFileCommand(FileManipulationModel fileModel, FileManipulationController fileController) {
        this.fileModel = fileModel;
        this.fileController = fileController;
    }

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
