package fileExplorer.controller.commands;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.view.MainView;

import java.io.File;

public class PathInputCommand implements Command {
    private final DirectoryManagementModel directoryModel;
    private final MainView mainView;

    public PathInputCommand(DirectoryManagementModel directoryModel, MainView mainView) {
        this.directoryModel = directoryModel;
        this.mainView = mainView;
    }
    @Override
    public void execute() {
        File selectedFile = new File(mainView.getTopMenu().getDirectoryField().getText());
        if (selectedFile.isDirectory()) {
            directoryModel.updateDirectory(selectedFile.getAbsolutePath());
            mainView.updateBtnState(false);
        }
    }
}
