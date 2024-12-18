package fileExplorer.controller;

import fileExplorer.controller.commands.Command;
import fileExplorer.controller.commands.CopyFileCommand;
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
            popupToolbarPanel.getPopup().show(e.getComponent(), e.getX(), e.getY());
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popupToolbarPanel.updateButtonState(fileController.getSelectedFile());
            popupToolbarPanel.getPopup().show(e.getComponent(), e.getX(), e.getY());
        }
    }

    private void setupToolbarActions() {
        LinkedHashMap<String, JMenuItem> tollbatMenuItems = popupToolbarPanel.getToolsMap();
        registerToolbarAction(tollbatMenuItems.get("Copy"), new CopyFileCommand(fileModel, fileController));
        registerToolbarAction(tollbatMenuItems.get("Paste"), new PasteFileCommand(fileModel, fileController));
        registerToolbarAction(tollbatMenuItems.get("Cut"), new CutFileCommand(fileModel, fileController));
        registerToolbarAction(tollbatMenuItems.get("Delete"), new DeleteFileCommand(fileModel, fileController));
    }

    private void registerToolbarAction(JMenuItem button, Command command) {
        button.addActionListener(new ViewActionListener(command));
    }
}