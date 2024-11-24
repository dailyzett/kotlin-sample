package org.example.keypadserver.keypad

data class KeypadResponse(
    val publicKey: String,
    val keypadHtml: String,
    val keypadId: String,
)