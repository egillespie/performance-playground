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

The latest benchmarks were collected on February 27, 2015 using Java 1.8.0_20 and the following platform:

* CPU: Intel Core i7 Q 720 (8 cores) @ 1.60 GHz
* RAM: 6,144 MB
* Disk: WD 5400 RPM
* OS: Windows 7 Home Premium 64-bit (6.1, build 7601)

### Serialization

| Framework | Average Time (ms) | Min. Time (ms) | Max. Time (ms) | Variance |
| :-------- | ----------------: | -------------: | -------------: | :------- |
| Jackson JSON 2.5.1 | 373 | 321 | 601 | -0.14% / +0.61% |
| Jackson Afterburner JSON 2.5.1 | 357 | 313 | 587 | -0.12% / +0.64% |
| Protocol Buffers 2.6.0 | 891 | 772 | 1,402 | -0.13% / +0.57% |

### Deserialization

| Framework | Average Time (ms) | Min. Time (ms) | Max. Time (ms) | Variance |
| :-------- | ----------------: | -------------: | -------------: | :------- |
| Jackson JSON 2.5.1 | 704 | 557 | 1,421 | -0.21% / +1.02% |
| Jackson Afterburner JSON 2.5.1 | 569 | 523 | 973 | -0.08% / +0.71% |
| Protocol Buffers 2.6.0 | 1,344 | 1,173 | 2,006 | -0.13% / +0.49% |

### Compression

Besides time spent converting data, some analysis was done on the binary data
produced after serialization to see if one framework produced significantly
smaller data sets either before or after compression.

| Framework | Uncompressed Size (bytes) | GZip Compressed Size (bytes) | Compression Ratio |
| :-------- | ------------------------: | ---------------------------: | ----------------: |
| Jackson JSON 2.5.1 | 27,018 | 9,591 | 2.82 |
| Jackson Afterburner JSON 2.5.1 | 26,910 | 9,610 | 2.80 |
| Protocol Buffers 2.6.0 | 18,851 | 9,199 | 2.05 |
