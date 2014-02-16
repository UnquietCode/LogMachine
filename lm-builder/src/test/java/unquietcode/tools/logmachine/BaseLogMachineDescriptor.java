package unquietcode.tools.logmachine;

import unquietcode.tools.flapi.DescriptorMaker;
import unquietcode.tools.flapi.Flapi;
import unquietcode.tools.flapi.builder.Descriptor.DescriptorBuilder_m1_m2_m3_m4_m5_m6;


/**
 * @author Ben Fagin
 * @version 2013-08-22
 */
public abstract class BaseLogMachineDescriptor implements DescriptorMaker {

	protected static DescriptorBuilder_m1_m2_m3_m4_m5_m6<Void> getStandardDescriptor() {
		return Flapi.builder()
			.addMethod("with(String key, Number value)")
				.withDocumentation("add new numeral data")
			.any()

			.addMethod("with(String key, String value)")
				.withDocumentation("add new string data")
			.any()

			.addMethod("from(String location)")
				.withDocumentation("set the location of the event to a string")
			.atMost(1, 1)

			.addMethod("fromHere()")
				.withDocumentation()
					.addContent("Set the location of the event reflectively\n")
					.addContent("The current line, method, and class are included.")
				.finish()
			.atMost(1, 1)

			.addMethod("to(unquietcode.tools.logmachine.core.topics.Topic...topics)")
				.withDocumentation("sets the topics for the log event")
			.atMost(1)

			.addMethod("because(Throwable cause)")
				.withDocumentation()
					.addContent("Sets the exception, or 'cause' of the log event.\n")
					.addContent("The cause can also be passed as the last argument\n")
					.addContent("of the message replacements varargs array.")
				.finish()
			.atMost(1)
		;
	}
}
