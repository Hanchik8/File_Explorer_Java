package fileExplorer.controller.commands;

import fileExplorer.model.DirectoryManagementModel;

public class RedoCommand implements Command {
    DirectoryManagementModel directoryModel;
    public RedoCommand(DirectoryManagementModel directoryModel) {
        this.directoryModel = directoryModel;
    }
    @Override
    public void execute() {
        directoryModel.redo();
    }
}
