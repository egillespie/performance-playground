performance-playground
======================

## Goal

This project is a simple way to compare relative performance of various serialization and deserialization tools.

Here's the list of benchmarked tools:

1. FasterXML's [Jackson JSON](http://wiki.fasterxml.com/JacksonHome) v2.5.1
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

The latest benchmarks were collected on February 27, 2015 using Java 1.8.0_20 and the following platform:

* CPU: Intel Core i7 Q 720 (8 cores) @ 1.60 GHz
* RAM: 6,144 MB
* Disk: WD 5400 RPM
* OS: Windows 7 Home Premium 64-bit (6.1, build 7601)

### Serialization

| Framework | Average Time (ms) | Min. Time (ms) | Max. Time (ms) | Variance |
| :-------- | ----------------: | -------------: | -------------: | :------- |
| Jackson JSON 2.5.1 | 897 | 815 | 1,264 | -0.09% / +0.41% |
| Jackson Afterburner JSON 2.5.1 | 886 | 835 | 1,288 | -0.06% / +0.45% |
| Protocol Buffers 2.6.0 | 867 | 775 | 1,240 | -0.11% / +0.43% |

### Deserialization

| Framework | Average Time (ms) | Min. Time (ms) | Max. Time (ms) | Variance |
| :-------- | ----------------: | -------------: | -------------: | :------- |
| Jackson JSON 2.5.1 | 1,726 | 1,540 | 2,571 | -0.11% / +0.49% |
| Jackson Afterburner JSON 2.5.1 | 1,730 | 1,504 | 2,517 | -0.13% / +0.45% |
| Protocol Buffers 2.6.0 | 1,576 | 1,375 | 2,175 | -0.13% / +0.38% |

### Compression

Besides time spent converting data, some analysis was done on the binary data
produced after serialization to see if one framework produced significantly
smaller data sets either before or after compression.

| Framework | Uncompressed Size (bytes) | GZip Compressed Size (bytes) | Compression Ratio |
| :-------- | ------------------------: | ---------------------------: | ----------------: |
| Jackson JSON 2.5.1 | 28,054 | 9,717 | 2.89 |
| Jackson Afterburner JSON 2.5.1 | 28,085 | 9,735 | 2.88 |
| Protocol Buffers 2.6.0 | 18,865 | 9,230 | 2.04 |
