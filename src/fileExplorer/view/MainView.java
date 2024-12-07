package fileExplorer.view;

import fileExplorer.controller.MainController;
import fileExplorer.view.viewComponents.CenterPanel;
import fileExplorer.view.viewComponents.EditPanel;
import fileExplorer.view.viewComponents.FileDetailsPanel;
import fileExplorer.view.viewComponents.SidebarPanel;
import fileExplorer.view.viewComponents.TopPanel;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import java.io.File;

public class MainView extends JFrame {
    private TopPanel topMenu;
    private CenterPanel centerPanel;
    private FileDetailsPanel fileDetailsPanel;
    private EditPanel editPanel;
    private SidebarPanel sidebarPanel;

    public MainView() {
        setTitle("File Explorer Lunar Seekers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        // Настроим topPanelPart, которое будет включать topMenu и editPanel
        JPanel topPanelPart = new JPanel(new BorderLayout());
        topMenu = new TopPanel();
        topPanelPart.add(topMenu.getTopMenuComponent(), BorderLayout.NORTH);

        editPanel = new EditPanel();
        topPanelPart.add(editPanel.getEditPanel(), BorderLayout.SOUTH);

        // Основной explorerPanel, который будет включать остальные панели
        JPanel explorerPanel = new JPanel(new BorderLayout());

        // Добавляем боковую панель (sidebar) в левую часть
        sidebarPanel = new SidebarPanel(this);
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
        editPanel.getCutBtn().setEnabled(isBtnActive);
        editPanel.getCopyBtn().setEnabled(isBtnActive);
        editPanel.getDeleteBtn().setEnabled(isBtnActive);
        editPanel.getRenameBtn().setEnabled(isBtnActive);
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

    public EditPanel getEditPanel() {
        return editPanel;
    }
}
