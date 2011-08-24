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
package nl.gogognome.lib.print.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

import nl.gogognome.lib.print.Label;
import nl.gogognome.lib.print.LabelSheet;
import nl.gogognome.lib.print.SimpleLabelSheet;
import nl.gogognome.lib.swing.ActionWrapper;
import nl.gogognome.lib.swing.ButtonPanel;
import nl.gogognome.lib.swing.SwingUtils;
import nl.gogognome.lib.swing.WidgetFactory;
import nl.gogognome.lib.swing.views.View;
import nl.gogognome.lib.text.TextResource;
import nl.gogognome.lib.util.Factory;

/**
 * This class implements a view for setting up <code>SimpleLabelSheet</code>s
 * to print labels.
 */
public class SimpleLabelSheetSetupView extends View {

    /** The labels to be printed. */
    private Label[] labels;

    /** Contains the label sheets needed to print the labels. */
    private ArrayList labelSheets = new ArrayList();

    private int nrColsPerSheet = 3;

    private int nrRowsPerSheet = 8;

    private LabelButton[][] labelButtons;

    private JButton btPreviousSheet;

    private JButton btNextSheet;

    private JLabel lbNrSheets;

    /** Titled border that shows the current sheet number. */
    private TitledBorder titledBorder;

    private JPanel mainPanel;

    /**
     * Contains the <code>LabelSheet</code>s that were set up when the user pressed the Ok button.
     * Is <code>null</code> if the user did not press the Ok button yet or if the user pressed the
     * Cancel button.
     */
    private LabelSheet[] resulingLabelSheets;

    /**
     * Index in <code>labelSheets</code> of the <code>LabelSheet</code> currently shown
     * in the dialog.
     */
    private int currentSheet = 0;

    private String idOkButton;
    private String idCancelButton;

    /** Contains the identifier of the button that was used to close this view. */
    private String idPressedButton;

    /**
     * Constructor.
     * @param frame the parent of this dialog
     * @param labels the labels to be printed
     * @param idOkButton the identifier of the OK button
     * @param idCancelButton the identifier of the cancel button
     */
    public SimpleLabelSheetSetupView(Label[] labels, String idOkButton, String idCancelButton) {
        this.idOkButton = idOkButton;
        this.idCancelButton = idCancelButton;
        setSheets(labels);
    }

    @Override
	public String getTitle() {
        return Factory.getInstance(TextResource.class).getString("simplelabelsheetsetupdialog.title");
    }

    @Override
	public void onClose() {
    }

    @Override
	public void onInit() {
        initLabelSheets();
        add(createPanel());
    }

    private void setSheets(Label[] labels) {
        this.labels = labels;
    }

