package vn.com.viettel.core.utils;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtils {
    private static Logger logger = Logger.getLogger(FileUtils.class);

    public static File saveMultiPartFile(MultipartFile file, String folder) throws IOException {
        File dir = new File(folder);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String originalFilename = URLEncoder.encode(file.getOriginalFilename(), "UTF-8");
        String fileType = FilenameUtils.getExtension(originalFilename);

        String randomName = new Date().getTime() + "." + fileType;
        File fileItem = new File(folder + File.separator + randomName);
        org.apache.commons.io.FileUtils.writeByteArrayToFile(fileItem, file.getBytes());
        return fileItem;
    }

    public static void deleteFile(File file) {
        if (file != null && file.exists()) {
            try {
                file.delete();
            } catch (Exception e) {
                logger.error(e);
            }
        }
    }

    public static void deleteDirectory(File folder) {
        if (folder != null && folder.exists()) {
            for (File file : folder.listFiles()) {
                deleteFile(file);
            }
            deleteFile(folder);
        }
    }

    public static File findFileWithExtension(File folder, String fileNameExtension)  {
        if (folder != null && folder.exists()) {
            for (File file : folder.listFiles()) {
                if (file.isDirectory()) {
                    return findFileWithExtension(file, fileNameExtension);
                }

                if (file.getName().toLowerCase().endsWith("." + fileNameExtension)) {
                    return file;
                }
            }
        }
        return null;
    }

    public static void unzipFile(File file, File dest) throws IOException {
        unzip(file, dest);
    }

    /**
     * Unzips the give source file in the target directory
     */
    private static void unzip(File file, File target) throws IOException {
        try (final ZipFile zipFile = new ZipFile(file)) {
            final Enumeration entries = zipFile.entries();
            final byte[] buffer = new byte[4096];
            while (entries.hasMoreElements()) {
                final ZipEntry entry = (ZipEntry) entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }
                final File path = new File(target, entry.getName());
                if (path.exists()) {
                    continue;
                }
                final File directory = path.getParentFile();
                if (directory != null && !directory.exists()) {
                    directory.mkdirs();
                }
                // Copy the file. Note: no need for a BufferedOutputStream,
                // since we are already using a buffer of type byte[4096].
                try (InputStream in = zipFile.getInputStream(entry); OutputStream out = new FileOutputStream(path)) {
                    int len;
                    while ((len = in.read(buffer)) >= 0) {
                        out.write(buffer, 0, len);
                    }
                }
                // Call 'deleteOnExit' only after after we closed the file,
                // because this method will save the modification time.
                // deleteOnExit(path, false);
            }
        }
    }

    public static File createFolder(String folder, String subFolder) {
        File tempFolder = new File(folder, subFolder);
        boolean exists = tempFolder.exists();
        if (!exists) {
            tempFolder.mkdirs();
        }

        return tempFolder;
    }
}
