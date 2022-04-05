package token

import visitor.TokenVisitor

sealed interface Op

object Plus : Op {
    override fun toString(): String {
        return "PLUS"
    }
}

object Minus : Op {
    override fun toString(): String {
        return "MINUS"
    }
}


object Mul : Op {
    override fun toString(): String {
        return "MUL"
    }
}

object Div : Op {
    override fun toString(): String {
        return "DIV"
    }
}

sealed interface Brace

object LeftBrace : Brace {
    override fun toString(): String {
        return "LEFT"
    }
}

object RightBrace : Brace {
    override fun toString(): String {
        return "RIGHT"
    }
}

sealed interface Token {
    fun accept(visitor: TokenVisitor)
}

data class NumberToken(val value: Int) : Token {
    override fun toString(): String {
        return "NUMBER($value)"
    }

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

data class OpToken(val op: Op): Token {
    override fun toString(): String {
        return op.toString()
    }

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}


data class BraceToken(val br: Brace): Token {
    override fun toString(): String {
        return br.toString()
    }

    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}
