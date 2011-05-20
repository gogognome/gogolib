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
