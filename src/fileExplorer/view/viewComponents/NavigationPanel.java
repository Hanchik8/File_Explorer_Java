package fileExplorer.view.viewComponents;

import fileExplorer.utils.ImageUtils;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JComponent;

import java.awt.BorderLayout;
import java.awt.Color;

import java.util.LinkedHashMap;

/**
 * Панель навигации, включающая кнопки для управления директорией,
 * поле для отображения текущего пути и поле для поиска.
 * Используется для навигации по файловой системе.
 */
public class NavigationPanel extends JPanel{
    private static final long serialVersionUID = 1L;
    private LinkedHashMap<String, JComponent> topMenuButtons;
    private JTextField directoryField;
    private JTextField searchField;

    /**
     * Конструктор класса. Инициализирует панель с кнопками, полем для пути и полем для поиска.
     * Размещает компоненты в панели с помощью BorderLayout.
     */
    public NavigationPanel() {
        setLayout(new BorderLayout());

        add(createBtnPanel(), BorderLayout.WEST);
        add(createDirectoryField(), BorderLayout.CENTER);
        add(createSearchField(), BorderLayout.EAST);
    }

    /**
     * Создаёт кнопки для управления директорией (назад, вперед, вверх, обновить)
     * и сохраняет их в коллекции {topMenuButtons}.
     */
    private void createButtons() {
        topMenuButtons = new LinkedHashMap<>();
        topMenuButtons.put("back", createButton("resources/images/btnIcons/backBtnIcon.png"));
        topMenuButtons.put("forward", createButton("resources/images/btnIcons/forwardBtnIcon.png"));
        topMenuButtons.put("up", createButton("resources/images/btnIcons/upperBtnIcon.png"));
        topMenuButtons.put("refresh", createButton("resources/images/btnIcons/refreshBtnIcon.png"));
    }

    /**
     * Создаёт панель с кнопками для управления директорией и добавляет их на панель.
     * @return панель с кнопками
     */
    private JPanel createBtnPanel() {
        JPanel btnPanel = new JPanel();

        createButtons();
        for (JComponent button : topMenuButtons.values()) {
            btnPanel.add(button);
        }

        return btnPanel;
    }

    /**
     * Создаёт кнопку с иконкой из указанного пути.
     * @param iconPath путь к иконке
     * @return созданная кнопка
     */
    private JButton createButton(String iconPath) {
        JButton button = new JButton(ImageUtils.getImageIcon(iconPath));
        button.setFocusable(false);

        if (!iconPath.contains("refresh")) {
            button.setEnabled(false);
        }

        return button;
    }

    /**
     * Создаёт поле для отображения текущего пути.
     * @return поле для пути
     */
    private JTextField createDirectoryField() {
        directoryField = new JTextField();
        directoryField.setEditable(true);
        return directoryField;
    }

    /**
     * Создаёт поле для поиска с начальным текстом "Search...".
     * @return поле для поиска
     */
    private JTextField createSearchField() {
        searchField = new JTextField(25);
        searchField.setText("Search...");
        searchField.setForeground(Color.GRAY);

        return searchField;
    }

    /**
     * Получает текущий путь из поля для пути.
     * @return текущий путь
     */
    public String getCurrentPath() {
        return directoryField.getText();
    }

    /**
     * Устанавливает новый путь в поле для пути.
     *
     * @param path новый путь
     */
    public void setCurrentPath(String path) {
        directoryField.setText(path);
    }

    /**
     * Получает кнопку "Назад".
     * @return кнопка "Назад"
     */
    public JComponent getBackBtn() {
        return topMenuButtons.get("back");
    }

    /**
     * Получает кнопку "Вперед".
     * @return кнопка "Вперед"
     */
    public JComponent getForwardBtn() {
        return topMenuButtons.get("forward");
    }

    /**
     * Получает кнопку "Вверх".
     * @return кнопка "Вверх"
     */
    public JComponent getUpperBtn() {
        return topMenuButtons.get("up");
    }

    /**
     * Получает кнопку "Обновить".
     * @return кнопка "Обновить"
     */
    public JComponent getRefreshBtn() {
        return topMenuButtons.get("refresh");
    }

    /**
     * Получает поле для пути.
     * @return поле для пути
     */
    public JTextField getDirectoryField() {
        return directoryField;
    }

    /**
     * Получает поле для поиска.
     * @return поле для поиска
     */
    public JTextField getSearchField() {
        return searchField;
    }

}
