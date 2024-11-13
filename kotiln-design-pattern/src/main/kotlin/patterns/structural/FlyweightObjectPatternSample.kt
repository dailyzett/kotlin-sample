package org.example.patterns.structural

import java.io.File

fun main() {
    val snails = List(10_000) {
        TansanianSnail()
    }

    snails.forEach { snail -> println(snail.getCurrentSprite()) }
}

enum class Direction {
    LEFT,
    RIGHT
}

class TansanianSnail {
    private val directionFacing = Direction.LEFT

    // This is the Flyweight we're using
    private val sprites = SnailSprites.sprites

    fun getCurrentSprite(): File {
        return when (directionFacing) {
            Direction.LEFT -> sprites[0]
            Direction.RIGHT -> sprites[1]
        }
    }
}

/**
 * 사진이 들어가는 달팽이 object 를 하나두고, 이것을 참조하는 방식으로 코드를 구성하면
 * 서버 메모리를 많이 아낄 수 있다.
 */
object SnailSprites {
    val sprites = List(8) { i ->
        File(
            when (i) {
                0 -> "snail-left.jpg"
                1 -> "snail-right.jpg"
                in 2..4 -> "snail-move-left-${i - 1}.jpg"
                else -> "snail-move-right${(4 - i)}.jpg"
            }
        )
    }
}