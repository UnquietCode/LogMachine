
package unquietcode.tools.logmachine.builder;


public class ImplLogMachineBuilder_to
    extends ImplLogMachineBuilder<LogMachineBuilder_to>
    implements LogMachineBuilder_to
{


    public ImplLogMachineBuilder_to(LogMachineHelper helper) {
        super(helper);
    }

    public ImplLogMachineBuilder to(Enum... categories) {
        _helper.to(categories);
        return new ImplLogMachineBuilder(_helper);
    }

}
