package token

import java.io.InputStream

class Tokenizer(private val input: InputStream) {
    private val tokens: MutableList<Token> = arrayListOf()
    private var curPos: Int = 0
    private var curChar: Int = 0

    init {
        getChar()
    }

    private fun getChar() {
        curChar = input.read()
        curPos++
    }

    private fun skipWhitespace() {
        while (curChar.toChar().isWhitespace()) {
            getChar()
        }
    }

    private fun getNumber(): Int {
        val sb = StringBuilder()

        while (curChar.toChar().isDigit()) {
            sb.append(curChar.toChar())
            getChar()
        }

        return sb.toString().toInt()
    }

    fun getTokens(): List<Token> {
        while (true) {
            skipWhitespace()

            if (curChar == -1) {
                break
            }

            val token = when (curChar.toChar()) {
                '+' -> OpToken(Plus)
                '-' -> OpToken(Minus)
                '*' -> OpToken(Mul)
                '/' -> OpToken(Div)
                '(' -> BraceToken(LeftBrace)
                ')' -> BraceToken(RightBrace)
                else -> {
                    if (curChar.toChar().isDigit()) {
                        val t = NumberToken(getNumber())
                        tokens.add(t)
                        continue
                    } else {
                        throw IllegalStateException("Illegal character ${curChar.toChar()} at position $curPos")
                    }
                }
            }

            tokens.add(token)
            getChar()
        }

        return tokens
    }
}
