
package unquietcode.tools.logmachine.builder;


public class ImplLogMachineBuilder_because_to
    extends ImplLogMachineBuilder<LogMachineBuilder_because_to>
    implements LogMachineBuilder_because_to
{


    public ImplLogMachineBuilder_because_to(LogMachineHelper helper) {
        super(helper);
    }

    public ImplLogMachineBuilder_to because(Throwable cause) {
        _helper.because(cause);
        return new ImplLogMachineBuilder_to(_helper);
    }

    public ImplLogMachineBuilder_because to(Enum... categories) {
        _helper.to(categories);
        return new ImplLogMachineBuilder_because(_helper);
    }

}
