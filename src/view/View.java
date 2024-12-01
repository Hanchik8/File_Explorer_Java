package src.view;

import javax.swing.*;
import java.awt.*;

public class View extends JFrame {
    private TopPanel topMenu;
    private CenterPanel centerPanel;
    private FileDetailsPanel fileDetailsPanel;
    private EditPanel editPanel;

    public View() {
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
        explorerPanel.add(topPanelPart, BorderLayout.NORTH);
        centerPanel = new CenterPanel();
        explorerPanel.add(centerPanel.getMainPanel(), BorderLayout.CENTER);

        fileDetailsPanel = new FileDetailsPanel();
        explorerPanel.add(fileDetailsPanel.getFileDetailsPanel(), BorderLayout.EAST);

        add(explorerPanel);

        // Инициализируем контроллер
//        new ExplorerController(this);
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
