package fileExplorer.controller.commands;

import fileExplorer.model.DirectoryManagementModel;

/**
 * Команда для отмены последнего действия в файловом менеджере.
 * Эта команда выполняет операцию отмены, используя функциональность модели управления директориями.
 */
public class UndoCommand implements Command {
    DirectoryManagementModel directoryModel;

    /**
     * Конструктор для создания команды отмены действия.
     * @param directoryModel модель, отвечающая за управление операциями с директориями.
     */
    public UndoCommand(DirectoryManagementModel directoryModel) {
        this.directoryModel = directoryModel;
    }

    /**
     * Возвращает на шаг назад в истории директорий.
     */
    @Override
    public void execute() {
        directoryModel.undo();
    }
}
