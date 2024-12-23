package fileExplorer.view.viewComponents;

import fileExplorer.utils.iconProviders.SidebarIconProvider;
import fileExplorer.utils.iconRenderers.SidebarListRenderer;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Панель бокового меню, которая отображает категории, такие как "Desktop", "Downloads", "Music" и т.д.,
 * а также дерево файловой системы.
 */
public class SidebarPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private JList<String> categoryList;
    private JTreePanel jTreePanel;

    /**
     * Конструктор класса. Создаёт панель бокового меню, состоящую из категории и дерева файлов.
     */
    public SidebarPanel() {
        setLayout(new BorderLayout());

        JPanel categoryPanel = createCategoryPanel();
        createJTreePanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                categoryPanel, new JScrollPane(jTreePanel.getFileTree()));
        splitPane.setDividerLocation(200);

        add(splitPane);
    }

    /**
     * Создаёт панель категорий.
     * Включает такие категории, как "Desktop", "Downloads", "Music", "Pictures", "Documents", "Videos".
     * Категория "My Computer" всегда присутствует.
     * Только существующие директории добавляются в список.
     * @return панель с категориями
     */
    private JPanel createCategoryPanel() {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BorderLayout());

        categoryPanel.setBackground(new Color(45, 45, 55));
        categoryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        ArrayList<String> categories = new ArrayList<>(List.of("Desktop", "Downloads", "Music", "Pictures", "Documents", "Videos"));

        categories.removeIf(category -> !new File(System.getProperty("user.home"), category).exists());
        categories.add("My Computer");

        categoryList = new JList<>(categories.toArray(new String[0]));
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryList.setCellRenderer(new SidebarListRenderer(new SidebarIconProvider()));

        categoryPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER);
        categoryList.setBackground(new Color(55, 55, 65));
        categoryList.setForeground(Color.WHITE);
        categoryList.setSelectionBackground(new Color(147, 147, 147));
        categoryList.setSelectionForeground(Color.WHITE);
        categoryList.setFont(new Font("SansSerif", Font.PLAIN, 14));

        return categoryPanel;
    }

    /**
     * Создаёт панель с деревом файлов.
     * @return панель с деревом файлов
     */
    private JTreePanel createJTreePanel() {
        return jTreePanel = new JTreePanel();
    }

    /**
     * Возвращает список категорий.
     * @return список категорий
     */
    public JList<String> getCategoryList() {
        return categoryList;
    }

    /**
     * Возвращает панель с деревом файлов.
     * @return панель с деревом файлов
     */
    public JTreePanel getjTreePanel() {
        return jTreePanel;
    }
}
