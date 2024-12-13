package fileExplorer.controller.commands;

import fileExplorer.model.DirectoryManagementModel;

public class MoveUpCommand implements Command {
    DirectoryManagementModel directoryModel;
    public MoveUpCommand(DirectoryManagementModel directoryModel) {
        this.directoryModel = directoryModel;
    }
    @Override
    public void execute() {
        directoryModel.moveToParentDirectory();
    }
}
