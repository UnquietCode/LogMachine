
package unquietcode.tools.logmachine.builder;


public interface LogMachineBuilder_because
    extends LogMachineBuilder<LogMachineBuilder_because>
{


    ImplLogMachineBuilder because(Throwable cause);

}
