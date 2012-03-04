
package unquietcode.tools.logmachine.builder;


public interface LogMachineBuilder_because_from_to
    extends LogMachineBuilder<LogMachineBuilder_because_from_to>
{


    ImplLogMachineBuilder_from_to because(Throwable cause);

    ImplLogMachineBuilder_because_to from(String location);

    ImplLogMachineBuilder_because_from to(Enum... categories);

}
