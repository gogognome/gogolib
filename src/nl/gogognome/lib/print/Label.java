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
package nl.gogognome.lib.print;

import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;

/**
 * This interface specificies a label that can be printed. It only consists of
 * a method that is called when a label is printed.
 */
public interface Label {

    /**
     * This method is called when the label is printed. The label should draw itself
     * in the graphics context <code>g</code> within the specified bounds.
     *
     * @param g the graphics context
     * @param x the X co-ordinate of the label's top-left corner
     * @param y the Y co-ordinate of the label's top-left corner
     * @param width the width of the label
     * @param height the height of the label
     * @param format the page format (might be needed to determine how to print the label)
     * @param pageIndex the page index (might be needed to determine how to print the label)
     * @throws PrinterException if printing cannot succeed for some reason
     */
    public void printLabel(Graphics2D g, double x, double y, double width, double height,
        PageFormat format, int pageIndex) throws PrinterException;
}
