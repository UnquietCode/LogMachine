// # Log Machine
// ### _I'm just a log machine!_

/**
 * Log Machine is **not** a logging framework, it is a wrapper or 'façade'
 * around existing frameworks, such as Log4j, Commons Logging, Logback, etc.
 * It will also wrap around the existing SLF4J logging façade.
 *
 * LM provides a clean, simple way to log events in your
 * Java applications. The status quo is robust under the hood, but somewhat
 * klunky and hard to maintain on the surface. Logging is code, and code is
 * data, so by enhancing the logging statements we can generate better
 * quality data.
 *
 * Another goal is to attempt to blur the lines between logging and analytics. They
 * tend to occupy the same space, doing the same thing but for different masters
 * (diagnostics versus metrics). By providing finer targeting of events, with
 * richer metadata, logging becomes a more useful tool for capturing application
 * metrics.
 */


/**
 * ## API
 *
 * The LogMachine fluent API provides the standard level-based `trace` &#8594; `error`
 * style of logging, while supporting several other features such as topic-based logging.
 * The API has been made compatible with SLF4J, so migration is a snap. All that must be
 * done is to replace the `Logger` declaration with the `LogMachine` implementation to start
 * enhancing your logging statements.
 */

log.to(REDIS, USER)
   .because(ex)
   .info("User {@ id} disconnected.", userID);


/**
 * ### Basic Methods
 *
 * The basic log methods are what you would expect to find in the existing
 * frameworks, namely the level-based logging.
 */

// Log an event.
.error(String message)
.warn(String message)
.info(String message)
.debug(String message)
.trace(String message)

// Log an event with an included exception.
.error(String message, Throwable exception)
.warn(String message, Throwable exception)
.info(String message, Throwable exception)
.debug(String message, Throwable exception)
.trace(String message, Throwable exception)

// Log an event, with extra data. The data can be accessed in order by
// using '{}' notation in your log message. This is similar to what the
// SLF4J/Logback API provides. If the last object in the varargs list is
// a `Throwable`, then it is used as the exception for the log event.
.error(String message, Object...data)
.warn(String message, Object...data)
.info(String message, Object...data)
.debug(String message, Object...data)
.trace(String message, Object...data)

// Check the log level. These are compatible with SLF4J.
.isErrorEnabled()
.isWarnEnabled()
.isInfoEnabled()
.isDebugEnabled()
.isTraceEnabled()

// Check the log level. These are the more concise LogMachine versions
// of the above statements.
.isError()
.isWarn()
.isInfo()
.isDebug()
.isTrace()


/**
 * ### Additional Methods
 *
 * LogMachine adds a few tricks to the basic repertoire of logging
 * abilities. The methods form a fluent chain, ending with the
 * actual logging statement.
 *
 *
 */

// Sets the exception for the event.
.because(Throwable cause)


// Sets the location for the log event, which could be a method name,
// class name, or some other logical identifier.
.from(String location)

// Sets the location to the current method/class/line as one would
// find in a stack trace.
.fromHere()


/**
 * ## Conditional Logging
 *
 * Sometimes you don't want messages to be processed if the
 * log level is too low or some other condition is not met.
 * This is pretty easy to do by using an `if` block, like so:
 *
 * ```java
 * if (log.isDebugEnabled()) {
 *     log.debug("this is a debug message");
 * }
 * ```
 *
 * Mainly this technique suffers from verbosity. Most log
 * frameworks now allow you to pass in a template message and
 * will generate the actual string value of arguments later
 * only if the log level is appropriate.
 *
 * Log Machine goes one step further. When a condition is not met,
 * the returned object will be a dummy proxy instance. This means
 * that every command you execute will be ignored. This has the
 * convenience of being clean, and fast.
 *
 * These calls have to be made up front, and so a `$Level()`
 * method is available for each level in order to support
 * conditional logging. At the end of the chain you call
 * `send()` to complete the logging.
 */

log.info().with("action", "send_email")
   .send("User '{@ user}' was not found.", currentUser());



// Checks the boolean first, and if it is `null` or `false` then
// the logging will not proceed.
.when(Boolean test)

// Conditionally log if the level is right. Must be completed with
// a call to `send()`.
.error() ... send(...)
.warn() ... send(...)
.info() ... send(...)
.debug() ... send(...)
.trace() ... send(...)

// Note that these two can also be combined.
log.when(true).trace()
   .because(exception)
   .to(DATABASE, UPDATE)
   .send("Oh no, not again.");


/**
 * ## Event Data
 *
 * A log event is often rich with data, but we usually settle for
 * turning all of this data into a flattened string. Log Machine
 * stores metadata along with the log messge, so that a detailed
 * data structure can be assembled later which describes the event.
 * 
 * Data can be assigned using the `with(...)` method, or though
 * an inline assignment in the message string (more on that soon).
 */

// Adds a data point to the log event.
.with(String name, Number value)
.with(String name, String value)

