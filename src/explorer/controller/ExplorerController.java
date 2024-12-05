package explorer.controller;

import explorer.model.DirectoryManagement;
import explorer.model.FileManipulation;
import explorer.view.ExplorerView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class ExplorerController {
    private ExplorerView explorerView; // представление для отображения данных
    private FileManipulation fileManipulation; // модель для работы с файлами
    private DirectoryManagement directoryManagement; // модель для работы с каталогами

    // Конструктор контроллера
    public ExplorerController(ExplorerView explorerView) {
        this.explorerView = explorerView;
        this.fileManipulation = new FileManipulation(explorerView); // создаем модель для работы с файлами
        directoryManagement = new DirectoryManagement(explorerView); // создаем модель для работы с каталогами

        // Создаем контроллер для работы с файлами
        new explorer.controller.FileController(explorerView, directoryManagement, fileManipulation);

        // Инициализация начальных данных
        directoryManagement.updateDirectory(); // обновляем данные каталога

        // Устанавливаем обработчики событий
        setupDirectoryFieldListener(); // обрабатываем действия с полем ввода пути
        setupNavigationButtonListeners(); // обрабатываем нажатия на кнопки навигации
    }

    // Метод для установки обработчика события для текстового поля с путем
    private void setupDirectoryFieldListener() {
        ActionListener directoryFieldListener = new DirectoryFieldListener(); // создаем обработчик
        explorerView.getTopMenu().getDirectoryField().addActionListener(directoryFieldListener); // добавляем обработчик к текстовому полю
    }

    // Класс-обработчик для поля ввода пути
    private class DirectoryFieldListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Получаем путь, введенный в текстовое поле
            File selectedFile = new File(explorerView.getTopMenu().getDirectoryField().getText());
            // Проверяем, что это каталог
            if (selectedFile.isDirectory()) {
                // Если это каталог, обновляем отображение каталога
                directoryManagement.updateDirectory(selectedFile.getAbsolutePath());
                // Отключаем кнопку, пока путь не выбран
                fileManipulation.updateBtnState(false);
            }
        }
    }

    // Метод для установки обработчиков для кнопок навигации
    private void setupNavigationButtonListeners() {
        // Для каждой кнопки создаем свой обработчик нажатия
        explorerView.getTopMenu().getUpperBtn().addMouseListener(new UpperButtonListener()); // Кнопка "Вверх"
        explorerView.getTopMenu().getBackBtn().addMouseListener(new BackButtonListener()); // Кнопка "Назад"
        explorerView.getTopMenu().getForwardBtn().addMouseListener(new ForwardButtonListener()); // Кнопка "Вперед"
        explorerView.getTopMenu().getRefreshBtn().addMouseListener(new RefreshButtonListener()); // Кнопка "Обновить"
    }

    // Обработчик для кнопки "Вверх"
    private class UpperButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Переходим в родительский каталог
            directoryManagement.moveToParentDirectory();
            // Обновляем состояние кнопок
            fileManipulation.updateBtnState(false);
        }
    }

    // Обработчик для кнопки "Назад"
    private class BackButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Отменяем последнее изменение (переходим назад по истории)
            directoryManagement.undo();
            // Обновляем состояние кнопок
            fileManipulation.updateBtnState(false);
        }
    }

    // Обработчик для кнопки "Вперед"
    private class ForwardButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Повторяем последнее изменение (переходим вперед по истории)
            directoryManagement.redo();
            // Обновляем состояние кнопок
            fileManipulation.updateBtnState(false);
        }
    }

    // Обработчик для кнопки "Обновить"
    private class RefreshButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            // Обновляем отображение каталога
            directoryManagement.updateDirectory();
            // Обновляем состояние кнопок
            fileManipulation.updateBtnState(false);
        }
    }
}
