package unquietcode.tools.logmachine;

import unquietcode.tools.flapi.Descriptor;
import unquietcode.tools.flapi.DescriptorMaker;
import unquietcode.tools.flapi.Flapi;

/**
 * @author Ben Fagin
 * @version 2013-08-22
 */
public class LogMachineGenericDescriptor implements DescriptorMaker {

	@Override
	public Descriptor descriptor() {
		Descriptor builder = Flapi.builder()
			.setPackage("unquietcode.tools.logmachine.builder.generic")
			.setDescriptorName("GenericLogMachine")
			.setStartingMethodName("start")

			.addMethod("with(String key, Number value)").any()
			.addMethod("with(String key, String value)").any()
			.addMethod("from(String location)").atMost(1,1)
			.addMethod("from()").atMost(1, 1)
			.addMethod("to(unquietcode.tools.logmachine.core.topics.Topic...topics)").atMost(1)
			.addMethod("because(Throwable cause)").atMost(1)

			.addMethod("debug(String message, Object...data)").last()
			.addMethod("info(String message, Object...data)").last()
			.addMethod("trace(String message, Object...data)").last()
			.addMethod("warn(String message, Object...data)").last()
			.addMethod("error(String message, Object...data)").last()
		.build();

		return builder;
	}
}
