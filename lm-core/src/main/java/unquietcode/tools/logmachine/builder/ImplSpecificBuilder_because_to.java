
package unquietcode.tools.logmachine.builder;

import unquietcode.tools.flapi.support.v0_2.BuilderImplementation;

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
public class ImplSpecificBuilder_because_to
    implements BuilderImplementation, SpecificBuilder_because_to
{

    private final SpecificHelper _helper;
    private final Object _returnValue;

    ImplSpecificBuilder_because_to(SpecificHelper helper, Object returnValue) {
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

    public SpecificBuilder_because_to with(String key, Number value) {
        _helper.with(key, value);
         
        return this;
    }

    public SpecificBuilder_because to(Enum... categories) {
        _helper.to(categories);
        ImplSpecificBuilder_because step1 = new ImplSpecificBuilder_because(_helper, _returnValue);
         
        _transferInvocations(step1);
        return step1;
    }

    public void send(String message, Object... data) {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.send(message, data);
         
    }

    public SpecificBuilder_because_to with(String key, String value) {
        _helper.with(key, value);
         
        return this;
    }

    public SpecificBuilder_to because(Throwable cause) {
        _helper.because(cause);
        ImplSpecificBuilder_to step1 = new ImplSpecificBuilder_to(_helper, _returnValue);
         
        _transferInvocations(step1);
        return step1;
    }

}