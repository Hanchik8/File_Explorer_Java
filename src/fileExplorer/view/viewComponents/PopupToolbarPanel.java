package fileExplorer.view.viewComponents;

import fileExplorer.utils.ImageUtils;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;

import java.io.File;
import java.util.LinkedHashMap;

public class PopupToolbarPanel extends JPopupMenu {
    private LinkedHashMap<String, JMenuItem> toolsMap;

    public PopupToolbarPanel() {
        setupItems();
    }

    private void setupItems() {
        toolsMap = new LinkedHashMap<>();
        toolsMap.put("Cut", createButton("Cut","resources/images/btnIcons/cutIcon.png"));
        toolsMap.put("Copy", createButton("Copy", "resources/images/btnIcons/copyIcon.png"));
        toolsMap.put("Paste", createButton("Paste", "resources/images/btnIcons/pasteIcon.png"));
        toolsMap.put("Delete", createButton("Delete","resources/images/btnIcons/deleteIcon.png"));
    }

    private JMenuItem createButton(String toolName, String iconPath) {
        JMenuItem tool = new JMenuItem(toolName, ImageUtils.getImageIcon(iconPath));

        add(tool);
        return tool;
    }

    public void updateButtonState(File selectedFile) {
        for (JMenuItem button : toolsMap.values()) {
            button.setEnabled(selectedFile != null);
        }
    }

    public LinkedHashMap<String, JMenuItem> getToolsMap() {
        return toolsMap;
    }
}
