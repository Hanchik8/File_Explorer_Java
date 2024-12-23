package fileExplorer.view.viewComponents;

import fileExplorer.utils.iconProviders.FileIconProvider;
import fileExplorer.utils.iconRenderers.FileListRenderer;

import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.JList;

import java.awt.BorderLayout;

/**
 * Панель, расположенная в центре окна, предназначенная для отображения списка файлов.
 * Содержит компонент {@link JList}, который отображает файлы с соответствующими иконками.
 */
public class CenterPanel extends JPanel {
    private static final long serialVersionUID = 1L;
    private DefaultListModel<String> fileListModel;
    private JList<String> fileList;
    private final FileListRenderer fileListRenderer;

    /**
     * Конструктор класса. Инициализирует панель с компонентом для отображения списка файлов.
     */
    public CenterPanel() {
        setLayout(new BorderLayout());
        fileListRenderer = new FileListRenderer(new FileIconProvider());
        add(createFileList());
    }

    /**
     * Создаёт компонент {@link JList} с моделью списка {@link DefaultListModel}.
     * Настроен с использованием рендерера для отображения иконок файлов.
     * @return компонент {@link JList} для отображения списка файлов
     */
    private JList<String> createFileList() {
        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        fileList.setCellRenderer(fileListRenderer);
        return fileList;
    }

    /**
     * Обновляет модель списка файлов с переданным массивом имён файлов.
     * Очистит текущие элементы списка и добавит новые.
     * @param fileNamesList массив строк, содержащий имена файлов для обновления списка
     */
    public void updateFileListModel(String[] fileNamesList) {
        fileListModel.clear();
        for (String filename : fileNamesList) {
            fileListModel.addElement(filename);
        }

        fileList.revalidate();
        fileList.repaint();
    }

    /**
     * Получает компонент {@link JList} для работы с ним извне.
     * @return компонент {@link JList} для списка файлов
     */
    public JList<String> getFileList() {
        return fileList;
    }
}
