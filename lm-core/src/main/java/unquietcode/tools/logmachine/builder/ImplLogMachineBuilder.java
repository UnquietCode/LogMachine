
package unquietcode.tools.logmachine.builder;

import unquietcode.tools.flapi.support.v0_2.BuilderImplementation;
import unquietcode.tools.flapi.support.v0_2.ObjectWrapper;

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
public class ImplLogMachineBuilder
    implements BuilderImplementation, LogMachineBuilder
{

    private final LogMachineHelper _helper;
    private final Object _returnValue;

    ImplLogMachineBuilder(LogMachineHelper helper, Object returnValue) {
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

    public SpecificBuilder_because_from_to specific() {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        ObjectWrapper<SpecificHelper> helper1 = new ObjectWrapper<SpecificHelper>();
        _helper.specific(helper1);
        ImplSpecificBuilder_because_from_to step1 = new ImplSpecificBuilder_because_from_to(helper1 .get(), null);
         
        return step1;
    }

    public GenericBuilder_because_from_to generic() {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        ObjectWrapper<GenericHelper> helper1 = new ObjectWrapper<GenericHelper>();
        _helper.generic(helper1);
        ImplGenericBuilder_because_from_to step1 = new ImplGenericBuilder_because_from_to(helper1 .get(), null);
         
        return step1;
    }

}
