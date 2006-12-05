/*
 * $Id: SwingUtils.java,v 1.1 2006-12-05 19:53:03 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.swing;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

/**
 * This class offers a variety of methods that are useful when writing
 * Swing applications. 
 *
 * @author Sander Kooijmans
 */
public class SwingUtils {

    public static void setGBConstraints(GridBagLayout gbl,
            Component c, int gridx, int gridy, 
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
        gbl.setConstraints(c, gbc);
    }

    public static void setGBConstraints(GridBagLayout gbl,
            Component c, int gridx, int gridy, 
            int gridwidth, int gridheight, double weightx, double weighty, 
            int anchor, int fill, int top, int left, int bottom, int right) {
        
        setGBConstraints(gbl, c, gridx, gridy, gridwidth, gridheight, weightx, 
                weighty, anchor, fill, new Insets(top, left, bottom, right));
    }

    public static void setGBConstraints(GridBagLayout gbl,
            Component c, int gridx, int gridy) {
        
        setGBConstraints(gbl, c, gridx, gridy, 1, 1, 1.0, 
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
                0, 0, 0, 0);
    }

    public static void setGBConstraints(GridBagLayout gbl,
            Component c, int gridx, int gridy, int gridwidth, int gridheight) {
        
        setGBConstraints(gbl, c, gridx, gridy, gridwidth, gridheight, 1.0, 
                1.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH, 
                0, 0, 0, 0);
    }
    
    
    public static void setTextFieldGBConstraints(GridBagLayout gbl,
            Component c, int gridx, int gridy) {
        setGBConstraints(gbl, c, gridx, gridy, 1, 1, 1.0, 0.0, 
                GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL,
                0, 0, 0, 0);
    }

    public static void setLabelGBConstraints(GridBagLayout gbl,
            Component c, int gridx, int gridy) {
        setGBConstraints(gbl, c, gridx, gridy, 1, 1, 0.0, 0.0, 
                GridBagConstraints.WEST, GridBagConstraints.NONE,
                0, 0, 0, 10);
    }

    public static void setPanelGBConstraints(GridBagLayout gbl,
            Component c, int gridx, int gridy) {
        setGBConstraints(gbl, c, gridx, gridy, 1, 1, 1.0, 1.0, 
                GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                10, 10, 10, 10);
    }
    
}
