
package unquietcode.tools.logmachine.builder;


public interface LogMachineBuilder_because_from
    extends LogMachineBuilder<LogMachineBuilder_because_from>
{


    ImplLogMachineBuilder_because from(String location);

    ImplLogMachineBuilder_from because(Throwable cause);

}
