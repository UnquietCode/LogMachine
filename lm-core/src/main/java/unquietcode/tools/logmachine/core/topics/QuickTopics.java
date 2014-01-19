package unquietcode.tools.logmachine.core.topics;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Ben Fagin
 * @version 2013-08-22
 */
public final class QuickTopics {
	private QuickTopics() { }

	public enum AWS implements HierarchicalTopic {
		AWS,
		DynamoDB(AWS, Databases.Database),
		SimpleDB(AWS, Databases.Database),
		SQS(AWS), SNS(AWS), S3(AWS), EC2(AWS),

		; // o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o

		private final List<Topic> parents;

		AWS(Topic...parents) {
			this.parents = Collections.unmodifiableList(Arrays.asList(parents));
		}

		@Override
		public List<Topic> getParents() {
			return parents;
		}
	}

	public enum Databases implements HierarchicalTopic {
		Database,
		Mysql(Database),
		Postgres(Database),
		Oracle(Database),
		HSQL(Database),
		Mongo(Database),
		Redis(Database),
		Riak(Database),

		SimpleDB(Database, AWS.AWS),
		DynamoDB(Database, AWS.AWS),

		; // o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o

		private final List<Topic> parents;

		Databases(Topic...parents) {
			this.parents = Collections.unmodifiableList(Arrays.asList(parents));
		}

		@Override
		public List<Topic> getParents() {
			return parents;
		}
	}

	public enum CrudOperations implements HierarchicalTopic {
		CRUD,
		Create, Read, Update, Delete

		; // o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o

		private static final List<Topic> parents = Collections.unmodifiableList(Arrays.<Topic>asList(CRUD));

		@Override
		public List<Topic> getParents() {
			return this != CRUD ? parents : Collections.<Topic>emptyList();
		}
	}
}
