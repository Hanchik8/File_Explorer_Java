package src.fileExplorer.view;

import javax.swing.*;
import java.awt.*;

/**
 * Центральная панель для отображения списка файлов.
 */
public class CenterPanel {
    private JPanel mainPanel; // Панель для всего содержимого
    private DefaultListModel<String> fileListModel; // Модель данных для списка файлов
    private JList<String> fileList; // Список файлов
    public CenterPanel() {
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
