package org.example.patterns.behavior

/**
 * 전략 패턴은 외부 클라이언트가 전략을 교체하는 반면, 상태 패턴에서의 상태는 오로지 입력에 의해 내부적으로 변경된다.
 * 실제 코드에서 상태 패턴이 많이 사용되는 것은 코루틴이다.
 */
fun main() {
    val snail = Snail()
    snail.seeHero()
    snail.getHit(5)
    snail.getHit(10)
}

interface WhatCanHappen {
    fun seeHero()
    fun getHit(pointsOfDamage: Int)
    fun calmAgain()
}

class Snail : WhatCanHappen {
    private var mood: Mood = Still
    private var healthPoints = 10

    override fun seeHero() {
        mood = when (mood) {
            is Still -> {
                println("Aggressive")
                Aggressive
            }

            else -> {
                println("No change")
                mood
            }
        }
    }

    override fun getHit(pointsOfDamage: Int) {
        println("Hit for $pointsOfDamage points")
        healthPoints -= pointsOfDamage

        println("Health: $healthPoints")
        mood = when {
            (healthPoints <= 0) -> {
                println("Dead")
                Dead
            }
            mood is Aggressive -> {
                println("Retreating")
                Retreating
            }
            else -> {
                println("No change")
                mood
            }
        }
    }

    override fun calmAgain() {
    }
}

//추상 클래스이면서 인스턴스화가 불가능하다.
sealed class Mood

data object Still : Mood()
data object Aggressive : Mood()
data object Retreating : Mood()
data object Dead : Mood()