
package unquietcode.tools.logmachine.builder.LogMachine;

import unquietcode.tools.flapi.support.BuilderImplementation;
import unquietcode.tools.flapi.support.ObjectWrapper;
import unquietcode.tools.logmachine.builder.Generic.GenericBuilder_because_debug_error_from_from$A_info_to_trace_warn_with_with$A;
import unquietcode.tools.logmachine.builder.Generic.GenericHelper;
import unquietcode.tools.logmachine.builder.Generic.ImplGenericBuilder_because_debug_error_from_from$A_info_to_trace_warn_with_with$A;
import unquietcode.tools.logmachine.builder.Specific.ImplSpecificBuilder_because_from_from$A_send_to_with_with$A;
import unquietcode.tools.logmachine.builder.Specific.SpecificBuilder_because_from_from$A_send_to_with_with$A;
import unquietcode.tools.logmachine.builder.Specific.SpecificHelper;

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
public class ImplLogMachineBuilder_generic_specific
    implements BuilderImplementation, LogMachineBuilder_generic_specific
{

    private final LogMachineHelper _helper;
    private final Object _returnValue;

    public ImplLogMachineBuilder_generic_specific(LogMachineHelper helper, Object returnValue) {
        _helper = helper;
        _returnValue = returnValue;
    }

    public BuilderImplementation _getParent() {
        if (_returnValue instanceof BuilderImplementation) {
            return ((BuilderImplementation) _returnValue);
        } else {
            return null;
        }
    }

    private void _transferInvocations(Object next) {
        // nothing
    }

    public void _checkInvocations() {
        // nothing
    }

    public GenericBuilder_because_debug_error_from_from$A_info_to_trace_warn_with_with$A generic() {
        _checkInvocations();
        ObjectWrapper<GenericHelper> helper1 = new ObjectWrapper<GenericHelper>();
        _helper.generic(helper1);
        ImplGenericBuilder_because_debug_error_from_from$A_info_to_trace_warn_with_with$A step1 = new ImplGenericBuilder_because_debug_error_from_from$A_info_to_trace_warn_with_with$A(helper1 .get(), _returnValue);
         
        return step1;
    }

    public SpecificBuilder_because_from_from$A_send_to_with_with$A specific() {
        _checkInvocations();
        ObjectWrapper<SpecificHelper> helper1 = new ObjectWrapper<SpecificHelper>();
        _helper.specific(helper1);
        ImplSpecificBuilder_because_from_from$A_send_to_with_with$A step1 = new ImplSpecificBuilder_because_from_from$A_send_to_with_with$A(helper1 .get(), _returnValue);
         
        return step1;
    }

}
