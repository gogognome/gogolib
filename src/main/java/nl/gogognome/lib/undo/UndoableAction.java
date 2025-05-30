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
package nl.gogognome.lib.undo;

/**
 * Interface for an action that can be undone and redone.
 *
 * <p>The methods doAction() and undoAction() will always be called
 * in alternating order, starting with doAction().
 *
 * @author Sander Kooijmans
 */
public interface UndoableAction {

	/**
	 * This method executes the action.
	 */
	public void doAction();

	/**
	 * This method makes undoes the result of {@link #doAction()}.
	 */
	public void undoAction();

	/**
	 * @return a human readable description of the action
	 */
	public String getDescription();
}
