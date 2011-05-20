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
