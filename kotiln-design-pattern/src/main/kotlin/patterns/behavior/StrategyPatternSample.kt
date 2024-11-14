package org.example.patterns.behavior

enum class Direction {
    LEFT, RIGHT
}

data class Projectile(
    private var x: Int,
    private var y: Int,
    private var direction: Direction,
)

object Weapons {
    fun peashooter(x: Int, y: Int, direction: Direction): Projectile {
        println("It's a peashooter")
        return Projectile(x, y, direction)
    }

    fun banana(x: Int, y: Int, direction: Direction): Projectile {
        println("It's a banana")
        return Projectile(x, y, direction)
    }
}

class OurHero {
    private var direction = Direction.LEFT
    private var x: Int = 42
    private var y: Int = 173

    var currentWeapon = Weapons::peashooter

    val shoot = fun() { currentWeapon(x, y, direction) }
}

/**
 * 전략 패턴은 런타임에 객체 동작을 바꾸고 싶을 때 매우 유용하게 사용된다.
 */
fun main() {
    val hero = OurHero()
    hero.shoot()

    hero.currentWeapon = Weapons::banana
    hero.shoot()
}