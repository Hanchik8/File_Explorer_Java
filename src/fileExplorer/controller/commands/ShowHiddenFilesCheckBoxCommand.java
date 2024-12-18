package fileExplorer.controller.commands;

import fileExplorer.view.MainView;
import fileExplorer.model.DirectoryManagementModel;

public class ShowHiddenFilesCheckBoxCommand implements Command {
    private final MainView mainView;
    private final DirectoryManagementModel directoryModel;

    public ShowHiddenFilesCheckBoxCommand(MainView mainView, DirectoryManagementModel directoryModel) {
        this.mainView = mainView;
        this.directoryModel = directoryModel;
    }

    @Override
    public void execute() {
        boolean isSelected = mainView.getToolbarPanel().getShowHiddenFilesCheckBox().isSelected();
        directoryModel.updateShowHiddenFiles(isSelected);
    }
}
