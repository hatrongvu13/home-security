package com.xxx.homesecurity.controller;

import com.xxx.homesecurity.services.DemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class DemoController {
    private final DemoService demoService;
}
