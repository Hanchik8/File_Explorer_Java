package fileExplorer.view.viewComponents;

import fileExplorer.utils.ImageUtils;

import java.awt.BorderLayout;

import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;

public class ToolbarPanel {

    private final JPanel toolbarPanel;

    private HashMap<String, JButton> toolbarWestPartButtons;

    private JCheckBox detailsCheckBox;

    public JComboBox<String> sortComboBox;

    private JComboBox<String> newComboBox;

    public ToolbarPanel() {
        toolbarPanel = new JPanel(new BorderLayout());

        toolbarPanel.add(createToolbarWestPart(), BorderLayout.WEST);
        toolbarPanel.add(createDetailsCheckBox(), BorderLayout.EAST);
    }

    /*
     * creating components of EditPanel
     */

    // creating Cut, Copy, Paste, Delete, Rename buttons
    private void createButtons() {
        toolbarWestPartButtons = new HashMap<>();
        toolbarWestPartButtons.put("Cut", createButton("resources/images/btnIcons/cutIcon.png"));
        toolbarWestPartButtons.put("Copy", createButton("resources/images/btnIcons/copyIcon.png"));
        toolbarWestPartButtons.put("Paste", createButton("resources/images/btnIcons/pasteIcon.png"));
        toolbarWestPartButtons.put("Delete", createButton("resources/images/btnIcons/deleteIcon.png"));
    }

    // creating editWestPart
    private JPanel createToolbarWestPart() {
        JPanel toolbarWestPart = new JPanel();

        toolbarWestPart.add(createNewComboBox());

        createButtons();
        for (JButton button : toolbarWestPartButtons.values()) {
            toolbarWestPart.add(button);
        }

        toolbarWestPart.add(createSortComboBox());

        return toolbarWestPart;
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

    // creating sortComboBox
    private JComboBox<String> createSortComboBox() {
        String[] sortOptions = { "Name", "Date", "Size" };
        sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.setSelectedItem("Name");
        return sortComboBox;
    }

    /*
     * getters
     */
    public JPanel getEditPanel() {
        return toolbarPanel;
    }

    public JButton getCutBtn() {
        return toolbarWestPartButtons.get("Cut");
    }

    public JButton getCopyBtn() {
        return toolbarWestPartButtons.get("Copy");
    }

    public JButton getPasteBtn() {
        return toolbarWestPartButtons.get("Paste");
    }

    public JButton getDeleteBtn() {
        return toolbarWestPartButtons.get("Delete");
    }

    public JCheckBox getDetailsCheckBox() {
        return detailsCheckBox;
    }

    public JComboBox<String> getNewComboBox() {
        return newComboBox;
    }

    public JComboBox<String> getSortComboBox() {
        return sortComboBox;
    }
}