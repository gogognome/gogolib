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
