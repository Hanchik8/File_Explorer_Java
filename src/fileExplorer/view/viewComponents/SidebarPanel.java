package fileExplorer.view.viewComponents;

import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;

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

        String[] categories = { "Downloads", "Music", "Images", "Documents", "Videos" };

        categoryList = new JList<>(categories);
        categoryList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        categoryPanel.add(new JScrollPane(categoryList), BorderLayout.CENTER);
        return categoryPanel;
    }

    private void createJTreePanel() {
        jTreePanel = new JTreePanel();
    }

    public JList<String> getCategoryList() {
        return categoryList;
    }

    public JTreePanel getjTreePanel() {
        return jTreePanel;
    }
}
