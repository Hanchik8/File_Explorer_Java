package fileExplorer.view.viewComponents;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import fileExplorer.controller.SidebarController;
import fileExplorer.view.MainView;

import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * Sidebar panel that displays categories like Downloads, Music, Images, Documents, and Videos.
 */
public class SidebarPanel extends JPanel {
    private JList<String> categoryList;

    public SidebarPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, getHeight()));

        // Create and add the category panel
        JPanel categoryPanel = createCategoryPanel();
        add(categoryPanel, BorderLayout.CENTER);
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

    /**
     * Returns the category list component.
     * 
     * @return JList<String> representing the category list
     */
    public JList<String> getCategoryList() {
        return categoryList;
    }
}
