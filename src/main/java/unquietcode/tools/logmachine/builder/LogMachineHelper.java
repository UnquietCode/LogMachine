
package unquietcode.tools.logmachine.builder;


public interface LogMachineHelper {


    void debug(String message, Object... data);

    void warn(String message, Object... data);

    void info(String message, Object... data);

    void error(String message, Object... data);

    void trace(String message, Object... data);

    void from(String location);

    void to(Enum... categories);

    void because(Throwable cause);

    void mark(String event);

    void mark(String event, Enum... categories);

}
