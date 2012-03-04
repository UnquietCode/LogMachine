
package unquietcode.tools.logmachine.builder;


public class ImplLogMachineBuilder<_ReturnValue >
    implements LogMachineBuilder<_ReturnValue>
{

    protected final LogMachineHelper _helper;

    public ImplLogMachineBuilder(LogMachineHelper helper) {
        _helper = helper;
    }

    public void build() {
    }

    public void error(String message, Object... data) {
        _helper.error(message, data);
    }

    public void trace(String message, Object... data) {
        _helper.trace(message, data);
    }

    public void info(String message, Object... data) {
        _helper.info(message, data);
    }

    public void warn(String message, Object... data) {
        _helper.warn(message, data);
    }

    public void mark(String event, Enum... categories) {
        _helper.mark(event, categories);
    }

    public void mark(String event) {
        _helper.mark(event);
    }

    public void debug(String message, Object... data) {
        _helper.debug(message, data);
    }

}
