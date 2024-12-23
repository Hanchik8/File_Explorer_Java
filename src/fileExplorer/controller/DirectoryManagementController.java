package fileExplorer.controller;

import fileExplorer.controller.commands.Command;
import fileExplorer.controller.commands.PathInputCommand;
import fileExplorer.controller.commands.MoveUpCommand;
import fileExplorer.controller.commands.UndoCommand;
import fileExplorer.controller.commands.RedoCommand;
import fileExplorer.controller.commands.RefreshCommand;
import fileExplorer.controller.commands.SearchCommand;

import fileExplorer.controller.listeners.ButtonClickListener;
import fileExplorer.controller.listeners.SearchFocusListener;
import fileExplorer.controller.listeners.SortComboBoxListener;
import fileExplorer.controller.listeners.ViewActionListener;
import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.view.MainView;

import javax.swing.JComponent;

/**
 * Контроллер, управляющий навигацией и действиями пользователя в панели управления каталогом.
 * Обрабатывает команды, связанные с навигацией по директориям, сортировкой и поиском файлов.
 */
public class DirectoryManagementController {
    private final MainView mainView;
    private final DirectoryManagementModel directoryModel;

    /**
     * Конструктор контроллера.
     * @param mainView основное представление, через которое пользователь взаимодействует с приложением.
     * @param directoryModel модель, которая управляет состоянием директорий.
     */
    public DirectoryManagementController(MainView mainView,
            DirectoryManagementModel directoryModel) {
        this.mainView = mainView;
        this.directoryModel = directoryModel;

        initialize();
    }

    /**
     * Инициализация контроллера: обновление директории и настройка обработчиков событий.
     */
    private void initialize() {
        directoryModel.updateDirectory();

        setupPathFieldListener();
        setupNavigationButtons();
        setupSearchField();
        setupSortListener();
    }

    /**
     * Настройка слушателя для поля ввода пути.
     */
    private void setupPathFieldListener() {
        mainView.getNavigationPanel().getDirectoryField().addActionListener(
                new ViewActionListener(new PathInputCommand(directoryModel, mainView)));
    }

    /**
     * Настройка слушателя для выпадающего списка сортировки.
     */
    private void setupSortListener() {
        mainView.getToolbarPanel().getSortComboBox().addPopupMenuListener(new SortComboBoxListener(directoryModel));
    }

    /**
     * Настройка кнопок навигации (вверх, назад, вперед, обновить).
     */
    private void setupNavigationButtons() {
        registerButtonAction(mainView.getNavigationPanel().getUpperBtn(), new MoveUpCommand(directoryModel));
        registerButtonAction(mainView.getNavigationPanel().getBackBtn(), new UndoCommand(directoryModel));
        registerButtonAction(mainView.getNavigationPanel().getForwardBtn(), new RedoCommand(directoryModel));
        registerButtonAction(mainView.getNavigationPanel().getRefreshBtn(), new RefreshCommand(directoryModel));
    }

    /**
     * Настройка поля поиска.
     */
    private void setupSearchField() {
        mainView.getNavigationPanel().getSearchField().addFocusListener(new SearchFocusListener());
        mainView.getNavigationPanel().getSearchField().addActionListener(new ViewActionListener(new SearchCommand(mainView, directoryModel)));
    }

    /**
     * Регистрация действия для кнопки.
     * @param button кнопка, к которой привязывается действие.
     * @param command команда, которая будет выполняться при нажатии на кнопку.
     */
    private void registerButtonAction(JComponent button, Command command) {
        button.addMouseListener(new ButtonClickListener(command));
    }
}
