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
     * @see nl.gogognome.lib.print.Label#printLabel(java.awt.Graphics2D, double, double, double, double, java.awt.print.PageFormat, int)
     */
    @Override
	public void printLabel(Graphics2D g, double x, double y, double width,
            double height, PageFormat format, int pageIndex)
            throws PrinterException {
        Graphics2D g2d = g;
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

        float textX = (float)x + ((float)width - maxWidth) / 2.0f;
        float textY = (float)y + ((float)height - textHeight) / 2.0f + fontHeight;
        for (int i=0; i<lines.length; i++) {
            g2d.drawString(lines[i], textX, textY + i * fontHeight);
        }
    }

}
