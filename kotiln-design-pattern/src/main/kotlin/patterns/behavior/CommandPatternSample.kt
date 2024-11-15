package org.example.patterns.behavior

/**
 * ```java
 * interface Command {
 *  fun execute()
 * }
 * ```
 * execute() 메서드는 인수도 없고 반환값도 없다. 즉 코틀린에서 `() -> Unit` 으로 작성 가능하다.
 * 함수는 일급객체이기 때문에 typealias 로 아래 코드처럼 간단하게 선언할 수 있다.
 */
typealias Command = () -> Unit

/**
 * 명령 디자인 패턴은 객체 내부에 동작을 캡슐화해서 나중에 실행되도록 할 수 있다.
 */
fun main() {
    val t = Trooper()

    t.addOrder(moveGenerator(t, 1, 1))
    t.addOrder(moveGenerator(t, 2, 2))
    t.addOrder(moveGenerator(t, 3, 3))

    t.executeOrders()

    t.appendMove(20, 0)
        .appendMove(20, 20)
        .appendMove(5, 20)
        .executeOrders()
}

open class Trooper {
    private val orders = mutableListOf<Command>()

    fun addOrder(order: Command) {
        this.orders.add(order)
    }

    fun executeOrders() {
        while (orders.isNotEmpty()) {
            val order = orders.removeFirst()
            order()
        }
    }

    fun move(x: Int, y: Int) {
        println("($x, $y)로 이동 중")
    }

    /**
     * fluent syntax(유창한 문법)을 통해 동일 객체에 대한 메서드를 사슬처럼 계속 호출할 수 있다.
     */
    fun appendMove(x: Int, y: Int) = apply {
        orders.add(moveGenerator(this, x, y))
    }
}

/**
 * 이것은 함수 생성기 코드이다. 함수 생성기란 다른 함수를 반환하는 함수를 뜻한다.
 * 자바스크립트에서 시야를 제한하고 상태를 기억하기 위해 클로저를 사용하는데, 여기서도 같은 방법을 쓰는 것이다.
 */
val moveGenerator = fun (
    s: Trooper,
    x: Int,
    y: Int
): Command {
    return fun() {
        s.move(x, y)
    }
}

