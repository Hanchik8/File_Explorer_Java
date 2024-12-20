package fileExplorer.view.viewComponents;

import fileExplorer.utils.ImageUtils;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComponent;

import java.awt.BorderLayout;
import java.awt.Color;

import java.util.LinkedHashMap;

public class NavigationPanel extends JPanel{
    private LinkedHashMap<String, JComponent> topMenuButtons;
    private JTextField directoryField;
    private JTextField searchField;

    public NavigationPanel() {
        setLayout(new BorderLayout());

        add(createBtnPanel(), BorderLayout.WEST);
        add(createDirectoryField(), BorderLayout.CENTER);
        add(createSearchField(), BorderLayout.EAST);
    }

    private void createButtons() {
        topMenuButtons = new LinkedHashMap<>();
        topMenuButtons.put("Back", createButton("resources/images/btnIcons/backBtnIcon.png"));
        topMenuButtons.put("Forward", createButton("resources/images/btnIcons/forwardBtnIcon.png"));
        topMenuButtons.put("Up", createButton("resources/images/btnIcons/upperBtnIcon.png"));
        topMenuButtons.put("Refresh", createButton("resources/images/btnIcons/refreshBtnIcon.png"));
    }

    private JPanel createBtnPanel() {
        JPanel btnPanel = new JPanel();

        createButtons();
        for (JComponent button : topMenuButtons.values()) {
            btnPanel.add(button);
        }

        return btnPanel;
    }

    private JButton createButton(String iconPath) {
        JButton button = new JButton(ImageUtils.getImageIcon(iconPath));
        button.setFocusable(false);

        if (!iconPath.contains("refresh")) {
            button.setEnabled(false);
        }

        return button;
    }

    private JTextField createDirectoryField() {
        directoryField = new JTextField();
        directoryField.setEditable(false);
        return directoryField;
    }

    private JTextField createSearchField() {
        searchField = new JTextField(25);
        searchField.setText("Search...");
        searchField.setForeground(Color.GRAY);

        return searchField;
    }

    public String getCurrentPath() {
        return directoryField.getText();
    }

    public void setCurrentPath(String path) {
        directoryField.setText(path);
    }

    public JComponent getBackBtn() {
        return topMenuButtons.get("Back");
    }

    public JComponent getForwardBtn() {
        return topMenuButtons.get("Forward");
    }

    public JComponent getUpperBtn() {
        return topMenuButtons.get("Up");
    }

    public JComponent getRefreshBtn() {
        return topMenuButtons.get("Refresh");
    }

    public JTextField getDirectoryField() {
        return directoryField;
    }

    public JTextField getSearchField() {
        return searchField;
    }

}
