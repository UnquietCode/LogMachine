
package unquietcode.tools.logmachine.builder;


public class ImplLogMachineBuilder_because
    extends ImplLogMachineBuilder<LogMachineBuilder_because>
    implements LogMachineBuilder_because
{


    public ImplLogMachineBuilder_because(LogMachineHelper helper) {
        super(helper);
    }

    public ImplLogMachineBuilder because(Throwable cause) {
        _helper.because(cause);
        return new ImplLogMachineBuilder(_helper);
    }

}
