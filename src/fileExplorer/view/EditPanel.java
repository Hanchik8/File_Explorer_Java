package src.fileExplorer.view;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

public class EditPanel {

    private JPanel editPanel;

    private HashMap<String, JButton> editWestPartButtons;

    private JCheckBox detailsCheckBox;

    private JComboBox<String> newComboBox;

    public EditPanel() {
        editPanel = new JPanel(new BorderLayout());

        editPanel.add(createEditWestPart(), BorderLayout.WEST);
        editPanel.add(createDetailsCheckBox(), BorderLayout.EAST);
    }

    /*
     * creating components of EditPanel
     */

    // creating Cut, Copy, Paste, Delete, Rename buttons
    private void createButtons() {
        editWestPartButtons = new HashMap<>();
        editWestPartButtons.put("Cut", createButton("src/imageIcon/cutIcon.png"));
        editWestPartButtons.put("Copy", createButton("src/imageIcon/copyIcon.png"));
        editWestPartButtons.put("Paste", createButton("src/imageIcon/pasteIcon.png"));
        editWestPartButtons.put("Rename", createButton("src/imageIcon/renameIcon.png"));
        editWestPartButtons.put("Delete", createButton("src/imageIcon/deleteIcon.png"));
    }
    // creating editWestPart
    private JPanel createEditWestPart() {
        JPanel editWestPart = new JPanel();
        
        editWestPart.add(createNewComboBox());

        createButtons();
        for (JButton button : editWestPartButtons.values()) {
            editWestPart.add(button);
        }

        return editWestPart;
    }

    private JButton createButton(String iconPath) {
        JButton button = new JButton(scaleImageIcon(iconPath));
        button.setFocusable(false);
        button.setEnabled(false);

        return button;
    }

    private ImageIcon scaleImageIcon(String iconPath) {
        Image scaledImage = new ImageIcon(iconPath).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    // creating detailsCheckBox
    private JCheckBox createDetailsCheckBox() {
        detailsCheckBox = new JCheckBox("Details");
        detailsCheckBox.setSelected(true);

        return detailsCheckBox;
    }

    // creating newComboBox
    private JComboBox<String> createNewComboBox() {
        String[] newListChoice = {"Create", "Directory", "Microsoft Word.docx", "Microsoft PowerPoint.pptx",
        "Note.txt", "Microsoft Excel.xlsx"};
        newComboBox = new JComboBox<>(newListChoice);
        newComboBox.setSelectedItem("Create");

        return newComboBox;
    }

    /*
     * getters
     */
    public JPanel getEditPanel() {
        return editPanel;
    }

    public JButton getCutBtn() {
        return editWestPartButtons.get("Cut");
    }

    public JButton getCopyBtn() {
        return editWestPartButtons.get("Copy");
    }

    public JButton getPasteBtn() {
        return editWestPartButtons.get("Paste");
    }

    public JButton getDeleteBtn() {
        return editWestPartButtons.get("Delete");
    }

    public JButton getRenameBtn() {
        return editWestPartButtons.get("Rename");
    }

    public JCheckBox getDetailsCheckBox() {
        return detailsCheckBox;
    }

    public JComboBox<String> getNewComboBox() {
        return newComboBox;
    }
}
