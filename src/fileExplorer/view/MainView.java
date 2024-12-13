package fileExplorer.view;

import fileExplorer.controller.MainController;
import fileExplorer.view.viewComponents.CenterPanel;
import fileExplorer.view.viewComponents.ToolbarPanel;
import fileExplorer.view.viewComponents.FileDetailsPanel;
import fileExplorer.view.viewComponents.SidebarPanel;
import fileExplorer.view.viewComponents.TopPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import java.io.File;

public class MainView extends JFrame {
    private final TopPanel topMenu;
    private final CenterPanel centerPanel;
    private final FileDetailsPanel fileDetailsPanel;
    private final ToolbarPanel toolbarPanel;
    private final SidebarPanel sidebarPanel;

    public MainView() {
        setTitle("File Explorer Lunar Seekers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Настроим topPanelPart, которое будет включать topMenu и editPanel
        JPanel topPanelPart = new JPanel(new BorderLayout());
        topMenu = new TopPanel();
        topPanelPart.add(topMenu.getTopMenuComponent(), BorderLayout.NORTH);

        toolbarPanel = new ToolbarPanel();
        topPanelPart.add(toolbarPanel.getEditPanel(), BorderLayout.SOUTH);

        // Основной explorerPanel, который будет включать остальные панели
        JPanel explorerPanel = new JPanel(new BorderLayout());

        // Добавляем боковую панель (sidebar) в левую часть
        sidebarPanel = new SidebarPanel();
        explorerPanel.add(sidebarPanel, BorderLayout.WEST);

        explorerPanel.add(topPanelPart, BorderLayout.NORTH);
        centerPanel = new CenterPanel();
        explorerPanel.add(centerPanel.getCenterPanel(), BorderLayout.CENTER);

        fileDetailsPanel = new FileDetailsPanel();
        explorerPanel.add(fileDetailsPanel.getFileDetailsPanel(), BorderLayout.EAST);

        add(explorerPanel);

        // Инициализируем контроллер
        new MainController(this);
    }

    public void updateView(String[] fileNames, String currentPath) {
        centerPanel.updateFileListModel(fileNames);
        topMenu.setCurrentPath(currentPath);
        updateBtnState(false);
    }

    public void updateBtnState(boolean isBtnActive) {
        toolbarPanel.getCutBtn().setEnabled(isBtnActive);
        toolbarPanel.getCopyBtn().setEnabled(isBtnActive);
        toolbarPanel.getDeleteBtn().setEnabled(isBtnActive);
    }

    public void updateFileDetails(File selectedFile, String fileExtension) {
        fileDetailsPanel.updateFileDetailsPanel(selectedFile, fileExtension);
    }

    // ======== Геттеры для доступа к компонентам ========

    public TopPanel getTopMenu() {
        return topMenu;
    }

    public CenterPanel getCenterPanel() {
        return centerPanel;
    }

    public FileDetailsPanel getFileDetailsPanel() {
        return fileDetailsPanel;
    }

    public ToolbarPanel getToolbarPanel() {
        return toolbarPanel;
    }

    public SidebarPanel getSidebarPanel() {
        return sidebarPanel;
    }
}
