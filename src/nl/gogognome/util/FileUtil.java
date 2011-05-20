/*
    This file is part of gogolib.

    gogolib is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    gogolib is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with gogolib.  If not, see <http://www.gnu.org/licenses/>.
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
     * Copies a file. The last-modification time of <code>dst</code> will be
     * the same as that of <code>src</code>.
     *
     * <p>If <code>dst</code> exists and it has the same last-modification time
     * as <code>src</code> and the same size, then it is assumed that
     * the files are equal. In this case, the method returns immediately.
     *
     * @param src the source file
     * @param dst the destination file
     * @throws IOException if a problem occurs while copying
     */
    public static void copyFile(File src, File dst) throws IOException {
        if (dst.exists() && src.lastModified() == dst.lastModified()
                && src.length() == dst.length()) {
            return;
        }

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
            if (dst.exists()) {
                dst.setLastModified(src.lastModified());
            }
        }
    }
}
