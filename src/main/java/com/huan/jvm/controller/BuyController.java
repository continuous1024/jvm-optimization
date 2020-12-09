package com.huan.jvm.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class BuyController {
    @RequestMapping("/buy")
    public String rushToBuy() {
        List<Byte[]> list = new ArrayList<>();

        Byte[] data = new Byte[1024*1024];
        list.add(data);

        return "Success";
    }
}