    /**
     * Creates the panel that contains all widgets for this dialog, except the
     * Ok and Cancel buttons, which are provided by the superclass.
     *
     * @return the panel
     */
    private JPanel createPanel() {
        TextResource tr = Factory.getInstance(TextResource.class);

        mainPanel = new JPanel(new GridBagLayout());

        // Create panel with statistics
        JPanel statisticsPanel = new JPanel(new GridBagLayout());
        statisticsPanel.add(new JLabel(tr.getString("simplelabelsheetsetupdialog.lbNrLabels", labels.length)),
            SwingUtils.createLabelGBConstraints(0, 0));
        lbNrSheets = new JLabel(tr.getString("simplelabelsheetsetupdialog.lbNrSheets", labelSheets.size()));
        statisticsPanel.add(lbNrSheets,
            SwingUtils.createLabelGBConstraints(0, 1));

        // Create panel with layout of sheet
        JPanel sheetPanel = new JPanel(new GridLayout(nrRowsPerSheet, nrColsPerSheet));
        titledBorder = new TitledBorder(tr.getString("simplelabelsheetsetupdialog.currentPage", currentSheet+1));
        sheetPanel.setBorder(titledBorder);

        labelButtons = new LabelButton[nrRowsPerSheet][nrColsPerSheet];
        for (int r=0; r<nrRowsPerSheet; r++) {
            for (int c=0; c<nrColsPerSheet; c++) {
                labelButtons[r][c] = new LabelButton(new ToggleLabelAction(r, c));
                sheetPanel.add(labelButtons[r][c]);
            }
        }

        ButtonPanel buttonPanel = new ButtonPanel(SwingConstants.CENTER);
        btPreviousSheet = widgetFactory.createButton("simplelabelsheetsetupdialog.btPrevSheet", new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent event) {
                if (currentSheet > 0) {
                    currentSheet--;
                    titledBorder.setTitle(Factory.getInstance(TextResource.class).getString(
                        "simplelabelsheetsetupdialog.currentPage", currentSheet+1));
                    initSheet();
                    mainPanel.repaint();
                }
            }
        });

        btNextSheet = widgetFactory.createButton("simplelabelsheetsetupdialog.btNextSheet", new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent event) {
                if (currentSheet+1 < labelSheets.size()) {
                    currentSheet++;
                    titledBorder.setTitle(Factory.getInstance(TextResource.class).getString(
                        "simplelabelsheetsetupdialog.currentPage", currentSheet+1));
                    initSheet();
                    mainPanel.repaint();
                }
            }
        });
        buttonPanel.add(btPreviousSheet);
        buttonPanel.add(btNextSheet);

        JPanel sheetPanelWithButtons = new JPanel(new BorderLayout());
        sheetPanelWithButtons.add(sheetPanel, BorderLayout.CENTER);
        sheetPanelWithButtons.add(buttonPanel, BorderLayout.SOUTH);

        // Create panel with ok and cancel buttons
        buttonPanel = new ButtonPanel(SwingConstants.RIGHT);
        JButton okButton = widgetFactory.createButton(idOkButton, new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
                idPressedButton = idOkButton;
                resulingLabelSheets = (LabelSheet[]) labelSheets.toArray(new LabelSheet[labelSheets.size()]);
                closeAction.actionPerformed(e);
            }
        });
        buttonPanel.add(okButton);
        JButton cancelButton = widgetFactory.createButton(idCancelButton, new AbstractAction() {
            @Override
			public void actionPerformed(ActionEvent e) {
                idPressedButton = idCancelButton;
                closeAction.actionPerformed(e);
            }
        });
        buttonPanel.add(cancelButton);

        mainPanel.add(statisticsPanel,
            SwingUtils.createPanelGBConstraints(0, 0));
        mainPanel.add(sheetPanelWithButtons,
            SwingUtils.createPanelGBConstraints(0, 1));
        mainPanel.add(buttonPanel,
            SwingUtils.createPanelGBConstraints(0, 2));

        return mainPanel;
    }

    /**
     * Makes sure that <code>labelSheets</code> contains the exact number of sheets
     * needed for <code>labels.length</code> labels.
     */
    private void initLabelSheets() {
        if (labels.length == 0) {
            labelSheets.clear();
        } else {
            // Ensure that the number of sheets is not too small.
            int nrAvailableLabels = 0;
            for (int i=0; i<labelSheets.size(); i++) {
                nrAvailableLabels += ((LabelSheet) labelSheets.get(i)).getNrAvailableLabels();
            }
            while (nrAvailableLabels < labels.length) {
                LabelSheet labelSheet = new SimpleLabelSheet(nrRowsPerSheet, nrColsPerSheet);
                nrAvailableLabels += labelSheet.getNrAvailableLabels();
                labelSheets.add(labelSheet);
            }

            // Ensure that the number of sheets is not too big.
            nrAvailableLabels = 0;
            int nrOfSheets = labelSheets.size();
            for (int i=0; i<labelSheets.size(); i++) {
                nrAvailableLabels += ((LabelSheet) labelSheets.get(i)).getNrAvailableLabels();
                if (nrAvailableLabels >= labels.length) {
                    nrOfSheets = i+1;
                    break;
                }
            }

            while (labelSheets.size() > nrOfSheets) {
                labelSheets.remove(labelSheets.size() - 1);
            }
        }
    }

    /**
     * Initializes the sheet with buttons for sheet <code>currentSheet</code>.
     * Also disables and enables the previous and next button.
     */
    private void initSheet() {
        LabelSheet sheet = (LabelSheet) labelSheets.get(currentSheet);
        for (int r=0; r<nrRowsPerSheet; r++) {
            for (int c=0; c<nrColsPerSheet; c++) {
                if (sheet != null) {
                    labelButtons[r][c].setEnabled(true);
                    labelButtons[r][c].setAvaialble(sheet.isLabelAvailble(r, c));
                } else {
                    labelButtons[r][c].setEnabled(false);
                }
            }
        }

        // Check whether the sheets contain sufficient available labels.
        lbNrSheets.setText(Factory.getInstance(TextResource.class)
        		.getString("simplelabelsheetsetupdialog.lbNrSheets", labelSheets.size()));
        btPreviousSheet.setEnabled(currentSheet > 0);
        btNextSheet.setEnabled(currentSheet+1 < labelSheets.size());
    }

    /**
     * Gets the identifier of the button that was used to close this view.
     * @return the identifier of the button
     */
    public String getIdPressedButton() {
        return idPressedButton;
    }

    /**
     * Gets the <code>LabelSheet</code>s that were set up when the user pressed the Ok button.
     * Is <code>null</code> if the user did not press the Ok button yet or if the user pressed the
     * Cancel button.
     * @return the <code>LabelSheet</code>s
     */
    public LabelSheet[] getResultingLabelSheets() {
        return resulingLabelSheets;
    }

    private class ToggleLabelAction extends AbstractAction {

        private int row;
        private int column;

        public ToggleLabelAction(int row, int column) {
            this.row = row;
            this.column = column;
        }

        @Override
		public void actionPerformed(ActionEvent event) {
            SimpleLabelSheet sheet = (SimpleLabelSheet) labelSheets.get(currentSheet);
            if (sheet != null) {
                sheet.setAvailable(row, column, !sheet.isLabelAvailble(row, column));
                initLabelSheets();
                initSheet();
            }
            mainPanel.repaint();
        }
    }

    private class LabelButton extends Component {

        private ActionWrapper actionWrapper;

        private boolean available = true;

        private final Dimension DIMENSION = new Dimension(80, 30);

        public LabelButton(Action action) {
            super();
            actionWrapper = Factory.getInstance(WidgetFactory.class).createAction(
                "simplelabelsheetsetupdialog.btLabel");
            actionWrapper.setAction(action);

            setFocusable(true);
            addMouseListener(new MouseAdapter() {
                @Override
				public void mouseClicked(MouseEvent e) {
                    actionWrapper.actionPerformed(null);
                }
            });

            addFocusListener(new FocusListener() {

                @Override
				public void focusGained(FocusEvent e) {
                    repaint();
                }

                @Override
				public void focusLost(FocusEvent e) {
                    repaint();
                }
            });

            addKeyListener(new KeyAdapter() {

                @Override
				public void keyTyped(KeyEvent event) {
                    if (KeyEvent.VK_SPACE == event.getKeyChar()) {
                        actionWrapper.actionPerformed(null);
                    }
                }
            });
        }

        public void setAvaialble(boolean available) {
            this.available = available;
            repaint();
        }

        @Override
		public void paint(Graphics g) {
            g.setColor(available ? Color.WHITE : Color.BLUE);
            if (!isEnabled()) {
                g.setColor(Color.DARK_GRAY);
            }

            Dimension d = getPreferredSize();
            g.fillRect(0, 0, d.width, d.height);

            g.setColor(Color.GRAY);
            g.drawRect(0, 0, d.width, d.height);

            if (hasFocus()) {
                g.setColor(Color.BLACK);
                g.drawRect(2, 2, d.width - 4, d.height - 4);
                g.setColor(Color.GRAY);
                g.drawRect(3, 3, d.width - 6, d.height - 6);
            }
        }

        @Override
		public Dimension getPreferredSize() {
            return DIMENSION;
        }
    }

}
