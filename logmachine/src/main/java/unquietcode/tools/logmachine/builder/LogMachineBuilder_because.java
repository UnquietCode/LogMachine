
package unquietcode.tools.logmachine.builder;

import javax.annotation.Generated;


/**
 * This class was generated using Flapi, the fluent API generator for Java.
 * Modifications to this file will be lost upon regeneration.
 * You have been warned!
 * 
 * Visit http://www.unquietcode.com/flapi for more information.
 * 
 * 
 * Generated on September 11, 2012 17:49:09 CDT using version 0.2
 * 
 */
@Generated(value = "unquietcode.tools.flapi", date = "September 11, 2012 17:49:09 CDT", comments = "generated using Flapi, the fluent API generator for Java")
public interface LogMachineBuilder_because<_ReturnType >{


    void error(String message, Object... data);

    void warn(String message, Object... data);

    void trace(String message, Object... data);

    LogMachineBuilder<_ReturnType> because(Throwable cause);

    void info(String message, Object... data);

    LogMachineBuilder_because<_ReturnType> with(String key, String value);

    LogMachineBuilder_because<_ReturnType> with(String key, Number value);

    void debug(String message, Object... data);

}
