performance-playground
======================

## Goal

Compare relative performance of various serialization and deserialization tools.

Here is the list of benchmarked tools:

1. FasterXML's [Jackson JSON](http://wiki.fasterxml.com/JacksonHome) v2.5.1
1. FasterXML's Jackson JSON with [Afterburner](https://github.com/FasterXML/jackson-module-afterburner) v2.5.1
2. Google's [Protocol Buffers](https://developers.google.com/protocol-buffers/) v2.6.0

## Notes

* You will need the Google Protocol Buffers compiler installed.  Additionally, the path for `protoc` is
  hard-coded to `/usr/local/bin/protoc`. If you use Microsoft Windows or have installed the compiler to
  a non-standard location then you will need to update this path in pom.xml.
* Benchmarks are collected via unit tests. Run the "test" lifecycle in order to collect your own
  benchmarks.

## Benchmarks

The following table shows timings for serialization and deserialization of 1,000
TestObject instances populated with random data.  Each batch of 1,000 instances
was performed 100 times in order to calculate average, minimum, and maximum
times as well as variance around the average to gauge how consistent the
serialization and deserialization processes are for the various frameworks.

The latest benchmarks were collected on June 25, 2015 using Java 1.8.0_40 and the following platform:

* CPU: 2.8 GHz Intel Core i7, 4 cores
* RAM: 16 GB 1600 MHz DDR3
* Disk: Apple SSD
* OS: Mac OS X Yosemite 10.10.3 on MacBook Pro (Retina, 15-inch, Mid 2014)

### Serialization

| Framework | Average Time (ms) | Min. Time (ms) | Max. Time (ms) | Variance |
| :-------- | ----------------: | -------------: | -------------: | :------- |
| Jackson JSON 2.6.0-rc2 | 176 | 155 | 244 | -0.12% / +0.39% |
| Jackson Afterburner JSON 2.6.0-rc2 | 158 | 141 | 237 | -0.11% / +0.50% |
| Protocol Buffers 3.0.0-alpha-3 | 428 | 402 | 553 | -0.06% / +0.29% |

### Deserialization

| Framework | Average Time (ms) | Min. Time (ms) | Max. Time (ms) | Variance |
| :-------- | ----------------: | -------------: | -------------: | :------- |
| Jackson JSON 2.6.0-rc2 | 318 | 303 | 392 | -0.05% / +0.23% |
| Jackson Afterburner JSON 2.6.0-rc2 | 313 | 297 | 494 | -0.05% / +0.58% |
| Protocol Buffers 3.0.0-alpha-3 | 673 | 650 | 734 | -0.03% / +0.09% |

### Compression

Besides time spent converting data, some analysis was done on the binary data
produced after serialization to see if one framework produced significantly
smaller data sets either before or after compression.

| Framework | Uncompressed Size (bytes) | GZip Compressed Size (bytes) | Compression Ratio |
| :-------- | ------------------------: | ---------------------------: | ----------------: |
| Jackson JSON 2.6.0-rc2 | 27,028 | 9,626 | 2.81 |
| Jackson Afterburner JSON 2.6.0-rc2 | 27,031 | 9,611 | 2.81 |
| Protocol Buffers 3.0.0-alpha-3 | 18,812 | 9,229 | 2.04 |
