/*
   Copyright 2011 Sander Kooijmans

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package nl.gogognome.lib.print;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

/**
 * This class prints labels. Currently only A4-sized sheets with 3x8 labels
 * are supported.
 *
 * @author Sander Kooijmans
 */
public class LabelPrinter {

    /** Indicates whether debug functionality of this class is enabled. */
    private final static boolean DEBUG = false;

    /**
     * Private constructor, because this class only has static methods.
     */
    private LabelPrinter() {
    }

    /**
     * Prints the labels on papers.
     * @param labels the labels to be printed
     * @param labelPapers the papers on which to print the labels. The papers must have sufficient
     *        labels available or otherwise not all labels will be printed
     * @throws PrinterException if a problem occurs while printing
     */
    public static void printLabels(Label[] labels, LabelSheet[] labelPapers) throws PrinterException {
        PrinterJob printerJob = PrinterJob.getPrinterJob();

        Book book = new Book();
        PrintableImpl printable = new PrintableImpl(labels, labelPapers);
        book.append(printable, new PageFormat(), labelPapers.length);
        printerJob.setPageable(book);
        boolean doPrint = printerJob.printDialog();
        if (doPrint) {
            printerJob.print();
        }
    }

    private static class PrintableImpl implements Printable {

        /** The labels to be printed. */
        private Label[] labels;

        /** The papers on which to print the labels. */
        private LabelSheet[] labelPapers;

        public PrintableImpl(Label[] labels, LabelSheet[] labelPapers) {
            this.labels = labels;
            this.labelPapers = labelPapers;
        }

        /**
         * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
         */
        @Override
		public int print(Graphics g, PageFormat format, int pageIndex) throws PrinterException {
            if (pageIndex >= labelPapers.length) {
                return Printable.NO_SUCH_PAGE;
            }

            Graphics2D g2d = (Graphics2D) g;
            g2d.setClip(null);

            Paper paper = format.getPaper();
            double paperWidth = paper.getWidth();
            double paperHeight = paper.getHeight();

            // It seems that under Linux always the size of Letter is used instead of A4.
            // This is a workaround:
            paperWidth = (210.0 / 25.4) * 72.0;
            paperHeight = (297.0 / 25.4) * 72.0;

            int labelIndex = 0;
            for (int i=0; i<pageIndex; i++) {
                labelIndex += labelPapers[i].getNrAvailableLabels();
            }
            double labelWidth = paperWidth / labelPapers[pageIndex].getNrColumns();
            double labelHeight = paperHeight / labelPapers[pageIndex].getNrRows();
            for (int y=0; labelIndex < labels.length && y<labelPapers[pageIndex].getNrRows(); y++) {
                for (int x=0; labelIndex < labels.length && x<labelPapers[pageIndex].getNrColumns(); x++) {
                    double labelX = labelWidth * x;
                    double labelY = labelHeight* y;
                    if (DEBUG) {
                        g2d.setPaint(Color.RED);
                        g2d.drawRect((int)labelX, (int)labelY, (int)labelWidth, (int)labelHeight);
                    }
                    g2d.setPaint(Color.BLACK);

                    if (labelPapers[pageIndex].isLabelAvailble(y, x)) {
                        labels[labelIndex].printLabel(g2d, labelX, labelY, labelWidth, labelHeight, format, pageIndex);
                        labelIndex++;
                    }
                }
            }
            return Printable.PAGE_EXISTS;
        }

    }
}