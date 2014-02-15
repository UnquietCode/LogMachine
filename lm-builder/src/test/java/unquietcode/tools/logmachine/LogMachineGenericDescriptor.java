package unquietcode.tools.logmachine;

import unquietcode.tools.flapi.Descriptor;

/**
 * @author Ben Fagin
 * @version 2013-08-22
 */
public class LogMachineGenericDescriptor extends BaseLogMachineDescriptor {

	@Override
	public Descriptor descriptor() {
		Descriptor builder = getStandardDescriptor()
			.setPackage("unquietcode.tools.logmachine.builder.generic")
			.setDescriptorName("GenericLogMachine")
			.setStartingMethodName("start")

			.addMethod("trace(String message, Object...data)")
				.withDocumentation("dispatch a new trace level event")
			.last()

			.addMethod("debug(String message, Object...data)")
				.withDocumentation("dispatch a new debug level event")
			.last()

			.addMethod("info(String message, Object...data)")
				.withDocumentation("dispatch a new info level event")
			.last()

			.addMethod("warn(String message, Object...data)")
				.withDocumentation("dispatch a new warn level event")
			.last()

			.addMethod("error(String message, Object...data)")
				.withDocumentation("dispatch a new error level event")
			.last()
		.build();

		return builder;
	}
}
