
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
public interface SpecificBuilder_from<_ReturnType >{


    SpecificBuilder_from<_ReturnType> with(String key, String value);

    SpecificBuilder_from<_ReturnType> with(String key, Number value);

    void send(String message, Object... data);

    SpecificBuilder<_ReturnType> from(String location);

}
