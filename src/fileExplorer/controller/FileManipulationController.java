package fileExplorer.controller;

import fileExplorer.controller.commands.Command;
import fileExplorer.controller.commands.CopyFileCommand;
import fileExplorer.controller.commands.PasteFileCommand;
import fileExplorer.controller.commands.CutFileCommand;
import fileExplorer.controller.commands.DeleteFileCommand;
import fileExplorer.controller.commands.DetailCheckBoxCommand;

import fileExplorer.controller.listeners.FileListMouseListener;
import fileExplorer.controller.listeners.ViewActionListener;
import fileExplorer.controller.listeners.ButtonClickListener;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

import java.io.File;
import javax.swing.JButton;

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

        initialize();
    }

    // Инициализация обработчиков
    private void initialize() {
        setupFileListListener();
        setupToolbarActions();
        setupDetailsCheckBox();
    }

    private void setupFileListListener() {
        mainView.getCenterPanel().getFileList().addMouseListener(
                new FileListMouseListener(this, directoryModel, fileModel, mainView)
        );
    }

    private void setupToolbarActions() {
        registerToolbarAction(mainView.getEditPanel().getCopyBtn(), new CopyFileCommand(fileModel, this));
        registerToolbarAction(mainView.getEditPanel().getPasteBtn(), new PasteFileCommand(fileModel, this));
        registerToolbarAction(mainView.getEditPanel().getCutBtn(), new CutFileCommand(fileModel, this));
        registerToolbarAction(mainView.getEditPanel().getDeleteBtn(), new DeleteFileCommand(fileModel, this));
    }

    private void setupDetailsCheckBox() {
        mainView.getEditPanel().getDetailsCheckBox().addActionListener(
                new ViewActionListener(new DetailCheckBoxCommand(mainView))
        );
    }

    private void registerToolbarAction(JButton button, Command command) {
        button.addMouseListener(new ButtonClickListener(command));
    }

    public File getCurrentDirectory() {
        String currentPath = mainView.getTopMenu().getCurrentPath();
        return (currentPath != null) ? new File(currentPath) : null;
    }

    public File getSelectedFile() {
        String selectedName = mainView.getCenterPanel().getFileList().getSelectedValue();
        String currentPath = getCurrentDirectory().toString();

        if (selectedName == null) {
            return null;
        }

        return "Root".equals(currentPath) ? new File(selectedName) : new File(currentPath, selectedName);
    }

    public void updateView() {
        directoryModel.updateDirectory();
    }
}