package nl.gogognome.lib.util;

import java.util.Date;

/**
 * Factory classes for creating dates.
 */
@FunctionalInterface
public interface DateFactory {

    /**
     * Creates a {@link Date} that represents the current time.
     *
     * @return a {@link Date} that represents the current time
     */
    Date createNow();
}
