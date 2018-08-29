package com.pgy.common;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * The zip helper.
 *
 * @author Felix
 */
public class ZipHelper {

    public static int unzipToFile(String sourceFile, String destDirPath) throws Exception {
        int size = 0;
        File srcFile = new File(sourceFile);
        if (!srcFile.exists()) {
            throw new RuntimeException("File does not exist:" + srcFile.getPath());
        }
        ZipFile zipFile = null;
        String[] charsetSet = {"GBK", "UTF-8"};
        for (int i = 0; i < charsetSet.length; i++) {
            try {
                zipFile = new ZipFile(srcFile, Charset.forName(charsetSet[i]));
                Enumeration<?> entries = zipFile.entries();
                while (entries.hasMoreElements()) {
                    ZipEntry entry = (ZipEntry) entries.nextElement();
                    if (entry.isDirectory()) {
                        String dirPath = destDirPath + "/" + entry.getName();
                        File dir = new File(dirPath);
                        dir.mkdirs();
                    } else {
                        File targetFile = new File(destDirPath + "/" + entry.getName());
                        if (!targetFile.getParentFile().exists()) {
                            targetFile.getParentFile().mkdirs();
                        }
                        targetFile.createNewFile();
                        InputStream is = zipFile.getInputStream(entry);
                        FileOutputStream fos = new FileOutputStream(targetFile);
                        int len;
                        byte[] buf = new byte[1024];
                        while ((len = is.read(buf)) != -1) {
                            fos.write(buf, 0, len);
                            size += len;
                        }
                        fos.close();
                        is.close();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("unzip error from ZipHelper", e);
            } finally {
                if (zipFile != null) {
                    try {
                        zipFile.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (size != 0) {
                    return size;
                }
            }
        }
        return 0;
    }
}
