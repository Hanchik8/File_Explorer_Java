package src.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;

public class EditPanel {

    private JPanel editPanel;

    private JButton cutBtn;
    private JButton copyBtn;
    private JButton pasteBtn;
    private JButton deleteBtn;
    private JButton renameBtn;

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
    private JPanel createEditWestPart() {
        JPanel editWestPart = new JPanel();

        editWestPart.add(createNewComboBox());

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

    public JCheckBox getDetailsCheckBox() {
        return detailsCheckBox;
    }

    public JComboBox<String> getNewComboBox() {
        return newComboBox;
    }
}
