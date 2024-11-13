package org.example.patterns.structural

import java.io.FileNotFoundException
import kotlin.io.path.Path

/**
 * 퍼사드는 지저분한 세부 구현 사항을 감추는데에 사용한다.
 *
 * 퍼사드 디자인 패턴의 목적은 서로 관련된 여러 클래스나 인터페이스를 더 깔끔하고 간단하게 다룰 수 있도록 하는 것이다.
 *
 * - 추상 팩토리 메서드 패턴은 연관된 클래스를 생성하는데 초점을 맞춘다.
 * - 퍼사드 패턴은 일단 생성된 객체들을 잘 사용하는 데 초점을 맞춘다.
 */
fun main() {
    try {
        val server = Server.withPort(0).startFromConfiguration("/path/to/config")
    } catch (e: FileNotFoundException) {
        println("If there was a file and a parser, it would have worked")
    }
}

interface Parser {
    fun property(prop: String): Property
    fun server(propertyStrings: List<String>): ServerConfiguration
}

/**
 * - 주어진 파일을 JSON 파서로 파싱을 시도함으로써 .json 파일인지 .yaml 파일인지 확인한다.
 * - 오류가 발생하는 경우 yaml 파일로 파싱을 시도한다.
 * - 오류가 없다면 파싱 결과를 추상 팩토리에 전달해 필요한 객체를 생성한다.
 *
 * 이 함수는 설정 파일의 경로를 입력받아 파일을 파싱하고, 파싱 과정에 오류가 없는 경우
 * 서버를 시작하는 일을 한 번에 수행한다.
 */
fun Server.startFromConfiguration(fileLocation: String) {
    val path = Path(fileLocation)

    val lines = path.toFile().readLines()

    val configuration = try {
        JsonParser().server(lines)
    } catch (e: RuntimeException) {
        YamlParser().server(lines)
    }

    Server.withPort(configuration.port)
}

class Server private constructor(port: Int) {
    companion object {
        fun withPort(port: Int): Server {
            return Server(port)
        }
    }
}

class YamlParser : Parser {
    // Implementation specific to YAML files
    override fun property(prop: String): Property {
        TODO("Not yet implemented")
    }

    override fun server(propertyStrings: List<String>): ServerConfiguration {
        TODO("Not yet implemented")
    }
}

class JsonParser : Parser {

    // Implementation specific to JSON files
    override fun property(prop: String): Property {
        TODO("Not yet implemented")
    }

    override fun server(propertyStrings: List<String>): ServerConfiguration {
        TODO("Not yet implemented")
    }
}

class Property

class ServerConfiguration {
    var port: Int = 8080
}