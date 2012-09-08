package unquietcode.tools.logmachine;

import unquietcode.tools.flapi.Descriptor;
import unquietcode.tools.flapi.Flapi;

/**
 * @author Ben Fagin
 * @version 05-16-2012
 */
public class LogMachineDescriptorGenerator {

	public static void main(String[] args) {
		generateBuilder(args[0]);
	}

	public static void generateBuilder(String folder) {
		Descriptor builder = Flapi.builder()
			.setPackage("unquietcode.tools.logmachine.builder")
			.setDescriptorName("LogMachine")
			.setStartingMethodName("start")

			// TODO add support for marking
			//.addMethod("mark(String event)").last()
			//.addMethod("mark(String event, Enum...categories)").last()

			.addMethod("from(String location)").atMost(1)
			.addMethod("to(Enum...categories)").atMost(1)
			.addMethod("because(Throwable cause)").atMost(1)

			.addMethod("debug(String message, Object...data)").last()
			.addMethod("info(String message, Object...data)").last()
			.addMethod("trace(String message, Object...data)").last()
			.addMethod("warn(String message, Object...data)").last()
			.addMethod("error(String message, Object...data)").last()
		.build();

		builder.writeToFolder(folder);
	}
}
