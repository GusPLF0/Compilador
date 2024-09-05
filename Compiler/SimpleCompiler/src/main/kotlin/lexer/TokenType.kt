package src.main.kotlin.lexer

enum class TokenType(val code: Int) {
    // Palavras reservadas
    REM(61),
    INPUT(62),
    LET(63),
    PRINT(64),
    GOTO(65),
    IF(66),
    END(67),

    // Operadores relacionais
    EQUAL(31),
    NOT_EQUAL(32),
    GREATER(33),
    LESS(34),
    GREATER_EQUAL(35),
    LESS_EQUAL(36),

    // Identificadores e constantes
    IDENTIFIER(41),
    NUMERIC_CONSTANT(51),

    // Delimitadores
    NEWLINE(10),
    END_OF_TEXT(3),

    // Operadores
    ASSIGNMENT(11),
    ADDITION(21),
    SUBTRACTION(22),
    MULTIPLICATION(23),
    DIVISION(24),
    REMAINDER(25),
    UNKNOWN(99),

    // Outros
    EOF(3)
}

