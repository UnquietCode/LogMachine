package unquietcode.tools.logmachine;

import unquietcode.tools.flapi.Descriptor;
import unquietcode.tools.flapi.DescriptorMaker;
import unquietcode.tools.flapi.Flapi;

/**
 * @author Ben Fagin
 * @version 2013-08-22
 */
public class LogMachineSpecificDescriptor implements DescriptorMaker {
	@Override
	public Descriptor descriptor() {
		Descriptor builder = Flapi.builder()
			.setPackage("unquietcode.tools.logmachine.builder.specific")
			.setDescriptorName("SpecificLogMachine")
			.setStartingMethodName("start")

			.addMethod("with(String key, Number value)").any()
			.addMethod("with(String key, String value)").any()

			.addMethod("from(String location)").atMost(1, 1)
			.addMethod("from()").atMost(1, 1)
			.addMethod("to(unquietcode.tools.logmachine.core.topics.Topic...topics)").atMost(1)
			.addMethod("because(Throwable cause)").atMost(1)
			.addMethod("send(String message, Object...data)").last()
		.build();

		return builder;	}
}
