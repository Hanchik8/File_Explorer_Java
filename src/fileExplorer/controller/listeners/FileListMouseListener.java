package fileExplorer.controller.listeners;

import fileExplorer.controller.FileManipulationController;
import fileExplorer.model.DirectoryManagementModel;
import fileExplorer.model.FileManipulationModel;
import fileExplorer.view.MainView;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.io.File;

/**
 * Слушатель для кликов по файлам в списке.
 * Обрабатывает двойной клик для открытия файлов или перехода в директорию.
 */
public class FileListMouseListener extends MouseAdapter {
    private final FileManipulationController controller;
    private final DirectoryManagementModel directoryModel;
    private final FileManipulationModel fileModel;
    private final MainView mainView;

    /**
     * Конструктор для создания слушателя, который обрабатывает клики по файлам.
     * @param controller     контроллер манипуляций с файлами.
     * @param directoryModel модель управления директориями.
     * @param fileModel      модель манипуляций с файлами.
     * @param mainView       основное окно приложения.
     */
    public FileListMouseListener(FileManipulationController controller,
            DirectoryManagementModel directoryModel,
            FileManipulationModel fileModel,
            MainView mainView) {
        this.controller = controller;
        this.directoryModel = directoryModel;
        this.fileModel = fileModel;
        this.mainView = mainView;
    }

    /**
     * Метод, вызываемый при клике на файл в списке.
     * Обрабатывает двойной клик для открытия директории или файла.
     * Также обновляет панель с деталями выбранного файла.
     * @param e событие клика мышью.
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        File selectedFile = controller.getSelectedFile();
        if (selectedFile == null)
            return;

        mainView.updateBtnState(true);

        if (e.getClickCount() == 2) {
            if (selectedFile.isDirectory()) {
                directoryModel.updateDirectory(selectedFile.getAbsolutePath());
            } else {
                fileModel.openFile(selectedFile);
            }
        }

        mainView.updateFileDetails(selectedFile, fileModel.getFileExtension(selectedFile));
    }
}
