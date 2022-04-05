package visitor

import token.*
import java.util.*

class ParserVisitor : TokenVisitor {
    private val deque: Deque<Token> = ArrayDeque()
    private val res: MutableList<Token> = mutableListOf()

    fun getResult(): List<Token> {
        while (deque.isNotEmpty()) {
            when (val t = deque.removeLast()) {
                is OpToken -> res.add(t)
                else -> throw IllegalStateException("Unexpected token $t")
            }
        }

        return res
    }

    override fun visit(token: NumberToken) {
        res.add(token)
    }

    override fun visit(token: BraceToken) {
        when (token.br) {
            is LeftBrace -> deque.addLast(token)
            is RightBrace -> {
                while (deque.isNotEmpty()) {
                    when (val t = deque.removeLast()) {
                        is OpToken -> res.add(t)
                        is BraceToken -> {
                            when (t.br) {
                                is LeftBrace -> break
                                is RightBrace -> throw IllegalStateException("Unexpected token: $token")
                            }
                        }
                        is NumberToken -> throw IllegalStateException("Unexpected token: $token")
                    }
                }
            }
        }
    }

    override fun visit(token: OpToken) {
        while (deque.isNotEmpty()) {
            when (val t = deque.last) {
                is OpToken -> {
                    if (getPriority(t.op) >= getPriority(token.op)) {
                        res.add(t)
                        deque.removeLast()
                    } else {
                        break
                    }
                }
                else -> break
            }
        }
        deque.addLast(token)
    }

    companion object {
        fun getPriority(op: Op): Int {
            return when (op) {
                is Plus, is Minus -> 1
                is Mul, is Div -> 2
            }
        }
    }
}