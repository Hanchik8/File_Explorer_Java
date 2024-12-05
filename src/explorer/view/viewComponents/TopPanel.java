package explorer.view.viewComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Панель содержащая компоненты для изменения директории.
 */
public class TopPanel {
    private JPanel topMenuComponent;
    private JButton backBtn;
    private JButton forwardBtn;
    private JButton upperBtn;
    private JButton refreshBtn;
    private JTextField directoryField;
    private JTextField searchField;

    public TopPanel() {
        topMenuComponent = new JPanel(new BorderLayout());
        JPanel btnPanel = createButtonPanel();

        topMenuComponent.add(btnPanel, BorderLayout.WEST);
        topMenuComponent.add(createDirectoryField(), BorderLayout.CENTER);
        topMenuComponent.add(createSearchField(), BorderLayout.EAST);
    }

    /**
     * Создаёт панель с кнопками навигации.
     *
     * @return панель с кнопками.
     */
    private JPanel createButtonPanel() {
        JPanel btnPanel = new JPanel();
        btnPanel.add(createBackBtn());
        btnPanel.add(createForwardBtn());
        btnPanel.add(createUpperBtn());
        btnPanel.add(createRefreshBtn());
        return btnPanel;
    }

    /**
     * Создаёт кнопку "Назад" с иконкой.
     *
     * @return кнопка "Назад".
     */
    private JButton createBackBtn() {
        backBtn = createButtonWithIcon("src/imageIcon/backBtnIcon.png");
        return backBtn;
    }

    /**
     * Создаёт кнопку "Вперёд" с иконкой.
     *
     * @return кнопка "Вперёд".
     */
    private JButton createForwardBtn() {
        forwardBtn = createButtonWithIcon("src/imageIcon/forwardBtnIcon.png");
        return forwardBtn;
    }

    /**
     * Создаёт кнопку "Вверх" с иконкой.
     *
     * @return кнопка "Вверх".
     */
    private JButton createUpperBtn() {
        upperBtn = createButtonWithIcon("src/imageIcon/upperBtnIcon.png");
        return upperBtn;
    }

    /**
     * Создаёт кнопку "Обновить" с иконкой.
     *
     * @return кнопка "Обновить".
     */
    private JButton createRefreshBtn() {
        refreshBtn = createButtonWithIcon("src/imageIcon/refreshBtnIcon.png");
        refreshBtn.setEnabled(true);
        return refreshBtn;
    }

    /**
     * Создаёт текстовое поле для отображения текущего пути.
     *
     * @return текстовое поле с путём.
     */
    private JTextField createDirectoryField() {
        directoryField = new JTextField();
        return directoryField;
    }

    /**
     * Создаёт текстовое поле для поиска.
     *
     * @return текстовое поле для поиска.
     */
    private JTextField createSearchField() {
        searchField = new JTextField(25);
        searchField.setText("Search: ");
        return searchField;
    }

    /**
     * Создаёт кнопку с иконкой, предварительно масштабируя её.
     *
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
     *
     * @param iconPath путь к иконке.
     * @return масштабированная иконка.
     */
    private ImageIcon scaleImageIcon(String iconPath) {
        Image scaledImage = new ImageIcon(iconPath).getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }

    /**
     * Устанавливает новый путь в текстовое поле.
     *
     * @param newPath новый путь.
     */
    public void setCurrentPath(String newPath) {
        directoryField.setText(newPath);
    }


    /**
     * Возвращает путь в текстовое поле.
     *
     * @return путь в текстовое поле.
     */
    public String getCurrentPath() {
        return directoryField.getText();
    }


    // ======== Геттеры для доступа к компонентам ========

    public JButton getBackBtn() {
        return backBtn;
    }

    public JButton getForwardBtn() {
        return forwardBtn;
    }

    public JButton getUpperBtn() {
        return upperBtn;
    }

    public JButton getRefreshBtn() {
        return refreshBtn;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JTextField getDirectoryField() {
        return directoryField;
    }

    public JPanel getTopMenuComponent() {
        return topMenuComponent;
    }
}
