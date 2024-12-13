package fileExplorer.controller.commands;

import fileExplorer.model.DirectoryManagementModel;

public class RefreshCommand implements Command {
    private final DirectoryManagementModel directoryModel;

    public RefreshCommand(DirectoryManagementModel directoryModel) {
        this.directoryModel = directoryModel;
    }

    @Override
    public void execute() {
        directoryModel.updateDirectory();
    }
}
