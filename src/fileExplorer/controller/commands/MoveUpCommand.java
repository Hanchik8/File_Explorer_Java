package fileExplorer.controller.commands;

import fileExplorer.model.DirectoryManagementModel;

/**
 * Реализация команды для перемещения в родительскую директорию.
 * Этот класс используется для обработки команды "Move Up" в файловом менеджере.
 */
public class MoveUpCommand implements Command {
    DirectoryManagementModel directoryModel;

    /**
     * Конструктор для создания команды перемещения в родительскую директорию.
     * @param directoryModel модель, отвечающая за управление директориями.
     */
    public MoveUpCommand(DirectoryManagementModel directoryModel) {
        this.directoryModel = directoryModel;
    }

    /**
     * Выполняет команду перемещения в родительскую директорию.
     * Этот метод вызывает соответствующий метод модели для перемещения на уровень выше.
     */
    @Override
    public void execute() {
        directoryModel.moveToParentDirectory();
    }
}
