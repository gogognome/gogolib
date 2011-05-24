/**
 *
 */
package nl.gogognome.lib.swing.models;

import java.io.File;

import nl.gogognome.lib.util.ComparatorUtil;

/**
 * This class implements a model to select a file.
 *
 * @author Sander Kooijmans
 */
public class FileSelectionModel extends AbstractModel {

	private File file;

	public FileSelectionModel(File file) {
		super();
		this.file = file;
	}

	public File getFile() {
		return file;
	}

    /**
     * Sets the file of this model.
     * @param newFile the new value of the file
     * @param source the model change listener that sets the file. It will not
     *         get notified. It may be <code>null</code>.
     */
	public void setFile(File newFile, ModelChangeListener source) {
        File oldFile = this.file;
        if (!ComparatorUtil.equals(oldFile, newFile)) {
            this.file = newFile;
            notifyListeners(source);
        }
	}
}
