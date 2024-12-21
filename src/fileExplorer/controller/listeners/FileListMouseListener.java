package fileExplorer.controller.listeners;

import fileExplorer.controller.FileManipulationController;
import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;

public class FileListMouseListener extends MouseAdapter {
    private final FileManipulationController controller;
    private final DirectoryManagementModel directoryModel;
    private final FileManipulationModel fileModel;
    private final MainView mainView;

    public FileListMouseListener(FileManipulationController controller,
            DirectoryManagementModel directoryModel,
            FileManipulationModel fileModel,
            MainView mainView) {
        this.controller = controller;
        this.directoryModel = directoryModel;
        this.fileModel = fileModel;
        this.mainView = mainView;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        File selectedFile = controller.getSelectedFile();
        if (selectedFile == null)
            return;

        mainView.updateBtnState(true);

        if (e.getClickCount() == 2) {
            if (selectedFile.isDirectory()) {
                directoryModel.updateDirectory(selectedFile.getAbsolutePath());
            } else {
                fileModel.openFile(selectedFile);
            }
        }

        mainView.updateFileDetails(selectedFile, fileModel.getFileExtension(selectedFile));
    }
}
