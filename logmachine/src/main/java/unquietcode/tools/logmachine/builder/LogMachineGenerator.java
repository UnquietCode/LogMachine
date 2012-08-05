
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
public class LogMachineGenerator {


    @SuppressWarnings("unchecked")
    public static LogMachineBuilder_because_from_to<Void> start(LogMachineHelper helper) {
        if (helper == null) {
            throw new IllegalArgumentException("Helper cannot be null.");
        }
         
        return new ImplLogMachineBuilder_because_from_to(helper, null);
    }

}
