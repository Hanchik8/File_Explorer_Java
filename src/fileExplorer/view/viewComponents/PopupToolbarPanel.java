package fileExplorer.view.viewComponents;

import fileExplorer.utils.ImageUtils;

import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;

import java.util.LinkedHashMap;

/**
 * Панель контекстного меню для инструментов с действиями, такими как вырезание, копирование, вставка, переименование и удаление.
 * Предназначена для работы с файлами в файловом менеджере.
 */
public class PopupToolbarPanel extends JPopupMenu {
    private static final long serialVersionUID = 1L;
    private LinkedHashMap<String, JMenuItem> toolsMap;

    /**
     * Конструктор класса, который инициализирует панель и настраивает элементы контекстного меню.
     */
    public PopupToolbarPanel() {
        setupItems();
    }

    /**
     * Настроить элементы контекстного меню с соответствующими иконками и действиями.
     */
    private void setupItems() {
        toolsMap = new LinkedHashMap<>();
        toolsMap.put("cut", createJMenuItem("Cut","resources/images/btnIcons/cutIcon.png"));
        toolsMap.put("rename", createJMenuItem("Rename","resources/images/btnIcons/renameIcon.png"));
        toolsMap.put("copy", createJMenuItem("Copy", "resources/images/btnIcons/copyIcon.png"));
        toolsMap.put("paste", createJMenuItem("Paste", "resources/images/btnIcons/pasteIcon.png"));
        toolsMap.put("delete", createJMenuItem("Delete","resources/images/btnIcons/deleteIcon.png"));
    }

    /**
     * Создаёт элемент меню с заданным именем и иконкой.
     * При создании элемента для "Paste" кнопка будет отключена по умолчанию.
     * @param toolName имя элемента меню
     * @param iconPath путь к иконке
     * @return созданный элемент меню
     */
    private JMenuItem createJMenuItem(String toolName, String iconPath) {
        JMenuItem tool = new JMenuItem(toolName, ImageUtils.getImageIcon(iconPath));

        if (toolName.equals("Paste"))
            tool.setEnabled(false);

        add(tool);
        return tool;
    }

    /**
     * Обновляет состояние всех элементов меню (кроме "Paste").
     * @param isActive флаг активности для элементов меню
     */
    public void updateJItemState(Boolean isActive) {
        for (JMenuItem item : toolsMap.values()) {
            if (!item.getText().equals("Paste"))
                item.setEnabled(isActive);
        }
    }

    /**
     * Включает или отключает элемент меню "Paste".
     * @param isActive флаг активности для элемента "Paste"
     */
    public void enablePasteItem(Boolean isActive) {
        toolsMap.get("paste").setEnabled(isActive);
    }

    /**
     * Возвращает карту всех элементов меню с их именами и состоянием.
     * @return карта элементов меню
     */
    public LinkedHashMap<String, JMenuItem> getToolsMap() {
        return toolsMap;
    }
}
