package fileExplorer.view.viewComponents;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.awt.Dimension;

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

        // Categories for the list
        String[] categories = { "Downloads", "Music", "Images", "Documents", "Videos" };

        // Create JList for categories
        categoryList = new JList<>(categories);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Wrap the list in a JScrollPane
        categoryPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER);

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
