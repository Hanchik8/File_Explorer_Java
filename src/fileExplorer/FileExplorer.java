package fileExplorer;

import fileExplorer.view.MainView;

/**
 * Главный класс приложения File Explorer.
 * Запускает файловый менеджер, создавая и отображая основное окно.
 */
public class FileExplorer {
    public static void main(String[] args) {
        MainView explorerView = new MainView();
        explorerView.setVisible(true);
        explorerView.updateComponentSize();
    }
}
