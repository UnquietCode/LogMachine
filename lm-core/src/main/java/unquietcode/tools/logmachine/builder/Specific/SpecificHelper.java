
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
public interface SpecificHelper {


    void because(Throwable cause);

    void from();

    void from(String location);

    void send(String message, Object... data);

    void to(Enum... categories);

    void with(String key, Number value);

    void with(String key, String value);

}
