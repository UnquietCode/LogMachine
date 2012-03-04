
package unquietcode.tools.logmachine.builder;


public class ImplLogMachineBuilder_from
    extends ImplLogMachineBuilder<LogMachineBuilder_from>
    implements LogMachineBuilder_from
{


    public ImplLogMachineBuilder_from(LogMachineHelper helper) {
        super(helper);
    }

    public ImplLogMachineBuilder from(String location) {
        _helper.from(location);
        return new ImplLogMachineBuilder(_helper);
    }

}
