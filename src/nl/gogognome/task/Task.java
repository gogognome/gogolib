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
