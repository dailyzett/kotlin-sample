package org.example.keypadserver.keypad

data class DecryptRequest (
    val keypadId: String,
    val encryptedValue: String
)