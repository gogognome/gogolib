/*
 * $Id: TextResource.java,v 1.6 2007-08-18 10:48:04 sanderk Exp $
 *
 * Copyright (C) 2006 Sander Kooijmans
 */
package nl.gogognome.text;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

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
	private ResourceBundle[] stringResources;
	
	/** The locale used to obtain resources and format currencies and dates. */
	private Locale locale = Locale.getDefault();
	
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
     * Loads the resources.
     */
    private void loadResources() {
        stringResources = new ResourceBundle[2];
        stringResources[0] = ResourceBundle.getBundle("stringresources", locale);
        stringResources[1] = ResourceBundle.getBundle("gogolibstrings", locale);
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
        for (int i=0; result == null && i<stringResources.length; i++) {
            try {
                result = stringResources[i].getString(id);
            } catch (MissingResourceException e) {
            }
        }
        return result;
	}

	/**
	 * Gets a string from the resources. The arguments in the form {1}, {2} etc.
	 * are replaced with the objects in the <code>arguments</code> array.
	 * @param id the id of the string
	 * @param arguments the arguments
	 * @return the string from the resources or <code>null</code> if no string was
	 *         found in the resources.
	 */
	public String getString(String id, Object[] arguments)
	{
	    String result;
        String s = getString(id);
        if (s != null) {
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
	public String formatDate(String formatId, Date date)
	{
	    SimpleDateFormat sdf = new SimpleDateFormat(getString(formatId), locale);
	    return sdf.format(date);
	}
	
}
