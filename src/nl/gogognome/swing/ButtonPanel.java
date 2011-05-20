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
package nl.gogognome.swing;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * This class implements a panel that can hold a number of buttons.
 * The buttons are shown in a single row or in a single column.
 * The location of the buttons within the panel is configurable.
 */
public class ButtonPanel extends JPanel {

    /** The location of the buttons within the panel. */
    private int location;

    /** Indicates whether the buttons are laid out horizontally or vertically. */
    private int orientation;

    /** A label that gets added to this panel to push the row of buttons to the left or the right. */
    private JLabel label = new JLabel();

    /**
     * Constructs a button panel.
     * @param location specifies the location of the buttons within the panel.
     *        Allowed values are <code>SwingConstants.LEFT</code>,
     *        <code>SwingConstants.CENTER</code> and <code>SwingConstants.RIGHT</code>
     */
    public ButtonPanel(int location) {
        this(location, SwingConstants.HORIZONTAL);
    }

    /**
     * Constructs a button panel.
     * @param location specifies the location of the buttons within the panel.
     *        Allowed values for horizontally alligned panels are <code>SwingConstants.LEFT</code>,
     *        <code>SwingConstants.CENTER</code> and <code>SwingConstants.RIGHT</code>.
     *        Allowed values for vertically alligned panels are <code>SwingConstants.TOP</code>,
     *        <code>SwingConstants.CENTER</code> and <code>SwingConstants.BOTTOM</code>.
     * @param orientation the orientation. Must be <code>SwingConstants.HORIZONTAL</code>
     *        or <code>SwingConstants.VERTICAL</code>
     */
    public ButtonPanel(int location, int orientation) {
        super();
        this.location = location;
        this.orientation = orientation;
        if (orientation != SwingConstants.HORIZONTAL && orientation != SwingConstants.VERTICAL) {
            throw new IllegalArgumentException("Invalid orientation: " + orientation);
        }
        setLayout(new GridBagLayout());
        switch(location) {
        case SwingConstants.CENTER:
            if (orientation == SwingConstants.HORIZONTAL) {
                add(new JLabel(), SwingUtils.createGBConstraints(0, 0, 1, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
                add(label, SwingUtils.createGBConstraints(1, 0, 1, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
            } else {
                add(new JLabel(), SwingUtils.createGBConstraints(0, 0, 1, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, 0, 0, 0, 0));
                add(label, SwingUtils.createGBConstraints(0, 1, 1, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, 0, 0, 0, 0));
            }
            break;

        case SwingConstants.LEFT:
            if (orientation != SwingConstants.HORIZONTAL) {
                throw new IllegalArgumentException("The location LEFT is only allowed for HORIZONTAL orientation.");
            }
            add(label, SwingUtils.createGBConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
            break;

        case SwingConstants.RIGHT:
            if (orientation != SwingConstants.HORIZONTAL) {
                throw new IllegalArgumentException("The location RIGHT is only allowed for HORIZONTAL orientation.");
            }
            add(label, SwingUtils.createGBConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
            break;

        case SwingConstants.TOP:
            if (orientation != SwingConstants.VERTICAL) {
                throw new IllegalArgumentException("The location TOP is only allowed for VERTICAL orientation.");
            }
            add(label, SwingUtils.createGBConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, 0, 0, 0, 0));
            break;

        case SwingConstants.BOTTOM:
            if (orientation != SwingConstants.VERTICAL) {
                throw new IllegalArgumentException("The location BOTTOM is only allowed for VERTICAL orientation.");
            }
            add(label, SwingUtils.createGBConstraints(0, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.VERTICAL, 0, 0, 0, 0));
            break;

        default:
            throw new IllegalArgumentException("Invalid location specified: " + location);
        }
    }

    /**
     * Adds a button to this panel.
     * @param button the button to be added
     * @return the button
     */
    @Override
    public Component add(Component button) {
        switch(location) {
        case SwingConstants.CENTER:
        case SwingConstants.LEFT:
        case SwingConstants.TOP:
            // Remove the label, add the button and add the label as right-most element in the row
            remove(label);
            if (orientation == SwingConstants.HORIZONTAL) {
                add(button, SwingUtils.createGBConstraints(getComponentCount(), 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.NONE, 5, 5, 5, 5));
                add(label, SwingUtils.createGBConstraints(getComponentCount(), 0, 1, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
            } else {
                add(button, SwingUtils.createGBConstraints(0, getComponentCount(), 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.NONE, 5, 5, 5, 5));
                add(label, SwingUtils.createGBConstraints(0, getComponentCount(), 1, 1, 1.0, 1.0,
                    GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL, 0, 0, 0, 0));
            }
            break;

        case SwingConstants.RIGHT:
            add(button, SwingUtils.createGBConstraints(getComponentCount(), 0, 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, 5, 5, 5, 5));
            break;
        case SwingConstants.BOTTOM:
            add(button, SwingUtils.createGBConstraints(0, getComponentCount(), 1, 1, 0.0, 0.0,
                GridBagConstraints.CENTER, GridBagConstraints.NONE, 5, 5, 5, 5));
            break;
        }
        return button;
    }

}
