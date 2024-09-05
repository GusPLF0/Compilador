package lexer

import src.main.kotlin.lexer.TokenType

class Lexer(private val input: String, private val symbolTable: SymbolTable) {
    private var position = 0
    private val length = input.length
    private var line = 1
    private var column = 1
    private var lexeme: StringBuilder = StringBuilder()
    val errors = mutableListOf<String>()
    fun getNextToken(): Token {
        lexeme.clear()
        while (position < length) {
            when (val currentChar = input[position]) {
                '\r' -> {
                    if (peekNextChar() == '\n') {
                        advanceJustPosition()
                    }
                    return createNewLineToken()
                }

                '\n' -> return createNewLineToken()

                ' ' -> advance()
                in '0'..'9' -> return processInteger(currentChar)
                in 'a'..'z', in 'A'..'Z' -> return processIdentifierOrKeyword(currentChar)
                '=' -> return processAssignmentOrEquality()
                '<' -> return processLessThan()
                '>' -> return processGreaterThan()
                '!' -> return processNotEqual()
                '+', '-', '*', '/', '%' -> return processOperator(currentChar)
                '\u0003' -> {
                    advanceJustPosition()
                    return Token(TokenType.END_OF_TEXT, "", Pair(line, column))
                }

                else -> {
                    addError("Unexpected character: '$currentChar' at line $line, column $column")
                    advance()
                }
            }
        }
        return Token(TokenType.EOF, "", Pair(line, column))
    }

    private fun advanceJustPosition() {
        position++
    }

    private fun advance() {
        position++
        column++
    }

    private fun advanceLine() {
        position++
        line++
        column = 1
    }

    private fun peekNextChar(): Char? {
        return if (position + 1 < length) input[position + 1] else null
    }

    private fun processInteger(currentChar: Char): Token {
        lexeme.append(currentChar)
        advance()
        while (position < length && input[position].isDigit()) {
            lexeme.append(input[position])
            advance()
        }
        val value = lexeme.toString()
        val symbolIndex = symbolTable.addSymbol(value)
        return Token(TokenType.NUMERIC_CONSTANT, symbolIndex.toString(), Pair(line, column - lexeme.length))
    }

    private fun processIdentifierOrKeyword(currentChar: Char): Token {
        if (currentChar.isUpperCase()) {
            val position = column - lexeme.length
            addError("Token não reconhecido: '$currentChar' ($line, $position)")

            advance()
            return Token(TokenType.UNKNOWN, currentChar.toString(), Pair(line, position))
        }
        lexeme.append(currentChar)
        advance()
        while (position < length && input[position].isLetter()) {
            lexeme.append(input[position])
            advance()
        }
        return when (val value = lexeme.toString()) {
            "rem" -> processComment()
            "input" -> Token(TokenType.INPUT, "", Pair(line, column - lexeme.length))
            "let" -> Token(TokenType.LET, "", Pair(line, column - lexeme.length))
            "print" -> Token(TokenType.PRINT, "", Pair(line, column - lexeme.length))
            "goto" -> Token(TokenType.GOTO, "", Pair(line, column - lexeme.length))
            "if" -> Token(TokenType.IF, "", Pair(line, column - lexeme.length))
            "end" -> Token(TokenType.END, "", Pair(line, column - lexeme.length))
            else -> {
                if (value.length == 1 && value[0].isLetter() && value[0].lowercaseChar() in 'a'..'z' || value[0] == '_') {
                    val symbolIndex = symbolTable.addSymbol(value)
                    return Token(TokenType.IDENTIFIER, symbolIndex.toString(), Pair(line, column - lexeme.length))
                } else {
                    val position = column - lexeme.length
                    addError("Token não reconhecido: '$value' ($line, $position)")
                    Token(TokenType.UNKNOWN, value, Pair(line, position))
                }

            }
        }
    }

    private fun processComment(): Token {
        val startColumn = column
        while (position < length && input[position] != '\n') {
            advance()
        }
        return Token(TokenType.REM, "", Pair(line, startColumn - 3))
    }

    private fun processAssignmentOrEquality(): Token {
        advance()
        return if (peekNextChar() == '=') {
            advance()
            Token(TokenType.EQUAL, "", Pair(line, column - 1))
        } else {
            Token(TokenType.ASSIGNMENT, "", Pair(line, column - 1))
        }
    }

    private fun processLessThan(): Token {
        advance()
        return if (peekNextChar() == '=') {
            advance()
            Token(TokenType.LESS_EQUAL, "", Pair(line, column - 1))
        } else {
            Token(TokenType.LESS, "", Pair(line, column - 1))
        }
    }

    private fun processGreaterThan(): Token {
        advance()
        return if (peekNextChar() == '=') {
            advance()
            Token(TokenType.GREATER_EQUAL, "", Pair(line, column - 1))
        } else {
            Token(TokenType.GREATER, "", Pair(line, column - 1))
        }
    }

    private fun processNotEqual(): Token {
        advance()
        return if (peekNextChar() == '=') {
            advance()
            Token(TokenType.NOT_EQUAL, "!=", Pair(line, column - 1))
        } else {
            addError("Unexpected character: '!' without '=' at line $line, column $column")
            advance()
            Token(TokenType.UNKNOWN, "!", Pair(line, column - 1))
        }
    }

    private fun processOperator(currentChar: Char): Token {
        advance()
        return when (currentChar) {
            '+' -> Token(TokenType.ADDITION, currentChar.toString(), Pair(line, column - 1))
            '-' -> Token(TokenType.SUBTRACTION, currentChar.toString(), Pair(line, column - 1))
            '*' -> Token(TokenType.MULTIPLICATION, currentChar.toString(), Pair(line, column - 1))
            '/' -> Token(TokenType.DIVISION, currentChar.toString(), Pair(line, column - 1))
            '%' -> Token(TokenType.REMAINDER, currentChar.toString(), Pair(line, column - 1))
            else -> {
                addError("Unexpected operator: '$currentChar' at line $line, column $column")
                Token(TokenType.UNKNOWN, currentChar.toString(), Pair(line, column - 1))
            }
        }
    }

    private fun addError(message: String) {
        errors.add(message)
    }

    private fun createNewLineToken(): Token {
        val token = Token(TokenType.NEWLINE, "", Pair(line, column))
        advanceLine()
        return token
    }
}
