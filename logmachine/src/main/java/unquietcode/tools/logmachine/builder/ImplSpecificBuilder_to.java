
package unquietcode.tools.logmachine.builder;

import javax.annotation.Generated;
import unquietcode.tools.flapi.support.v0_2.BuilderImplementation;


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
public class ImplSpecificBuilder_to
    implements BuilderImplementation, SpecificBuilder_to
{

    private final SpecificHelper _helper;
    private final Object _returnValue;

    ImplSpecificBuilder_to(SpecificHelper helper, Object returnValue) {
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

    public SpecificBuilder_to with(String key, String value) {
        _helper.with(key, value);
         
        return this;
    }

    public SpecificBuilder_to with(String key, Number value) {
        _helper.with(key, value);
         
        return this;
    }

    public SpecificBuilder to(Enum... categories) {
        _helper.to(categories);
        ImplSpecificBuilder step1 = new ImplSpecificBuilder(_helper, _returnValue);
         
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

}
