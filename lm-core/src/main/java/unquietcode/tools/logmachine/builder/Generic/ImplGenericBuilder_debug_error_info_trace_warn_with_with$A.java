
package unquietcode.tools.logmachine.builder.Generic;

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
public class ImplGenericBuilder_debug_error_info_trace_warn_with_with$A
    implements BuilderImplementation, GenericBuilder_debug_error_info_trace_warn_with_with$A
{

    private final GenericHelper _helper;
    private final Object _returnValue;

    public ImplGenericBuilder_debug_error_info_trace_warn_with_with$A(GenericHelper helper, Object returnValue) {
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

    public Object debug(String message, Object... data) {
        _checkInvocations();
        _helper.debug(message, data);
         
        return _returnValue;
    }

    public Object error(String message, Object... data) {
        _checkInvocations();
        _helper.error(message, data);
         
        return _returnValue;
    }

    public Object info(String message, Object... data) {
        _checkInvocations();
        _helper.info(message, data);
         
        return _returnValue;
    }

    public Object trace(String message, Object... data) {
        _checkInvocations();
        _helper.trace(message, data);
         
        return _returnValue;
    }

    public Object warn(String message, Object... data) {
        _checkInvocations();
        _helper.warn(message, data);
         
        return _returnValue;
    }

    public GenericBuilder_debug_error_info_trace_warn_with_with$A with(String key, Number value) {
        _helper.with(key, value);
         
        return this;
    }

    public GenericBuilder_debug_error_info_trace_warn_with_with$A with(String key, String value) {
        _helper.with(key, value);
         
        return this;
    }

}