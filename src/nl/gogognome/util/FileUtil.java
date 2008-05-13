/*
 * $Id: FileUtil.java,v 1.1 2008-05-13 19:28:05 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * This class contains utility methods for files. 
 */
public class FileUtil {

    /** Private constructor. Use static methods only. */
    private FileUtil() {
        // should never be called
    }
    
    /**
     * Copies a file.
     * @param src the source file
     * @param dst the destination file
     * @throws IOException if a problem occurs while copying
     */
    public static void copyFile(File src, File dst) throws IOException {
        BufferedInputStream inputStream = null;
        BufferedOutputStream outputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(src));
            outputStream = new BufferedOutputStream(new FileOutputStream(dst));
            byte[] buffer = new byte[16 * 1024];
            int size = inputStream.read(buffer);
            while (size != -1) {
                outputStream.write(buffer, 0, size);
                size = inputStream.read(buffer);
            }
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }
}
