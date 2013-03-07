
package unquietcode.tools.logmachine.builder.Generic;

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
public interface GenericBuilder_debug_error_from_from$A_info_to_trace_warn_with_with$A<_ReturnType >{


    _ReturnType debug(String message, Object... data);

    _ReturnType error(String message, Object... data);

    GenericBuilder_debug_error_info_to_trace_warn_with_with$A<_ReturnType> from();

    GenericBuilder_debug_error_info_to_trace_warn_with_with$A<_ReturnType> from(String location);

    _ReturnType info(String message, Object... data);

    GenericBuilder_debug_error_from_from$A_info_trace_warn_with_with$A<_ReturnType> to(Enum... categories);

    _ReturnType trace(String message, Object... data);

    _ReturnType warn(String message, Object... data);

    GenericBuilder_debug_error_from_from$A_info_to_trace_warn_with_with$A<_ReturnType> with(String key, Number value);

    GenericBuilder_debug_error_from_from$A_info_to_trace_warn_with_with$A<_ReturnType> with(String key, String value);

}
