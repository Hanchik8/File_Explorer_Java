package fileExplorer.model;

import fileExplorer.view.MainView;

import java.awt.Toolkit;
import java.awt.Desktop;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.util.List;

public class FileManipulationModel {
   private final MainView mainView;
   private File copiedFile;
   private boolean cutPressed = false;

   public FileManipulationModel(MainView mainView) {
      this.mainView = mainView;
   }

   public void openFile(File file) {
      if (Desktop.isDesktopSupported()) {
         try {
            Desktop.getDesktop().open(file);
         } catch (IOException e) {
            System.err.println("Error opening file: " + e.getMessage());
         }
      } else {
         System.err.println("Desktop is not supported on this system.");
      }
   }

   public void createFile(File parentDirectory, String fileName, String fileExtension) {
      File newFile = new File(parentDirectory, fileName);
      try {
         if (fileExtension == null) {
            newFile = ensureUniqueFileName(newFile);
            newFile.mkdir();
         } else {
            newFile = ensureUniqueFileName(new File(newFile + "." + fileExtension));
            newFile.createNewFile();
         }
      } catch (IOException e) {
         System.err.println("Error! Unable to create file or directory. " + e.getMessage());
      }
   }

   public void renameFile(File file, String newFileName) {
      if (newFileName == null || newFileName.isEmpty()) {
         newFileName = getFileNameWithoutExtension(file.getName());
      }

      if (getFileExtension(file.getName()) != null) {
         newFileName = newFileName + '.' + getFileExtension(file.getName());
      }

      File parentDirectory = file.getParentFile();
      File newFile = new File(parentDirectory, newFileName);

      if (!file.getName().equals(newFile.getName())) {
         newFile = ensureUniqueFileName(newFile);
      }

      file.renameTo(newFile);
   }

   public void copyFile(File file) {
      copiedFile = file;
      cutPressed = false;
      FileTransferable transferable = new FileTransferable(file);
      Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferable, null);
      mainView.updateBtnState(false);
      mainView.getToolbarPanel().getPasteBtn().setEnabled(true);
      mainView.getPopupToolbarPanel().enablePasteItem(true);
   }

   private void copyFileContent(File source, File target) throws IOException {
      if (source.isDirectory()) {
         if (!target.exists()) {
            target.mkdirs();
         }
         File[] files = source.listFiles();
         if (files != null) {
            for (File child : files) {
               File newTarget = new File(target, child.getName());
               copyFileContent(child, newTarget);
            }
         }
      } else {
         try (InputStream in = new FileInputStream(source);
               OutputStream out = new FileOutputStream(target)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
               out.write(buffer, 0, length);
            }
         }
      }
   }

   public void pasteFile(File targetDirectory) {
      try {
         Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
         Transferable transferable = clipboard.getContents(null);

         if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
            @SuppressWarnings("unchecked")
            List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);

            for (File file : files) {
               File targetFile = ensureUniqueFileName(new File(targetDirectory, file.getName()));

               if (cutPressed) {
                  Files.move(file.toPath(), targetFile.toPath());
                  cutPressed = false;
                  mainView.getToolbarPanel().getPasteBtn().setEnabled(false);
               } else {
                  copyFileContent(file, targetFile);
               }
            }
         } else if (copiedFile != null) {
            File targetFile = ensureUniqueFileName(new File(targetDirectory, copiedFile.getName()));
            if (cutPressed) {
               Files.move(copiedFile.toPath(), targetFile.toPath());
               cutPressed = false;
            } else {
               copyFileContent(copiedFile, targetFile);
            }
         }

      } catch (Exception e) {
         System.err.println("Error pasting file: " + e.getMessage());
      }
   }

   public void cutFile(File file) {
      copyFile(file);
      cutPressed = true;
   }

   public void deleteFile(File fileOrDir) {
      if (fileOrDir.isDirectory()) {
         for (File file : fileOrDir.listFiles()) {
            deleteFile(file);
         }
      }
      if (!fileOrDir.delete()) {
         System.err.println("Failed to delete: " + fileOrDir.getAbsolutePath());
      }
      mainView.updateBtnState(false);
   }

   private File ensureUniqueFileName(File file) {
      String name = file.getName();
      String baseName = name.contains(".") ? name.substring(0, name.lastIndexOf('.')) : name;
      String extension = name.contains(".") ? name.substring(name.lastIndexOf('.')) : "";
      int counter = 1;

      while (file.exists()) {
         String newName = baseName + " (" + counter + ")" + extension;
         file = new File(file.getParentFile(), newName);
         counter++;
      }

      return file;
   }

   public String getFileExtension(String fileName) {
      int lastDotIndex = fileName.lastIndexOf('.');
      if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
         return fileName.substring(lastDotIndex + 1);
      }
      return null;
   }

   public String getFileNameWithoutExtension(String fileName) {
      int lastDotIndex = fileName.lastIndexOf('.');
      if (lastDotIndex > 0) {
         return fileName.substring(0, lastDotIndex);
      }
      return fileName;
   }

   private static class FileTransferable implements Transferable {
      private final File file;

      public FileTransferable(File file) {
         this.file = file;
      }

      @Override
      public DataFlavor[] getTransferDataFlavors() {
         return new DataFlavor[] { DataFlavor.javaFileListFlavor };
      }

      @Override
      public boolean isDataFlavorSupported(DataFlavor flavor) {
         return DataFlavor.javaFileListFlavor.equals(flavor);
      }

      @Override
      public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
         if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
         }
         return List.of(file);
      }
   }
}
