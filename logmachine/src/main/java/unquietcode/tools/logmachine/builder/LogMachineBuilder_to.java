
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
 * Generated on September 07, 2012 21:52:34 CDT using version 0.2
 * 
 */
@Generated(value = "unquietcode.tools.flapi", date = "September 07, 2012 21:52:34 CDT", comments = "generated using Flapi, the fluent API generator for Java")
public interface LogMachineBuilder_to<_ReturnType >{


    void error(String message, Object... data);

    void trace(String message, Object... data);

    void warn(String message, Object... data);

    void debug(String message, Object... data);

    void info(String message, Object... data);

    LogMachineBuilder<_ReturnType> to(Enum... categories);

}
