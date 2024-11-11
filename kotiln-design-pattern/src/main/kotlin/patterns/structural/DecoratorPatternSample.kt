package org.example.patterns.structural

/**
 * 동작이 조금씩 다른 클래스를 여럿 만들어야할 때 데코레이터 패턴을 사용한다.
 * 데코레이터 패턴으로 사용자가 어떤 기능을 추가할 지 자유롭게 선택가능하다
 *
 * ```kotlin
 * open class StarTrekRepository {
 *     private val starshipCaptains = mutableMapOf("USS 엔터프라이즈" to "장뤽 피카드")
 *
 *     open fun getCaptain(starshipName: String): String {
 *         return starshipCaptains[starshipName] ?: "알 수 없음"
 *     }
 *
 *     open fun addCaptain(starshipName: String, captainName: String) {
 *         starshipCaptains[starshipName] = captainName
 *     }
 * }
 * ```
 * 여기서 관리자의 요구사항이 추가되었다.
 *
 * 1. 반드시 콘솔에 로그를 남겨야 한다.
 * 2. StarTrekRepository 를 수정하면 안 된다. 다른 사람도 이 클래스를 사용하는데, 그들은 로그를 남기는 것을 좋아하지 않는다.
 *
 * 데코레이턴 패턴의 목적은 객체에 새로운 동작을 동적으로 추가하는 것이다.
 * 그래서 위의 example 코드를 인터페이스로 일단 바꾼다.
 */
interface StarTrekRepository {
    fun getCaptain(starshipName: String): String
    fun addCaptain(starshipName: String, captainName: String)
}

class DefaultStarTrekRepository : StarTrekRepository {
    private val starshipCaptains = mutableMapOf("USS 엔터프라이즈" to "장뤽 피카드")

    override fun getCaptain(starshipName: String): String {
        return starshipCaptains[starshipName] ?: ""
    }

    override fun addCaptain(starshipName: String, captainName: String) {
        starshipCaptains[starshipName] = captainName
    }
}

/**
 * `DefaultStarTrekRepository`를 상속받는 대신 `by` 키워드를 사용한다.
 */
class LoggingGetCaptain(private val repository: StarTrekRepository): StarTrekRepository by repository {
    override fun getCaptain(starshipName: String): String {
        println("$starshipName 함선의 선장을 조회 중입니다.")
        return repository.getCaptain(starshipName)
    }
}

class ValidatingAdd(private val repo: StarTrekRepository): StarTrekRepository by repo {
    private val maxNameLength = 7

    override fun addCaptain(starshipName: String, captainName: String) {
        require(captainName.length < maxNameLength) {
            "${captainName}의 이름이 7글자를 넘습니다."
        }
        repo.addCaptain(starshipName, captainName)
    }
}

fun main() {
    val starTrekRepository = DefaultStarTrekRepository()
    val withValidating = ValidatingAdd(starTrekRepository)
    val withLoggingAndValidating = LoggingGetCaptain(withValidating)

    withLoggingAndValidating.getCaptain("USS 엔터프라이즈")
    withLoggingAndValidating.addCaptain("USS 보이저", "캐서린 제인웨이")
}