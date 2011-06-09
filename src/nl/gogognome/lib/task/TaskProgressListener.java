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
