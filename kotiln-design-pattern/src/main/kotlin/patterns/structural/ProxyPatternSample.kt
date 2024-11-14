package org.example.patterns.structural

import java.net.URL

fun main() {
    val cat = CatImage(
        "https://i.chzbgr.com/full/9026714368/hBB09ABBE/i-will-has-attention",
        "https://i.chzbgr.com/full/9026714368/hBB09ABBE/i-will-has-attention"
    )

    println(cat.image.size)
    println(cat.image.size)
}

data class CatImage(
    val thumbnailUrl: String,
    val url: String
) {
    /**
     * 필드 초기화가 나중에 되도록 위임하는 역할의 by lazy.
     * image 속성을 호출하면 코드 블록이 실행되고 그 결과가 image 속성에 저장된다.
     * 이후 호출할 때는 속성에 저장된 값을 반환한다.
     */
    val image: ByteArray by lazy {
        println("Fetching image, please wait")
        // Read image as bytes
        URL(url).readBytes()
    }
/*
    val image: ByteArray
        get() = run {
            println("Fetching image, please wait")
            URL(url).readBytes()
        }*/
}