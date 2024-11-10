package org.example.patterns.creational

data class User(
    val name: String,
    val role: Role,
    val permissions: List<String>,
    val tasks: List<String>,
)

enum class Role {
    ADMIN, SUPER_ADMIN, REGULAR_USER
}

/**
 * 프로토타입 패턴의 핵심 아이디어는 객체를 쉽게 복사할 수 있도록 하자는 것이다.
 * 코틀린의 data class 를 사용하면 copy 를 통해 객체를 새로 생성할 수 있다.
 * 이 방법의 장점은 User 에 프로퍼티가 추가되어도 다른 데이터는 그대로 복사되기 때문에 모든 코드를 손댈 필요가 없다는 것이다.
 */
fun main() {
    val allUsers = mutableListOf<User>()

    fun createUser(_name: String, role: Role) {
        for(user in allUsers) {
            if(user.role == role) {
                allUsers += user.copy(name = _name)
            }
        }
    }
}