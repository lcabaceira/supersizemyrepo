package org.alfresco.consulting.locator;

import org.alfresco.consulting.locator.exceptions.PropertiesLocatorException;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Looks for a properties file and loads the properties.
 *
 * @author Luis Cabaceira
 */
public final class PropertiesLocator
{
   private static final String INITIAL_CONTEXT_PROPERTY_PREFIX = "initialcontext";
   private static final String DEFAULT_FILE = "super-size-my-repo.properties";
   private static final PropertiesLocator me;
   private final Map cache;

   static
   {
      me = new PropertiesLocator();
   }

   /**
    * Creates a new instance of the PropertiesLocator class.
    */
   private PropertiesLocator()
   {
      cache = Collections.synchronizedMap(new HashMap());
   }

   /**
    * Returns the instance of PropertiesLocator class.
    *
    * @return the PropertiesLocator instance.
    */
   public static PropertiesLocator getInstance()
   {
      return me;
   }

   /**
    * Loads a properties file and returns it.
    *
    * @return the properties.
    * @throws org.alfresco.consulting.locator.exceptions.PropertiesLocatorException
    *          if could not load resource.
    */
   public static Properties getProperties() throws PropertiesLocatorException
   {
      return getProperties(DEFAULT_FILE);
   }

   /**
    * Loads a properties file and returns it.
    *
    * @param resource name of the resource file.
    * @return the properties.
    * @throws org.alfresco.consulting.locator.exceptions.PropertiesLocatorException
    *          if could not load resource.
    */
   public static Properties getProperties(String resource) throws PropertiesLocatorException
   {
      if (me.cache.containsKey(resource))
      {
         return (Properties) me.cache.get(resource);
      }

      final Properties prop = new Properties();
      try
      {
         prop.load(getResourceAsStream(resource));
         me.cache.put(resource, prop);
      }
      catch (IOException e)
      {
         throw new PropertiesLocatorException("Could not load  properties file \"" + resource + '\"', e);
      }
      return prop;
   }

   /**
    * Returns only the properties to be used for creating a InitialContext.
    *
    * @return the properties.
    */
   static Properties getInitialContextProperties()
   {
      return getInitialContextProperties(DEFAULT_FILE);
   }

   /**
    * Returns only the properties to be used for creating a InitialContext.
    *
    * @param resource name of the resource file.
    * @return the properties.
    */
   static Properties getInitialContextProperties(String resource)
   {
      final Properties props;
      final Properties returnProps = new Properties();
      try
      {
         props = PropertiesLocator.getProperties(resource);
      }
      catch (PropertiesLocatorException ex)
      {
         return returnProps;
      }

      final Enumeration keys = props.propertyNames();
      String key;
      while (keys.hasMoreElements())
      {
         key = keys.nextElement().toString();
         if (key.startsWith(INITIAL_CONTEXT_PROPERTY_PREFIX))
            returnProps.put(key.substring(key.indexOf('.') + 1), props.getProperty(key));
      }
      return returnProps;
   }

   /**
    * Looks for a resource in the classpath and returns it as a stream.
    *
    * @param resource the resource name
    * @return the stream.
    * @throws java.io.IOException if the resource is not found.
    */
   private static InputStream getResourceAsStream(String resource) throws IOException
   {
      InputStream in = null;
      final ClassLoader loader = PropertiesLocator.class.getClassLoader();
      if (loader != null)
         in = loader.getResourceAsStream(resource);
      if (in == null)
         in = ClassLoader.getSystemResourceAsStream(resource);
      if (in == null)
         throw new IOException("Could not find resource " + resource);
      return in;
   }
}