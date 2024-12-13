package fileExplorer.controller.commands;

import fileExplorer.model.DirectoryManagementModel;

public class UndoCommand implements Command {
    DirectoryManagementModel directoryModel;
    public UndoCommand(DirectoryManagementModel directoryModel) {
        this.directoryModel = directoryModel;
    }
    @Override
    public void execute() {
        directoryModel.undo();
    }
}
