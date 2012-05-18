
package unquietcode.tools.logmachine.builder;



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
public class ImplLogMachineBuilder_to
    implements LogMachineBuilder_to
{

    private final LogMachineHelper _helper;
    private final Object _returnValue;

    ImplLogMachineBuilder_to(LogMachineHelper helper, Object returnValue) {
        _helper = helper;
        _returnValue = returnValue;
    }

    private void _transferInvocations(Object next) {
        // nothing
    }

    private void _checkInvocations() {
        // nothing
    }

    public Object debug(String message, Object... data) {
        _checkInvocations();
        _helper.debug(message, data);
         
        Object retval = _returnValue;
        return retval;
    }

    public Object error(String message, Object... data) {
        _checkInvocations();
        _helper.error(message, data);
         
        Object retval = _returnValue;
        return retval;
    }

    public Object info(String message, Object... data) {
        _checkInvocations();
        _helper.info(message, data);
         
        Object retval = _returnValue;
        return retval;
    }

    public Object mark(String event) {
        _checkInvocations();
        _helper.mark(event);
         
        Object retval = _returnValue;
        return retval;
    }

    public Object mark(String event, Enum... categories) {
        _checkInvocations();
        _helper.mark(event, categories);
         
        Object retval = _returnValue;
        return retval;
    }

    public Object trace(String message, Object... data) {
        _checkInvocations();
        _helper.trace(message, data);
         
        Object retval = _returnValue;
        return retval;
    }

    public Object warn(String message, Object... data) {
        _checkInvocations();
        _helper.warn(message, data);
         
        Object retval = _returnValue;
        return retval;
    }

    public LogMachineBuilder to(Enum... categories) {
        _helper.to(categories);
         
        LogMachineBuilder retval = new ImplLogMachineBuilder(_helper, _returnValue);
        _transferInvocations(retval);
        return retval;
    }

}
