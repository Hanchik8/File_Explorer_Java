package fileExplorer.controller;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;

import javax.swing.JComboBox;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class FileManipulationController {
    private final MainView mainView;
    private final DirectoryManagementModel directoryModel;
    private final FileManipulationModel fileModel;

    public FileManipulationController(
            MainView mainView,
            DirectoryManagementModel directoryModel,
            FileManipulationModel fileModel) {
        this.mainView = mainView;
        this.directoryModel = directoryModel;
        this.fileModel = fileModel;

        setupFileActions();
    }

    private void setupFileActions() {
        mainView.getCenterPanel().getFileList().addMouseListener(new FileListMouseAdapter());
        mainView.getEditPanel().getNewComboBox().addActionListener(new NewFileComboBoxListener());
        mainView.getEditPanel().getCopyBtn().addMouseListener(new CopyButtonListener());
        mainView.getEditPanel().getPasteBtn().addMouseListener(new PasteButtonListener());
        mainView.getEditPanel().getCutBtn().addMouseListener(new CutButtonListener());
        mainView.getEditPanel().getDeleteBtn().addMouseListener(new DeleteButtonListener());
        mainView.getEditPanel().getDetailsCheckBox().addActionListener(new DetailCheckBoxListener());
        mainView.getEditPanel().getSortComboBox().addPopupMenuListener(new SortComboBoxListener());
    }

    private File getSelectedFile() {
        String selectedName = mainView.getCenterPanel().getFileList().getSelectedValue();
        String currentPath = mainView.getTopMenu().getCurrentPath();
        File selectedFile;
        if ("Root".equals(currentPath)) {
            selectedFile = new File(selectedName);
        } else {
            selectedFile = new File(currentPath, selectedName);
        }
        return selectedFile;
    }

    private void refreshDirectory() {
        directoryModel.updateDirectory();
        mainView.getTopMenu().getForwardBtn().setEnabled(false);
    }

    private class FileListMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            File selectedFile = getSelectedFile();
            mainView.updateBtnState(true);
            if (e.getClickCount() == 2) {
                if (selectedFile.isDirectory()) {
                    directoryModel.updateDirectory(selectedFile.getAbsolutePath());
                } else {
                    fileModel.openFile(selectedFile);
                }
            }
            mainView.updateFileDetails(selectedFile, fileModel.getFileExtension(selectedFile.getName()));
        }
    }

    private class NewFileComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String newFileType = (String) mainView.getEditPanel().getNewComboBox().getSelectedItem();
            File parentDirectory = new File(mainView.getTopMenu().getCurrentPath());
            fileModel.createFile(parentDirectory, newFileType);
            mainView.getEditPanel().getNewComboBox().setSelectedIndex(0); // Сбрасываем выбор
            refreshDirectory();
        }
    }

    private class SortComboBoxListener implements PopupMenuListener {
        @Override
        public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
            JComboBox<String> source = (JComboBox<String>) e.getSource();
            String selectedCriteria = (String) source.getSelectedItem();
            FileManipulationModel.SortCriteria criteria = FileManipulationModel.SortCriteria
                    .valueOf(selectedCriteria.toUpperCase());

            // Получаем текущую директорию из модели
            String currentPath = directoryModel.getCurrentDirectory();
            File[] files = new File(currentPath).listFiles();
            if (files != null) {
                // Сортируем и обновляем список
                File[] sortedFiles = fileModel.updateAndSortFileList(files, criteria);
                directoryModel.updateView(sortedFiles, currentPath); // Обновляем представление
            }
        }

        @Override
        public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        }

        @Override
        public void popupMenuCanceled(PopupMenuEvent e) {
        }
    }

    private class CopyButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            fileModel.copyFile(getSelectedFile());
        }
    }

    private class PasteButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            File targetDirectory = new File(mainView.getTopMenu().getCurrentPath());
            fileModel.pasteFile(targetDirectory);
            refreshDirectory();
        }
    }

    private class CutButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            fileModel.cutFile(getSelectedFile());
        }
    }

    private class DeleteButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            fileModel.deleteFile(getSelectedFile());
            refreshDirectory();
        }
    }

    private class DetailCheckBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isSelected = mainView.getEditPanel().getDetailsCheckBox().isSelected();
            mainView.getFileDetailsPanel().getFileDetailsPanel().setVisible(isSelected);
        }
    }
}
