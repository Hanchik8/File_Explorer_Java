package fileExplorer.controller.listeners;

import fileExplorer.controller.commands.Command;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewActionListener implements ActionListener {
    private final Command command;

    public ViewActionListener(Command command) {
        this.command = command;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        command.execute();
    }
}
