package org.alfresco.consulting.locator.exceptions;


/**
 * Exception class used to encapsulate all the exceptions that can occur when
 * using the <b>Service Locator</b> pattern.
 *
 * @author Luis Cabaceira
 * @version 1.0
 */

public final class ServiceLocatorException extends RuntimeException
{
   /**
    * Constructs a new ServiceLocatorException with a message and an exception.
    *
    * @param message the exception message.
    * @param ex      the exception to be encapsulated.
    */
   public ServiceLocatorException(String message, Exception ex)
   {
      super(message, ex);
   }

   /**
    * Constructs a new ServiceLocatorException with an encapsulated exception.
    *
    * @param ex the exception to be encapsulated.
    */
   public ServiceLocatorException(Exception ex)
   {
      super(ex);
   }
}

