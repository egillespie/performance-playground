performance-playground
======================

## Goal

This project is a simple way to compare performance of various serialization and deserialization
tools.

Here's the list of benchmarked tools:

1. FasterXML's [Jackson JSON](http://wiki.fasterxml.com/JacksonHome) v2.3.3
2. Google's [Protocol Buffers](https://developers.google.com/protocol-buffers/) v2.5.0

## Notes

* You will need the Google Protocol Buffers compiler installed.  Additionally, the path for `protoc` is
  hard-coded to `/usr/local/bin/protoc`. If you use Microsoft Windows or have installed the compiler to
  a non-standard location then you will need to update this path in pom.xml.
* Benchmarks are collected via unit tests. Run the "test" lifecycle in order to collect your own
  benchmarks.
* Unit tests are executed in the order that the methods are supplied to JUnit by the JVM. This order
  was chosen because for Java 1.6.0u65 the methods will be returned in the order they appear in the
  class.

## Benchmarks

The following table shows timings for serialization and deserialization of 1,000
TestObject instances populated with random data.  Each batch of 1,000 instances
was performed 100 times in order to calculate average, minimum, and maximum
times as well as variance around the average to gauge how consistent the
serialization and deserialization processes are for the various frameworks.

### Serialization

| Benchmark | Average Time (ms) | Min. Time (ms) | Max. Time (ms) | Variance |
| :-------- | ----------------: | -------------: | -------------: | :------- |
| Jackson JSON 2.3.3 | 521 | 506 | 725 | -0.03% / +0.39% |
| Protocol Buffers 2.5.0 | 535 | 518 | 724 | -0.03% / +0.35% |

### Deserialization

| Benchmark | Average Time (ms) | Min. Time (ms) | Max. Time (ms) | Variance |
| :-------- | ----------------: | -------------: | -------------: | :------- |
| Jackson JSON 2.3.3 | 912 | 891 | 1144 | -0.02% / +0.25% |
| Protocol Buffers 2.5.0 | 880 | 861 | 993 | -0.02% / +0.13% |

### Compression

Besides time spent converting data, some analysis was done on the binary data
produced after serialization to see if one framework produced significantly
smaller data sets either before or after compression.

| Benchmark | Uncompressed Size (bytes) | GZip Compressed Size (bytes) | Compression Ratio |
| :-------- | ------------------------: | ---------------------------: | ----------------: |
| Jackson JSON 2.3.3 | 28,064 | 9,747 | 2.88 |
| Protocol Buffers 2.5.0 | 18,827 | 9,252 | 2.03 |
