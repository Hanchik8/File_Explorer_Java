package fileExplorer.view.viewComponents;

import fileExplorer.utils.ImageUtils;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;

public class ToolbarPanel extends JPanel {
    private HashMap<String, JComponent> toolbarWestPartButtons;

    private JCheckBox detailsCheckBox;

    public JComboBox<String> sortComboBox;

    private JComboBox<String> newComboBox;

    private JCheckBox showHiddenFilesCheckBox;

    public ToolbarPanel() {
        super(new BorderLayout());

        add(createToolbarWestPart(), BorderLayout.WEST);
        add(createDetailsCheckBox(), BorderLayout.EAST);
        add(createShowHiddenFilesCheckBox(), BorderLayout.PAGE_END);
    }

    private void createButtons() {
        toolbarWestPartButtons = new HashMap<>();
        toolbarWestPartButtons.put("Cut", createButton("resources/images/btnIcons/cutIcon.png"));
        toolbarWestPartButtons.put("Copy", createButton("resources/images/btnIcons/copyIcon.png"));
        toolbarWestPartButtons.put("Paste", createButton("resources/images/btnIcons/pasteIcon.png"));
        toolbarWestPartButtons.put("Delete", createButton("resources/images/btnIcons/deleteIcon.png"));
    }

    private JPanel createToolbarWestPart() {
        JPanel toolbarWestPart = new JPanel();
        toolbarWestPart.add(createNewComboBox());

        createButtons();
        for (JComponent button : toolbarWestPartButtons.values()) {
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

    private JCheckBox createDetailsCheckBox() {
        detailsCheckBox = new JCheckBox("Details");
        detailsCheckBox.setSelected(true);

        return detailsCheckBox;
    }

    private JComboBox<String> createNewComboBox() {
        String[] newListChoice = { "Create", "Directory", "Microsoft Word.docx", "Microsoft PowerPoint.pptx",
                "Note.txt", "Microsoft Excel.xlsx" };
        newComboBox = new JComboBox<>(newListChoice);
        newComboBox.setSelectedItem("Create");

        return newComboBox;
    }

    private JComboBox<String> createSortComboBox() {
        String[] sortOptions = { "Name", "Date", "Size" };
        sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.setSelectedItem("Name");

        return sortComboBox;
    }

    private JCheckBox createShowHiddenFilesCheckBox() {
        showHiddenFilesCheckBox = new JCheckBox("Show hidden files");
        showHiddenFilesCheckBox.setSelected(false);

        return showHiddenFilesCheckBox;
    }

    public JComponent getCutBtn() {
        return toolbarWestPartButtons.get("Cut");
    }

    public JComponent getCopyBtn() {
        return toolbarWestPartButtons.get("Copy");
    }

    public JComponent getPasteBtn() {
        return toolbarWestPartButtons.get("Paste");
    }

    public JComponent getDeleteBtn() {
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

    public JCheckBox getShowHiddenFilesCheckBox() {
        return showHiddenFilesCheckBox;
    }
}