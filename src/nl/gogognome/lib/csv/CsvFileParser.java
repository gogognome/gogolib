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
package nl.gogognome.lib.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This class parses a comma separated value (CSV) file. It also offers
 * functionality to combine values of different columns into a single <code>String</code>.
 *
 * @author Sander Kooijmans
 */
public class CsvFileParser {

    /** Contains the indices of the rows to be parsed. */
    private int rowIndices[];

    /** The character that separates values in the CSV file. */
    private char separator = ',';

    /** The CSV file to be parsed. */
    private File file;

    /**
     * Constructor.
     * @param file the file to be parsed
     */
    public CsvFileParser(File file) {
        this.file = file;
    }

    /**
     * Gets the values from the CSV file.
     * @return the values. Element <code>[r][c]</code> of the returned array represents
     *          the value of column <code>c</code> in row <code>r</code>
     * @throws IOException if a problem occurs while reading the CSV fie
     */
    public String[][] getValues() throws IOException {
        if (file == null) {
            return new String[0][0];
        }

        BufferedReader reader = null;
        try {
        	reader = new BufferedReader(new FileReader(file));
            ArrayList<String[]> rows;
            rows = new ArrayList<String[]>(rowIndices != null ? rowIndices.length : 100);

            // Sort row indices. Now we can scan rowIndices from start to end to check line numbers.
            int rowIndicesIndex = 0;
            if (rowIndices != null) {
                Arrays.sort(rowIndices);
            }

            int lineNr = 0;
            String line = reader.readLine();
            while (line != null) {
                if (rowIndices == null || (rowIndicesIndex < rowIndices.length && rowIndices[rowIndicesIndex] == lineNr)) {
                    rowIndicesIndex++;
                    rows.add(splitCsvLine(line));
                }
                line = reader.readLine();
                lineNr++;
            }

            return rows.toArray(new String[rows.size()][]);
        } finally {
        	if (reader != null) {
        		reader.close();
        	}
        }
    }

    /**
     * Gets the formatted values from the CSV file.
     * @param pattern the pattern applied to the values of each line
     * @return the formatted values.
     * @throws IOException if a problem occurs while reading the CSV fie
     */
    public String[] getFormattedValues(String pattern) throws IOException {
        String[][] values = getValues();
        String[] result = new String[values.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = composeValue(pattern, values[i]);
        }
        return result;
    }

    /**
     * Splits a string based on <code>separator</code>. Also removes quotes from the
     * resulting strings.
     *
     * @param line the line to be split
     * @return the split line
     */
    public String[] splitCsvLine(String line) {
        ArrayList<String> columns = new ArrayList<String>();
        int index = 0;
        StringBuilder sb = new StringBuilder();
        boolean betweenQuotes = false;
        while (index < line.length()) {
            if (line.charAt(index) == '"') {
                betweenQuotes = !betweenQuotes;
            } else if (!betweenQuotes && line.charAt(index) == separator) {
                columns.add(sb.toString());
                sb = new StringBuilder();
        	} else {
                sb.append(line.charAt(index));
            }
            index++;
        }
        columns.add(sb.toString());
        return columns.toArray(new String[columns.size()]);
    }

    /**
     * Composes a value based on a pattern. Place holders of the form <code>{<i>n</i>}</code>
     * are replaced by <code>columns[<i>n</i>]</code>.
     *
     * @param pattern the pattern
     * @param columns the values used to substitute the place holders
     * @return the composed value
     */
    public static String composeValue(String pattern, String[] columns) {
        StringBuilder sb = new StringBuilder(20);
        int index = 0;
        while (index < pattern.length()) {
            char c = pattern.charAt(index);
            if (c == '{') {
                index++;
                int colNr = 0;
                while (pattern.charAt(index) != '}') {
                    colNr = 10*colNr + Character.digit(pattern.charAt(index), 10);
                    index++;
                }
                index++;
                if (colNr < columns.length) {
                    sb.append(columns[colNr]);
                }
            } else {
                sb.append(c);
                index++;
            }
        }

        return sb.toString();
    }

    public char getSeparator() {
        return separator;
    }
    public void setSeparator(char separator) {
        this.separator = separator;
    }

    public int[] getRowIndices() {
        return rowIndices;
    }

    public void setRowIndices(int[] rowIndices) {
        this.rowIndices = rowIndices;
    }

}