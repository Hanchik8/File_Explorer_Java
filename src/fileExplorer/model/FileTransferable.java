package fileExplorer.model;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.util.List;

/**
 * Класс, реализующий интерфейс Transferable, предназначенный для хранения и передачи файлов через буфер обмена.
 * Он используется для обработки операции копирования/вырезания файлов в приложении.
 */
public class FileTransferable implements Transferable {
    private final File file;

    /**
     * Конструктор, который инициализирует объект с файлом для передачи через буфер обмена.
     * @param file Файл, который будет передан через буфер обмена.
     */
    public FileTransferable(File file) {
        this.file = file;
    }

    /**
     * Возвращает массив типов данных, которые поддерживает данный объект для передачи.
     * В данном случае поддерживается только тип javaFileListFlavor.
     * @return Массив поддерживаемых типов данных.
     */
    @Override
    public DataFlavor[] getTransferDataFlavors() {
        return new DataFlavor[] { DataFlavor.javaFileListFlavor };
    }

    /**
     * Проверяет, поддерживает ли объект передачу данных в указанном формате.
     * @param flavor Тип данных для проверки.
     * @return true, если формат поддерживается, иначе false.
     */
    @Override
    public boolean isDataFlavorSupported(DataFlavor flavor) {
        return DataFlavor.javaFileListFlavor.equals(flavor);
    }

    /**
     * Возвращает данные, которые будут переданы. В данном случае — список файлов.
     * @param flavor Тип данных, который требуется передать.
     * @return Список файлов, которые были переданы.
     * @throws UnsupportedFlavorException Если формат данных не поддерживается.
     */
    @Override
    public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException {
        if (!isDataFlavorSupported(flavor)) {
            throw new UnsupportedFlavorException(flavor);
        }
        return List.of(file);
    }
}
