package fileExplorer.view.viewComponents;

import fileExplorer.model.FileIconProvider;
import fileExplorer.model.FileListRenderer;

import javax.swing.JPanel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

/**
 * Центральная панель для отображения списка файлов.
 */
public class CenterPanel {
    private JPanel centerPanel; // Панель для всего содержимого
    private DefaultListModel<String> fileListModel; // Модель данных для списка файлов
    private JList<String> fileList; // Список файлов
    private FileListRenderer fileListRenderer; // Кастомный рендерер для иконок

    public CenterPanel() {
        centerPanel = new JPanel(new BorderLayout());
        fileListRenderer = new FileListRenderer(new FileIconProvider());
        centerPanel.add(new JScrollPane(createFileList()), BorderLayout.CENTER);
    }

    /**
     * Создаёт JList для отображения файлов.
     * 
     * @return JList для отображения списка файлов.
     */
    private JList<String> createFileList() {
        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        fileList.setCellRenderer(fileListRenderer);
        return fileList;
    }

    /**
     * Обновляет список файлов в модели с новыми данными.
     * 
     * @param fileNamesList массив имен файлов для обновления.
     */
    public void updateFileListModel(String[] fileNamesList) {
        fileListModel.clear();  // Очищаем старые данные
        for (String filename : fileNamesList) {
            fileListModel.addElement(filename);  // Добавляем новые файлы
        }
        
        // После обновления данных обновляем отображение
        fileList.revalidate();  // Обновление компонента
        fileList.repaint();     // Перерисовка компонента
    }

    // ======== Геттеры для доступа к компонентам ========

    public JList<String> getFileList() {
        return fileList;
    }

    public JPanel getCenterPanel() {
        return centerPanel;
    }
}
