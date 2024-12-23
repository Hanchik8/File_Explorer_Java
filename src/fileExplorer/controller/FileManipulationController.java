package fileExplorer.controller;

import fileExplorer.controller.commands.Command;
import fileExplorer.controller.commands.NewFileCommand;
import fileExplorer.controller.commands.CopyFileCommand;
import fileExplorer.controller.commands.PasteFileCommand;
import fileExplorer.controller.commands.CutFileCommand;
import fileExplorer.controller.commands.DeleteFileCommand;
import fileExplorer.controller.commands.RenameFileCommand;
import fileExplorer.controller.commands.DetailCheckBoxCommand;

import fileExplorer.controller.listeners.FileListMouseListener;
import fileExplorer.controller.listeners.ViewActionListener;
import fileExplorer.controller.listeners.ButtonClickListener;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

import javax.swing.JButton;
import java.io.File;

/**
 * Контроллер, управляющий операциями с файлами (создание, копирование, удаление, переименование и т. д.).
 * Также управляет взаимодействием с файловым списком и панелями интерфейса.
 */
public class FileManipulationController {
    private final MainView mainView;
    private final DirectoryManagementModel directoryModel;
    private final FileManipulationModel fileModel;

    /**
     * Конструктор контроллера.
     * @param mainView основное представление, через которое пользователь взаимодействует с приложением.
     * @param directoryModel модель, которая управляет состоянием директорий.
     * @param fileModel модель, которая управляет операциями с файлами.
     */
    public FileManipulationController(
            MainView mainView,
            DirectoryManagementModel directoryModel,
            FileManipulationModel fileModel) {
        this.mainView = mainView;
        this.directoryModel = directoryModel;
        this.fileModel = fileModel;

        initialize();
    }

    /**
     * Инициализация контроллера: настройка всех обработчиков событий для работы с файлами.
     */
    private void initialize() {
        setupFileListListener();
        setupNewComboBoxListener();
        setupToolbarActions();
        setupDetailsCheckBox();
    }

    /**
     * Настройка обработчика кликов по элементам списка файлов.
     */
    private void setupFileListListener() {
        mainView.getCenterPanel().getFileList().addMouseListener(
                new FileListMouseListener(this, directoryModel, fileModel, mainView));

        mainView.getCenterPanel().getFileList().addMouseListener(
                new PopupToolbarController(this, mainView.getPopupToolbarPanel(), fileModel));
    }

    /**
     * Настройка слушателя для комбинированного списка "Новый файл".
     */
    private void setupNewComboBoxListener() {
        mainView.getToolbarPanel().getNewComboBox().addActionListener(
                new ViewActionListener(new NewFileCommand(fileModel, mainView, this)));
    }

    /**
     * Настройка действия для чекбокса с деталями (отображение/скрытие деталей).
     */
    private void setupDetailsCheckBox() {
        mainView.getToolbarPanel().getDetailsCheckBox().addActionListener(
                new ViewActionListener(new DetailCheckBoxCommand(mainView)));
    }

    /**
     * Настройка действий для кнопок панели инструментов (копирование, вставка, вырезание, удаление, переименование).
     */
    private void setupToolbarActions() {
        registerToolbarAction(mainView.getToolbarPanel().getCopyBtn(), new CopyFileCommand(fileModel, this));
        registerToolbarAction(mainView.getToolbarPanel().getPasteBtn(), new PasteFileCommand(fileModel, this));
        registerToolbarAction(mainView.getToolbarPanel().getCutBtn(), new CutFileCommand(fileModel, this));
        registerToolbarAction(mainView.getToolbarPanel().getDeleteBtn(), new DeleteFileCommand(fileModel, this));
        registerToolbarAction(mainView.getToolbarPanel().getRenameBtn(), new RenameFileCommand(fileModel, this));
    }

    /**
     * Регистрация действия для кнопки панели инструментов.
     * @param button кнопка панели инструментов.
     * @param command команда, которая будет выполнена при клике на кнопку.
     */
    private void registerToolbarAction(JButton button, Command command) {
        button.addMouseListener(new ButtonClickListener(command));
    }

    /**
     * Получение текущей директории.
     * @return файл, представляющий текущую директорию.
     */
    public File getCurrentDirectory() {
        String currentPath = mainView.getNavigationPanel().getCurrentPath();
        return (currentPath != null) ? new File(currentPath) : null;
    }

    /**
     * Получение выбранного файла из списка файлов.
     * @return файл, который выбран в списке файлов.
     */
    public File getSelectedFile() {
        String selectedName = mainView.getCenterPanel().getFileList().getSelectedValue();
        String currentPath = getCurrentDirectory().toString();

        if (selectedName == null) {
            return null;
        }

        return "Root".equals(currentPath) ? new File(selectedName) : new File(currentPath, selectedName);
    }

    /**
     * Обновление отображения директорий и панели сортировки.
     */
    public void updateView() {
        directoryModel.updateDirectory();
        mainView.getToolbarPanel().getSortComboBox().setSelectedItem("Name");
    }

    /**
     * Обновление дерева каталогов в боковой панели.
     */
    public void updateJTree() {
        File[] directoryContent = directoryModel.listDirectoryContent(getCurrentDirectory());
        mainView.getSidebarPanel().getjTreePanel().updateDirectoryNode(getCurrentDirectory(), directoryContent);
    }
}