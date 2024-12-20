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
import javax.swing.JComponent;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JPopupMenu;

import java.awt.BorderLayout;

import java.io.File;
import java.util.HashMap;

public class MainView extends JFrame {
    private HashMap<String, JComponent> viewPanels = new HashMap<>();
    private JSplitPane splitPane2;

    public MainView() {
        setTitle("File Explorer Lunar Seekers");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);

        add(setupExplorerView());

        new MainController(this);
    }

    public JPanel setupExplorerView() {
        JPanel topPanelPart = new JPanel(new BorderLayout());
        NavigationPanel navigationPanel = new NavigationPanel();
        topPanelPart.add(navigationPanel, BorderLayout.NORTH);
        viewPanels.put("navigation", navigationPanel);

        ToolbarPanel toolbarPanel = new ToolbarPanel();
        topPanelPart.add(toolbarPanel, BorderLayout.SOUTH);
        viewPanels.put("toolbar", toolbarPanel);

        JPanel explorerPanel = new JPanel(new BorderLayout());

        explorerPanel.add(topPanelPart, BorderLayout.NORTH);
        CenterPanel centerPanel = new CenterPanel();
        viewPanels.put("center", centerPanel);
        SidebarPanel sidebarPanel = new SidebarPanel();
        viewPanels.put("sidebar", sidebarPanel);
        FileDetailsPanel fileDetailsPanel = new FileDetailsPanel();
        viewPanels.put("fileDetails", fileDetailsPanel);

        getToolbarPanel();

        JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JScrollPane(sidebarPanel),
                new JScrollPane(centerPanel));
        splitPane1.setDividerLocation(300);

        splitPane2 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                splitPane1, new JScrollPane(fileDetailsPanel));
        splitPane2.setDividerLocation(1300);

        explorerPanel.add(splitPane2);

        JPopupMenu popupToolbarPanel = new PopupToolbarPanel();
        viewPanels.put("popupToolbar", popupToolbarPanel);

        return explorerPanel;
    }

    public void updateView(String[] fileNames, String currentPath) {
        CenterPanel centerPanel = (CenterPanel) viewPanels.get("center");
        centerPanel.updateFileListModel(fileNames);

        NavigationPanel navigationPanel = (NavigationPanel) viewPanels.get("navigation");
        navigationPanel.setCurrentPath(currentPath);
        updateBtnState(false);
    }

    public void updateBtnState(boolean isBtnActive) {
        ToolbarPanel toolbarPanel = (ToolbarPanel) viewPanels.get("toolbar");
        toolbarPanel.getCutBtn().setEnabled(isBtnActive);
        toolbarPanel.getCopyBtn().setEnabled(isBtnActive);
        toolbarPanel.getDeleteBtn().setEnabled(isBtnActive);
    }

    public void updateFileDetails(File selectedFile, String fileExtension) {
        FileDetailsPanel fileDetailsPanel = (FileDetailsPanel) viewPanels.get("fileDetails");
        fileDetailsPanel.updateFileDetailsPanel(selectedFile, fileExtension);
    }

    public void showHideFileDetailsPanel(boolean showDetails) {
        if (showDetails) {
            FileDetailsPanel fileDetailsPanel = (FileDetailsPanel) viewPanels.get("fileDetails");
            splitPane2.setRightComponent(new JScrollPane(fileDetailsPanel));
            splitPane2.setDividerLocation(1300);
        } else {
            splitPane2.setRightComponent(null);
        }
        splitPane2.revalidate();
        splitPane2.repaint();
    }

    public NavigationPanel getNavigationPanel() {
        return (NavigationPanel) viewPanels.get("navigation");
    }

    public CenterPanel getCenterPanel() {
        return (CenterPanel) viewPanels.get("center");
    }

    public ToolbarPanel getToolbarPanel() {
        return (ToolbarPanel) viewPanels.get("toolbar");
    }

    public SidebarPanel getSidebarPanel() {
        return (SidebarPanel) viewPanels.get("sidebar");
    }
    public PopupToolbarPanel getPopupToolbarPanel() { return (PopupToolbarPanel) viewPanels.get("popupToolbar");};
}
