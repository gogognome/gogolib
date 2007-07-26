/*
 * $Id: LabelPrinter.java,v 1.1 2007-07-26 19:36:53 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.print;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaPrintableArea;
import javax.print.attribute.standard.MediaSizeName;

/**
 * This class prints labels. Currently only A4-sized sheets with 3x8 labels
 * are supported.
 *
 * @author Sander Kooijmans
 */
public class LabelPrinter implements Printable {

    /** The labels to be printed. */
    private Label[] labels;

    /**
     * Constructor.
     * @param parties the parties whose addresses are to be printed
     */
    public LabelPrinter(Label[] labels) {
        this.labels = labels;
    }
    
    /**
     * Prints the labels.
     * @throws PrinterException if a problem occurs while printing
     */
    public void printLabels() throws PrinterException {
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        pras.add(MediaSizeName.ISO_A4);
        pras.add(new MediaPrintableArea(5, 10, 210 - 2*5, 297 - 2*10, MediaPrintableArea.MM));
        
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        
        Book book = new Book();
        book.append(this, new PageFormat(), (labels.length + getNrPartiesPerPage() - 1) / getNrPartiesPerPage());
        printerJob.setPageable(book);
        boolean doPrint = printerJob.printDialog(pras);
        if (doPrint) {
            printerJob.print(pras);
        }
    }

    /* (non-Javadoc)
     * @see java.awt.print.Printable#print(java.awt.Graphics, java.awt.print.PageFormat, int)
     */
    public int print(Graphics g, PageFormat format, int pageIndex) throws PrinterException {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(Color.black);
        g2d.setClip(null);
        
        Paper paper = format.getPaper();
        double paperWidth = paper.getWidth();
        double paperHeight = paper.getHeight();
        // It seems that under Linux always the size of Letter is used instead of A4.
        // This is a workaround:
        paperWidth = (210.0 / 25.4) * 72.0;
        paperHeight = (297.0 / 25.4) * 72.0;
        
        int labelIndex = pageIndex * getNrPartiesPerPage();
        double labelWidth = paperWidth / getNrColumnsPerPage();
        double labelHeight = paperHeight / (getNrRowsPerPage() + (isTypeOfPaperOverruled() ? 1 : 0));
        int y = 0;
        int x = 0;
        for (int i=labelIndex; i < labels.length && i<labelIndex + getNrPartiesPerPage(); i++) {
            double labelX = labelWidth * x;
            double labelY = labelHeight* y;
//            g2d.drawRect((int)labelX, (int)labelY, (int)labelWidth, (int)labelHeight);
            
            labels[i].printLabel(g2d, labelX, labelY, labelWidth, labelHeight, format, pageIndex);
            x++;
            if (x == getNrColumnsPerPage()) {
                x = 0;
                y++;
            }
        }
        return Printable.PAGE_EXISTS;
    }
    
    public int getNrColumnsPerPage() {
        return 3;
    }
    
    public int getNrRowsPerPage() {
        if (isTypeOfPaperOverruled()) {
            return 7;
        }
        return 8;
    }
    
    public int getNrPartiesPerPage() {
        return getNrColumnsPerPage() * getNrRowsPerPage();
    }
    
    /**
     * Checks whether the paper type is overruled. If it is overruled, then the
     * bottom row of labels is not printed. This can be handy if the printer cannot
     * print the bottom row of labels, possibly because of a printer driver that
     * always assumes the paper type "letter" instead of "A4".
     * 
     * @return <code>true</code> if the type of paper is overruled; <code>false</code> otherwise
     */
    private boolean isTypeOfPaperOverruled() {
        return "A4".equals(System.getProperty("forcePaper"));
    }
}