package org.example.patterns.behavior

/**
 * 기억 패턴은 외부에서 변경 불가능하며 객체 내부에서만 사용하는 내부 상태를 저장함으로써 문제를 해결한다.
 */
fun main() {
    val michael = Manager()

    michael.think("코코넛 대포를 구현해야해")
    michael.think("커피를 좀 마셔야겠어")

    val memento = michael.saveThatThought()

    with(michael) {
        think("아니면 차를 마실까?")
        think("아니, 파인애플 대포를 구현해야 해")
    }

    michael.printThoughts()
    michael.restoreThought(memento)
    michael.printThoughts()
}

class Manager {
    private var thoughts = mutableListOf<String>()

    fun printThoughts() {
        println(thoughts)
    }

    fun think(thought: String) {
        thoughts.add(thought)
        if (thoughts.size > 2) {
            this.thoughts.removeFirst()
        }
    }

    inner class Memory(private val mindState: List<String>) {
        fun restore() {
            thoughts = mindState.toMutableList()
        }
    }

    fun saveThatThought(): Memory {
        return Memory(thoughts.toList())
    }

    fun restoreThought(memory: Memory) {
        memory.restore()
    }
}