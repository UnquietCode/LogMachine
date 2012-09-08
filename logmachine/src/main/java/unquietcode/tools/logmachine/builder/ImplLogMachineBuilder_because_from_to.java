
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
 * Generated on September 07, 2012 22:37:16 CDT using version 0.2
 * 
 */
@Generated(value = "unquietcode.tools.flapi", date = "September 07, 2012 22:37:16 CDT", comments = "generated using Flapi, the fluent API generator for Java")
public class ImplLogMachineBuilder_because_from_to
    implements BuilderImplementation, LogMachineBuilder_because_from_to
{

    private final LogMachineHelper _helper;
    private final Object _returnValue;

    ImplLogMachineBuilder_because_from_to(LogMachineHelper helper, Object returnValue) {
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

    public LogMachineBuilder_because_to from(String location) {
        _helper.from(location);
        ImplLogMachineBuilder_because_to step1 = new ImplLogMachineBuilder_because_to(_helper, _returnValue);
         
        _transferInvocations(step1);
        return step1;
    }

    public LogMachineBuilder_because_from to(Enum... categories) {
        _helper.to(categories);
        ImplLogMachineBuilder_because_from step1 = new ImplLogMachineBuilder_because_from(_helper, _returnValue);
         
        _transferInvocations(step1);
        return step1;
    }

    public void info(String message, Object... data) {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.info(message, data);
         
    }

    public void warn(String message, Object... data) {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.warn(message, data);
         
    }

    public void error(String message, Object... data) {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.error(message, data);
         
    }

    public void debug(String message, Object... data) {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.debug(message, data);
         
    }

    public void trace(String message, Object... data) {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.trace(message, data);
         
    }

    public LogMachineBuilder_from_to because(Throwable cause) {
        _helper.because(cause);
        ImplLogMachineBuilder_from_to step1 = new ImplLogMachineBuilder_from_to(_helper, _returnValue);
         
        _transferInvocations(step1);
        return step1;
    }

}
