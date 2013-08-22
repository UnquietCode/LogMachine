package unquietcode.tools.logmachine.core.topics;

/**
 * @author Ben Fagin
 * @version 2013-08-22
 */
public final class QuickTopics {
	private QuickTopics() { }


	public enum CrudOperations implements HierarchicalTopic {
		CRUD,
		CREATE, READ, UPDATE, DELETE

		; // -o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-oo

		@Override
		public Topic getParent() {
			return this != CRUD ? CRUD : null;
		}
	}
}
