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
package nl.gogognome.lib.text;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * This class represents a text resource. It offers functionality to
 * obtain texts from resource files. It also offers easy methods to
 * format strings.
 *
 * @author Sander Kooijmans
 */
public class TextResource {

	/** The singleton instance of this class. */
	private static TextResource instance;

	/** Contains the string resources of the user interface. */
	private List<ResourceBundle> stringResources;

	/** The locale used to obtain resources and format currencies and dates. */
	private Locale locale = Locale.getDefault();

	private final static Logger LOGGER = Logger.getLogger(TextResource.class.getName());

	/**
	 * An <code>AmountFormat</code> created with <code>locale</code> as its
	 * locale.
	 */
	private AmountFormat amountFormat = new AmountFormat(locale);

	/**
	 * Gets the singleton instance of this class.
	 * @return the singleton instance of this class.
	 */
	public static synchronized TextResource getInstance()
	{
		if (instance == null)
		{
			instance = new TextResource();
		}
		return instance;
	}

	/** Private constructor to enforce usage of <tt>getInstance()</tt>. */
	private TextResource() {
        loadResources();
	}

    /**
     * Loads the resources of gogolib.
     */
    private void loadResources() {
        stringResources = new ArrayList<ResourceBundle>();
        stringResources.add(ResourceBundle.getBundle("gogolibstrings", locale));
    }

    /**
     * Loads a resource bundle.
     * @param resourceBundle the name of the resource bundle
     */
    public void loadResourceBundle(String resourceBundle) {
        ResourceBundle b = ResourceBundle.getBundle(resourceBundle, locale);
        stringResources.add(0, b);
    }

	/**
	 * Sets the locale for the string resources.
	 * @param locale the locale for the string resources
	 */
	public void setLocale(Locale locale)
	{
	    this.locale = locale;
	    this.amountFormat = new AmountFormat(locale);
	    loadResources();
	}

	/**
	 * Gets the locale for the string resources.
	 * @return the locale for the string resources
	 */
	public Locale getLocale()
	{
	    return locale;
	}

	/**
	 * Gets the <code>AmountFormat</code> with the same <code>Locale</code>
	 * as this <code>TextResource</code>.
	 * @return the <code>AmountFormat</code>
	 */
	public AmountFormat getAmountFormat()
	{
	    return amountFormat;
	}

	/**
	 * Gets a string from the resources.
	 * @param id the id of the string
	 * @return the string from the resources or <code>null</code> if no string was
	 *         found in the resources.
	 */
	public String getString(String id) {
        String result = null;
        for (ResourceBundle rb : stringResources) {
        	if (rb.containsKey(id)) {
        		result = rb.getString(id);
        		break;
        	}
        }
        if (result == null && !isOptionalId(id)) {
        	LOGGER.warning("String resource " + id + " was not found. Have all resource bundles been loaded?");
        }
        return result;
	}

	private static boolean isOptionalId(String id) {
		return id.endsWith(".mnemonic");
	}

	/**
	 * Gets a string from the resources. The arguments in the form {1}, {2} etc.
	 * are replaced with the objects in the <code>arguments</code> array.
	 * @param id the id of the string
	 * @param arguments the arguments
	 * @return the string from the resources or <code>null</code> if no string was
	 *         found in the resources.
	 */
	public String getString(String id, Object[] arguments) {
	    String result;
        String s = getString(id);
        if (s != null) {
            for (int i=0; i<arguments.length; i++) {
                if (arguments[i] instanceof Date) {
                    arguments[i] = formatDate("gen.dateFormatFull", (Date) arguments[i]);
                }
            }
            result = MessageFormat.format(s, arguments);
        } else {
            result = null;
        }
	    return result;
	}

	public String getString(String id, Object argument) {
	    return getString(id, new Object[] { argument } );
	}

	public String getString(String id, int argument) {
	    return getString(id, Integer.toString(argument));
	}

	public String getString(String id, float argument) {
	    return getString(id, new Float(argument));
	}

	/**
	 * Formats a date.
	 * @param formatId the id of the string resource that describes the format of the date
	 * @param date the date to be formatted
	 * @return the formatted date
	 */
	public String formatDate(String formatId, Date date) {
	    SimpleDateFormat sdf = new SimpleDateFormat(getString(formatId), locale);
	    return sdf.format(date);
	}

}
