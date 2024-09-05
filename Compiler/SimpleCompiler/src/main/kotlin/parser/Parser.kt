package src.main.kotlin.parser

import lexer.Lexer
import lexer.Token
import src.main.kotlin.lexer.TokenType

class Parser(private val lexer: Lexer) {
    private var currentToken: Token = lexer.getNextToken()

    fun parseProgram(): Program {
        val statements = mutableListOf<Statement>()
        while (currentToken.type != TokenType.EOF) {
            statements.add(parseStatement())
        }
        return Program(statements)
    }

    private fun parseStatement(): Statement {
        val lineNumber = expect(TokenType.NUMERIC_CONSTANT).value.toInt()
        return when (currentToken.type) {
            TokenType.REM -> RemStatement(parseRemStatement())
            TokenType.INPUT -> InputStatement(parseIdentifier())
            TokenType.LET -> LetStatement(parseIdentifier(), parseExpression())
            TokenType.PRINT -> PrintStatement(parseIdentifier())
            TokenType.GOTO -> GotoStatement(parseLineNumber())
            TokenType.IF -> parseIfGotoStatement()
            TokenType.END -> EndStatement.also { expect(TokenType.END) }
            else -> throw RuntimeException("Unexpected token: ${currentToken.value}")
        }
    }

    private fun parseRemStatement(): String {
        expect(TokenType.REM)
        return expect(TokenType.REM).value
    }

    private fun parseIdentifier(): String {
        return expect(TokenType.IDENTIFIER).value
    }

    private fun parseExpression(): Expression {
        val left = parseTerm()
        return if (currentToken.type in listOf(
                TokenType.ADDITION,
                TokenType.SUBTRACTION,
                TokenType.MULTIPLICATION,
                TokenType.DIVISION,
                TokenType.REMAINDER
            )) {
            val operator = currentToken.value
            currentToken = lexer.getNextToken()
            val right = parseTerm()
            BinaryOperation(left, operator, right)
        } else {
            left
        }
    }

    private fun parseTerm(): Expression {
        return when (currentToken.type) {
            TokenType.NUMERIC_CONSTANT -> IntegerLiteral(expect(TokenType.NUMERIC_CONSTANT).value.toInt())
            TokenType.IDENTIFIER -> Variable(expect(TokenType.IDENTIFIER).value)
            else -> throw RuntimeException("Unexpected token: ${currentToken.value}")
        }
    }

    private fun parseLineNumber(): Int {
        return expect(TokenType.NUMERIC_CONSTANT).value.toInt()
    }

    private fun parseIfGotoStatement(): IfGotoStatement {
        expect(TokenType.IF)  // consume 'if'
        val condition = parseCondition()
        expect(TokenType.GOTO)  // consume 'goto'
        val lineNumber = parseLineNumber()
        return IfGotoStatement(condition, lineNumber)
    }

    private fun parseCondition(): Condition {
        val left = parseTerm()
        val operator = parseOperator()
        val right = parseTerm()
        return Condition(left, operator, right)
    }

    private fun parseOperator(): String {
        return when (currentToken.type) {
            TokenType.EQUAL,
            TokenType.NOT_EQUAL,
            TokenType.GREATER,
            TokenType.LESS,
            TokenType.GREATER_EQUAL,
            TokenType.LESS_EQUAL -> {
                val operator = currentToken.value
                currentToken = lexer.getNextToken()
                operator
            }
            else -> throw RuntimeException("Unexpected token: ${currentToken.value}")
        }
    }

    private fun expect(expectedType: TokenType): Token {
        val token = currentToken
        if (token.type == expectedType) {
            currentToken = lexer.getNextToken()
            return token
        } else {
            throw RuntimeException("Expected token of type ${expectedType.code} but found ${token.type.code}")
        }
    }
}
