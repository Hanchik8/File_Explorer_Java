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
        setPreferredSize(new Dimension(250, getHeight()));

        // Create and add the category panel
        JPanel categoryPanel = createCategoryPanel();
        add(categoryPanel, BorderLayout.NORTH);

        createJTreePanel();
        add(jTreePanel.getFileTree(), BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                categoryPanel, new JScrollPane(jTreePanel.getFileTree()));
        splitPane.setDividerLocation(200);

        add(splitPane);
    }

    /**
     * Creates the category panel with predefined categories.
     * 
     * @return JPanel containing the category list
     */
    private JPanel createCategoryPanel() {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BorderLayout());

        categoryPanel.setBackground(new Color(45, 45, 55));
        categoryPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add padding

        // Categories for the list
        String[] categories = { "Downloads", "Music", "Images", "Documents", "Videos" };

        // Create JList for categories
        categoryList = new JList<>(categories);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        categoryList.setCellRenderer(new SidebarListRenderer(new SidebarIconProvider()));

        // Wrap the list in a JScrollPane
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

    /**
     * Returns the category list component.
     * 
     * @return JList<String> representing the category list
     */
    public JList<String> getCategoryList() {
        return categoryList;
    }

    public JTreePanel getjTreePanel() {
        return jTreePanel;
    }
}
