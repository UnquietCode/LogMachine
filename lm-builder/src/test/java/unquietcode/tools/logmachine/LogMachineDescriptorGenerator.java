package unquietcode.tools.logmachine;

import unquietcode.tools.flapi.Descriptor;
import unquietcode.tools.flapi.DescriptorMaker;
import unquietcode.tools.flapi.Flapi;

/**
 * @author Ben Fagin
 * @version 05-16-2012
 */
public class LogMachineDescriptorGenerator implements DescriptorMaker {

	@Override
	public Descriptor descriptor() {
		Descriptor builder = Flapi.builder()
			.setPackage("unquietcode.tools.logmachine.builder")
			.setDescriptorName("LogMachine")
			.setStartingMethodName("start")

			.startBlock("Generic", "generic()").last()
				.addMethod("with(String key, Number value)").any()
				.addMethod("with(String key, String value)").any()
				.addMethod("from(String location)").atMost(1,1)
				.addMethod("from()").atMost(1, 1)
				.addMethod("to(Enum...categories)").atMost(1)
				.addMethod("because(Throwable cause)").atMost(1)

				.addMethod("debug(String message, Object...data)").last()
				.addMethod("info(String message, Object...data)").last()
				.addMethod("trace(String message, Object...data)").last()
				.addMethod("warn(String message, Object...data)").last()
				.addMethod("error(String message, Object...data)").last()
			.endBlock()

			.startBlock("Specific", "specific()").last()
				.addMethod("with(String key, Number value)").any()
				.addMethod("with(String key, String value)").any()

				.addMethod("from(String location)").atMost(1, 1)
				.addMethod("from()").atMost(1, 1)
				.addMethod("to(Enum...categories)").atMost(1)
				.addMethod("because(Throwable cause)").atMost(1)
				.addMethod("send(String message, Object...data)").last()
			.endBlock()
		.build();

		return builder;
	}


	// TODO add support for marking
	//.addMethod("mark(String event)").last()
	//.addMethod("mark(String event, Enum...categories)").last()
}
