package nl.gogognome.lib.text;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents a text resource. It offers functionality to
 * obtain texts from resource files. It also offers easy methods to
 * format strings.
 */
public class TextResource {
    private final Locale locale;
    private final NumberFormat numberFormat;
    private final List<ResourceBundle> stringResources = new ArrayList<>();

    private final Set<String> optionalIdSuffixes = new HashSet<>();

    private final static Logger LOGGER = LoggerFactory.getLogger(TextResource.class);

    /**
     * Constructs a text resource for the specified locale.
     * @param locale the locale
     */
    public TextResource(Locale locale) {
        this.locale = locale;
        this.numberFormat = DecimalFormat.getNumberInstance(locale);
        initOptionalIdSuffixes();
        loadResourceBundle("gogolibstrings");
    }

    private void initOptionalIdSuffixes() {
        optionalIdSuffixes.add(".accelerator");
        optionalIdSuffixes.add(".contexthelp");
        optionalIdSuffixes.add(".description");
        optionalIdSuffixes.add(".icon16");
        optionalIdSuffixes.add(".keystroke");
        optionalIdSuffixes.add(".mnemonic");
        optionalIdSuffixes.add(".tooltip");
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
     * Gets the locale for the string resources.
     * @return the locale for the string resources
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * Checks whether a string with the specified id exists.
     * @param id the id of the string
     * @return true if a string with the specified id exists; false otherwise
     */
    public boolean containsString(String id) {
        for (ResourceBundle rb : stringResources) {
            if (rb.containsKey(id)) {
                return true;
            }
        }
        return false;
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
            LOGGER.warn("String resource " + id + " was not found. Have all resource bundles been loaded?");
        }
        return result;
    }

    private boolean isOptionalId(String id) {
        int lastDotIndex = id.lastIndexOf('.');
        if (lastDotIndex >= 0) {
            return optionalIdSuffixes.contains(id.substring(lastDotIndex));
        } else {
            return false;
        }
    }

    /**
     * Gets a string represenation of an enumeration value from the resources.
     * @param enumValue the enumeration value
     * @return the string representation
     */
    public String getString(Enum<?> enumValue) {
        return getString(enumValue.getClass().getCanonicalName() + '_' + enumValue.name());
    }

    /**
     * Gets a string from the resources. The arguments in the form {1}, {2} etc.
     * are replaced with the objects in the <code>arguments</code> array.
     * @param id the id of the string
     * @param arguments the arguments
     * @return the string from the resources or <code>null</code> if no string was
     *         found in the resources.
     */
    public String getString(String id, Object... arguments) {
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

    /**
     * Gets a date format.
     * @param formatId the id of the string resource that describes the format of the date
     * @return the date format
     */
    public DateFormat getDateFormat(String formatId) {
        return new SimpleDateFormat(getString(formatId), locale);
    }

    /**
     * Formats a date.
     * @param formatId the id of the string resource that describes the format of the date
     * @param date the date to be formatted
     * @return the formatted date or the empty string if date == null
     */
    public String formatDate(String formatId, Date date) {
        if (date == null) {
            return "";
        } else {
            DateFormat df = getDateFormat(formatId);
            return df.format(date);
        }
    }

    /**
     * Parses a string containing a representation of a date.
     * @param formatId the id of the string resource that describes the format of the date
     * @param s the string
     * @return the date
     * @throws ParseException if the string does not represent a valid date
     */
    public Date parseDate(String formatId, String s) throws ParseException {
        ParsePosition position = new ParsePosition(0);
        DateFormat df = getDateFormat(formatId);
        Date date = df.parse(s, position);
        if (position.getIndex() < s.length()) {
            throw new ParseException("Date contains illegal characters", position.getIndex());
        }
        return date;
    }

    /**
     * Formats a double as a string.
     * @param d the double to be formatted
     * @return the formatted date
     */
    public String formatDouble(Double d) {
        return numberFormat.format(d);
    }

    /**
     * Parses a string containing a representation of a double.
     * @param s the string
     * @return the double value
     * @throws ParseException if the string does not represent a valid double
     */
    public double parseDouble(String s) throws ParseException {
        ParsePosition position = new ParsePosition(0);
        Number number = numberFormat.parse(s, position);
        if (position.getIndex() < s.length()) {
            throw new ParseException("Double contains illegal characters", position.getIndex());
        }
        return number.doubleValue();
    }
}
