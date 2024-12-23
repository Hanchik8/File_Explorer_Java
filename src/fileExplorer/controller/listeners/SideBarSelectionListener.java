package fileExplorer.controller.listeners;

import fileExplorer.controller.SidebarController;
import fileExplorer.view.viewComponents.SidebarPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.File;
import java.nio.file.Paths;

/**
 * Слушатель для обработки выбора категории в боковой панели (Sidebar).
 * При выборе категории обновляется основная панель с соответствующей директорией.
 */
public class SideBarSelectionListener implements ListSelectionListener {
    private final SidebarPanel sidebarPanel;
    private final SidebarController sidebarController;

    /**
     * Конструктор класса.
     * @param sidebarPanel    панель бокового меню.
     * @param sidebarController контроллер для обновления основной панели.
     */
    public SideBarSelectionListener(SidebarPanel sidebarPanel, SidebarController sidebarController) {
        this.sidebarPanel = sidebarPanel;
        this.sidebarController = sidebarController;
    }

    /**
     * Метод, вызываемый при изменении выбора категории в боковой панели.
     * Обновляет основную панель в зависимости от выбранной категории.
     * @param e событие изменения выбора.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            String selectedCategory = sidebarPanel.getCategoryList().getSelectedValue();

            if (selectedCategory.equals("My Computer")) {
                sidebarController.updateMainPanel("Root");
            } else {
                File selectedFolder = Paths.get(System.getProperty("user.home"), selectedCategory).toFile();
                sidebarController.updateMainPanel(selectedFolder);
            }
        }
    }
}
