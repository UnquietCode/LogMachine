
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
public class ImplGenericBuilder_because_from
    implements BuilderImplementation, GenericBuilder_because_from
{

    private final GenericHelper _helper;
    private final Object _returnValue;

    ImplGenericBuilder_because_from(GenericHelper helper, Object returnValue) {
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

    public void warn(String message, Object... data) {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.warn(message, data);
         
    }

    public GenericBuilder_because_from with(String key, String value) {
        _helper.with(key, value);
         
        return this;
    }

    public void trace(String message, Object... data) {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.trace(message, data);
         
    }

    public void debug(String message, Object... data) {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.debug(message, data);
         
    }

    public void error(String message, Object... data) {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.error(message, data);
         
    }

    public void info(String message, Object... data) {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.info(message, data);
         
    }

    public GenericBuilder_from because(Throwable cause) {
        _helper.because(cause);
        ImplGenericBuilder_from step1 = new ImplGenericBuilder_from(_helper, _returnValue);
         
        _transferInvocations(step1);
        return step1;
    }

    public GenericBuilder_because from(String location) {
        _helper.from(location);
        ImplGenericBuilder_because step1 = new ImplGenericBuilder_because(_helper, _returnValue);
         
        _transferInvocations(step1);
        return step1;
    }

    public GenericBuilder_because_from with(String key, Number value) {
        _helper.with(key, value);
         
        return this;
    }

}
