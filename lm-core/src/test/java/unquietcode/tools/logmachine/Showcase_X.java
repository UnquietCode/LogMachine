package unquietcode.tools.logmachine;

import org.junit.Test;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.topics.Topic;

import static unquietcode.tools.logmachine.Showcase_X.Topics.*;

/**
 * @author Ben Fagin
 * @version 2013-08-22
 */
public class Showcase_X {
	LogMachine lm;

	@Test
	public void test() {

			lm.info()
				.to(REDIS, USER, NOTIFY)
				.send("User {@id} disconnected.");


			lm.because(ex)
				.with("statusCode", response)
				.error("The server returned code {statusCode}.");


		}
			// preconfigure your logging message even though the level
			// is unknown, preloading it with data relevant in this context

//		log().from("unit test").debug("Oh no, not again.");
//		log().info("User {@userID} has reached their maximum quota.", userID);




//	GenericLogMachineBuilder.$<Void> log() {
//		return lm.with("one", 1)
//		         .with("two", 2)
//		;
//	}

		// TODO precconfigure method, so that
		// the above is not required
		// change uses from 'lm' to 'log'


		// set flapi to always make the X.$ for each block,
		// if it's the only one, ok, if not, nbd it will just
		// be another part of it.

		// fix this and the build and should be good to go

		// oh, also need to figure out why .error, .debug etc are
		// in the interface name (guess is that it's ascending
		// but not the top level, so it gets by. Probably any
		// ascending method should qualify.
		// oh right, but the interfaces have to be uniquely named.

		// it would be nice if these went away entirely in favor
		// of some generated names (or a small part generated, the
		// rest english).



		int userID = 0;
		int response = 404;
		RuntimeException ex = new RuntimeException();




	public enum Topics implements Topic {
		REDIS, USER, NOTIFY,


	}


}

