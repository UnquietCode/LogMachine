
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
public interface LogMachineBuilder_because_from<_ReturnType >{


    _ReturnType debug(String message, Object... data);

    _ReturnType error(String message, Object... data);

    _ReturnType info(String message, Object... data);

    _ReturnType mark(String event);

    _ReturnType mark(String event, Enum... categories);

    _ReturnType trace(String message, Object... data);

    _ReturnType warn(String message, Object... data);

    LogMachineBuilder_from<_ReturnType> because(Throwable cause);

    LogMachineBuilder_because<_ReturnType> from(String location);

}
