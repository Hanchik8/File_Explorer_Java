package src.view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.HashMap;
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
        topMenuButtons.put("Back", createButton("Back"));
        topMenuButtons.put("Forward", createButton("Forward"));
        topMenuButtons.put("Up", createButton("Up"));
        topMenuButtons.put("Refresh", createButton("Refresh"));
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

    private JButton createButton(String name) {
        JButton button = new JButton(name);
        button.setFocusable(false);
        if (!name.equals("Refresh")) {
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

}
