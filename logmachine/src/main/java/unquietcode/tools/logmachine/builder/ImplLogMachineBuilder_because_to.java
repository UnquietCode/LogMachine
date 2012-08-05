
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
 * Generated on July 07, 2012 21:09:26 CDT using version 0.2
 * 
 */
@Generated(value = "unquietcode.tools.flapi", date = "July 07, 2012 21:09:26 CDT", comments = "generated using Flapi, the fluent API generator for Java")
public class ImplLogMachineBuilder_because_to
    implements BuilderImplementation, LogMachineBuilder_because_to
{

    private final LogMachineHelper _helper;
    private final BuilderImplementation _returnValue;
    int ic_LogMachine_because$Throwable_cause = 1;
    int ic_LogMachine_from$String_location = 1;
    int ic_LogMachine_to$ = 1;

    ImplLogMachineBuilder_because_to(LogMachineHelper helper, BuilderImplementation returnValue) {
        _helper = helper;
        _returnValue = returnValue;
    }

    public BuilderImplementation _getParent() {
        return _returnValue;
    }

    private void _transferInvocations(Object next) {
        Class clazz = next.getClass();
         
        try {
            Field field = clazz.getDeclaredField("ic_LogMachine_because$Throwable_cause");
            field.setInt(next, ic_LogMachine_because$Throwable_cause);
        } catch (Exception _x) {
            // nothing
        }
         
        try {
            Field field = clazz.getDeclaredField("ic_LogMachine_from$String_location");
            field.setInt(next, ic_LogMachine_from$String_location);
        } catch (Exception _x) {
            // nothing
        }
         
        try {
            Field field = clazz.getDeclaredField("ic_LogMachine_to$");
            field.setInt(next, ic_LogMachine_to$);
        } catch (Exception _x) {
            // nothing
        }
    }

    public void _checkInvocations() {
        if (ic_LogMachine_because$Throwable_cause > 0) {
            throw new ExpectedInvocationsException("Expected at least 1 invocations of method 'because(Throwable cause)'.");
        }
        if (ic_LogMachine_from$String_location > 0) {
            throw new ExpectedInvocationsException("Expected at least 1 invocations of method 'from(String location)'.");
        }
        if (ic_LogMachine_to$ > 0) {
            throw new ExpectedInvocationsException("Expected at least 1 invocations of method 'to(Enum...categories)'.");
        }
    }

    public BuilderImplementation debug(String message, Object... data) {
        BuilderImplementation cur = _returnValue;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.debug(message, data);
         
        return _returnValue;
    }

    public BuilderImplementation error(String message, Object... data) {
        BuilderImplementation cur = _returnValue;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.error(message, data);
         
        return _returnValue;
    }

    public BuilderImplementation info(String message, Object... data) {
        BuilderImplementation cur = _returnValue;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.info(message, data);
         
        return _returnValue;
    }

    public BuilderImplementation trace(String message, Object... data) {
        BuilderImplementation cur = _returnValue;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.trace(message, data);
         
        return _returnValue;
    }

    public BuilderImplementation warn(String message, Object... data) {
        BuilderImplementation cur = _returnValue;
        while (cur!= null) {
            cur._checkInvocations();
            cur = cur._getParent();
        }
         
        _helper.warn(message, data);
         
        return _returnValue;
    }

    public LogMachineBuilder_to because(Throwable cause) {
        --ic_LogMachine_because$Throwable_cause;
        _helper.because(cause);
         
        LogMachineBuilder_to retval = new ImplLogMachineBuilder_to(_helper, _returnValue);
        _transferInvocations(retval);
        return retval;
    }

    public LogMachineBuilder_because to(Enum... categories) {
        --ic_LogMachine_to$;
        _helper.to(categories);
         
        LogMachineBuilder_because retval = new ImplLogMachineBuilder_because(_helper, _returnValue);
        _transferInvocations(retval);
        return retval;
    }

}
