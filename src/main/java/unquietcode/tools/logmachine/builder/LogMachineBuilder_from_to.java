
package unquietcode.tools.logmachine.builder;


public interface LogMachineBuilder_from_to
    extends LogMachineBuilder<LogMachineBuilder_from_to>
{


    ImplLogMachineBuilder_to from(String location);

    ImplLogMachineBuilder_from to(Enum... categories);

}
