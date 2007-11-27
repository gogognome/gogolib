/*
 * $Id: SwingUtils.java,v 1.3 2007-11-27 21:16:45 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.swing;

import java.awt.GridBagConstraints;
import java.awt.Insets;

/**
 * This class offers a variety of methods that are useful when writing
 * Swing applications. 
 *
 * @author Sander Kooijmans
 */
public class SwingUtils {

    public static GridBagConstraints createGBConstraints(
            int gridx, int gridy, 
            int gridwidth, int gridheight, double weightx, double weighty, 
            int anchor, int fill, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.gridheight = gridheight;
        gbc.weightx = weightx;
        gbc.weighty = weighty;
        gbc.anchor = anchor;
        gbc.fill = fill;
        gbc.insets = insets;
        return gbc;
    }

    public static GridBagConstraints createGBConstraints(
            int gridx, int gridy, 
            int gridwidth, int gridheight, double weightx, double weighty, 
            int anchor, int fill, int top, int left, int bottom, int right) {
        
        return createGBConstraints(gridx, gridy, gridwidth, gridheight, weightx, 
                weighty, anchor, fill, new Insets(top, left, bottom, right));
    }

    public static GridBagConstraints createGBConstraints(
            int gridx, int gridy) {
        
        return createGBConstraints(gridx, gridy, 1, 1, 1.0, 
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
                0, 0, 0, 0);
    }

    public static GridBagConstraints createGBConstraints(
            int gridx, int gridy, int gridwidth, int gridheight) {
        
        return createGBConstraints(gridx, gridy, gridwidth, gridheight, 1.0, 
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
                0, 0, 0, 0);
    }
    
    
    public static GridBagConstraints createTextFieldGBConstraints(
            int gridx, int gridy) {
        return createGBConstraints(gridx, gridy, 1, 1, 1.0, 0.0, 
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                0, 0, 3, 0);
    }

    public static GridBagConstraints createLabelGBConstraints(
            int gridx, int gridy) {
        return createGBConstraints(gridx, gridy, 1, 1, 0.0, 0.0, 
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                0, 0, 3, 10);
    }

    public static GridBagConstraints createPanelGBConstraints(
            int gridx, int gridy) {
        return createGBConstraints(gridx, gridy, 1, 1, 1.0, 1.0, 
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                10, 10, 10, 10);
    }
    
}
