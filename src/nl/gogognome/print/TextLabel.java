/*
 * $Id: TextLabel.java,v 1.1 2007-08-08 18:57:31 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.print;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;

/**
 * This class implements a label that consists of text only.
 */

public class TextLabel implements Label {

    /** The text of the label. */
    private String text;
    
    /**
     * Constructor.
     * @param text the text of the label
     */
    public TextLabel(String text) {
        this.text = text;
    }
    
    /**
     * @see nl.gogognome.print.Label#printLabel(java.awt.Graphics2D, double, double, double, double, java.awt.print.PageFormat, int)
     */
    public void printLabel(Graphics2D g, double x, double y, double width,
            double height, PageFormat format, int pageIndex)
            throws PrinterException {
        Graphics2D g2d = (Graphics2D)g;
        g2d.setPaint(Color.BLACK);
        
        String[] lines = text.split("\\\\n");
        
        Font font = new Font("Serif", Font.PLAIN, 10);
        g2d.setFont(font);
        FontMetrics fm = g2d.getFontMetrics();
        int fontHeight = fm.getHeight();
 
        int maxWidth = 0;
        int textHeight = 0;
        for (int i=0; i<lines.length; i++) {
            maxWidth = Math.max(maxWidth, fm.stringWidth(lines[i]));
            textHeight += fontHeight;
        }
        
        float textX = (float)x + ((float)width - (float)maxWidth) / 2.0f;
        float textY = (float)y + ((float)height - (float)textHeight) / 2.0f + fontHeight;
        for (int i=0; i<lines.length; i++) {
            g2d.drawString(lines[i], textX, textY + i * fontHeight);
        }
    }

}
