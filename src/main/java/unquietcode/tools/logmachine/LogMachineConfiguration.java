package unquietcode.tools.logmachine;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Benjamin Fagin
 * @version 03-03-2012
 */
public class LogMachineConfiguration {
	Map<String, PackageInfo> packageMappings = new HashMap<String, PackageInfo>();
	Map<String, List<Enum>> methodMappings = new HashMap<String, List<Enum>>();
	
	
	public BasicBuilder mapPackage(String packageName, Enum...groups) {
		if (packageName == null || (packageName = packageName.trim()).isEmpty()) {
			throw new IllegalArgumentException("You must supply a valid package name.");
		}

		final PackageInfo info = new PackageInfo();
		info.name = packageName.trim();
		packageMappings.put(packageName, info);
		
		return new BasicBuilder() {
			@Override
			public BasicBuilder addGroups(Enum...groups) {
				if (groups == null) {
					throw new IllegalArgumentException("Null is not a valid group.");
				}
				
				info.groups = Arrays.asList(groups);
				return this;
			}

			@Override
			public BasicBuilder setLevel(Level level) {
				// null level is ok, means use default over existing
				
				info.level = level;
				return this;
			}
		};
	}
	
	public LogMachineConfiguration mapMethod(String methodName, Enum...groups) {
		if (methodName == null || methodName.trim().isEmpty()) {
			throw new IllegalArgumentException("You must supply a valid method name.");
		}
		
		methodMappings.put(methodName.trim(), Arrays.asList(groups));
		return this;
	}
	
	static class PackageInfo {
		String name;
		Level level;
		List<Enum> groups;
	}
	
	public interface BasicBuilder {
		BasicBuilder addGroups(Enum...groups);
		BasicBuilder setLevel(Level level);

	}
}
