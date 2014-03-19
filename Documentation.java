// # Log Machine
// ### _I'm just a log machine!_

/**
 * Log Machine is **not** a logging framework, it is a wrapper or 'façade'
 * around existing frameworks, such as Log4j, Commons Logging, Logback, etc.
 * It will also wrap around the existing SLF4J logging façade.
 *
 * LM provides a clean and simple way to log events in your
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
 * done is to replace the `Logger` declaration with a `LogMachine` implementation to start
 * enhancing your logging statements.
 */

log.to(Redis, User)
   .because(ex)
   .info("User {@ id} disconnected.", userID);


/**
 * ### Methods
 *
 * The basic logging methods are what you might expect to find in any
 * of the existing frameworks, namely the level-based logging.
 * LogMachine adds a few tricks to the basic repertoire of logging
 * capabilities. These and the other methods form a fluent chain,
 * decorating the final logging statemtent with additional information.
 */

// Log an event. If the current log level is too low, then no action
// will be taken.
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

// Log an event, with a templated message. The arguments can be accessed
// in order by using '{}' notation in your log message. This is similar
// to what the SLF4J/Logback API provides. If the last object in the varargs list is
// a `Throwable`, then it is used as the exception for the log event
// instead of a message replacement.
//
// See the [Message Formatting](#message%20formatting) section below for more
// information on that feature.
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

// Sets the exception for the event.
.because(Throwable cause)

// Sets the location for the log event, which could be a method name,
// class name, or some other logical identifier. The event location
// is initialized to the current 'class#method:line'. Calling this
// method will replace that data. If `null` is used then the
// location information is removed.
.from(String location)


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

// ### Methods

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

// ### Syntax

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
 * Topics provide an alternative way to organize and filter your logging
 * statements, instead of the usual package hierachy. In a sense, it
 * decouples your package naming from your logging configuration, allowing
 * you to configure appenders and levels for a set of topics instead packages.
 *
 * Here's an example: Let's say I'm creating a
 * new user in my database, which is PostgreSQL. When I'm going through
 * the log data sometime later, maybe to troubleshoot a bug, I want to
 * query for when the user with id `50` was created. I could search for
 * words like 'user', 'created', '50', and so on. 
 *
 * That tends to make for noisy, imprecise results. Instead, using topics
 * I can query for only those events whose topics include `Postgres`,
 * `Users`, and `Create`, three predefined constants in my appliction.
 * I could even set up a log file dedicated to tracking only events
 * in these categories.
 *
 * Topics can be conveniently created from Strings and Enums in your
 * application. The `Topic` interface is designed to be drop-in for
 * any enum class. As well, a special `QuickTopics` class is
 * included in the core Log Machine module, containing some common
 * appliation concepts, like CRUD operations, `User`, `File`,
 * `Database`, etc.
 */

log.to(Postgres, Users, Create)
   .info("Created a new user with id '{@ id}'.", user.getId());


// ### Methods

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

// The broker can also be used to set the threshold level
// for one or more `Topic`'s.

/**
 * ## Implementations
 *
 * Several modules are available to connect the Log Machine API to
 * an underlying logging implementation. (And adding new connectors
 * for your favorite library is easy!)
 *
 * + [Log4j](https://github.com/UnquietCode/LogMachine/tree/master/lm-via-log4j) &ndash; use the `Log4jLogMachine`
 * + [Logback](https://github.com/UnquietCode/LogMachine/tree/master/lm-via-logback) &ndash; use the `SLF4JLogMachine`
 * + [SLF4J](https://github.com/UnquietCode/LogMachine/tree/master/lm-via-slf4j) &ndash; use the `SLF4JLogMachine`
 * + [JDK-1.4](https://github.com/UnquietCode/LogMachine/tree/master/lm-via-jdk14) &ndash; use the `JDKLogMachine`
 *
 *
 * ### Logstash / ElasticSearch / Kibana
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
 * JSON in the proper format, and then reads it back in later. Or,
 * the SQS component (below) can be used to store messages in a queue.
 *
 * #### Components
 * + [lm-to-elasticsearch](https://github.com/UnquietCode/LogMachine/tree/master/lm-to-elasticsearch) &ndash; appender for writing to ElasticSearch in Logstash format
 * + [lm-elasticsearch-sqs-loader](https://github.com/UnquietCode/LogMachine/tree/master/lm-elasticsearch-sqs-loader) &ndash; reads from SQS and indexes to ElasticSearch
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
 * + [lm-to-sqs](https://github.com/UnquietCode/LogMachine/tree/master/lm-to-sqs) &ndash; appender for writing to SQS
 * + [lm-elasticsearch-sqs-loader](https://github.com/UnquietCode/LogMachine/tree/master/lm-elasticsearch-sqs-loader) &ndash; reads from SQS and indexes to ElasticSearch
 */

/**
 * ## Thanks!
 *
 * Visit the project page [on GitHub](https://github.com/UnquietCode/LogMachine)
 * for more information.
 * 
 * <a href="https://github.com/UnquietCode/LogMachine"><img style="position: absolute; top: 0; right: 0; border: 0;" src="https://github-camo.global.ssl.fastly.net/38ef81f8aca64bb9a64448d0d70f1308ef5341ab/68747470733a2f2f73332e616d617a6f6e6177732e636f6d2f6769746875622f726962626f6e732f666f726b6d655f72696768745f6461726b626c75655f3132313632312e706e67" alt="Fork me on GitHub" data-canonical-src="https://s3.amazonaws.com/github/ribbons/forkme_right_darkblue_121621.png"></a>
 */