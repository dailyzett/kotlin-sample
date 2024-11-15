package org.example.patterns.behavior

import javax.net.ssl.HandshakeCompletedListener

/**
 * 책임 사슬 패턴은 복잡한 로직을 여러 개의 작은 단계로 쪼갠다.
 * 그리고 각 단계가 그 결과에 따라 계속해서 다음 단계로 진행할지, 또는 처리를 끝내고 반환할지 결정한다.
 *
 * - 매개 변수가 이미 검증됐다.
 * - 사용자가 이미 인증됐다.
 * - 사용자의 역할과 권한 정보가 주어졌고 동작을 수행하기 위한 인가가 이뤄졌다.
 *
 * ```kotlin
 * fun handleRequest(r: Request) {
 *     // Validate
 *     if (r.email.isEmpty() || r.question.isEmpty()) {
 *         return
 *     }
 *
 *     // Authenticate
 *     // Make sure that you know whos is this user
 *     if (r.isKnownEmail()) {
 *         return
 *     }
 *
 *     // Authorize
 *     // Requests from juniors are automatically ignored by architects
 *     if (r.isFromJuniorDeveloper()) {
 *         return
 *     }
 *
 *     println("I don't know. Did you check StackOverflow?")
 * }
 * ```
 *
 *  다소 코드가 지저분하다. 그리고 두 개의 질문을 동시에 보내면 이를 막기위한 또 다른 로직을 추가해야 한다.
 */
fun main() {
    val req = Request(
        "developer@company.com",
        "Why do we need Software Architects?"
    )

    val chain = basicValidation(authentication(finalResponse()))

    val res = chain(req)
    println(res)
}

typealias Handler = (request: Request) -> Response

val basicValidation = fun(next: Handler) =
    fun(request: Request): Response {
        if (request.email.isEmpty() || request.question.isEmpty()) {
            throw IllegalArgumentException()
        }

        return next(request)
    }

val authentication = fun(next: Handler) =
    fun(request: Request): Response {
        if (!request.isKnownEmail()) {
            throw IllegalArgumentException()
        }

        return next(request)
    }

val finalResponse = fun() =
    fun(_: Request): Response {
        return Response("I don't know")
    }

data class Request(val email: String, val question: String) {
    fun isKnownEmail(): Boolean {
        return true
    }

    fun isFromJuniorDeveloper(): Boolean {
        return false
    }
}

data class Response(val answer: String)