package fileExplorer;

import fileExplorer.view.MainView;

public class FileExplorer {
    public static void main(String[] args) {
        MainView explorerView = new MainView();
        explorerView.setVisible(true);
        explorerView.updateComponentSize();
    }
}
