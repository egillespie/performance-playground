performance-playground
======================

## Notes About This Project

* You will need the Google Protocol Buffers compiler installed.  Additionally, the path for `protoc` is
  hard-coded to `/usr/local/bin/protoc`. If you use Microsoft Windows or have installed the compiler to
  a non-standard location then you will need to update this path in pom.xml.
* Benchmarks are collected via unit tests. Run the "test" lifecycle in order to collect your own
  benchmarks.
* Unit tests are executed in the order that the methods are supplied to JUnit by the JVM. This order
  was chosen because for Java 1.6.0u65 the methods will be returned in the order they appear in the
  class.

## Object Serialization and Deserialization Benchmarks

The following table shows timings for serialization and deserialization of 1,000
TestObject instances populated with random data.  Each batch of 1,000 instances
was performed 100 times in order to calculate average, minimum, and maximum
times as well as variance around the average to gauge how consistent the
serialization and deserialization processes are for the various frameworks.

| Benchmark | Average Time (ms) | Min. Time (ms) | Max. Time (ms) | Variance |
| :-------- | ----------------: | -------------: | -------------: | :------- |
| Jackson JSON Serialization | 521 | 506 | 725 | -0.03% / +0.39% |
| Google Protocol Buffers Serialization | 535 | 518 | 724 | -0.03% / +0.35% |
| Jackson JSON Deserialization | 912 | 891 | 1144 | -0.02% / +0.25% |
| Google Protocol Buffers Deserialization | 880 | 861 | 993 | -0.02% / +0.13% |

Besides time spent converting data, some analysis was done on the binary data
produced after serialization to see if one framework produced significantly
smaller data sets either before or after compression.

| Benchmark | Uncompressed Size (bytes) | GZip Compressed Size (bytes) | Compression Ratio |
| :-------- | ------------------------: | ---------------------------: | ----------------: |
| Jackson JSON Serialization | 28,064 | 9,747 | 2.88 |
| Google Protocol Buffers Serialization | 18,827 | 9,252 | 2.03 |
