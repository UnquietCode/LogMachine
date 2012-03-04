
package unquietcode.tools.logmachine.builder;


public class ImplLogMachineBuilder_from_to
    extends ImplLogMachineBuilder<LogMachineBuilder_from_to>
    implements LogMachineBuilder_from_to
{


    public ImplLogMachineBuilder_from_to(LogMachineHelper helper) {
        super(helper);
    }

    public ImplLogMachineBuilder_to from(String location) {
        _helper.from(location);
        return new ImplLogMachineBuilder_to(_helper);
    }

    public ImplLogMachineBuilder_from to(Enum... categories) {
        _helper.to(categories);
        return new ImplLogMachineBuilder_from(_helper);
    }

}
