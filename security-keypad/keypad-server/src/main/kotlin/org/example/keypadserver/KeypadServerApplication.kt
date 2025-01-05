package org.example.keypadserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KeypadServerApplication

fun main(args: Array<String>) {
	runApplication<KeypadServerApplication>(*args)
}
