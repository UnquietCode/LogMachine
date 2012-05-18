
package unquietcode.tools.logmachine.builder;



/**
 * This class was generated using Flapi, the fluent API generator for Java.
 * Modifications to this file will be lost upon regeneration.
 * You have been warned!
 * 
 * Visit http://www.unquietcode.com/flapi for more information.
 * 
 * 
 * Generated on May 16, 2012 20:59:08 CDT using version 0.2
 * 
 */
public interface LogMachineHelper {


    Void _getReturnValue();

    void because(Throwable cause);

    void to(Enum... categories);

    void from(String location);

    void error(String message, Object... data);

    void warn(String message, Object... data);

    void info(String message, Object... data);

    void mark(String event, Enum... categories);

    void debug(String message, Object... data);

    void trace(String message, Object... data);

    void mark(String event);

}
