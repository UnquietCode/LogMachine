
package unquietcode.tools.logmachine.builder.Specific;

import unquietcode.tools.flapi.support.BuilderImplementation;

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
public class ImplSpecificBuilder_send_with_with$A
    implements BuilderImplementation, SpecificBuilder_send_with_with$A
{

    private final SpecificHelper _helper;
    private final Object _returnValue;

    public ImplSpecificBuilder_send_with_with$A(SpecificHelper helper, Object returnValue) {
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

    public Object send(String message, Object... data) {
        _checkInvocations();
        _helper.send(message, data);
         
        return _returnValue;
    }

    public SpecificBuilder_send_with_with$A with(String key, Number value) {
        _helper.with(key, value);
         
        return this;
    }

    public SpecificBuilder_send_with_with$A with(String key, String value) {
        _helper.with(key, value);
         
        return this;
    }

}
