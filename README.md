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
3. We should see some success logging but instead we see an error.

N.b. Remember to recycle the docker container if we have malformed events stuck on the FIFO.