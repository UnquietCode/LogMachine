package unquietcode.tools.logmachine;

import org.junit.Test;
import unquietcode.tools.logmachine.core.LogMachine;
import unquietcode.tools.logmachine.core.topics.Topic;
import unquietcode.tools.logmachine.core.topics.TopicLogMachine;

import static unquietcode.tools.logmachine.Showcase_X.Topics.*;
import static unquietcode.tools.logmachine.core.topics.QuickTopics.CrudOperations.Create;
import static unquietcode.tools.logmachine.core.topics.QuickTopics.Databases.Postgres;

/**
 * @author Ben Fagin
 * @version 2013-08-22
 */
public class Showcase_X {
	LogMachine log;

	private void doSomethingCrazy() throws Exception {
		// nfn
	}


	@Test
	public void test() {

		try {
			doSomethingCrazy();
		} catch (Exception ex) {

			// normal form

			log.to(REDIS, USER)
			   .because(ex)
			   .info("User {@id} disconnected.", userID)
			;







			// guarded form

			log.info()
			   .to(REDIS, USER)
			   .because(ex)
			   .send("User {@id} disconnected.", userID)
			;

			// add extra data, and reference it by name

			log.because(ex)
			   .with("statusCode", response)
			   .error("The server returned code {:statusCode}.")
			;

			// or use the shorter, inline form

			log.fromHere().to(Postgres, Create)
			   .warn("A user with the id {@ id} already exists.", userID);

			// reference it as many times as you want!

			log.fromHere().to(Postgres, User, Create)
			   .warn("Could not create user with id {: id} because a user with id {@ id} already exists.", userID);




			// guarded form...

			if (log.isInfo()) {
				log.because(ex).fromHere().info("Yo!", toString());
			}


			// fromHere is actually pretty expensive, relatively speaking,
			// so it would be nice to keep the fluent api while not
			// incurring that cost

			log.fromHere().to(Postgres, Create, User)
			   .error("Could not create user with id {@ id}.", userID)
			;

			// so we can use the guarded form, which in this case is
			// a minimal cost since no getters, just existing variables

			log.error()
			   .fromHere().to(Postgres, Create, User)
			   .send("Could not create user with id {@ id}.", userID)
			;

			// you can skip traditional loggers in favor of simple topics
			final TopicLogMachine dbLog = new TopicLogMachine(Postgres);

			dbLog.because(ex).warn("Something is broken.");

			// same as doing this
			dbLog.to(Postgres).because(ex).warn("Something is broken.");

			// this refines it further
			dbLog.to(User, Create) // implicit Database.Postgres, User, CRUD.Create,
				 .because(ex)
				 .warn("Could not create user with id '{@ id}'.", userID)
			;
		}



		// maximum savings come from deferred parameter usage
		// but that's it, the cost of getting the param remains

		log.info()

		// saves on this
		.because(ex)

		// saves on these
		.with("one", 1)

		// saves on this for sure
		.fromHere()

		// saves a little on this, but calls toString so not as much
		.send("Yo!", toString());
	}

	//


	int userID = 290;
	int response = 404;
	RuntimeException ex = new RuntimeException();

	public enum Topics implements Topic {
		REDIS, USER, NOTIFY, CREATE, User


	}
}

