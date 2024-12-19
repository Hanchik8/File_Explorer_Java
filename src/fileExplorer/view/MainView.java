package fileExplorer.view;

import fileExplorer.controller.MainController;
import fileExplorer.view.viewComponents.CenterPanel;
import fileExplorer.view.viewComponents.ToolbarPanel;
import fileExplorer.view.viewComponents.FileDetailsPanel;
import fileExplorer.view.viewComponents.SidebarPanel;
import fileExplorer.view.viewComponents.NavigationPanel;
import fileExplorer.view.viewComponents.PopupToolbarPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import java.awt.BorderLayout;

import java.io.File;

public class MainView extends JFrame {
    private NavigationPanel navigationPanel;
    private ToolbarPanel toolbarPanel;
    private CenterPanel centerPanel;
    private SidebarPanel sidebarPanel;
    private FileDetailsPanel fileDetailsPanel;
    private PopupToolbarPanel popupToolbarPanel;

    public MainView() {
        setTitle("File Explorer Lunar Seekers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Настроим topPanelPart, которое будет включать topMenu и editPanel
        JPanel topPanelPart = new JPanel(new BorderLayout());
        navigationPanel = new NavigationPanel();
        topPanelPart.add(navigationPanel.getTopMenuComponent(), BorderLayout.NORTH);

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
        explorerPanel.add(fileDetailsPanel, BorderLayout.EAST);

        JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(sidebarPanel), centerPanel.getCenterPanel());
        splitPane1.setDividerLocation(300);

        JSplitPane splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                splitPane1, new JScrollPane(fileDetailsPanel.getFileDetailsPanel()));
        splitPane2.setDividerLocation(1300);

        explorerPanel.add(splitPane2);

        popupToolbarPanel = new PopupToolbarPanel();

        add(explorerPanel);

        // Инициализируем контроллер
        new MainController(this);
    }

    public void updateView(String[] fileNames, String currentPath) {
        centerPanel.updateFileListModel(fileNames);
        navigationPanel.setCurrentPath(currentPath);
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

    public NavigationPanel getNavigationPanel() {
        return navigationPanel;
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
    public PopupToolbarPanel getPopupToolbarPanel() {return popupToolbarPanel;};
}
