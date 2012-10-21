
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
 * Generated on October 21, 2012 1:37:21 CDT using version 0.2
 * 
 */
@Generated(value = "unquietcode.tools.flapi", date = "October 21, 2012 1:37:21 CDT", comments = "generated using Flapi, the fluent API generator for Java")
public interface GenericBuilder_because_from_to<_ReturnType >{


    void info(String message, Object... data);

    void warn(String message, Object... data);

    void trace(String message, Object... data);

    GenericBuilder_because_from_to<_ReturnType> with(String key, String value);

    GenericBuilder_because_from<_ReturnType> to(Enum... categories);

    GenericBuilder_from_to<_ReturnType> because(Throwable cause);

    void debug(String message, Object... data);

    GenericBuilder_because_to<_ReturnType> from(String location);

    void error(String message, Object... data);

    GenericBuilder_because_from_to<_ReturnType> with(String key, Number value);

}