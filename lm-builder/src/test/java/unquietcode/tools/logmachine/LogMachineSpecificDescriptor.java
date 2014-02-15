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

			.addMethod("send(String message, Object...data)")
				.withDocumentation()
					.addContent("Complete the log event and dispatch it.\n\n")
					.addContent("This absolutely MUST be called to trigger the event,\n")
					.addContent("although it does not guarantee that the event will\n")
					.addContent("actually be sent, depending on if the initial\n")
					.addContent("conditions were met (like 'info()', 'debug()', when(..)').")
				.finish()
			.last()
		.build();

		return builder;
	}
}
