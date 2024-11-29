package src.view;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class TopPanel {

    private JPanel topMenuComponent;
    private JButton backBtn;
    private JButton forwardBtn;
    private JButton upperBtn;
    private JButton refreshBtn;

    public TopPanel() {
        topMenuComponent = new JPanel(new BorderLayout());

        topMenuComponent.add(createBtnPanel(), BorderLayout.WEST);
    }

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
    
}

