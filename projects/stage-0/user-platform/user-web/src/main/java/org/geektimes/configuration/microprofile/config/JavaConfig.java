package org.geektimes.configuration.microprofile.config;


import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigValue;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.eclipse.microprofile.config.spi.Converter;
import org.geektimes.configuration.microprofile.config.converter.StringConverter;

import java.util.*;

public class JavaConfig implements Config {

    /**
     * 内部可变的集合，不要直接暴露在外面
     */
    private List<ConfigSource> configSources = new LinkedList<>();

    private Map<String,Converter> converters = new HashMap();

    private static Comparator<ConfigSource> configSourceComparator = new Comparator<ConfigSource>() {
        @Override
        public int compare(ConfigSource o1, ConfigSource o2) {
            return Integer.compare(o2.getOrdinal(), o1.getOrdinal());
        }
    };

    public JavaConfig() {
        ClassLoader classLoader = getClass().getClassLoader();
        ServiceLoader<ConfigSource> serviceLoader = ServiceLoader.load(ConfigSource.class, classLoader);
        serviceLoader.forEach(configSources::add);
        // 排序
        configSources.sort(configSourceComparator);

        ServiceLoader<Converter> converterServiceLoader = ServiceLoader.load(Converter.class, classLoader);
        converterServiceLoader.forEach((c)->{
            try {
                converters.put(c.getClass().getDeclaredMethod("convert", String.class).getReturnType().getName(), c);
            } catch (NoSuchMethodException e) {

            }
        });
    }

    @Override
    public <T> T getValue(String propertyName, Class<T> propertyType) {
        String propertyValue = getPropertyValue(propertyName);
        Converter<T> converter = converters.get(propertyType.getName());
        return converter.convert(propertyValue);
    }

    @Override
    public ConfigValue getConfigValue(String propertyName) {
        return null;
    }

    protected String getPropertyValue(String propertyName) {
        String propertyValue = null;
        for (ConfigSource configSource : configSources) {
            propertyValue = configSource.getValue(propertyName);
            if (propertyValue != null) {
                break;
            }
        }
        return propertyValue;
    }

    @Override
    public <T> Optional<T> getOptionalValue(String propertyName, Class<T> propertyType) {
        T value = getValue(propertyName, propertyType);
        return Optional.ofNullable(value);
    }

    @Override
    public Iterable<String> getPropertyNames() {
        return null;
    }

    @Override
    public Iterable<ConfigSource> getConfigSources() {
        return Collections.unmodifiableList(configSources);
    }

    @Override
    public <T> Optional<Converter<T>> getConverter(Class<T> forType) {
        return Optional.empty();
    }

    @Override
    public <T> T unwrap(Class<T> type) {
        return null;
    }
}
