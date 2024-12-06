package fileExplorer.view.viewComponents;

import fileExplorer.utils.ImageUtils;

import java.awt.BorderLayout;

import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

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
        editWestPartButtons.put("Cut", createButton("resources/images/btnIcons/cutIcon.png"));
        editWestPartButtons.put("Copy", createButton("resources/images/btnIcons/copyIcon.png"));
        editWestPartButtons.put("Paste", createButton("resources/images/btnIcons/pasteIcon.png"));
        editWestPartButtons.put("Rename", createButton("resources/images/btnIcons/renameIcon.png"));
        editWestPartButtons.put("Delete", createButton("resources/images/btnIcons/deleteIcon.png"));
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
        JButton button = new JButton(ImageUtils.getImageIcon(iconPath));
        button.setFocusable(false);
        button.setEnabled(false);

        return button;
    }

    // creating detailsCheckBox
    private JCheckBox createDetailsCheckBox() {
        detailsCheckBox = new JCheckBox("Details");
        detailsCheckBox.setSelected(true);

        return detailsCheckBox;
    }

    // creating newComboBox
    private JComboBox<String> createNewComboBox() {
        String[] newListChoice = { "Create", "Directory", "Microsoft Word.docx", "Microsoft PowerPoint.pptx",
                "Note.txt", "Microsoft Excel.xlsx" };
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
