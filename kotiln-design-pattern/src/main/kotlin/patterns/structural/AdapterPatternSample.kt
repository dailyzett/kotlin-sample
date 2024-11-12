package org.example.patterns.structural

/**
 * 값이 1이면 전원이 공급
 * 다른 값이면 끊어진 것이다.
 */
interface PlugTypeF {
    val hasPower: Int
}

interface PlugTypeA {
    val hasPower: String // "TRUE 또는 FALSE"
}

interface UsbMini {
    val hasPower: Power
}

enum class Power {
    TRUE, FALSE
}

interface UsbTypeC {
    val hasPower: Boolean
}

/**
 * 목표: 전원 값이 F 타입 플러그에서 휴대전화까지 흐르게하는 것
 */

fun main() {

    fun cellPhone(chargeCable: UsbTypeC) {
        if (chargeCable.hasPower) {
            println("충전 중입니다!")
        } else {
            println("전원이 연결되지 않았습니다.")
        }
    }

    fun krPowerOutlet(): PlugTypeF {
        return object : PlugTypeF {
            override val hasPower = 1
        }
    }

    /**
     * 휴대전화 충전기를 PlugTypeA를 입력으로 받아 UsbMini를 반환한다.
     */
    fun charger(plug: PlugTypeA): UsbMini {
        return object : UsbMini {
            override val hasPower = Power.valueOf(plug.hasPower)
        }
    }

    cellPhone(charger(krPowerOutlet().toPlugTypeA()).toUsbTypeC())
}

/**
 * 코틀린에서 어댑터 패턴은 확장 함수를 이용해 구현할 수 있다.
 * 어댑터 메서드의 일반적인 이름은 `to` 로 시작한다.
 *
 * 가령 toTypedArray() 함수는 리스트를 배열로 변환한다.
 */
fun PlugTypeF.toPlugTypeA(): PlugTypeA {
    val hasPower = if (this.hasPower == 1) "TRUE" else "FALSE"
    return object : PlugTypeA {
        override val hasPower = hasPower
    }
}

fun UsbMini.toUsbTypeC(): UsbTypeC {
    val hasPower = this.hasPower == Power.TRUE
    return object : UsbTypeC {
        override val hasPower = hasPower
    }
}