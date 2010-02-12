package nl.gogognome.task;

import nl.gogognome.task.ui.TaskWithProgressDialog;

/**
 * This interface specifies a task that can be executed by the {@link TaskWithProgressDialog}.
 *
 * @author Sander Kooijmans
 */
public interface Task {

	/**
	 * Executes the task. The task can call {@link TaskProgressListener#onProgressUpdate(int)}
	 * to indicate that a percentage of the task has completed.
	 * The task completes when this method ends. The task can be used to compute some result.
	 * The result can be returned by this method.
	 *
	 * @param progressListener the progress listener
	 * @return the result of the task
	 * @throws Exception if the task terminates exceptionally.
	 */
	public Object execute(TaskProgressListener progressListener) throws Exception;
}