/**
 * ## Message Formatting
 *
 * A special formatting syntax allows data to be both read and
 * written via the message string associated with each log event.
 * When a pair of unescaped brackets `{}` is detected, the
 * contents are evaluated using the formatting rules.
 * 
 * For users of SLF4J, this syntax should be at least partially
 * familiar. That style of replacement where `{}` is a shortcut
 * to the arguments array is still supported, with the addition
 * of a few new features.
 */

// Access the event arguments array, where the
// first instance of `{}` is the 0th element, the
// second instance of `{}` is the 1st element, etc.
// (When the last argument is an instance of Throwable,
// it is assumed to be the cause of the event
// and is not included in the argument array.)
log.error("failed to process chunk {} of {}", currentChunk, totalChunks);

// Access event data by name.
log.with("id", userID).to(User, Postgres, Create)
   .info("created a new user with id {: id}");

// Set new event data under the given name,
// where the first `@`ssignment maps to the first
// event argument, the second `@` to the second
// argument, etc., and printing them  in the process.
log.info("created a new user with id {@ id}", userID);

// Access the event arguments array by the
// position number, where the first argument is `1`.
// (When the last argument is an instance of Throwable,
// it is assumed to be the cause of the event
// and is not included in the argument array.)
log.info("created a new user with id {2}", userName, userID);



/**
 * ## Topic-Based Logging
 *
 * Topics provide an alternative way to organize your logging statements,
 * instead of the usual package hierachy. In a sense, it decouples your
 * package naming from your logging configuration, allowing you to
 * configure appenders and levels for a set of topics instead packages.
 *
 * If that was a mouthful, here's an example: Let's say I'm creating a
 * new user in my database, which is PostgreSQL. When I'm going through
 * the log data sometime later, maybe to troubleshoot a bug, I want to
 * query for when some user with id `50` was created. I could search for
 * words like 'user' 'created' '50', and on. That tends to make for
 * noisy, imprecise results.
 *
 * Instead, using topics I can query for only those events whose
 * topics include `Postgres`, `Users`, and `Create`, three predefined
 * constants in my appliction.
 *
 * Topics can be conveniently created from Strings and Enums in your
 * application. The `Topic` interface is designed to be drop-in for
 * any enum class.
 *
 * A special `QuickTopics` class is included with LogMachine, containing
 * some common concepts, like CRUD operations, `User`, `File`, `Database`,
 * etc.
 */

log.fromHere()
   .to(Postgres, Users, Create)
   .info("Created a new user with id '{@ id}'.", user.getId());


// Sets the topics for the log event, which can be any thing you like. Strings,
// and Enums are the most likely sources of topics, stored within your
// application.
.to(Topic...topics);

// Access the event topics by the position number,
// where the first argument is `{~ 1}`.
log.to(User, Postgres, Create)
   .info("new user with id {@ id} stored to {~ 2}", userID);

// Subscribe a `LoggingComponent` to one or more `Topic`'s by using the
// `TopicBroker` singleton class.
TopicBroker.subscribe(component, TopicOne, TopicTwo);


/**
 * ## Implementations
 * Available.
 *
 * ### Log4j
 * Yep.
 * 
 * ### Logstash
 * Yep.
 * 
 * ### SLF4J
 * Yep.
 *
 * ### JDK-1.4
 * Yep.
 * 
 * 
 *
 * ### LogStash / ElasticSearch / Kibana
 *
 * One great way to interact with your log data is to index it with
 * a search index, like ElasticSearch. A common workflow might be:
 *
 * 0. write log events in plaintext to a file
 * 0. use a logstash process to parse the file and index it to ElasticSearch
 * 0. use Kibana to visualize and query the search index
 *
 * This seems a bit roundabout. We just spent all that time decorating
 * our log event with lots of good data, just to have it turned into
 * a string, and then (to add insult to injury) parsed back in a
 * haphazard, coupled way, before finally being indexed?
 *
 * Instead, LogMachine proposes this alternative:
 *
 * 0. log directly to ElasticSearch in LogStash format
 * 0. use Kibana to visualize and query the search index
 *
 * Most applications can do this, as the volume to be indexed won't
 * overwhelm the ES cluster. However in scaled out architectures you
 * could still log to a file, just use the formatter which prints
 * JSON in the proper format, and then reads it back in later.
 *
 * #### Components
 * + [lm-to-elasticsearch](#url) &ndash; appender for writing to ElasticSearch in Logstash format
 * + [lm-elasticsearch-sqs-loader](#url) &ndash; reads from SQS and indexes to ElasticSearch
 */

/*
 * ### Amazon SQS
 *
 * The SQS component provides an appender which writes log events to
 * an Amazon SQS queue. SQS is cheap, high throughput, and can be
 * useful when you need a buffer between your application and your
 * log visualizer.
 * 
 * In combination with the ElasticSearch components, it is possible to
 * write log events to an SQS queue in Logstash JSON format and then
 * later read and index them into your search cluster.
 *
 * #### Components
 * + [lm-to-sqs](#url) &ndash; appender for writing to SQS
 * + [lm-elasticsearch-sqs-loader](#url) &ndash; reads from SQS and indexes to ElasticSearch
 */

/**
 * ## Thanks!
 * Visit the project page [on GitHub](https://github.com/UnquietCode/LogMachine)
 * for more information.
 */