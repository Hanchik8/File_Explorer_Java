//package src.explorer.controller;
//
//import src.explorer.model.DirectoryManagement;
//import src.explorer.model.FileManipulation;
//import src.explorer.view.ExplorerView;
//
//import java.awt.event.*;
//import java.io.File;
//import java.lang.ref.SoftReference;
//
//public class MainController {
//    private ExplorerView explorerView;
//    private DirectoryManagement directoryManagement;
//    private FileManipulation fileManipulation;
//    public MainController(ExplorerView explorerView) {
//        this.explorerView = explorerView;
//        directoryManagement = new DirectoryManagement(explorerView);
//        fileManipulation = new FileManipulation(explorerView);
//        changeDirectory(directoryManagement.getInitialDirectories(), directoryManagement.getCurrentDirectory());
//
//        explorerView.getTopMenu().getDirectoryField().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                File selectedFile = new File(explorerView.getTopMenu().getDirectoryField().getText());
//                if (selectedFile.isDirectory()) {
//                    File[] contents = directoryManagement.getDirectoryContents(selectedFile.getAbsolutePath());
//                    changeDirectory(contents, selectedFile.getAbsolutePath());
//                }
//            }
//        });
//
//        explorerView.getMainPanel().getFileList().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                String selectedName = explorerView.getMainPanel().getFileList().getSelectedValue();
//                String currentPath = directoryManagement.getCurrentDirectory();
//
//                File selectedFile;
//                if ("Root".equals(currentPath)) {
//                    selectedFile = new File(selectedName);
//                } else {
//                    selectedFile = new File(currentPath, selectedName);
//                }
//                if (e.getClickCount() == 2) {
//                    if (selectedFile.isDirectory()) {
//                        File[] contents = directoryManagement.getDirectoryContents(selectedFile.getAbsolutePath());
//                        directoryManagement.getRedoStack().clear();
//                        changeDirectory(contents, selectedFile.getAbsolutePath());
//                    } else if (selectedFile.isFile()) {
//                        fileManipulation.openFile(selectedFile);
//                    }
//                } else {
//                    explorerView.getFileDetailsPanel().updateFileDetailsPanel(selectedFile, directoryManagement.getFileExtension(selectedName));
//                    if (selectedFile.isDirectory()) {
//                        explorerView.getEditPanel().getCopyBtn().setEnabled(false);
//                        explorerView.getEditPanel().getCutBtn().setEnabled(false);
//                        explorerView.getEditPanel().getDeleteBtn().setEnabled(false);
//                    } else {
//                        explorerView.getEditPanel().getCopyBtn().setEnabled(true);
//                        explorerView.getEditPanel().getCutBtn().setEnabled(true);
//                        explorerView.getEditPanel().getDeleteBtn().setEnabled(true);
//                    }
//                }
//            }
//        });
//
//        explorerView.getEditPanel().getDetailsCheckBox().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (explorerView.getEditPanel().getDetailsCheckBox().isSelected()) {
//                    explorerView.getFileDetailsPanel().getFileDetailsPanel().setVisible(true);
//                } else {
//                    explorerView.getFileDetailsPanel().getFileDetailsPanel().setVisible(false);
//                }
//
//            }
//        });
//
//        explorerView.getTopMenu().getUpperBtn().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//
//                File[] fileList = directoryManagement.moveToParentDirectory();
//                changeDirectory(fileList, directoryManagement.getCurrentDirectory());
//            }
//        });
//
//        explorerView.getTopMenu().getBackBtn().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//
//                File[] fileList = directoryManagement.undo();
//                changeDirectory(fileList, directoryManagement.getCurrentDirectory());
//            }
//        });
//
//        explorerView.getTopMenu().getForwardBtn().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//
//                File[] fileList = directoryManagement.redo();
//                changeDirectory(fileList, directoryManagement.getCurrentDirectory());
//            }
//        });
//
//        explorerView.getTopMenu().getRefreshBtn().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//
//                File[] fileList = directoryManagement.getDirectoryContents(directoryManagement.getCurrentDirectory());
//                changeDirectory(fileList, directoryManagement.getCurrentDirectory());
//            }
//        });
//
//        explorerView.getEditPanel().getNewComboBox().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String newFile = String.valueOf(explorerView.getEditPanel().getNewComboBox().getSelectedItem());
//                File parentDirectory = new File(directoryManagement.getCurrentDirectory());
//                fileManipulation.createFile(parentDirectory, newFile);
//                explorerView.getEditPanel().getNewComboBox().setSelectedItem("Create");
//            }
//        });
//
//        explorerView.getEditPanel().getCopyBtn().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                String selectedName = explorerView.getMainPanel().getFileList().getSelectedValue();
//                String currentPath = directoryManagement.getCurrentDirectory();
//
//                File selectedFile;
//                if ("Root".equals(currentPath)) {
//                    selectedFile = new File(selectedName);
//                } else {
//                    selectedFile = new File(currentPath, selectedName);
//                }
//                fileManipulation.copyFile(selectedFile);
//                explorerView.getEditPanel().getPasteBtn().setEnabled(true);
//            }
//        });
//
//        explorerView.getEditPanel().getPasteBtn().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                File currentDirectory = new File(directoryManagement.getCurrentDirectory());
//                fileManipulation.pasteFile(currentDirectory);
//            }
//        });
//
//        explorerView.getEditPanel().getCutBtn().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                String selectedName = explorerView.getMainPanel().getFileList().getSelectedValue();
//                String currentPath = directoryManagement.getCurrentDirectory();
//
//                File selectedFile;
//                if ("Root".equals(currentPath)) {
//                    selectedFile = new File(selectedName);
//                } else {
//                    selectedFile = new File(currentPath, selectedName);
//                }
//                fileManipulation.cutFile(selectedFile);
//
//                explorerView.getEditPanel().getCutBtn().setEnabled(false);
//                explorerView.getEditPanel().getPasteBtn().setEnabled(true);
//            }
//        });
//
//        explorerView.getEditPanel().getDeleteBtn().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                String selectedName = explorerView.getMainPanel().getFileList().getSelectedValue();
//                String currentPath = directoryManagement.getCurrentDirectory();
//
//                File selectedFile;
//                if ("Root".equals(currentPath)) {
//                    selectedFile = new File(selectedName);
//                } else {
//                    selectedFile = new File(currentPath, selectedName);
//                }
//                fileManipulation.deleteFile(selectedFile);
//
//                explorerView.getEditPanel().getDeleteBtn().setEnabled(false);
//            }
//        });
//    }
//
//    private void changeDirectory(File[] files, String newPath) {
//        if (files != null) {
//            String[] fileNames = new String[files.length];
//            if (newPath.equals("Root")) {
//                for (int i = 0; i < files.length; i++) {
//                    fileNames[i] = files[i].getAbsolutePath();
//                }
//                explorerView.getTopMenu().getUpperBtn().setEnabled(false);
//            } else {
//                for (int i = 0; i < files.length; i++) {
//                    fileNames[i] = files[i].getName();
//                }
//                explorerView.getTopMenu().getUpperBtn().setEnabled(true);
//            }
//            explorerView.getMainPanel().updateFileListModel(fileNames);
//            explorerView.getTopMenu().setCurrentPath(newPath);
//
//            if (directoryManagement.getRedoStack().isEmpty()) {
//                explorerView.getTopMenu().getForwardBtn().setEnabled(false);
//            } else {
//                explorerView.getTopMenu().getForwardBtn().setEnabled(true);
//            }
//            if (directoryManagement.getUndoStack().isEmpty()) {
//                explorerView.getTopMenu().getBackBtn().setEnabled(false);
//            } else {
//                explorerView.getTopMenu().getBackBtn().setEnabled(true);
//            }
//        }
//    }
//}
