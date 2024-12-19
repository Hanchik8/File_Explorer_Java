package fileExplorer.controller.commands;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.view.MainView;

import java.io.File;
import java.util.ArrayList;

public class SearchCommand implements Command {
    private final MainView mainView;
    private final DirectoryManagementModel directoryModel;

    public SearchCommand(MainView mainView, DirectoryManagementModel directoryModel) {
        this.mainView = mainView;
        this.directoryModel = directoryModel;
    }

    @Override
    public void execute() {
        String fileName = mainView.getNavigationPanel().getSearchField().getText();
        if (!fileName.equals("")) {
            String currentDirectory = mainView.getNavigationPanel().getDirectoryField().getText();
            ArrayList<File> searchedFiles = new ArrayList<>();
            directoryModel.searchFileByName(currentDirectory, fileName, searchedFiles);
        } else {
            directoryModel.updateDirectory();
        }
    }
}
