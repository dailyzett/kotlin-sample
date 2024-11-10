package org.example.patterns.creational

/**
 * 추상 팩토리는 팩토리를 만들어내는 팩토리이다. 즉 추상 팩토리란 여러 팩토리 메서드를 감싸는 클래스다.
 */
interface Property {
    val name: String
    val value: Any
}

interface ServerConfiguration {
    val properties: List<Property>
}

data class PropertyImpl(
    override val name: String,
    override val value: Any
): Property

data class ServerConfigurationImpl(
    override val properties: List<Property>
): ServerConfiguration

fun property(prop: String): Property {
    val (name, value) = prop.split(":")
    return when(name) {
        "port" -> PropertyImpl(name, value.trim().toInt())
        "environment" -> PropertyImpl(name, value.trim())
        else -> throw RuntimeException("Unsupported property $name")
    }
}

fun createConfiguration(factory: ConfigurationFactory, propertyStrings: List<String>): ServerConfiguration {
    val parsedProperties = mutableListOf<Property>()
    for(p in propertyStrings) {
        val (name, value) = p.split(":")
        parsedProperties += factory.createProperty(name, value)
    }
    return factory.createServerConfiguration(parsedProperties)
}
/**
 * 추상 팩토리 인터페이스
 */
interface ConfigurationFactory {
    fun createProperty(name: String, value: String): Property
    fun createServerConfiguration(properties: List<Property>): ServerConfiguration
}

// 개발 환경용 구체적인 팩토리
class DevelopmentConfigurationFactory : ConfigurationFactory {
    override fun createProperty(name: String, value: String): Property {
        return when(name) {
            "port" -> DevPropertyImpl(name, 8080) // 개발 환경은 항상 8080 포트 사용
            "environment" -> DevPropertyImpl(name, "development")
            else -> throw RuntimeException("Unsupported property $name")
        }
    }

    override fun createServerConfiguration(properties: List<Property>): ServerConfiguration {
        return DevServerConfigurationImpl(properties)
    }
}

data class DevPropertyImpl(
    override val name: String,
    override val value: Any
): Property

data class DevServerConfigurationImpl(
    override val properties: List<Property>
) : ServerConfiguration

fun main() {
    val devFactory = DevelopmentConfigurationFactory()
    val devConfig = createConfiguration(devFactory, listOf("port: 8080", "environment: development"))
    println("Dev config: $devConfig")
}