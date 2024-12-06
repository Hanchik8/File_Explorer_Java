package explorer.controller;

import explorer.model.DirectoryManagement;
import explorer.model.FileManipulation;
import explorer.view.ExplorerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class FileController {
    private ExplorerView explorerView;
    private DirectoryManagement directoryManagement;
    private FileManipulation fileManipulation;

    public FileController(ExplorerView explorerView, DirectoryManagement directoryManagement, FileManipulation fileManipulation) {
        this.explorerView = explorerView;
        this.directoryManagement = directoryManagement;
        this.fileManipulation = fileManipulation;

        // Устанавливаем обработчики событий для панели с файлами и кнопок
        setupFileActions();
    }

    // Установка обработчиков для файловых действий
    private void setupFileActions() {
        explorerView.getMainPanel().getFileList().addMouseListener(new FileListMouseAdapter());
        explorerView.getEditPanel().getNewComboBox().addActionListener(new NewFileComboBoxListener());
        explorerView.getEditPanel().getCopyBtn().addMouseListener(new CopyButtonListener());
        explorerView.getEditPanel().getPasteBtn().addMouseListener(new PasteButtonListener());
        explorerView.getEditPanel().getCutBtn().addMouseListener(new CutButtonListener());
        explorerView.getEditPanel().getDeleteBtn().addMouseListener(new DeleteButtonListener());
        explorerView.getEditPanel().getDetailsCheckBox().addMouseListener(new DetailCheckBoxListener());
    }

    // Обработчик для двойного клика по файлу
    private class FileListMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            String selectedName = explorerView.getMainPanel().getFileList().getSelectedValue();
            String currentPath = explorerView.getTopMenu().getCurrentPath();

            File selectedFile;
            if ("Root".equals(currentPath)) {
                selectedFile = new File(selectedName);
            } else {
                selectedFile = new File(currentPath, selectedName);
            }

            if (e.getClickCount() == 2) {
                if (selectedFile.isDirectory()) {
                    directoryManagement.updateDirectory(selectedFile.getAbsolutePath());
                } else if (selectedFile.isFile()){
                    fileManipulation.openFile(selectedFile);
                }
            }

            fileManipulation.updateFileDetails(selectedFile);
            explorerView.getTopMenu().getForwardBtn().setEnabled(false);
        }
    }

    // Обработчик для выпадающего списка для создания нового файла
    private class NewFileComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String newFileType = String.valueOf(explorerView.getEditPanel().getNewComboBox().getSelectedItem());
            File parentDirectory = new File(explorerView.getTopMenu().getCurrentPath());
            fileManipulation.createFile(parentDirectory, newFileType);
            explorerView.getEditPanel().getNewComboBox().setSelectedItem("Create");
            directoryManagement.updateDirectory();
            fileManipulation.updateBtnState(false);
        }
    }

    // Обработчик для копирования файла
    private class CopyButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            String selectedName = explorerView.getMainPanel().getFileList().getSelectedValue();
            File currentPath = new File(explorerView.getTopMenu().getCurrentPath());
            File selectedFile = new File(currentPath, selectedName);
            fileManipulation.copyFile(selectedFile);
        }
    }

    // Обработчик для вставки файла
    private class PasteButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            File currentDirectory = new File(explorerView.getTopMenu().getCurrentPath());
            fileManipulation.pasteFile(currentDirectory);
            directoryManagement.updateDirectory();
        }
    }

    // Обработчик для вырезания файла
    private class CutButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            String selectedName = explorerView.getMainPanel().getFileList().getSelectedValue();
            File currentPath = new File(explorerView.getTopMenu().getCurrentPath());
            File selectedFile = new File(currentPath, selectedName);
            fileManipulation.cutFile(selectedFile);
        }
    }

    // Обработчик для удаления файла
    private class DeleteButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            String selectedName = explorerView.getMainPanel().getFileList().getSelectedValue();
            String currentPath = explorerView.getTopMenu().getCurrentPath();
            File selectedFile = new File(currentPath, selectedName);
            fileManipulation.deleteFile(selectedFile);
            directoryManagement.updateDirectory();
        }
    }

    private class DetailCheckBoxListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (!explorerView.getEditPanel().getDetailsCheckBox().isSelected())
                explorerView.getFileDetailsPanel().getFileDetailsPanel().setVisible(false);
            else
                explorerView.getFileDetailsPanel().getFileDetailsPanel().setVisible(true);
        }
    }
}
