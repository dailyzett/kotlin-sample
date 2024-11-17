package org.example.patterns.behavior

/**
 * 중개인 패턴은 간단한 인터페이스를 제공함으로써 여러 객체 간의 복잡한 상호작용을 캡슐화한다.
 *
 * 그래서 중개인 패턴은 잘못하면 "God" 객체가 될 수 있다. 신 객체를 만드는 것은 안티 패턴이다.
 * 마이클이 중요하다고 해도 그가 무엇을 해야하며, 무엇을 하면 안되는지를 명확하게 정의해야 한다.
 */
fun main() {
    val manager = Michael
    val company = MyCompany(manager)
    company.taskCompleted(true)
}

interface ProductManager { fun isAllGood(majorRelease: Boolean): Boolean }
interface Canary

object Michael : Canary, ProductManager {
    private val kenny = Kenny(this)
    private val brad = Brad(this)

    override fun isAllGood(majorRelease: Boolean): Boolean {
        if (!kenny.isEating() && !kenny.isSleeping()) {
            println(kenny.doesMyCodeWork())
        } else if (!brad.isEating() && !brad.isSleeping()) {
            println(brad.doesMyCodeWork())
        }
        return true
    }

}

interface QA {
    fun doesMyCodeWork(): Boolean
}

interface Parrot {
    fun isEating(): Boolean
    fun isSleeping(): Boolean
}

class Kenny(private val productManager: ProductManager) : QA, Parrot {
    override fun isSleeping(): Boolean {
        return false
    }

    override fun isEating(): Boolean {
        return false
    }

    override fun doesMyCodeWork(): Boolean {
        return true
    }
}

class Brad(private val productManager: ProductManager) : QA, Parrot {
    override fun isSleeping(): Boolean {
        return false
    }

    override fun isEating(): Boolean {
        return false
    }

    override fun doesMyCodeWork(): Boolean {
        return true
    }
}

object Me

class MyCompany(private val productManager: ProductManager) {
    fun taskCompleted(isMajorRelease: Boolean) {
        println(productManager.isAllGood(isMajorRelease))
    }
}