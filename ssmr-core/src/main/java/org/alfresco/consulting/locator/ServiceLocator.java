package org.alfresco.consulting.locator;

import org.alfresco.consulting.locator.exceptions.ServiceLocatorException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * This class is an implementation of the <b>Service Locator</b> pattern.
 *
 * @author Luis Cabaceira
 * @version 1.0
 */
public final class ServiceLocator
{
   private static final String PROPERTIES_FILE = "super-size-my-repo.properties";
   private static final ServiceLocator me;
   private final InitialContext context;
   private Map cache;

   static
   {
      me = new ServiceLocator();
   }

   /**
    * Creates a new instance of the ServiceLocator class.
    *
    * @throws ServiceLocatorException if a NamingException occurs when creating InitialContext.
    */
   private ServiceLocator() throws ServiceLocatorException
   {
      try
      {
         context = new InitialContext(PropertiesLocator.getInitialContextProperties(PROPERTIES_FILE));
      }
      catch (NamingException ne)
      {
         throw new ServiceLocatorException("Error creating Initial Context", ne);
      }
      cache = Collections.synchronizedMap(new HashMap());
   }

   /**
    * Returns the instance of ServiceLocator class.
    *
    * @return the ServiceLocator instance.
    */
   public static ServiceLocator getInstance()
   {
      return me;
   }

   /**
    * Deletes the cache.
    */
   public void refresh()
   {
      cache.clear();
   }



   /**
    * Returns the DataSource object for the requested service name.
    *
    * @param name the name by which the object is bound in the JNDI tree.
    * @return DataSource of the object being looked up.
    * @throws ServiceLocatorException if any error occurs in the lookup.
    */
   public DataSource getDBConn(String name) throws ServiceLocatorException
   {
      if (cache.containsKey(name))
      {
         return (DataSource) cache.get(name);
      }

      try
      {
         final DataSource ds = (DataSource) context.lookup(name);
         cache.put(name, ds);

         return ds;
      }
      catch (NamingException ne)
      {
         throw new ServiceLocatorException("Error returning data source for \"" + name + '\"', ne);
      }
      catch (ClassCastException ce)
      {
         throw new ServiceLocatorException("Error returning data source for \"" + name + '\"', ce);
      }
   }
}

