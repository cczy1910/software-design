package visitor

import token.*
import java.util.*

class CalcVisitor : TokenVisitor {

    private val deque: Deque<Int> = ArrayDeque()

    fun getResult(): Int {
        return deque.last
    }

    override fun visit(token: NumberToken) {
        deque.addLast(token.value)
    }

    override fun visit(token: BraceToken) {
        throw IllegalArgumentException("Unexpected token $token")
    }

    override fun visit(token: OpToken) {
        if (deque.size < 2) {
            throw IllegalStateException("Wrong arguments count for operation $token")
        }

        val x = deque.removeLast()
        val y = deque.removeLast()
        val op = getOp(token.op)

        deque.addLast(op(y, x))
    }

    companion object {
        fun getOp(op: Op): (Int, Int) -> Int {
            return when (op) {
                is Plus -> Int::plus
                is Minus -> Int::minus
                is Mul -> Int::times
                is Div -> Int::div
            }
        }
    }
}