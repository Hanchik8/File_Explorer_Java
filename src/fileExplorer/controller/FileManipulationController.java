package fileExplorer.controller;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.view.MainView;

import fileExplorer.model.FileManipulationModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

// УБРАТЬ ВСЕ КОММЕНТАРИИ В МЕТОДАХ ПОСЛЕ СОЗДАНИЯ View и DirectoryManagement

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
