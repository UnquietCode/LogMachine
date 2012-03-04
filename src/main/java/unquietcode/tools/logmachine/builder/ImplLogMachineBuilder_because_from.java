
package unquietcode.tools.logmachine.builder;


public class ImplLogMachineBuilder_because_from
    extends ImplLogMachineBuilder<LogMachineBuilder_because_from>
    implements LogMachineBuilder_because_from
{


    public ImplLogMachineBuilder_because_from(LogMachineHelper helper) {
        super(helper);
    }

    public ImplLogMachineBuilder_because from(String location) {
        _helper.from(location);
        return new ImplLogMachineBuilder_because(_helper);
    }

    public ImplLogMachineBuilder_from because(Throwable cause) {
        _helper.because(cause);
        return new ImplLogMachineBuilder_from(_helper);
    }

}
