package org.example.patterns.creational

/**
 * 팩토리 메서드 패턴 예제
 * 객체를 생성하기 위해 기존 생성자로는 한계가 있을 때 사용한다.
 *
 * 체스판은 각 기물의 위치와 종류만 설정하면 된다. C3에 있는 퀸을 qc3, A8에 있는 폰을 pa8 이라고 저장한다.
 */
fun main() {
    val notations = listOf("pa8", "qc3")
    val pieces = mutableListOf<ChessPiece>()
    for(n in notations) {
        pieces.add(createPiece(n))
    }
    println(pieces)
}

interface ChessPiece {
    val file: Char
    val rank: Char
}

data class Pawn(
    override val file: Char,
    override val rank: Char
): ChessPiece

data class Queen(
    override val file: Char,
    override val rank: Char
): ChessPiece

/**
 * when 표현식은 기물 종류를 나타내는 문자에 따라 ChessPiece 인터페이스를 구현하는 여러 구현체 중 하나를 인스턴스화한다.
 * 이게 팩토리 메서드 디자인 패턴의 활용 예다.
 */
fun createPiece(notation: String): ChessPiece {
    val (type, file, rank) = notation.toCharArray()

    return when(type) {
        'q' -> Queen(file, rank)
        'p' -> Pawn(file, rank)
        else -> throw RuntimeException("알 수 없는 기물 종류: $type")
    }
}