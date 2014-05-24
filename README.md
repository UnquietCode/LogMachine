# LogMachine (v0.0)
#### *I'm just a log machine!*

[![Build Status](https://travis-ci.org/UnquietCode/LogMachine.png?branch=master)](https://travis-ci.org/UnquietCode/LogMachine)

Semantic logging in Java. Not a framework!
Wraps around existing tools like SLF4J, Log4j, and more.

### [Documentation](http://unquietcode.github.io/LogMachine)
Check out the documentation for more information.

### [Implementations](https://github.com/UnquietCode/LogMachine/wiki/Implementations)
The following connectors are available for integrating with existing logging frameworks:

* [Log4j](https://github.com/UnquietCode/LogMachine/tree/master/lm-via-log4j)
* [Logback](https://github.com/UnquietCode/LogMachine/tree/master/lm-via-logback)
* [SLF4J](https://github.com/UnquietCode/LogMachine/tree/master/lm-via-slf4j)
* [JDK-1.4](https://github.com/UnquietCode/LogMachine/tree/master/lm-via-jdk14)


### Maven
```
<repository>
  <id>uqc</id>
  <name>UnquietCode Repository</name>
  <url>http://www.unquietcode.com/maven/releases</url>
</repository>

<dependency>
  <groupId>unquietcode.tools.logmachine</groupId>
  <artifactId>lm-core</artifactId>
  <version>0.1</version>
</dependency>  
```

### Gradle
```
repositories {
    uqc {
        url 'http://www.unquietcode.com/maven/releases'
    }
}

dependencies {
	compile 'unquietcode.tools.logmachine:lm-core:0.1'
}
```


## Motivation

When we log events in our applications, we are really trying to capture information about the current runtime state and store it for later. This could be a simple string, or a complex data structure. One great way to interact with your log data is to index it with a search index, like ElasticSearch. A common workflow utilizing [Logstash](http://logstash.net), [ElasticSearch](http://elasticsearch.org), and [Kibana](http://demo.kibana.org)
might be:

0. write log events in plaintext to a file
0. use a logstash process to parse the file and index it to ElasticSearch
0. use Kibana to visualize and query the search index

This seems a bit roundabout. We just spent all that time decorating
our log event with lots of good data, just to have it turned into
a string, and then (to add insult to injury) parsed back in a
haphazard, coupled way, before finally being indexed?

Instead, LogMachine proposes this alternative:

0. log directly to ElasticSearch in JSON
0. use Kibana to visualize and query

LogMachine offers a clean, fluent way of logging. Adding metadata should be easy, and logging it in JSON a no-brainer. Topics offer an escape from the tyranny of logging by package hierarchies, routing events by topic to the underlying native loggers.
 

### Example

Using something like SLF4J, this is what most logging statements
look like today:

```java
Logger log = LoggerFactory.getLogger(TestBasicSLF4J.class);

try {
    createUser(signupEmail);
} catch (Exception e) {
    log.error("error while creating user in Postgres", e);
}
```

Using Log Machine, it looks something like this:

```java
LogMachine log = new SLF4JLogMachine(TestBasicSLF4J.class);

try {
    createUser(signupEmail);
} catch (Exception e) {
    log.with("email", signupEmail)
       .because(e)
       .to(Postgres)
       .error("error while creating user");
}
```

#### Discussion
Using the above example, I might want to see what kinds of errors have been occurring for a partcular user. I can open up Kibana and search for all log events where the `email` field was 'samantha82@geocities.com' with topics `Database`, and `Create`. Sure enough, I can see there was an issue.

In a production application with millions of log records, keeping log data structured makes it easier to write more specific queries that can cut
through the noise. The data generated can also be used for analytics purposes: once indexed, you can perform all sorts of useful queries. Analytics and logging tend to occupy the same space, doing the same thing but for different masters (diagnostics versus metrics). By providing finer targeting of events, with richer metadata, logging becomes a more useful tool for capturing application metrics.


### License
LogMachine is free and open software provided without a license. Licenses byte.

  
&nbsp; 
&nbsp;   
  
## Thanks!

Peace, love, and code.
