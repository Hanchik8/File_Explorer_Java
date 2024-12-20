package fileExplorer.controller.listeners;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.model.enums.SortCriteria;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.io.File;

public class SortComboBoxListener implements PopupMenuListener {
    private DirectoryManagementModel directoryModel;

    public SortComboBoxListener(DirectoryManagementModel directoryModel) {
        this.directoryModel = directoryModel;
    }

    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        @SuppressWarnings("unchecked")
        JComboBox<String> source = (JComboBox<String>) e.getSource();
        String selectedCriteria = (String) source.getSelectedItem();

        SortCriteria criteria = SortCriteria.valueOf(selectedCriteria.toUpperCase());

        String currentPath = directoryModel.getCurrentDirectory();
        File[] files = directoryModel.listDirectoryContent(new File(currentPath));
        if (files != null) {
            File[] sortedFiles = directoryModel.updateAndSortFileList(files, criteria);
            directoryModel.updateView(sortedFiles, currentPath);
        }
    }

    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

    }

    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {

    }
}
