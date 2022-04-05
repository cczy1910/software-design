package visitor

import token.BraceToken
import token.NumberToken
import token.OpToken

interface TokenVisitor {
    fun visit(token: NumberToken)

    fun visit(token: BraceToken)

    fun visit(token: OpToken)
}