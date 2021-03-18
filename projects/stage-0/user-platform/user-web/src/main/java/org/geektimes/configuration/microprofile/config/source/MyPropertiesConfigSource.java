package org.geektimes.configuration.microprofile.config.source;

import org.eclipse.microprofile.config.spi.ConfigSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Lin Zehuan
 */
public class MyPropertiesConfigSource implements ConfigSource {

    private HashMap<String, String> properties = new HashMap<String, String>() {
        {
            put("version", "20210317");
            put("over", "true");
        }
    };

    @Override
    public Map<String, String> getProperties() {
        return this.properties;
    }

    @Override
    public Set<String> getPropertyNames() {
        return properties.keySet();
    }

    @Override
    public int getOrdinal() {
        return 0;
    }

    @Override
    public String getValue(String key) {
        return properties.get(key);
    }

    @Override
    public String getName() {
        return this.getClass().getName();
    }
}
