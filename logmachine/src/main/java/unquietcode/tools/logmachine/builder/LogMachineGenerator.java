
package unquietcode.tools.logmachine.builder;

import unquietcode.tools.flapi.DescriptorBuilderException;


/**
 * This class was generated using Flapi, the fluent API generator for Java.
 * Modifications to this file will be lost upon regeneration.
 * You have been warned!
 * 
 * Visit http://www.unquietcode.com/flapi for more information.
 * 
 * 
 * Generated on May 16, 2012 20:59:08 CDT using version 0.2
 * 
 */
public class LogMachineGenerator {


    @SuppressWarnings("unchecked")
    public static LogMachineBuilder_because_from_to<Void> start(LogMachineHelper helper) {
        if (helper == null) {
            throw new DescriptorBuilderException("Helper cannot be null.");
        }
         
        return new ImplLogMachineBuilder_because_from_to(helper, helper._getReturnValue());
    }

}
