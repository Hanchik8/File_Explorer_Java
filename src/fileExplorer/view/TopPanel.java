package src.fileExplorer.view;

import javax.swing.*;

import java.awt.*;
import java.util.LinkedHashMap;

public class TopPanel {

    private JPanel topMenuComponent;

    private LinkedHashMap<String, JButton> topMenuButtons;

    private JTextField directoryField;

    private JTextField searchField;

    public TopPanel() {
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
        topMenuButtons.put("Back", createButton("src/imageIcon/backBtnIcon.png"));
        topMenuButtons.put("Forward", createButton("src/imageIcon/forwardBtnIcon.png"));
        topMenuButtons.put("Up", createButton("src/imageIcon/upperBtnIcon.png"));
        topMenuButtons.put("Refresh", createButton("src/imageIcon/refreshBtnIcon.png"));
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
        JButton button = new JButton(scaleImageIcon(iconPath));
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

    private ImageIcon scaleImageIcon(String iconPath) {
        Image scaledImage = new ImageIcon(iconPath).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
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
