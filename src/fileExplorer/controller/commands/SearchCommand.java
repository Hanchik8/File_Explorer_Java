package fileExplorer.controller.commands;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.view.MainView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.ArrayList;

public class SearchCommand implements FocusListener, Command {
    private final String placeholderText = "Search...";
    private final MainView mainView;
    private final DirectoryManagementModel directoryModel;

    public SearchCommand(MainView mainView, DirectoryManagementModel directoryModel) {
        this.mainView = mainView;
        this.directoryModel = directoryModel;
    }

    @Override
    public void focusGained(FocusEvent e) {
        JTextField field = (JTextField) e.getSource();
        if (field.getText().equals(placeholderText)) {
            field.setText("");
            field.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e) {
        JTextField field = (JTextField) e.getSource();
        if (field.getText().isEmpty()) {
            field.setText(placeholderText);
            field.setForeground(Color.GRAY);
        }
    }

    @Override
    public void execute() {
        String currentDirectory = mainView.getTopMenu().getDirectoryField().getText();
        ArrayList<File> searchedFiles = new ArrayList<>();
        String fileName = mainView.getTopMenu().getSearchField().getText();
        directoryModel.searchFileByName(currentDirectory, fileName, searchedFiles);
    }
}
