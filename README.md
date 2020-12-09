# JVM 调优

## 启动命令
cd work/jvm-optimization

java -Xms4g -Xmx4g -Xmn3g 
-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/ai/ailab/User/chenjingyu/logs/heapdump.hprof
-XX:+PrintGCTimeStamps -XX:+PrintGCDetails -Xloggc:/ai/ailab/User/chenjingyu/logs/jvm-optimization.log -jar target/jvm-0.0.1-SNAPSHOT.java

## 工具

### AB压测工具

#### 安装
apt install apache2-utils

-n 指定要执行的请求数
-c 指定并发，就是同时执行的请求数

ab -c 10 -n 100000 http://127.0.0.1:8080/buy

### GC 参数

-XX:PrintGCTimeStamps：打印 GC 具体时间；
-XX:PrintGCDetails ：打印出 GC 详细日志；
-Xloggc: path：GC 日志生成路径。

### GC 命令
jcmd pid VM.flags  可以查看到相关的设置参数
jstat -gc pid interval 查看每次 GC 之后，具体每一个分区的内存使用率变化情况。

### GC日志查看工具
https://sourceforge.net/projects/gcviewer/

## 调优的方案
1. 调整堆内存空间减少 FullGC 
通过日志分析，堆内存基本被用完了，而且存在大量 FullGC，这意味着我们的堆内存严重不足，这个时候我们需要调大堆内存空间。
2. 调整年轻代减少 MinorGC 
通过调整堆内存大小，我们已经提升了整体的吞吐量，降低了响应时间。那还有优化空间吗？我们还可以将年轻代设置得大一些，从而减少一些 MinorGC
3. 设置 Eden、Survivor 区比例：在 JVM 中，如果开启 AdaptiveSizePolicy，则每次 GC 后都会重新计算 Eden、From Survivor 和 To Survivor 区的大小，计算依据是 GC 过程中统计的 GC 时间、吞吐量、内存占用量。这个时候 SurvivorRatio 默认设置的比例会失效。
在 JDK1.8 中，默认是开启 AdaptiveSizePolicy 的，我们可以通过 -XX:-UseAdaptiveSizePolicy 关闭该项配置，或显示运行 -XX:SurvivorRatio=8 将 Eden、Survivor 的比例设置为 8:2。大部分新对象都是在 Eden 区创建的，我们可以固定 Eden 区的占用比例，来调优 JVM 的内存分配性能。

## 内存溢出排查

## 工具

### top

### MAT