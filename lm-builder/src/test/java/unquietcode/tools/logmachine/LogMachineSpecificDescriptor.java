package unquietcode.tools.logmachine;

import unquietcode.tools.flapi.Descriptor;

/**
 * @author Ben Fagin
 * @version 2013-08-22
 */
public class LogMachineSpecificDescriptor extends BaseLogMachineDescriptor {
	@Override
	public Descriptor descriptor() {
		Descriptor builder = getStandardDescriptor()
			.setPackage("unquietcode.tools.logmachine.builder.specific")
			.setDescriptorName("SpecificLogMachine")
			.setStartingMethodName("start")

			.addMethod("send(String message, Object...data)").last()
		.build();

		return builder;	}
}
