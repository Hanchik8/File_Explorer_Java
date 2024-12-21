package fileExplorer.view.viewComponents;

import fileExplorer.utils.ImageUtils;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;

import java.util.LinkedHashMap;

public class PopupToolbarPanel extends JPopupMenu {
    private static final long serialVersionUID = 1L;
    private LinkedHashMap<String, JMenuItem> toolsMap;

    public PopupToolbarPanel() {
        setupItems();
    }

    private void setupItems() {
        toolsMap = new LinkedHashMap<>();
        toolsMap.put("cut", createJMenuItem("Cut","resources/images/btnIcons/cutIcon.png"));
        toolsMap.put("rename", createJMenuItem("Rename","resources/images/btnIcons/renameIcon.png"));
        toolsMap.put("copy", createJMenuItem("Copy", "resources/images/btnIcons/copyIcon.png"));
        toolsMap.put("paste", createJMenuItem("Paste", "resources/images/btnIcons/pasteIcon.png"));
        toolsMap.put("delete", createJMenuItem("Delete","resources/images/btnIcons/deleteIcon.png"));
    }

    private JMenuItem createJMenuItem(String toolName, String iconPath) {
        JMenuItem tool = new JMenuItem(toolName, ImageUtils.getImageIcon(iconPath));

        if (toolName.equals("Paste"))
            tool.setEnabled(false);

        add(tool);
        return tool;
    }

    public void updateJItemState(Boolean isActive) {
        for (JMenuItem item : toolsMap.values()) {
            if (!item.getText().equals("Paste"))
                item.setEnabled(isActive);
        }
    }

    public void enablePasteItem(Boolean isActive) {
        toolsMap.get("paste").setEnabled(isActive);
    }

    public LinkedHashMap<String, JMenuItem> getToolsMap() {
        return toolsMap;
    }
}
