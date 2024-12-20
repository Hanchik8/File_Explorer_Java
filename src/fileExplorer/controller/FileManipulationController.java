package fileExplorer.controller;

import fileExplorer.controller.commands.*;

import fileExplorer.controller.listeners.FileListMouseListener;
import fileExplorer.controller.listeners.ViewActionListener;
import fileExplorer.controller.listeners.ButtonClickListener;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

import javax.swing.JButton;
import java.io.File;

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

    private void initialize() {
        setupFileListListener();
        setupNewComboBoxListener();
        setupToolbarActions();
        setupDetailsCheckBox();
        setupShowHiddenFilesCheckBox();
    }

    private void setupFileListListener() {
        mainView.getCenterPanel().getFileList().addMouseListener(
                new FileListMouseListener(this, directoryModel, fileModel, mainView));

        mainView.getCenterPanel().getFileList().addMouseListener(
                new PopupToolbarController(this, mainView.getPopupToolbarPanel(), fileModel));
    }

    private void setupNewComboBoxListener() {
        mainView.getToolbarPanel().getNewComboBox().addActionListener(
                new ViewActionListener(new NewFileCommand(fileModel, mainView, this)));
    }

    private void setupToolbarActions() {
        registerToolbarAction(mainView.getToolbarPanel().getCopyBtn(), new CopyFileCommand(fileModel, this));
        registerToolbarAction(mainView.getToolbarPanel().getPasteBtn(), new PasteFileCommand(fileModel, this));
        registerToolbarAction(mainView.getToolbarPanel().getCutBtn(), new CutFileCommand(fileModel, this));
        registerToolbarAction(mainView.getToolbarPanel().getDeleteBtn(), new DeleteFileCommand(fileModel, this));
        registerToolbarAction(mainView.getToolbarPanel().getRenameBtn(), new RenameFileCommand(fileModel, this));
    }

    private void setupDetailsCheckBox() {
        mainView.getToolbarPanel().getDetailsCheckBox().addActionListener(
                new ViewActionListener(new DetailCheckBoxCommand(mainView)));
    }

    private void setupShowHiddenFilesCheckBox() {
        mainView.getToolbarPanel().getShowHiddenFilesCheckBox().addActionListener(
                new ViewActionListener(new ShowHiddenFilesCommand(mainView, directoryModel)));
    }

    private void registerToolbarAction(JButton button, Command command) {
        button.addMouseListener(new ButtonClickListener(command));
    }

    public File getCurrentDirectory() {
        String currentPath = mainView.getNavigationPanel().getCurrentPath();
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
        mainView.getToolbarPanel().getSortComboBox().setSelectedItem("Name");
    }
}