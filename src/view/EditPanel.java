package src.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class EditPanel {

    private JPanel editPanel;

    private JButton cutBtn;
    private JButton copyBtn;
    private JButton pasteBtn;
    private JButton deleteBtn;
    private JButton renameBtn;
    

    public EditPanel() {
        editPanel = new JPanel(new BorderLayout());

        editPanel.add(createEditWestPart(), BorderLayout.WEST);
    }

    /*
     * creating components of EditPanel
     */

    // creating Cut, Copy, Paste, Delete, Rename buttons
    private JPanel createEditWestPart() {
        JPanel editWestPart = new JPanel();

        editWestPart.add(createButton(cutBtn, "Cut"));
        editWestPart.add(createButton(copyBtn, "Copy"));
        editWestPart.add(createButton(pasteBtn, "Paste"));
        editWestPart.add(createButton(renameBtn, "Rename"));
        editWestPart.add(createButton(deleteBtn, "Delete"));
        
        return editWestPart;
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
    public JPanel getEditPanel() {
        return editPanel;
    }
    
    public JButton getCutBtn() {
        return cutBtn;
    }
    
    public JButton getCopyBtn() {
        return copyBtn;
    }
    
    public JButton getPasteBtn() {
        return pasteBtn;
    }
    
    public JButton getDeleteBtn() {
        return deleteBtn;
    }
    
    public JButton getRenameBtn() {
        return renameBtn;
    }
}
