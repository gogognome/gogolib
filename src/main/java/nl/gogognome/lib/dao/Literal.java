package nl.gogognome.lib.dao;

/**
 * This class represents a string that will be put in an SQL statement literally
 * and not as parameter.
 *
 * @author Sander Kooijmans
 */
class Literal {

	private String value;

	public Literal(String value) {
		super();
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
