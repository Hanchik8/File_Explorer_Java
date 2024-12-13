package fileExplorer.controller.listeners;

import fileExplorer.controller.commands.Command;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonClickListener extends MouseAdapter {
    private final Command command;

    public ButtonClickListener(Command command) {
        this.command = command;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        command.execute();
    }
}
