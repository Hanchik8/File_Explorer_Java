package fileExplorer.controller.listeners;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.view.MainView;

import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.event.MouseAdapter;

/**
 * Слушатель для событий раскрытия/сворачивания узлов дерева в интерфейсе.
 * Обрабатывает событие раскрытия узла дерева и загружает содержимое директории, если это необходимо.
 */
public class JTreeExpansionListener extends MouseAdapter implements TreeExpansionListener {
    private DirectoryManagementModel directoryModel;
    private MainView mainView;

    /**
     * Конструктор для создания слушателя, который обрабатывает события раскрытия узлов дерева.
     * @param mainView       основное окно приложения.
     * @param directoryModel модель управления директориями.
     */
    public JTreeExpansionListener(MainView mainView, DirectoryManagementModel directoryModel) {
        this.directoryModel = directoryModel;
        this.mainView = mainView;
    }

    /**
     * Метод, вызываемый при раскрытии узла дерева.
     * Загружает содержимое директории, если узел содержит запись "Loading..." и в нем нет детей.
     * @param event событие раскрытия узла дерева.
     */
    @Override
    public void treeExpanded(TreeExpansionEvent event) {
        TreePath path = event.getPath();
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) path.getLastPathComponent();
        if (node.getChildCount() == 1) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) node.getChildAt(0);
            if ("Loading...".equals(childNode.getUserObject())) {
                node.removeAllChildren();
                String directoryName = node.getUserObject().toString();
                mainView.getSidebarPanel().getjTreePanel().addFilesToNode(node, directoryModel.updateDirectory(directoryName));
            }
        }
    }

    /**
     * Метод, вызываемый при сворачивании узла дерева.
     * В текущей реализации не используется.
     * @param event событие сворачивания узла дерева.
     */
    @Override
    public void treeCollapsed(TreeExpansionEvent event) {

    }
}
