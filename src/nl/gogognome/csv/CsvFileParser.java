/*
 * $Id: CsvFileParser.java,v 1.3 2007-09-15 18:55:13 sanderk Exp $
 *
 * Copyright (C) 2007 Sander Kooijmans
 */
package nl.gogognome.csv;

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
        
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList rows;
        rows = new ArrayList(rowIndices != null ? rowIndices.length : 100);

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
        
        return (String[][]) rows.toArray(new String[rows.size()][]);
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
        ArrayList columns = new ArrayList();
        int index = 0;
        StringBuffer sb = new StringBuffer();
        boolean betweenQuotes = false;
        while (index < line.length()) {
            if (line.charAt(index) == '"') {
                betweenQuotes = !betweenQuotes;
            } else if (!betweenQuotes && line.charAt(index) == separator) {
                columns.add(sb.toString());
                sb = new StringBuffer();
        	} else {
                sb.append(line.charAt(index));
            }
            index++;
        }
        columns.add(sb.toString());
        return (String[]) columns.toArray(new String[columns.size()]);
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
        StringBuffer sb = new StringBuffer(20);
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