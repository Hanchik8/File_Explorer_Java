package fileExplorer.controller.listeners;

import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.model.enums.SortCriteria;

import javax.swing.JComboBox;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.io.File;

/**
 * Слушатель для обработки изменений в выпадающем меню сортировки файлов.
 * При выборе критерия сортировки обновляется и сортируется список файлов в текущей директории.
 */
public class SortComboBoxListener implements PopupMenuListener {
    private DirectoryManagementModel directoryModel;

    /**
     * Конструктор класса.
     * @param directoryModel модель управления директориями.
     */
    public SortComboBoxListener(DirectoryManagementModel directoryModel) {
        this.directoryModel = directoryModel;
    }

    /**
     * Метод, вызываемый, когда меню сортировки становится невидимым (когда пользователь выбирает критерий).
     * Обновляет и сортирует файлы в текущей директории согласно выбранному критерию сортировки.
     * @param e событие всплывающего меню.
     */
    @Override
    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
        @SuppressWarnings("unchecked")
        JComboBox<String> source = (JComboBox<String>) e.getSource();
        String selectedCriteria = (String) source.getSelectedItem();

        SortCriteria criteria = SortCriteria.valueOf(selectedCriteria.toUpperCase());

        String currentPath = directoryModel.getCurrentDirectory();
        File[] files = directoryModel.listDirectoryContent(new File(currentPath));
        if (files != null) {
            File[] sortedFiles = directoryModel.updateAndSortFileList(files, criteria);
            directoryModel.updateView(sortedFiles, currentPath);
        }
    }

    /**
     * Пустой метод, вызываемый, когда меню становится видимым.
     * @param e событие всплывающего меню.
     */
    @Override
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {

    }

    /**
     * Пустой метод, вызываемый, когда меню отменяется.
     * @param e событие всплывающего меню.
     */
    @Override
    public void popupMenuCanceled(PopupMenuEvent e) {

    }
}
