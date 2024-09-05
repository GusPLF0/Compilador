package src.main.kotlin.lexer

import lexer.Token

class LexerTest {

    fun testLexer() {
        val input = """
            1000 rem Encontrar o maior de dois inteiros
            1100 input x
            1200 input y
            1300 if x < y goto 2000
            1400 print x
            1500 goto 3000
            2000 print y
            3000 end""".trimIndent() + '\u0003'

        val expectedTokens = listOf(
            Token(TokenType.NUMERIC_CONSTANT, "1000", Pair(1, 1)),
            Token(TokenType.REM, "", Pair(1, 6)),
            Token(TokenType.NEWLINE, "", Pair(1, 44)),
            Token(TokenType.NUMERIC_CONSTANT, "1100", Pair(2, 1)),
            Token(TokenType.INPUT, "input", Pair(2, 6)),
            Token(TokenType.IDENTIFIER, "x", Pair(2, 12)),
            Token(TokenType.NEWLINE, "", Pair(2, 13)),
            Token(TokenType.NUMERIC_CONSTANT, "1200", Pair(3, 1)),
            Token(TokenType.INPUT, "input", Pair(3, 6)),
            Token(TokenType.IDENTIFIER, "y", Pair(3, 12)),
            Token(TokenType.NEWLINE, "", Pair(3, 13)),
            Token(TokenType.NUMERIC_CONSTANT, "1300", Pair(4, 1)),
            Token(TokenType.IF, "if", Pair(4, 6)),
            Token(TokenType.IDENTIFIER, "x", Pair(4, 9)),
            Token(TokenType.LESS, "<", Pair(4, 11)),
            Token(TokenType.IDENTIFIER, "y", Pair(4, 13)),
            Token(TokenType.GOTO, "goto", Pair(4, 15)),
            Token(TokenType.NUMERIC_CONSTANT, "2000", Pair(4, 20)),
            Token(TokenType.NEWLINE, "", Pair(4, 24)),
            Token(TokenType.NUMERIC_CONSTANT, "1400", Pair(5, 1)),
            Token(TokenType.PRINT, "print", Pair(5, 6)),
            Token(TokenType.IDENTIFIER, "x", Pair(5, 12)),
            Token(TokenType.NEWLINE, "", Pair(5, 13)),
            Token(TokenType.NUMERIC_CONSTANT, "1500", Pair(6, 1)),
            Token(TokenType.GOTO, "goto", Pair(6, 6)),
            Token(TokenType.NUMERIC_CONSTANT, "3000", Pair(6, 11)),
            Token(TokenType.NEWLINE, "", Pair(6, 15)),
            Token(TokenType.NUMERIC_CONSTANT, "2000", Pair(7, 1)),
            Token(TokenType.PRINT, "print", Pair(7, 6)),
            Token(TokenType.IDENTIFIER, "y", Pair(7, 12)),
            Token(TokenType.NEWLINE, "", Pair(7, 13)),
            Token(TokenType.NUMERIC_CONSTANT, "3000", Pair(8, 1)),
            Token(TokenType.END, "end", Pair(8, 6)),
            Token(TokenType.END_OF_TEXT, "", Pair(8, 9))
        )

    }
}
