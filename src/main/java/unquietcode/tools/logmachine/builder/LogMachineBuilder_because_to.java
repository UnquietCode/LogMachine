
package unquietcode.tools.logmachine.builder;


public interface LogMachineBuilder_because_to
    extends LogMachineBuilder<LogMachineBuilder_because_to>
{


    ImplLogMachineBuilder_to because(Throwable cause);

    ImplLogMachineBuilder_because to(Enum... categories);

}
