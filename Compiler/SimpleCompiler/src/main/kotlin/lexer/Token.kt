package lexer

import src.main.kotlin.lexer.TokenType

data class Token(val type: TokenType, val value: String, val position: Pair<Int, Int>? = null) {


    override fun toString(): String {
        return "[type=${type.code}, value='$value', position=$position]"
    }
}