
package unquietcode.tools.logmachine.builder;


public class ImplLogMachineBuilder_because_from_to
    extends ImplLogMachineBuilder<LogMachineBuilder_because_from_to>
    implements LogMachineBuilder_because_from_to
{


    public ImplLogMachineBuilder_because_from_to(LogMachineHelper helper) {
        super(helper);
    }

    public ImplLogMachineBuilder_from_to because(Throwable cause) {
        _helper.because(cause);
        return new ImplLogMachineBuilder_from_to(_helper);
    }

    public ImplLogMachineBuilder_because_to from(String location) {
        _helper.from(location);
        return new ImplLogMachineBuilder_because_to(_helper);
    }

    public ImplLogMachineBuilder_because_from to(Enum... categories) {
        _helper.to(categories);
        return new ImplLogMachineBuilder_because_from(_helper);
    }

}
