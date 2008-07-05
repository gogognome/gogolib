/*
 * $Id: DefaultTabbedPaneUI.java,v 1.3 2008-07-05 10:50:43 sanderk Exp $
 *
 * Copyright (C) 2005 Sander Kooijmans
 *
 */

package nl.gogognome.swing.plaf;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

/**
 * Default UI for tabbed panes.
 */
public class DefaultTabbedPaneUI extends BasicTabbedPaneUI {

    private final static DefaultTabbedPaneUI INSTANCE = new DefaultTabbedPaneUI();
    
    private final static int CROSS_X = 7;
    private final static int CROSS_Y = 5;
    private final static int CROSS_WIDTH = 10;
    private final static int CROSS_HEIGHT = 10;
    
    public static ComponentUI createUI(JComponent c) {
        return INSTANCE;
    }
    
    public void installUI(JComponent c) {
        JTabbedPane tabbedPane = (JTabbedPane)c;
        super.installUI(tabbedPane);
        tabbedPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics metrics) {
        return super.calculateTabWidth(tabPlacement, tabIndex, metrics) + 20;
    }

    protected void paintTab(Graphics g, int tabPlacement, Rectangle[] rects,
            int tabIndex, Rectangle iconRect, Rectangle textRect) {
        Rectangle tabRect = rects[tabIndex];
        int selectedIndex = tabPane.getSelectedIndex();
        boolean isSelected = selectedIndex == tabIndex;

        paintTabBackground(g, tabPlacement, tabIndex, tabRect.x, tabRect.y,
                tabRect.width, tabRect.height, isSelected);

        paintTabBorder(g, tabPlacement, tabIndex, tabRect.x, tabRect.y, 
                       tabRect.width, tabRect.height, isSelected);
        
        Font font = tabPane.getFont();
        FontMetrics metrics = tabPane.getFontMetrics(font);
        Icon icon = getIconForTab(tabIndex);
        String title = tabPane.getTitleAt(tabIndex);
        
        layoutLabel(tabPlacement, metrics, tabIndex, title, icon, 
                    tabRect, iconRect, textRect, isSelected);

        Graphics2D g2d = (Graphics2D) g;
        textRect = new Rectangle(textRect.x + CROSS_WIDTH, textRect.y, textRect.width, textRect.height);
        iconRect = new Rectangle(iconRect.x + CROSS_WIDTH, iconRect.y, iconRect.width, iconRect.height);
        paintText(g2d, tabPlacement, font, metrics, 
                  tabIndex, title, textRect, isSelected);
        
        paintIcon(g, tabPlacement, tabIndex, icon, iconRect, isSelected);

        g.setColor(Color.BLACK);
        int x = tabRect.x + CROSS_X;
        int y = tabRect.y + CROSS_Y;
        int w = CROSS_WIDTH;
        int h = CROSS_HEIGHT;
        
        for (int i=0; i<2; i++) {
            for (int j=0; j<2; j++) {
                g2d.drawLine(x + i, y + j, x + i + w, y + j + h);
                g2d.drawLine(x + i, y + j + h, x + i + w, y + j);
            }
        }
        
        paintFocusIndicator(g, tabPlacement, rects, tabIndex, 
                  iconRect, textRect, isSelected);
        
//        g.setColor(Color.RED);
//        Rectangle clipBounds = g.getClipBounds();
//        g.drawRect(clipBounds.x, clipBounds.y, clipBounds.width, clipBounds.height);
    }
    
    protected MouseListener createMouseListener() {
        return new MouseHandler(super.createMouseListener());
    }
    
    private class MouseHandler implements MouseListener {

        private MouseListener wrappedMouseListener;
        
        public MouseHandler(MouseListener wrappedMouseListener) {
            this.wrappedMouseListener = wrappedMouseListener;
        }
        
        public void mouseClicked(MouseEvent e) {
            wrappedMouseListener.mouseClicked(e);
        }

        public void mouseEntered(MouseEvent e) {
            wrappedMouseListener.mouseEntered(e);
        }

        public void mouseExited(MouseEvent e) {
            wrappedMouseListener.mouseExited(e);
        }

        public void mousePressed(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            for (int i=0; i<rects.length; i++) {
                if (rects[i].contains(x, y)) {
                    int relativeX = x - rects[i].x;
                    int relativeY = y - rects[i].y;
                    if (CROSS_X <= relativeX && relativeX < CROSS_X + CROSS_WIDTH &&
                        CROSS_Y <= relativeY && relativeY < CROSS_Y + CROSS_HEIGHT) {
                        tabPane.remove(i);
                        return;
                    }
                }
            }
            wrappedMouseListener.mousePressed(e);
        }

        public void mouseReleased(MouseEvent e) {
            wrappedMouseListener.mouseReleased(e);
        }
        
    }
}
