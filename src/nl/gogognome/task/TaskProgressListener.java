/**
 * 
 */
package nl.gogognome.task;

/**
 * This interface specifies a listener to monitor the progress of task.
 *  
 * @author Sander Kooijmans
 */
public interface TaskProgressListener {

	/**
	 * This method may be called any time to notify that a part of the task has been completed.
	 * @param percentageCompleted the percentage of the task that has been completed
	 */
	public void onProgressUpdate(int percentageCompleted);
}
