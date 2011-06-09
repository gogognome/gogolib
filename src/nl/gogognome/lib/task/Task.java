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
package nl.gogognome.lib.task;

import nl.gogognome.lib.task.ui.TaskWithProgressDialog;

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
