package ku.cs.services;

import javafx.scene.Node;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public final class FileUploader {
    private String fileTypeDescription;
    private String[] fileTypes;
    private int num_fileTypes;
    private String destPath;
    private File file;

    public FileUploader(String destPath) {
        this.destPath = destPath;
        this.num_fileTypes = 0;
        this.fileTypeDescription = "";
    }

    public void setFileTypeDescription(String fileTypeDescription) {
        this.fileTypeDescription = fileTypeDescription;
    }

    public void setDestPath(String destPath) {
        this.destPath = destPath;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public void addFileType(String type) {
        String newFileTypes[] = new String[num_fileTypes + 1];
        int i;
        for (i = 0; i < num_fileTypes; i++) {
            newFileTypes[i] = fileTypes[i];
        }
        newFileTypes[num_fileTypes] = type;
        num_fileTypes++;
        fileTypes = newFileTypes;
    }

    public File fileChooserUpload(Node source) {
        FileChooser chooser = new FileChooser();
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(fileTypeDescription, fileTypes));
        return chooser.showOpenDialog(source.getScene().getWindow());
    }

    public void saveFile(String fileName) throws IOException {
        File destDir = new File(destPath);
        if (!destDir.exists()) destDir.mkdirs();
        Path target = FileSystems.getDefault().getPath(destDir.getAbsolutePath() + System.getProperty("file.separator") + fileName);
        Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
    }
}
