package fileExplorer.controller.commands;

import fileExplorer.model.DirectoryManagementModel;

/**
 * Реализация команды для обновления содержимого текущей директории.
 * Эта команда используется для перезагрузки или обновления данных о содержимом директории.
 */
public class RefreshCommand implements Command {
    private final DirectoryManagementModel directoryModel;

    /**
     * Конструктор для создания команды "Обновить" (Refresh).
     * @param directoryModel модель, отвечающая за управление операциями с директориями.
     */
    public RefreshCommand(DirectoryManagementModel directoryModel) {
        this.directoryModel = directoryModel;
    }

    /**
     * Выполняет команду "Обновить", обновляя содержимое текущей директории.
     */
    @Override
    public void execute() {
        directoryModel.updateDirectory();
    }
}
