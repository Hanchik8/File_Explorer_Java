package fileExplorer.controller.listeners;

import fileExplorer.controller.commands.Command;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Слушатель кликов по кнопке. Этот класс обрабатывает события кликов мышью
 * и выполняет соответствующую команду при клике на кнопку.
 */
public class ButtonClickListener extends MouseAdapter {
    private final Command command;

    /**
     * Конструктор для создания слушателя, который будет выполнять заданную команду
     * при клике на кнопку.
     * @param command команда, которая будет выполнена при клике на кнопку.
     */
    public ButtonClickListener(Command command) {
        this.command = command;
    }

    /**
     * Метод, вызываемый при клике на кнопку.
     * Он вызывает выполнение команды, связанной с этим слушателем.
     * @param e событие клика мыши.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        command.execute();
    }
}
