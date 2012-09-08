
package unquietcode.tools.logmachine.builder;

import java.lang.reflect.Field;
import javax.annotation.Generated;
import unquietcode.tools.flapi.support.v0_2.BuilderImplementation;
import unquietcode.tools.flapi.support.v0_2.ExpectedInvocationsException;


/**
 * This class was generated using Flapi, the fluent API generator for Java.
 * Modifications to this file will be lost upon regeneration.
 * You have been warned!
 * 
 * Visit http://www.unquietcode.com/flapi for more information.
 * 
 * 
 * Generated on September 07, 2012 21:52:34 CDT using version 0.2
 * 
 */
@Generated(value = "unquietcode.tools.flapi", date = "September 07, 2012 21:52:34 CDT", comments = "generated using Flapi, the fluent API generator for Java")
public class ImplLogMachineBuilder_to
    implements BuilderImplementation, LogMachineBuilder_to
{

    private final LogMachineHelper _helper;
    private final Object _returnValue;
    int ic_LogMachine_to$ = 1;

    ImplLogMachineBuilder_to(LogMachineHelper helper, Object returnValue) {
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
        Class clazz = next.getClass();
         
        try {
            Field field = clazz.getDeclaredField("ic_LogMachine_to$");
            field.setInt(next, ic_LogMachine_to$);
        } catch (Exception _x) {
            // nothing
        }
    }

    public void _checkInvocations() {
        if (ic_LogMachine_to$ > 0) {
            throw new ExpectedInvocationsException("Expected at least 1 invocations of method 'to(Enum...categories)'.");
        }
    }

    public void error(String message, Object... data) {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.error(message, data);
         
    }

    public void trace(String message, Object... data) {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.trace(message, data);
         
    }

    public void warn(String message, Object... data) {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.warn(message, data);
         
    }

    public void debug(String message, Object... data) {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.debug(message, data);
         
    }

    public void info(String message, Object... data) {
        BuilderImplementation cur = this;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.info(message, data);
         
    }

    public LogMachineBuilder to(Enum... categories) {
        --ic_LogMachine_to$;
        _helper.to(categories);
        ImplLogMachineBuilder step1 = new ImplLogMachineBuilder(_helper, _returnValue);
         
        _transferInvocations(step1);
        return step1;
    }

}
