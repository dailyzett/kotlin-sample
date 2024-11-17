package org.example.patterns.behavior

/**
 * 관찰자 패턴에는 발행자와 이를 구독하는 여러 구독자가 있다. 발행자에게 어떤 일이 발생하면 모든 구독자가 그 사실을 알게 된다.
 *
 * 중개인 패턴과는 다른 것이 관찰자 패턴은 런타임에 구독 및 구독 취소가 가능하다.
 */
fun main() {
    val catToConductor = Cat()

    val bat = Bat()
    val dog = Dog()
    val turkey = Turkey()

    catToConductor.joinChoir(bat::screech)
    catToConductor.joinChoir(turkey::gobble)
    catToConductor.joinChoir(dog::bark)

    catToConductor.conduct(HighMessage(3))

    catToConductor.leaveChoir(turkey::gobble)
    catToConductor.conduct(LowMessage(1))
}

/**
 * 지휘자 역할을 하는 Cat,
 * 발행자를 설계할 때는 여러 속성을 갖는 하나의 데이터 클래스를 전달하도록 해야한다. 그렇게 하지않고
 * 여러개의 자료를 전달하면 새 속성이 추가될 때마다 구독자의 코드를 모두 고쳐야한다.
 */
class Cat {
    private val participants = mutableMapOf<(Message) -> Unit, (Message) -> Unit>()

    fun joinChoir(whatToCall: (Message) -> Unit) {
        participants[whatToCall] = whatToCall
    }

    fun leaveChoir(whatNotToCall: (Message) -> Unit) {
        participants.remove(whatNotToCall)
    }

    fun conduct(message: Message) {
        for (p in participants.values) {
            p(message)
        }
    }
}

class Bat {
    fun screech(message: Message) {
        when (message) {
            is HighMessage -> {
                for (i in 1..message.repeat) {
                    println("${message.pitch} 이-----")
                }
            }

            else -> {
                println("낼 수 없는 소리에요 :(")
            }
        }
    }
}

class Turkey {
    fun gobble(message: Message) {
        when (message) {
            is HighMessage, is LowMessage -> {
                for (i in 1..message.repeat) {
                    println("${message.pitch} 구블이 웁니다.")
                }
            }
        }
    }
}

class Dog {
    fun bark(message: Message) {
        println("Woof")
    }

    fun howl(message: Message) {
        println("Auuuu")
    }
}

enum class SoundPitch { HIGH, LOW }

interface Message {
    val repeat: Int
    val pitch: SoundPitch
}

data class LowMessage(override val repeat: Int) : Message {
    override val pitch = SoundPitch.LOW
}

data class HighMessage(override val repeat: Int) : Message {
    override val pitch = SoundPitch.HIGH
}