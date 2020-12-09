package com.huan.jvm.controller;

import org.springframework.web.bind.annotation.RequestMapping;

public class ThreadLocalMemoryController {

    @RequestMapping(value = "/memory")
    public String memoryLeak() {
        // 每次请求进来都创建新的线程局部变量，并往里面添加4M的数据，由于Tomcat线程复用，所以内存不会被释放，从而导致内存溢出
        // 这种内存溢出是由于泄漏引起的，就是说当前请求的线程局部变量已经不需要了，但没有得到释放。
        ThreadLocal<Byte[]> mem = new ThreadLocal<>();
        mem.set(new Byte[1024 * 4096]);

        return "Success";
    }
}
