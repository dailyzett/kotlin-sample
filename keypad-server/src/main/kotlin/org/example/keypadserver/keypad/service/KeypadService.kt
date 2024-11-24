package org.example.keypadserver.keypad.service

import org.example.keypadserver.keypad.KeypadResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.PrivateKey
import java.security.spec.MGF1ParameterSpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.spec.OAEPParameterSpec
import javax.crypto.spec.PSource

@Service
class KeypadService {

    val keyMap = mutableMapOf<String, PrivateKey>()
    val log = LoggerFactory.getLogger(javaClass)

    fun generateKeypad(): KeypadResponse {
        //1. 키페어 생성
        val keyPair = generateKeyPair()
        val keypadId = UUID.randomUUID().toString()

        //2. 치환 테이블 생성
        val shuffledNumbers = (0..9).toList().shuffled()

        //3. 키패드 HTML 생성
        val keypadHtml = generateKeypadHtml(shuffledNumbers)

        //4. 메모리에 개인키 저장
        keyMap[keypadId] = keyPair.private

        // Base64로 인코딩하여 전송
        val publicKeyBase64 = Base64.getEncoder().encodeToString(keyPair.public.encoded)

        return KeypadResponse(
            publicKey = publicKeyBase64,
            keypadHtml = keypadHtml,
            keypadId = keypadId
        )
    }

    private fun generateKeyPair(): KeyPair {
        val keyPairGenerator = KeyPairGenerator.getInstance("RSA")
        keyPairGenerator.initialize(2048)
        return keyPairGenerator.generateKeyPair()
    }

    private fun generateKeypadHtml(numbers: List<Int>): String {
        val rows = numbers.chunked(3).joinToString("\n") { row ->
            val buttons = row.joinToString("\n") { num ->
                """<button data-value="$num" type="button">$num</button>"""
            }
            """<div>$buttons</div>"""
        }

        return """<div>$rows</div>"""
    }

    fun decryptValue(keypadId: String, encryptedValue: String): String {
        val privateKey = keyMap[keypadId] ?: throw IllegalArgumentException("Invalid keypad ID")

        log.info("entered Value: $keypadId, encryptedValue: $encryptedValue")

        val cipher = Cipher.getInstance("RSA/ECB/OAEPWITHSHA-256ANDMGF1PADDING")
        val oaepParams = OAEPParameterSpec(
            "SHA-256",
            "MGF1",
            MGF1ParameterSpec.SHA256,
            PSource.PSpecified.DEFAULT
        )

        cipher.init(Cipher.DECRYPT_MODE, privateKey, oaepParams)

        val encryptedBytes = Base64.getDecoder().decode(encryptedValue)
        val decryptedBytes = cipher.doFinal(encryptedBytes)

        val decryptedValue = String(decryptedBytes)
        log.info("Decrypted value: $decryptedValue") // 로그 출력

        return decryptedValue
    }
}