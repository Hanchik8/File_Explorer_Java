package src.controller;

// import src.model.DirectoryManagement;
import src.view.View;

import src.model.FileManipulationModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

// УБРАТЬ ВСЕ КОММЕНТАРИИ В МЕТОДАХ ПОСЛЕ СОЗДАНИЯ View и DirectoryManagement

public class FileManipulationController {
    // private final View View;
    // private final DirectoryManagement directoryManagement;
    private final FileManipulationModel fileManipulation;

    public FileManipulationController(
            // View View,
            // DirectoryManagement directoryManagement,
            FileManipulationModel fileManipulation) {
        // this.View = View;
        // this.directoryManagement = directoryManagement;
        this.fileManipulation = fileManipulation;

        // setupFileActions();
    }

  //private void setupFileActions() {
    //  View.getMainPanel().getFileList().addMouseListener(new FileListMouseAdapter());
    //  View.getEditPanel().getNewComboBox().addActionListener(new NewFileComboBoxListener());
    //  View.getEditPanel().getCopyBtn().addMouseListener(new CopyButtonListener());
    //  View.getEditPanel().getPasteBtn().addMouseListener(new PasteButtonListener());
    //  View.getEditPanel().getCutBtn().addMouseListener(new CutButtonListener());
    //  View.getEditPanel().getDeleteBtn().addMouseListener(new DeleteButtonListener());
    //  View.getEditPanel().getDetailsCheckBox().addActionListener(new DetailCheckBoxListener());
  //}

    private File getSelectedFile() {
        String selectedName = View.getMainPanel().getFileList().getSelectedValue();
        String currentPath = View.getTopMenu().getCurrentPath();
        return new File(currentPath, selectedName);
    }

    private void refreshDirectory() {
        directoryManagement.updateDirectory();
        View.getTopMenu().getForwardBtn().setEnabled(false);
    }

    private class FileListMouseAdapter extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                File selectedFile = getSelectedFile();
                if (selectedFile.isDirectory()) {
                    directoryManagement.updateDirectory(selectedFile.getAbsolutePath());
                } else {
                    fileManipulation.openFile(selectedFile);
                }
            }
            fileManipulation.updateFileDetails(getSelectedFile());
        }
    }

    private class NewFileComboBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String newFileType = (String) View.getEditPanel().getNewComboBox().getSelectedItem();
            File parentDirectory = new File(View.getTopMenu().getCurrentPath());
            // Ниже вызов метода которого не сущетвует, не забудь ипользовать те же данные
            // и название для создания его в будущем
            fileManipulation.createFile(parentDirectory, newFileType);
            View.getEditPanel().getNewComboBox().setSelectedIndex(0); // Сбрасываем выбор
            refreshDirectory();
        }
    }

    private class CopyButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            fileManipulation.copyFile(getSelectedFile());
        }
    }

    private class PasteButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            File targetDirectory = new File(View.getTopMenu().getCurrentPath());
            fileManipulation.pasteFile(targetDirectory);
            refreshDirectory();
        }
    }

    private class CutButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            fileManipulation.cutFile(getSelectedFile());
        }
    }

    private class DeleteButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            fileManipulation.deleteFile(getSelectedFile());
            refreshDirectory();
        }
    }

    private class DetailCheckBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean isSelected = View.getEditPanel().getDetailsCheckBox().isSelected();
            View.getFileDetailsPanel().getFileDetailsPanel().setVisible(isSelected);
        }
    }
}
