package fileExplorer.view.viewComponents;

import fileExplorer.model.SidebarIconProvider;
import fileExplorer.model.SidebarListRenderer;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import java.awt.*;

/**
 * Sidebar panel that displays categories like Downloads, Music, Images,
 * Documents, and Videos.
 */
public class SidebarPanel extends JPanel {
    private JList<String> categoryList;
    private JTreePanel jTreePanel;

    public SidebarPanel() {
        setLayout(new BorderLayout());

        JPanel categoryPanel = createCategoryPanel();
        createJTreePanel();

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                categoryPanel, new JScrollPane(jTreePanel.getFileTree()));
        splitPane.setDividerLocation(200);

        add(splitPane);
    }

    private JPanel createCategoryPanel() {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BorderLayout());

        categoryPanel.setBackground(new Color(45, 45, 55));
        categoryPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[] categories = { "Downloads", "Music", "Images", "Documents", "Videos" };

        categoryList = new JList<>(categories);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryList.setCellRenderer(new SidebarListRenderer(new SidebarIconProvider()));

        categoryPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER);
        categoryList.setBackground(new Color(55, 55, 65));
        categoryList.setForeground(Color.WHITE);
        categoryList.setSelectionBackground(new Color(147, 147, 147)); // Highlight color
        categoryList.setSelectionForeground(Color.WHITE);
        categoryList.setFont(new Font("SansSerif", Font.PLAIN, 14));

        return categoryPanel;
    }

    private JTreePanel createJTreePanel() {
        return jTreePanel = new JTreePanel();
    }

    public JList<String> getCategoryList() {
        return categoryList;
    }

    public JTreePanel getjTreePanel() {
        return jTreePanel;
    }
}
