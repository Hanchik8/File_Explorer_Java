package fileExplorer.model;

import fileExplorer.view.MainView;

import java.awt.Toolkit;
import java.awt.Desktop;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.DataFlavor;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.nio.file.Files;
import java.util.List;

/**
 * Класс для выполнения операций с файлами.
 * Предоставляет методы для открытия, создания, переименования, копирования, вырезания, вставки и удаления файлов.
 * Поддерживает работу с буфером обмена для операций копирования и вырезания файлов.
 */
public class FileManipulationModel {
   private final MainView mainView;
   private File copiedFile;
   private boolean cutPressed = false;

   /**
    * Конструктор класса.
    * @param mainView Главное окно, через которое обновляются элементы интерфейса.
    */
   public FileManipulationModel(MainView mainView) {
      this.mainView = mainView;
   }

   /**
    * Открывает указанный файл с помощью системного приложения.
    * @param file Файл, который нужно открыть.
    */
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

   /**
    * Создает новый файл или директорию в указанной родительской директории.
    * @param parentDirectory Родительская директория, в которой будет создан новый файл/директория.
    * @param fileName Имя нового файла/директории.
    * @param fileExtension Расширение файла (если создается файл).
    */
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

   /**
    * Переименовывает файл или директорию.
    * @param file Файл или директория, которую нужно переименовать.
    * @param newFileName Новое имя для файла или директории.
    */
   public void renameFile(File file, String newFileName) {
      if (newFileName == null || newFileName.isEmpty()) {
         newFileName = getFileNameWithoutExtension(file.getName());
      }

      if (getFileExtension(file) != null) {
         newFileName = newFileName + '.' + getFileExtension(file);
      }

      File parentDirectory = file.getParentFile();
      File newFile = new File(parentDirectory, newFileName);

      if (!file.getName().equals(newFile.getName())) {
         newFile = ensureUniqueFileName(newFile);
      }

      file.renameTo(newFile);
   }

   /**
    * Копирует файл в буфер обмена для дальнейшей операции вставки.
    * @param file Файл, который нужно скопировать.
    */
   public void copyFile(File file) {
      copiedFile = file;
      cutPressed = false;
      FileTransferable transferable = new FileTransferable(file);
      Toolkit.getDefaultToolkit().getSystemClipboard().setContents(transferable, null);
      mainView.updateBtnState(false);
      mainView.getToolbarPanel().getPasteBtn().setEnabled(true);
      mainView.getPopupToolbarPanel().enablePasteItem(true);
   }

   /**
    * Копирует содержимое исходного файла или директории в целевой файл или директорию.
    * @param source Исходный файл или директория.
    * @param target Целевой файл или директория.
    * @throws IOException Если произошла ошибка при копировании данных.
    */
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

   /**
    * Вставляет скопированные или вырезанные файлы в целевую директорию.
    * @param targetDirectory Целевая директория, куда будут вставлены файлы.
    */
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

   /**
    * Подготавливает файл для вырезания, копируя его в буфер обмена и устанавливая флаг cutPressed.
    * @param file Файл, который нужно вырезать.
    */
   public void cutFile(File file) {
      copyFile(file);
      cutPressed = true;
   }

   /**
    * Удаляет файл или директорию.
    * @param fileOrDir Файл или директория, которые нужно удалить.
    */
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

   /**
    * Обеспечивает уникальность имени файла, добавляя номер в случае существования файла с таким именем.
    * @param file Файл, для которого нужно обеспечить уникальность имени.
    * @return Файл с уникальным именем.
    */
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

   /**
    * Возвращает расширение файла.
    * @param file Файл, для которого нужно получить расширение.
    * @return Расширение файла (без точки) или null, если расширение отсутствует.
    */
   public String getFileExtension(File file) {
      String fileName = file.getName();
      int lastDotIndex = fileName.lastIndexOf('.');
      if (lastDotIndex > 0 && lastDotIndex < fileName.length() - 1) {
         return fileName.substring(lastDotIndex + 1);
      }
      return null;
   }

   /**
    * Возвращает имя файла без расширения.
    * @param fileName Имя файла с расширением.
    * @return Имя файла без расширения.
    */
   public String getFileNameWithoutExtension(String fileName) {
      int lastDotIndex = fileName.lastIndexOf('.');
      if (lastDotIndex > 0) {
         return fileName.substring(0, lastDotIndex);
      }
      return fileName;
   }
}
