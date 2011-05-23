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
package nl.gogognome.lib.task.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Point;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import nl.gogognome.lib.swing.MessageDialog;
import nl.gogognome.lib.swing.views.View;
import nl.gogognome.lib.task.Task;
import nl.gogognome.lib.task.TaskProgressListener;

/**
 * This class can execute a task and show the progress of the task in a dialog.
 * The task will be executed on a worker thread.
 *
 * @author Sander Kooijmans
 */
public class TaskWithProgressDialog implements TaskProgressListener {

    /** The parent frame that contains this view. */
    private Frame parentFrame;

    /** The parent dialog that contains this view. */
    private JDialog parentDialog;

    private JProgressBar progressBar;
    private JDialog progressDialog;

    private String description;

    public TaskWithProgressDialog(Frame parentFrame, String description) {
    	this.parentFrame = parentFrame;
    	this.description = description;
    }

    public TaskWithProgressDialog(JDialog parentDialog, String description) {
    	this.parentDialog = parentDialog;
    	this.description = description;
    }

    public TaskWithProgressDialog(View view, String description) {
    	this.parentDialog = view.getParentDialog();
    	this.parentFrame = view.getParentFrame();
    	this.description = description;
    }

    /**
     * Executes the task while a dialog shows the progress of the task.
     * @param task the task
     * @return the result of the task
     */
    public Object execute(Task task) {
    	WorkerThread thread = new WorkerThread(task, description, this);
    	thread.start();
    	if (parentFrame != null) {
    		progressDialog = new JDialog(parentFrame);
    	} else {
    		progressDialog = new JDialog(parentDialog);
    	}
    	progressBar = new JProgressBar(0, 100);
    	JPanel panel = new JPanel(new BorderLayout());
    	progressDialog.setLayout(new BorderLayout());
    	panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	panel.add(new JLabel(description), BorderLayout.NORTH);
    	panel.add(progressBar, BorderLayout.CENTER);
    	progressDialog.add(panel);
    	progressDialog.pack();
    	progressDialog.setModal(true);
    	progressDialog.setResizable(false);

    	// Put dialog in center of parent frame or dialog.
    	Dimension d;
    	Point location;
    	if (parentFrame != null) {
	    	d = parentFrame.getSize();
	    	location = parentFrame.getLocation();
    	} else if (parentDialog != null) {
    		d = parentDialog.getSize();
    		location = parentDialog.getLocation();
    	} else {
    		d = new Dimension(400, 400);
    		location = new Point(200,200);
    	}
    	location.translate((int)(d.getWidth() / 2), (int)(d.getHeight() / 2));
    	progressDialog.setLocation(location);

    	progressDialog.setVisible(true);
    	try {
			thread.join();
		} catch (InterruptedException e) {
		}
    	return thread.getResult();
    }

    public void onFinished(final Exception e) {
    	SwingUtilities.invokeLater(new Runnable() {
    		@Override
			public void run() {
    			progressDialog.setVisible(false);
		    	if (e != null) {
		            MessageDialog dialog = new MessageDialog(parentFrame, "gen.titleError", e);
		            dialog.showDialog();
		    	}
    		}
    	});
    }

    @Override
	public void onProgressUpdate(final int percentageCompleted) {
    	SwingUtilities.invokeLater(new Runnable() {
    		@Override
			public void run() {
    			progressBar.setValue(percentageCompleted);
    		}
    	});
    }

    private class WorkerThread extends Thread {
    	private TaskWithProgressDialog processDialog;
    	private Task task;
    	private Object result;
    	private Object lock = new Object();

		public WorkerThread(Task task, String description, TaskWithProgressDialog progressListener) {
			super("worker thread for \"" + description + '"');
			this.task = task;
			this.processDialog = progressListener;
		}

		@Override
		public void run() {
	        try {
	            Object tempResult = task.execute(processDialog);
	            synchronized(lock) {
	            	result = tempResult;
	            }
	            processDialog.onFinished(null);
	        } catch (Exception e) {
	        	processDialog.onFinished(e);
	        }
		}

		public Object getResult() {
			synchronized(lock) {
				return result;
			}
		}
    }

}
