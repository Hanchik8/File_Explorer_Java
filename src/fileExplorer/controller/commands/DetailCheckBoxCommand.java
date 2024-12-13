package fileExplorer.controller.commands;

import fileExplorer.view.MainView;

public class DetailCheckBoxCommand implements Command {
    private final MainView mainView;

    public DetailCheckBoxCommand(MainView mainView) {
        this.mainView = mainView;
    }

    @Override
    public void execute() {
        boolean isSelected = mainView.getToolbarPanel().getDetailsCheckBox().isSelected();
        mainView.getFileDetailsPanel().getFileDetailsPanel().setVisible(isSelected);
    }
}
