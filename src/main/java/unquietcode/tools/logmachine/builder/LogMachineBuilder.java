
package unquietcode.tools.logmachine.builder;


public interface LogMachineBuilder<_ReturnValue >{


    void build();

    void debug(String message, Object... data);

    void warn(String message, Object... data);

    void info(String message, Object... data);

    void error(String message, Object... data);

    void trace(String message, Object... data);

    void mark(String event);

    void mark(String event, Enum... categories);

}
