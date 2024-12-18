package fileExplorer.controller;

import fileExplorer.controller.commands.PathInputCommand;
import fileExplorer.controller.commands.MoveUpCommand;
import fileExplorer.controller.commands.UndoCommand;
import fileExplorer.controller.commands.RedoCommand;
import fileExplorer.controller.commands.RefreshCommand;
import fileExplorer.controller.commands.SearchCommand;
import fileExplorer.controller.commands.Command;

import fileExplorer.controller.listeners.ButtonClickListener;
import fileExplorer.controller.listeners.SortComboBoxListener;
import fileExplorer.controller.listeners.ViewActionListener;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.view.MainView;

import javax.swing.JButton;

public class DirectoryManagementController {
    private final MainView mainView;
    private final DirectoryManagementModel directoryModel;

    public DirectoryManagementController(MainView mainView,
            DirectoryManagementModel directoryModel) {
        this.mainView = mainView;
        this.directoryModel = directoryModel;

        initialize();
    }

    private void initialize() {
        // Обновление начального состояния
        directoryModel.updateDirectory();

        // Настройка обработчиков событий
        setupPathFieldListener();
        setupNavigationButtons();
        setupSearchField();
        setupSortListener();
    }

    private void setupPathFieldListener() {
        mainView.getTopMenu().getDirectoryField().addActionListener(
                new ViewActionListener(new PathInputCommand(directoryModel, mainView)));
    }

    private void setupSortListener() {
        mainView.getToolbarPanel().getSortComboBox().addPopupMenuListener(new SortComboBoxListener(directoryModel));
    }

    private void setupNavigationButtons() {
        registerButtonAction(mainView.getTopMenu().getUpperBtn(), new MoveUpCommand(directoryModel));
        registerButtonAction(mainView.getTopMenu().getBackBtn(), new UndoCommand(directoryModel));
        registerButtonAction(mainView.getTopMenu().getForwardBtn(), new RedoCommand(directoryModel));
        registerButtonAction(mainView.getTopMenu().getRefreshBtn(), new RefreshCommand(directoryModel));
    }

    private void setupSearchField() {
        mainView.getTopMenu().getSearchField().addActionListener(
                new ViewActionListener(new SearchCommand(mainView, directoryModel)));
    }

    private void registerButtonAction(JButton button, Command command) {
        button.addMouseListener(new ButtonClickListener(command));
    }
}
