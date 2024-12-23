package fileExplorer.view.viewComponents;

import fileExplorer.utils.ImageUtils;

import java.awt.BorderLayout;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JButton;

/**
 * Панель инструментов для файлового менеджера, которая включает различные элементы управления,
 * такие как кнопки для операций с файлами (cut, copy, paste, delete, rename),
 * флажок для отображения деталей и комбинированные списки для создания новых объектов и сортировки.
 */
public class ToolbarPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private HashMap<String, JButton> toolbarWestPartButtons;

    private JCheckBox detailsCheckBox;

    public JComboBox<String> sortComboBox;

    private JComboBox<String> newComboBox;

    /**
     * Конструктор класса. Создаёт панель инструментов с кнопками, флажками и комбинированными списками.
     */
    public ToolbarPanel() {
        super(new BorderLayout());

        add(createToolbarWestPart(), BorderLayout.WEST);
        add(createDetailsCheckBox(), BorderLayout.EAST);
    }

    /**
     * Создаёт кнопки на панели инструментов.
     * Кнопки включают действия: cut, copy, paste, delete, rename.
     */
    private void createButtons() {
        toolbarWestPartButtons = new HashMap<>();
        toolbarWestPartButtons.put("cut", createButton("resources/images/btnIcons/cutIcon.png"));
        toolbarWestPartButtons.put("copy", createButton("resources/images/btnIcons/copyIcon.png"));
        toolbarWestPartButtons.put("paste", createButton("resources/images/btnIcons/pasteIcon.png"));
        toolbarWestPartButtons.put("delete", createButton("resources/images/btnIcons/deleteIcon.png"));
        toolbarWestPartButtons.put("rename", createButton("resources/images/btnIcons/renameIcon.png"));
    }

    /**
     * Создаёт панель с кнопками, комбинированным списком для создания объектов и кнопкой для сортировки.
     * @return панель с кнопками
     */
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

    /**
     * Создаёт кнопку с иконкой для панели инструментов.
     * @param iconPath путь к иконке
     * @return созданная кнопка
     */
    private JButton createButton(String iconPath) {
        JButton button = new JButton(ImageUtils.getImageIcon(iconPath));
        button.setFocusable(false);
        button.setEnabled(false);

        return button;
    }

    /**
     * Создаёт флажок для отображения деталей.
     * @return флажок для отображения деталей
     */
    private JCheckBox createDetailsCheckBox() {
        detailsCheckBox = new JCheckBox("Details");
        detailsCheckBox.setSelected(true);

        return detailsCheckBox;
    }

    /**
     * Создаёт комбинированный список для создания новых объектов.
     * Включает такие опции, как "Создать папку" и создание различных типов файлов.
     * @return комбинированный список для создания объектов
     */
    private JComboBox<String> createNewComboBox() {
        String[] newListChoice = { "Create", "Directory", "Microsoft Word.docx", "Microsoft PowerPoint.pptx",
                "Note.txt", "Microsoft Excel.xlsx" };
        newComboBox = new JComboBox<>(newListChoice);
        newComboBox.setSelectedItem("Create");

        return newComboBox;
    }

    /**
     * Создаёт комбинированный список для сортировки файлов.
     * Опции включают сортировку по имени, дате и размеру.
     * @return комбинированный список для сортировки
     */
    private JComboBox<String> createSortComboBox() {
        String[] sortOptions = { "Name", "Date", "Size" };
        sortComboBox = new JComboBox<>(sortOptions);
        sortComboBox.setSelectedItem("Name");

        return sortComboBox;
    }

    /**
     * Возвращает кнопку "Cut" (вырезать).
     * @return кнопка "Cut"
     */
    public JButton getCutBtn() {
        return toolbarWestPartButtons.get("cut");
    }

    /**
     * Возвращает кнопку "Copy" (копировать).
     * @return кнопка "Copy"
     */
    public JButton getCopyBtn() {
        return toolbarWestPartButtons.get("copy");
    }

    /**
     * Возвращает кнопку "Paste" (вставить).
     * @return кнопка "Paste"
     */
    public JButton getPasteBtn() {
        return toolbarWestPartButtons.get("paste");
    }

    /**
     * Возвращает кнопку "Delete" (удалить).
     * @return кнопка "Delete"
     */
    public JButton getDeleteBtn() {
        return toolbarWestPartButtons.get("delete");
    }

    /**
     * Возвращает кнопку "Rename" (переименовать).
     * @return кнопка "Rename"
     */
    public JButton getRenameBtn() {
        return toolbarWestPartButtons.get("rename");
    }

    /**
     * Возвращает флажок для отображения деталей.
     * @return флажок "Details"
     */
    public JCheckBox getDetailsCheckBox() {
        return detailsCheckBox;
    }

    /**
     * Возвращает комбинированный список для создания объектов.
     * @return комбинированный список для создания объектов
     */
    public JComboBox<String> getNewComboBox() {
        return newComboBox;
    }

    /**
     * Возвращает комбинированный список для сортировки.
     * @return комбинированный список для сортировки
     */
    public JComboBox<String> getSortComboBox() {
        return sortComboBox;
    }
}