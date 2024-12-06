package explorer.view;

import explorer.controller.ExplorerController;
import explorer.view.viewComponents.*;

import javax.swing.*;
import java.awt.*;

public class ExplorerView extends JFrame {
    private TopPanel topMenu;
    private MainPanel mainPanel;
    private FileDetailsPanel fileDetailsPanel;
    private EditPanel editPanel;
    private SidebarPanel sidebarPanel;

    public ExplorerView() {
        setTitle("Paint");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
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
        explorerPanel.add(sidebarPanel, BorderLayout.WEST);  // Добавляем боковую панель



        explorerPanel.add(topPanelPart, BorderLayout.NORTH);
        mainPanel = new MainPanel();
        explorerPanel.add(mainPanel.getMainPanel(), BorderLayout.CENTER);

        fileDetailsPanel = new FileDetailsPanel();
        explorerPanel.add(fileDetailsPanel.getFileDetailsPanel(), BorderLayout.EAST);

        add(explorerPanel);

        // Инициализируем контроллер
        new ExplorerController(this);
    }

    // ======== Геттеры для доступа к компонентам ========

    public TopPanel getTopMenu() {
        return topMenu;
    }

    public MainPanel getMainPanel() {
        return mainPanel;
    }

    public FileDetailsPanel getFileDetailsPanel() {
        return fileDetailsPanel;
    }

    public EditPanel getEditPanel() {
        return editPanel;
    }
}
