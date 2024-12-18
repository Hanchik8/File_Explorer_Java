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
        String currentDirectory = mainView.getTopMenu().getDirectoryField().getText();
        ArrayList<File> searchedFiles = new ArrayList<>();
        String fileName = mainView.getTopMenu().getSearchField().getText();
        directoryModel.searchFileByName(currentDirectory, fileName, searchedFiles);
    }
}
