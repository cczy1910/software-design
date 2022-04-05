package visitor

import token.BraceToken
import token.NumberToken
import token.OpToken

class PrintVisitor: TokenVisitor {
    override fun visit(token: NumberToken) {
        print(" $token ")
    }

    override fun visit(token: BraceToken) {
        print(" $token ")
    }

    override fun visit(token: OpToken) {
        print(" $token ")
    }
}