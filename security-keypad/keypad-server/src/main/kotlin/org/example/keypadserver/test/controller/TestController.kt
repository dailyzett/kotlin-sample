package org.example.keypadserver.test.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/test")
class TestController {

    @GetMapping("/hello")
    fun testHello(): ResponseEntity<String> {
        return ResponseEntity.ok("Hello World!!")
    }
}