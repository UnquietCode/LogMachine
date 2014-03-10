# LogMachine (v0.0)
#### *I'm just a log machine!*

[![Build Status](https://travis-ci.org/UnquietCode/LogMachine.png)](https://travis-ci.org/UnquietCode/LogMachine)

Semantic logging in Java. Not a framework!
Wraps around existing tools like SLF4J, Logback, Log4J, JDK1.4, and more.

Check out some of [the tests](https://github.com/UnquietCode/LogMachine/blob/master/lm-core/src/test/java/unquietcode/tools/logmachine/TestBasicLogMachine.java#L143) for usage patterns.

### Implementations
The following connectors are available for integrating with existing logging frameworks.

	* [Log4j](https://github.com/UnquietCode/LogMachine/tree/master/lm-via-log4j)
	* [Logback](https://github.com/UnquietCode/LogMachine/tree/master/lm-via-logback)
	* [SLF4J](https://github.com/UnquietCode/LogMachine/tree/master/lm-via-slf4j)
	* [JDK-1.4](https://github.com/UnquietCode/LogMachine/tree/master/lm-via-jdk14)

More to come...