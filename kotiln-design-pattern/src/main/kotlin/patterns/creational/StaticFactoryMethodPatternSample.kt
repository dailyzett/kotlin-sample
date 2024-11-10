package org.example.patterns.creational

/**
 * 1. 다양한 생성자에 명시적인 이름을 붙일 수 있다.
 * 2. 생성자 인스턴스도 간헐적으로 예외가 발생하기도 한다. 예외가 터질것이라면 메서드에서 터지는 것이 낫다.
 * 3. 생성하는 데에 오래 걸리는 인스턴스라면 정적 팩토리 메서드 패턴을 고려한다.
 *
 * 장점:
 * - 캐시를 적용할 수 있다. 같은 값으로 정적 메서드 팩토리를 계속 호출하면 GC해야하는 객체가 덜 생긴다.
 *
 * ```kotlin
 *  Server.withPort(8080)
 * ```
 */
class Server private constructor(port: String) {
    companion object {
        fun withPort(port: String) = Server(port)
    }
}
