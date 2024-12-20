package fileExplorer.controller.listeners;

import fileExplorer.controller.SidebarController;
import fileExplorer.view.viewComponents.SidebarPanel;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.io.File;
import java.nio.file.Paths;

public class SideBarSelectionListener implements ListSelectionListener {
    private final SidebarPanel sidebarPanel;
    private final SidebarController sidebarController;

    public SideBarSelectionListener(SidebarPanel sidebarPanel, SidebarController sidebarController) {
        this.sidebarPanel = sidebarPanel;
        this.sidebarController = sidebarController;
    }

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
