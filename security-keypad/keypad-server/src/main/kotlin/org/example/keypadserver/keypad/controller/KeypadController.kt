package org.example.keypadserver.keypad.controller

import org.example.keypadserver.keypad.DecryptRequest
import org.example.keypadserver.keypad.KeypadResponse
import org.example.keypadserver.keypad.service.KeypadService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class KeypadController(
    private val keypadService: KeypadService,
) {

    @PostMapping("/keypad")
    fun generateKeypad(): ResponseEntity<KeypadResponse> {
        val keypadData = keypadService.generateKeypad()
        return ResponseEntity.ok(keypadData)
    }

    @PostMapping("/decrypt")
    fun decryptValue(@RequestBody request: DecryptRequest): String {
        return keypadService.decryptValue(request.keypadId, request.encryptedValue)
    }
}