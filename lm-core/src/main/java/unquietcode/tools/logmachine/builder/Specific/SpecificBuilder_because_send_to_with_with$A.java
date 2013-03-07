
package unquietcode.tools.logmachine.builder.Specific;

import javax.annotation.Generated;


/**
 * This class was generated using Flapi, the fluent API generator for Java.
 * Modifications to this file will be lost upon regeneration.
 * You have been warned!
 * 
 * Visit https://github.com/UnquietCode/Flapi for more information.
 * 
 * 
 * Generated on March 07, 2013 15:03:14 PST using version 0.3
 * 
 */
@Generated(value = "unquietcode.tools.flapi", date = "March 07, 2013 15:03:14 PST", comments = "generated using Flapi, the fluent API generator for Java")
public interface SpecificBuilder_because_send_to_with_with$A<_ReturnType >{


    SpecificBuilder_send_to_with_with$A<_ReturnType> because(Throwable cause);

    _ReturnType send(String message, Object... data);

    SpecificBuilder_because_send_with_with$A<_ReturnType> to(Enum... categories);

    SpecificBuilder_because_send_to_with_with$A<_ReturnType> with(String key, Number value);

    SpecificBuilder_because_send_to_with_with$A<_ReturnType> with(String key, String value);

}
