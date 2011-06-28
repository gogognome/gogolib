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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class keeps track of actions that can be undone and redone.
 *
 * <p>To execute an action, call doAction().
 *
 * <p>To undo the last action, call undoAction(). To redo the last undone action,
 * call redoAction().
 *
 * @author Sander Kooijmans
 */
public class UndoRedoActionStack {

	private final static Logger LOGGER = Logger.getLogger(UndoRedoActionStack.class.getName());

	private LinkedList<UndoableAction> undoActions = new LinkedList<UndoableAction>();

	private LinkedList<UndoableAction> redoActions = new LinkedList<UndoableAction>();

	private List<UndoableActionListener> listeners = new ArrayList<UndoableActionListener>();

	public UndoRedoActionStack() {
		super();
	}

	public UndoableAction getDoAction() {
		return undoActions.isEmpty() ? null : undoActions.getLast();
	}

	public UndoableAction getRedoAction() {
		return redoActions.isEmpty() ? null : redoActions.getLast();
	}

	/**
	 * Executes an action and adds the action to the undo stack.
	 * The redo list is cleared.
	 * @param action the action to be executed
	 */
	public void doAction(UndoableAction action) {
		LOGGER.fine("Executing action " + action.getDescription());
		action.doAction();
		undoActions.addLast(action);
		redoActions.clear();
		notifyListeners();
	}

	/**
	 * Undo the last action. The action is added to the redo stack.
	 */
	public void undoAction() {
		if (!undoActions.isEmpty()) {
			UndoableAction action = undoActions.removeLast();
			LOGGER.fine("Undoing action " + action.getDescription());
			action.undoAction();
			redoActions.add(action);
			notifyListeners();
		}
	}

	/**
	 * Redo the last undone action. The action is added to the undo stack.
	 */
	public void redoAction() {
		if (!redoActions.isEmpty()) {
			UndoableAction action = redoActions.removeLast();
			LOGGER.fine("Redoing action " + action.getDescription());
			action.doAction();
			undoActions.add(action);
			notifyListeners();
		}
	}

	public void addListener(UndoableActionListener listener) {
		listeners.add(listener);
	}

	public void removeListener(UndoableActionListener listener) {
		listeners.remove(listener);
	}

	private void notifyListeners() {
		for (UndoableActionListener l : new ArrayList<UndoableActionListener>(listeners)) {
			l.actionExecuted();
		}
	}
}
