
package unquietcode.tools.logmachine.builder.LogMachine;

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
public class LogMachineGenerator {


    @SuppressWarnings("unchecked")
    public static LogMachineBuilder_generic_specific<Void> start(LogMachineHelper helper) {
        if (helper == null) {
            throw new IllegalArgumentException("Helper cannot be null.");
        }
         
        return new ImplLogMachineBuilder_generic_specific(helper, null);
    }

}
