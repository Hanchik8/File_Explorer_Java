package fileExplorer.view.viewComponents;

import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import java.io.File;
import java.io.Serializable;

/**
 * Панель с деревом файлов для отображения структуры каталогов.
 * Использует {@link JTree} для визуализации структуры каталогов и файлов.
 */
public class JTreePanel implements Serializable {
    private static final long serialVersionUID = 1L;
    private JTree fileTree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode mainRoot;

    /**
     * Конструктор класса. Инициализирует корневой узел дерева и модель дерева.
     * Создаёт дерево с корневым узлом "My Computer".
     */
    public JTreePanel() {
        mainRoot = new DefaultMutableTreeNode("My Computer");
        treeModel = new DefaultTreeModel(mainRoot);
        fileTree = new JTree(treeModel);
    }

    /**
     * Генерирует корневые узлы для списка начальных директорий.
     * Каждый каталог добавляется в дерево как новый корневой узел.
     * Для каждого каталога создаётся дочерний узел "Loading...", пока не будет загружено содержимое.
     * @param initialDirectories начальные директории
     */
    public void generateRoots(File[] initialDirectories) {
        for (File directory : initialDirectories) {
            DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode(directory.getAbsoluteFile());
            mainRoot.add(rootNode);

            rootNode.add(new DefaultMutableTreeNode("Loading..."));
        }
    }

    /**
     * Добавляет файлы и каталоги в указанный узел дерева.
     * Для каждого файла создаётся дочерний узел. Если файл является директорией,
     * добавляется дочерний узел "Loading..." для дальнейшей загрузки содержимого.
     * @param parentNode родительский узел
     * @param directoryContent содержимое директории
     */
    public void addFilesToNode(DefaultMutableTreeNode parentNode, File[] directoryContent) {
        if (directoryContent != null) {
            for (File file : directoryContent) {
                DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(file.getAbsolutePath());
                parentNode.add(childNode);
                if (file.isDirectory()) {
                    childNode.add(new DefaultMutableTreeNode("Loading..."));
                }
            }
        }
        treeModel.reload(parentNode);
    }

    /**
     * Обновляет узел, представляющий указанную директорию, с новым списком файлов.
     * Все старые дочерние узлы удаляются, и добавляются новые файлы.
     * @param directory директория, которую необходимо обновить
     * @param fileList новые файлы для отображения
     */
    public void updateDirectoryNode(File directory, File[] fileList) {
        DefaultMutableTreeNode targetNode = findNode(mainRoot, directory);
        if (targetNode != null) {
            targetNode.removeAllChildren();
            addFilesToNode(targetNode, fileList);
        }
    }

    /**
     * Находит узел, представляющий указанную директорию, в дереве.
     * @param rootNode корневой узел для поиска
     * @param directory директория, для которой нужно найти узел
     * @return узел, представляющий директорию, или null, если не найдено
     */
    private DefaultMutableTreeNode findNode(DefaultMutableTreeNode rootNode, File directory) {
        if (rootNode.getUserObject().equals(directory.getAbsolutePath())) {
            return rootNode;
        }

        for (int i = 0; i < rootNode.getChildCount(); i++) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) rootNode.getChildAt(i);
            DefaultMutableTreeNode result = findNode(childNode, directory);
            if (result != null) {
                return result;
            }
        }

        return null;
    }

    /**
     * Получает дерево файлов.
     * @return дерево файлов
     */
    public JTree getFileTree() {
        return fileTree;
    }
}
