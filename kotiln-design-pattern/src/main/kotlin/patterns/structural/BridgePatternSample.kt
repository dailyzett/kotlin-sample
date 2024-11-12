package org.example.patterns.structural

/**
 * 브리지 패턴은 디자인 패턴을 상속하는 것을 막아준다.
 *
 * - Trooper 를 구현하는 인터페이스에 메서드가 하나 추가되면, 이 인터페이스를 구현하는 모든 클래스에 변경점이 따른다.
 * - 구현한 클래스가 50개라면 50개의 모든 클래스를 변경해줘야 한다.
 *
 * ```kotlin
 * interface Trooper {
 *     fun move(x: Long, y: Long)
 *     fun attackRebel(x: Long, y: Long)
 * }
 * ```
 * 브리지 패턴의 핵심 아이디어는 클래스 계층 구조를 얕게 만듦으로써 시스템에 구체 클래스의 수를 줄이는 것이다.
 * 뿐만 아니라 부모 클래스를 수정했을 때 자식 클래스에서 발견하기 어려운 버그가 발생하는 것도 막는다(깨지기 쉬운 기반 클래스 문제)
 */

typealias PointOfDamage = Long
typealias Meter = Int

const val RIFLE_DAMAGE = 3L
const val REGULAR_SPEED: Meter = 1

interface Weapon {
    fun attack(): PointOfDamage
}

interface Legs {
    fun move(): Meter
}

/**
 * 무기에 관한 구현
 */

class Rifle : Weapon {
    override fun attack(): PointOfDamage = RIFLE_DAMAGE
}

class Flamethrower : Weapon {
    override fun attack(): PointOfDamage = RIFLE_DAMAGE * 2
}

class Batton : Weapon {
    override fun attack(): PointOfDamage = RIFLE_DAMAGE * 3
}

/**
 * 이동에 관한 인터페이스 구현
 */
class RegularLegs : Legs {
    override fun move(): Meter = REGULAR_SPEED
}

class AthleticLegs : Legs {
    override fun move(): Meter = REGULAR_SPEED * 2
}


data class StormTrooper(
    private val weapon: Weapon,
    private val legs: Legs,
)

/**
 * 현업에서 브리지 패턴은 의존성 주입과 함께 사용될 때가 많다. 브리지 패턴을 사용하면 데이터베이스를 사용하는 구현체를
 * 쉽게 목 객체로 대체할 수 있기 때문이다.
 */
fun main() {
    val stormTrooper = StormTrooper(Rifle(), RegularLegs())
    val flameTrooper = StormTrooper(Flamethrower(), RegularLegs())
    val scoutTrooper = StormTrooper(Rifle(), AthleticLegs())
}