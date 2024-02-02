# spring-cloud-aws-sample
  <!-- Java version -->
  <a href="https://img.shields.io/badge/Java-21-blue.svg?logo=Java">
    <img src="https://img.shields.io/badge/Java-21-blue.svg?logo=Java"
      alt="Java version" />
  </a>
  <!-- Spring Boot -->
  <a href="https://github.com/spring-projects/spring-boot/releases">
    <img src="https://img.shields.io/badge/SpringBoot-3.2.x-blue.svg?logo=Spring"
      alt="Spring Boot" />
  </a>


Sample project to diagnose issue discussed
on https://github.com/awspring/spring-cloud-aws/discussions/1036

## Getting started

1. Run docker-compose up
2. Start service
3. We should see some success logging but instead we see the following error.

```
Caused by: com.fasterxml.jackson.core.JsonParseException: Unrecognized token 'TxEvent': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false')
 at [Source: (String)"TxEvent[text=Green]"; line: 1, column: 8]
	at com.fasterxml.jackson.core.JsonParser._constructError(JsonParser.java:2477) ~[jackson-core-2.15.3.jar:2.15.3]
	at com.fasterxml.jackson.core.base.ParserMinimalBase._reportError(ParserMinimalBase.java:760) ~[jackson-core-2.15.3.jar:2.15.3]
	at com.fasterxml.jackson.core.json.ReaderBasedJsonParser._reportInvalidToken(ReaderBasedJsonParser.java:3041) ~[jackson-core-2.15.3.jar:2.15.3]
	at com.fasterxml.jackson.core.json.ReaderBasedJsonParser._handleOddValue(ReaderBasedJsonParser.java:2082) ~[jackson-core-2.15.3.jar:2.15.3]
	at com.fasterxml.jackson.core.json.ReaderBasedJsonParser.nextToken(ReaderBasedJsonParser.java:808) ~[jackson-core-2.15.3.jar:2.15.3]
	at com.fasterxml.jackson.databind.ObjectMapper._initForReading(ObjectMapper.java:4912) ~[jackson-databind-2.15.3.jar:2.15.3]
	at com.fasterxml.jackson.databind.ObjectMapper._readMapAndClose(ObjectMapper.java:4818) ~[jackson-databind-2.15.3.jar:2.15.3]
	at com.fasterxml.jackson.databind.ObjectMapper.readValue(ObjectMapper.java:3772) ~[jackson-databind-2.15.3.jar:2.15.3]
	at org.springframework.messaging.converter.MappingJackson2MessageConverter.convertFromInternal(MappingJackson2MessageConverter.java:250) ~[spring-messaging-6.1.3.jar:6.1.3]
	... 26 common frames omitted
```

N.b. Remember to recycle the docker container if we have malformed events stuck on the FIFO.
