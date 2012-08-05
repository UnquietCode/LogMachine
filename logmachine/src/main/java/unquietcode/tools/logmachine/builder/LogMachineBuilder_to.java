
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
 * Generated on July 07, 2012 21:09:26 CDT using version 0.2
 * 
 */
@Generated(value = "unquietcode.tools.flapi", date = "July 07, 2012 21:09:26 CDT", comments = "generated using Flapi, the fluent API generator for Java")
public interface LogMachineBuilder_to<_ReturnType >{


    _ReturnType debug(String message, Object... data);

    _ReturnType error(String message, Object... data);

    _ReturnType info(String message, Object... data);

    _ReturnType trace(String message, Object... data);

    _ReturnType warn(String message, Object... data);

    LogMachineBuilder<_ReturnType> to(Enum... categories);

}
