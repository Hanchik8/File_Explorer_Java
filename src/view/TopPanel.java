package src.view;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;

public class TopPanel {

    private JPanel topMenuComponent;
    private JButton backBtn;
    private JButton forwardBtn;
    private JButton upperBtn;
    private JButton refreshBtn;

    private JTextField directoryField;

    public TopPanel() {
        topMenuComponent = new JPanel(new BorderLayout());

        topMenuComponent.add(createBtnPanel(), BorderLayout.WEST);
        topMenuComponent.add(createDirectoryField(), BorderLayout.CENTER);
    }

    /*
     * creating components of TopPanel
     */
    private JPanel createBtnPanel() {
        JPanel btnPanel = new JPanel();

        btnPanel.add(createButton(backBtn, "Back"));
        btnPanel.add(createButton(forwardBtn, "Forward"));
        btnPanel.add(createButton(upperBtn, "Up"));
        btnPanel.add(createButton(refreshBtn, "Refresh"));

        return btnPanel;
    }

    private JButton createButton(JButton button, String name) {
        button = new JButton(name);
        button.setFocusable(false);
        button.setEnabled(false);
        return button;
    }

    private JTextField createDirectoryField() {
        directoryField = new JTextField();
        directoryField.setEditable(false);
        return directoryField;
    }

    /*
     * work with paths
     */
    public String getCurrentPath() {
        return directoryField.getText();
    }

    public void setCurrentPath(String path) {
        directoryField.setText(path);
    }

    /*
     * getters
     */
    public JButton getBacButton() {
        return backBtn;
    }

    public JButton getForwardBtn() {
        return forwardBtn;
    }

    public JButton getUpperBtn() {
        return upperBtn;
    }

    public JButton getRefreshBtn() {
        return refreshBtn;
    }

    public JPanel getTopMenuComponent() {
        return topMenuComponent;
    }

    public JTextField getDirecField() {
        return directoryField;
    }

}
