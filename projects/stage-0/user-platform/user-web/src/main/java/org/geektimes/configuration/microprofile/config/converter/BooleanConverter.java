package org.geektimes.configuration.microprofile.config.converter;

import org.eclipse.microprofile.config.spi.Converter;

/**
 * @author Lin Zehuan
 */
public class BooleanConverter implements Converter<Boolean> {
    @Override
    public Boolean convert(String value) throws IllegalArgumentException, NullPointerException {
        return Boolean.parseBoolean(value);
    }
}
