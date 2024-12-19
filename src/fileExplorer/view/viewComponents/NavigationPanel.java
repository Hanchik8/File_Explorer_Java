package fileExplorer.view.viewComponents;

import fileExplorer.utils.ImageUtils;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;

import java.util.LinkedHashMap;

public class NavigationPanel {

    private JPanel topMenuComponent;

    private LinkedHashMap<String, JButton> topMenuButtons;

    private JTextField directoryField;

    private JTextField searchField;

    public NavigationPanel() {
        topMenuComponent = new JPanel(new BorderLayout());

        topMenuComponent.add(createBtnPanel(), BorderLayout.WEST);
        topMenuComponent.add(createDirectoryField(), BorderLayout.CENTER);
        topMenuComponent.add(createSearchField(), BorderLayout.EAST);
    }

    /*
     * creating components of TopPanel
     */

    // creating Back, Forward, Up, Refresh buttons
    private void createButtons() {
        topMenuButtons = new LinkedHashMap<>();
        topMenuButtons.put("Back", createButton("resources/images/btnIcons/backBtnIcon.png"));
        topMenuButtons.put("Forward", createButton("resources/images/btnIcons/forwardBtnIcon.png"));
        topMenuButtons.put("Up", createButton("resources/images/btnIcons/upperBtnIcon.png"));
        topMenuButtons.put("Refresh", createButton("resources/images/btnIcons/refreshBtnIcon.png"));
    }

    // creating ButtonPanel
    private JPanel createBtnPanel() {
        JPanel btnPanel = new JPanel();

        createButtons();
        for (JButton button : topMenuButtons.values()) {
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

    // creating Directory Field
    private JTextField createDirectoryField() {
        directoryField = new JTextField();
        directoryField.setEditable(false);

        return directoryField;
    }

    public String getCurrentPath() {
        return directoryField.getText();
    }

    public void setCurrentPath(String path) {
        directoryField.setText(path);
    }

    // Creating Search Field
    private JTextField createSearchField() {
        searchField = new JTextField(25);
        searchField.setText("Search...");
        searchField.setForeground(Color.GRAY);

        return searchField;
    }

    /*
     * getters
     */
    public JButton getBackBtn() {
        return topMenuButtons.get("Back");
    }

    public JButton getForwardBtn() {
        return topMenuButtons.get("Forward");
    }

    public JButton getUpperBtn() {
        return topMenuButtons.get("Up");
    }

    public JButton getRefreshBtn() {
        return topMenuButtons.get("Refresh");
    }

    public JPanel getTopMenuComponent() {
        return topMenuComponent;
    }

    public JTextField getDirectoryField() {
        return directoryField;
    }

    public JTextField getSearchField() {
        return searchField;
    }

}
