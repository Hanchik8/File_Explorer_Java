package fileExplorer.controller;

import fileExplorer.controller.commands.Command;
import fileExplorer.controller.commands.CopyFileCommand;
import fileExplorer.controller.commands.RenameFileCommand;
import fileExplorer.controller.commands.PasteFileCommand;
import fileExplorer.controller.commands.CutFileCommand;
import fileExplorer.controller.commands.DeleteFileCommand;

import fileExplorer.controller.listeners.ViewActionListener;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.viewComponents.PopupToolbarPanel;

import javax.swing.JMenuItem;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedHashMap;

public class PopupToolbarController extends MouseAdapter {
    private final FileManipulationController fileController;
    private final FileManipulationModel fileModel;
    private final PopupToolbarPanel popupToolbarPanel;

    public PopupToolbarController(FileManipulationController fileController, PopupToolbarPanel popupToolbarPanel, FileManipulationModel fileModel) {
        this.fileController = fileController;
        this.popupToolbarPanel = popupToolbarPanel;
        this.fileModel = fileModel;

        setupToolbarActions();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popupToolbarPanel.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popupToolbarPanel.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private void setupToolbarActions() {
        LinkedHashMap<String, JMenuItem> toolbarMenuItems = popupToolbarPanel.getToolsMap();
        registerToolbarAction(toolbarMenuItems.get("copy"), new CopyFileCommand(fileModel, fileController));
        registerToolbarAction(toolbarMenuItems.get("rename"), new RenameFileCommand(fileModel, fileController));
        registerToolbarAction(toolbarMenuItems.get("paste"), new PasteFileCommand(fileModel, fileController));
        registerToolbarAction(toolbarMenuItems.get("cut"), new CutFileCommand(fileModel, fileController));
        registerToolbarAction(toolbarMenuItems.get("delete"), new DeleteFileCommand(fileModel, fileController));
    }

    private void registerToolbarAction(JMenuItem button, Command command) {
        button.addActionListener(new ViewActionListener(command));
    }
}