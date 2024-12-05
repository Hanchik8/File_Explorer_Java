package explorer.view.viewComponents;

import javax.swing.*;
import java.awt.*;

/**
 * Основная панель для отображения списка файлов.
 */
public class MainPanel {
    private JPanel mainPanel; // Панель для всего содержимого
    private DefaultListModel<String> fileListModel; // Модель данных для списка файлов
    private JList<String> fileList; // Список файлов

    public MainPanel() {
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new JScrollPane(createFileList()), BorderLayout.CENTER);
    }

    /**
     * Создаёт JList для отображения файлов.
     * @return JList для отображения списка файлов.
     */
    private JList<String> createFileList() {
        fileListModel = new DefaultListModel<>();
        fileList = new JList<>(fileListModel);
        return fileList;
    }

    /**
     * Обновляет список файлов в модели с новыми данными.
     * @param fileNamesList массив имен файлов для обновления.
     */
    public void updateFileListModel(String[] fileNamesList) {
        System.out.println("Updating file list with " + fileNamesList.length + " items."); // Для отладки
        fileListModel.clear();
        for (String filename : fileNamesList) {
            fileListModel.addElement(filename);
        }
    }

    // ======== Геттеры для доступа к компонентам ========

    public JList<String> getFileList() {
        return fileList;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
