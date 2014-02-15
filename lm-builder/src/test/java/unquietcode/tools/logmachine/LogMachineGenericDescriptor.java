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

			.addMethod("debug(String message, Object...data)").last()
			.addMethod("info(String message, Object...data)").last()
			.addMethod("trace(String message, Object...data)").last()
			.addMethod("warn(String message, Object...data)").last()
			.addMethod("error(String message, Object...data)").last()
		.build();

		return builder;
	}
}
