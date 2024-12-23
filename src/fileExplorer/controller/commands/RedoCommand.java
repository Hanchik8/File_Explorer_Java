package fileExplorer.controller.commands;

import fileExplorer.model.DirectoryManagementModel;

/**
 * Реализация команды для выполнения операции "Повтор" (Redo) в файловом менеджере.
 * Эта команда используется для восстановления последней отмененной операции в контексте работы с директориями.
 */
public class RedoCommand implements Command {
    DirectoryManagementModel directoryModel;

    /**
     * Конструктор для создания команды "Повтор".
     * @param directoryModel модель, отвечающая за управление операциями с директориями.
     */
    public RedoCommand(DirectoryManagementModel directoryModel) {
        this.directoryModel = directoryModel;
    }

    /**
     * Перемещает на шаг вперёд в истории директорий.
     */
    @Override
    public void execute() {
        directoryModel.redo();
    }
}
