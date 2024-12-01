package src.fileExplorer.controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JTextField;

import src.fileExplorer.model.DirectoryManagementModel;
import src.fileExplorer.model.FileManipulationModel;
import src.fileExplorer.view.MainView;

public class MainController {
    private MainView mainView;
    private FileManipulationModel fileModel;
    private DirectoryManagementModel directoryModel;

    public MainController(MainView mainView) {
        this.mainView = mainView;
        this.fileModel = new FileManipulationModel(mainView);
        directoryModel = new DirectoryManagementModel(mainView);

        new FileManipulationController(mainView, directoryModel, fileModel);

        // Инициализация начальных данных
        directoryModel.updateDirectory();

        // Устанавливаем обработчики событий
        setupDirectoryFieldListener();
        setupNavigationButtonListeners();
        setupSearchFieldListeners();
    }

    // Обработчик для текстового поля с путем
    private void setupDirectoryFieldListener() {
        ActionListener directoryFieldListener = new DirectoryFieldListener();
        mainView.getTopMenu().getDirectoryField().addActionListener(directoryFieldListener);
    }

    private class DirectoryFieldListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            File selectedFile = new File(mainView.getTopMenu().getDirectoryField().getText());
            if (selectedFile.isDirectory()) {
                directoryModel.updateDirectory(selectedFile.getAbsolutePath());
                mainView.updateBtnState(false);
            }
        }
    }

    // Обработчики для кнопок навигации (вверх, назад, вперед, обновить)
    private void setupNavigationButtonListeners() {
        mainView.getTopMenu().getUpperBtn().addMouseListener(new UpperButtonListener());
        mainView.getTopMenu().getBackBtn().addMouseListener(new BackButtonListener());
        mainView.getTopMenu().getForwardBtn().addMouseListener(new ForwardButtonListener());
        mainView.getTopMenu().getRefreshBtn().addMouseListener(new RefreshButtonListener());
    }

    private class UpperButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            directoryModel.moveToParentDirectory();
        }
    }

    private class BackButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            directoryModel.undo();
        }
    }

    private class ForwardButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            directoryModel.redo();
        }
    }

    private class RefreshButtonListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            directoryModel.updateDirectory();
        }
    }

    // Обработчик для текстового поля поиска
    private void setupSearchFieldListeners() {
        mainView.getTopMenu().getSearchField().addFocusListener(new SearchFieldListener());
    }

    private class SearchFieldListener implements FocusListener {
        private final String placeholderText = "Search...";
        private final Color placeholderColor = Color.GRAY;
        private final Color normalColor = Color.BLACK;

        @Override
        public void focusGained(FocusEvent e) {
            JTextField field = (JTextField) e.getSource();
            if (field.getText().equals(placeholderText)) {
                field.setText("");
                field.setForeground(normalColor);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            JTextField field = (JTextField) e.getSource();
            if (field.getText().isEmpty()) {
                field.setText(placeholderText);
                field.setForeground(placeholderColor);
            }
        }
    }
}
