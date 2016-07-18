package org.alfresco.consulting;

import java.util.Properties;

public class PropertiesUtil {
    public static Boolean getBooleanValue(Properties props, String propName, Boolean defaultValue) {
        return props.containsKey(propName) ? Boolean.valueOf(props.getProperty(propName)) : defaultValue;
    }
}
