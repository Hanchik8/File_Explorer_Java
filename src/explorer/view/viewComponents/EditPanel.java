package explorer.view.viewComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Панель содержащая компоненты для манипуляции с файлами.
 */
public class EditPanel {
    private JPanel editPanel;
    private JComboBox<String> newComboBox;
    private JButton copyBtn;
    private JButton cutBtn;
    private JButton pasteBtn;
    private JButton renameBtn;
    private JButton deleteBtn;
    private JComboBox<String> sortComboBox;
    private JCheckBox detailsCheckBox;

    public EditPanel() {
        editPanel = new JPanel(new BorderLayout());
        JPanel editWestPart = createEditWestPart();

        editPanel.add(editWestPart, BorderLayout.WEST);
        editPanel.add(createDetailsCheckBox(), BorderLayout.EAST);
    }

    /**
     * Создаёт панель для кнопок редактирования.
     * @return панель с кнопками.
     */
    private JPanel createEditWestPart() {
        JPanel editWestPart = new JPanel();
        editWestPart.add(createNewComboBox());
        editWestPart.add(createCutBtn());
        editWestPart.add(createCopyBtn());
        editWestPart.add(createPasteBtn());
        editWestPart.add(createRenameBtn());
        editWestPart.add(createDeleteBtn());
        editWestPart.add(createSortComboBox());
        return editWestPart;
    }

    /**
     * Создаёт комбинированный список для выбора типа создаваемого объекта.
     * @return комбинированный список.
     */
    private JComboBox<String> createNewComboBox() {
        String[] newListChose = {"Create", "Directory", "Microsoft Word.docx", "Microsoft PowerPoint.pptx",
        "Note.txt", "Microsoft Excel.xlsx"};
        newComboBox = new JComboBox<>(newListChose);
        newComboBox.setSelectedItem("Create");
        return newComboBox;
    }

    /**
     * Создаёт кнопку "Вырезать".
     * @return кнопка "Вырезать".
     */
    private JButton createCutBtn() {
        cutBtn = createButtonWithIcon("src/imageIcon/cutIcon.png");
        return cutBtn;
    }

    /**
     * Создаёт кнопку "Копировать".
     * @return кнопка "Копировать".
     */
    private JButton createCopyBtn() {
        copyBtn = createButtonWithIcon("src/imageIcon/copyIcon.png");
        return copyBtn;
    }

    /**
     * Создаёт кнопку "Вставить".
     * @return кнопка "Вставить".
     */
    private JButton createPasteBtn() {
        pasteBtn = createButtonWithIcon("src/imageIcon/pasteIcon.png");
        return pasteBtn;
    }

    /**
     * Создаёт кнопку "Переименовать".
     * @return кнопка "Переименовать".
     */
    private JButton createRenameBtn() {
        renameBtn = createButtonWithIcon("src/imageIcon/renameIcon.png");
        return renameBtn;
    }

    /**
     * Создаёт кнопку "Удалить".
     * @return кнопка "Удалить".
     */
    private JButton createDeleteBtn() {
        deleteBtn = createButtonWithIcon("src/imageIcon/deleteIcon.png");
        return deleteBtn;
    }

    /**
     * Создаёт комбинированный список для выбора способа сортировки.
     * @return комбинированный список для сортировки.
     */
    private JComboBox<String> createSortComboBox() {
        String[] sortChose = {"Sort", "Date", "Name", "Type"};
        sortComboBox = new JComboBox<>(sortChose);
        sortComboBox.setSelectedItem("Sort");
        return sortComboBox;
    }

    /**
     * Создаёт флажок для отображения в подробном режиме.
     * @return флажок "Details".
     */
    private JCheckBox createDetailsCheckBox() {
        detailsCheckBox = new JCheckBox("Details");
        detailsCheckBox.setSelected(true);
        return detailsCheckBox;
    }

    /**
     * Создаёт кнопку с заданной иконкой.
     * @param iconPath путь к иконке.
     * @return кнопка с иконкой.
     */
    private JButton createButtonWithIcon(String iconPath) {
        JButton button = new JButton(scaleImageIcon(iconPath));
        button.setFocusable(false);
        button.setEnabled(false);
        return button;
    }

    /**
     * Масштабирует иконку для кнопки.
     * @param iconPath путь к иконке.
     * @return масштабированная иконка.
     */
    private ImageIcon scaleImageIcon(String iconPath) {
        Image scaledImage = new ImageIcon(iconPath).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    // ======== Геттеры для доступа к компонентам ========

    public JComboBox<String> getNewComboBox() {
        return newComboBox;
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

    public JButton getRenameBtn() {
        return renameBtn;
    }

    public JButton getDeleteBtn() {
        return deleteBtn;
    }

    public JComboBox<String> getSortComboBox() {
        return sortComboBox;
    }

    public JCheckBox getDetailsCheckBox() {
        return detailsCheckBox;
    }

    public JPanel getEditPanel() {
        return editPanel;
    }
}
