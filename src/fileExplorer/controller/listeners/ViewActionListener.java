package fileExplorer.controller.listeners;

import fileExplorer.controller.commands.Command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс слушателя действия для обработки нажатий на кнопки интерфейса.
 * Вызывает исполнение переданной команды при событии действия.
 */
public class ViewActionListener implements ActionListener {
    private final Command command;

    /**
     * Конструктор класса.
     * @param command объект, представляющий команду, которую нужно выполнить.
     */
    public ViewActionListener(Command command) {
        this.command = command;
    }

    /**
     * Метод, вызываемый при событии действия (например, нажатие кнопки).
     * Выполняет переданную команду.
     * @param e объект события, содержащий информацию о событии.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        command.execute();
    }
}
