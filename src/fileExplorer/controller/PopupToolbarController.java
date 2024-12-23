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

/**
 * Контроллер для обработки действий в контекстном меню панели инструментов.
 * Управляет отображением и выполнением операций с файлами через контекстное меню.
 */
public class PopupToolbarController extends MouseAdapter {
    private final FileManipulationController fileController;
    private final FileManipulationModel fileModel;
    private final PopupToolbarPanel popupToolbarPanel;

    /**
     * Конструктор класса.
     * Инициализирует контроллер, устанавливает панель инструментов и модель работы с файлами.
     * @param fileController  Контроллер для работы с файлами.
     * @param popupToolbarPanel  Панель с контекстным меню.
     * @param fileModel Модель для манипуляций с файлами.
     */
    public PopupToolbarController(FileManipulationController fileController, PopupToolbarPanel popupToolbarPanel, FileManipulationModel fileModel) {
        this.fileController = fileController;
        this.popupToolbarPanel = popupToolbarPanel;
        this.fileModel = fileModel;

        setupToolbarActions();
    }

    /**
     * Обработчик события при нажатии кнопки мыши.
     * Отображает контекстное меню, если это правый клик.
     * @param e Событие нажатия кнопки мыши.
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popupToolbarPanel.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    /**
     * Обработчик события при отпускании кнопки мыши.
     * Отображает контекстное меню, если это правый клик.
     * @param e Событие отпускания кнопки мыши.
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popupToolbarPanel.show(e.getComponent(), e.getX(), e.getY());
        }
    }

    /**
     * Настройка действий для каждого элемента контекстного меню.
     * Регистрируются команды, выполняющиеся при выборе действий в контекстном меню.
     */
    private void setupToolbarActions() {
        LinkedHashMap<String, JMenuItem> toolbarMenuItems = popupToolbarPanel.getToolsMap();
        registerToolbarAction(toolbarMenuItems.get("copy"), new CopyFileCommand(fileModel, fileController));
        registerToolbarAction(toolbarMenuItems.get("rename"), new RenameFileCommand(fileModel, fileController));
        registerToolbarAction(toolbarMenuItems.get("paste"), new PasteFileCommand(fileModel, fileController));
        registerToolbarAction(toolbarMenuItems.get("cut"), new CutFileCommand(fileModel, fileController));
        registerToolbarAction(toolbarMenuItems.get("delete"), new DeleteFileCommand(fileModel, fileController));
    }

    /**
     * Регистрирует действие для кнопки в контекстном меню.
     * Привязывает команду к кнопке и добавляет слушателя для выполнения команды.
     * @param item Кнопка меню.
     * @param command Команда, которая будет выполнена при выборе кнопки.
     */
    private void registerToolbarAction(JMenuItem item, Command command) {
        item.addActionListener(new ViewActionListener(command));
    }
}