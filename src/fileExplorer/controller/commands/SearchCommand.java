package fileExplorer.controller.commands;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.view.MainView;

import java.io.File;
import java.util.ArrayList;

/**
 * Команда для поиска файла в текущей директории по имени.
 * Эта команда выполняет поиск файла по введенному имени в поисковом поле.
 */
public class SearchCommand implements Command {
    private final MainView mainView;
    private final DirectoryManagementModel directoryModel;

    /**
     * Конструктор для создания команды поиска файла.
     * @param mainView представление главного окна, содержащего элементы управления.
     * @param directoryModel модель, отвечающая за управление операциями с директориями.
     */
    public SearchCommand(MainView mainView, DirectoryManagementModel directoryModel) {
        this.mainView = mainView;
        this.directoryModel = directoryModel;
    }

    /**
     * Выполняет команду поиска файла по имени, используя введенное в поле поиска значение.
     * Если поле поиска пустое, обновляется отображение текущей директории.
     */
    @Override
    public void execute() {
        String fileName = mainView.getNavigationPanel().getSearchField().getText();
        if (!fileName.isEmpty()) {
            String currentDirectory = mainView.getNavigationPanel().getDirectoryField().getText();
            ArrayList<File> searchedFiles = new ArrayList<>();
            directoryModel.searchFileByName(currentDirectory, fileName, searchedFiles);
        } else {
            directoryModel.updateDirectory();
        }
    }
}
