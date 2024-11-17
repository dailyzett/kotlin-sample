package org.example.patterns.behavior

/**
 * 알고리즘의 뼈대를 정의하되 세부 동작은 나중에 다른 누군가가 결정하게 하는 것이
 * 템플릿 메서드 패턴의 요지이다.
 */
fun main() {
    runSchedule(
        afterLaunch = {
            println("점심 후 토론")
            println("일과 관련되지 않은 자료 읽기")
        },
        beforeLaunch = {
            println("여행 계획 세우기")
            println("스택 오버플로우 읽기")
        },
        bossHook =  { println("Boss: 개인적으로 일 이야기좀 할까요?") }
    )
}

fun runSchedule(
    beforeLaunch: () -> Unit,
    afterLaunch: () -> Unit,
    bossHook: (() -> Unit)? = fun() { println() }
) {
    fun arriveToWork() {
        println("How are you all?")
    }

    val drinkCoffee = { println("커피를 마신다") }

    fun goToLaunch() = println("이탈리안 음식을 먹는다.")

    val goHome = fun() {
        println("집에 간다")
    }

    arriveToWork()
    drinkCoffee()
    beforeLaunch()
    goToLaunch()
    afterLaunch()
    bossHook?.let { it() }
    goHome()
}
