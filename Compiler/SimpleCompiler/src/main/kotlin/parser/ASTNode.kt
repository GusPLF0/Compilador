package src.main.kotlin.parser

sealed class ASTNode

data class Program(val statements: List<Statement>) : ASTNode()

sealed class Statement : ASTNode()

data class RemStatement(val comment: String) : Statement()
data class InputStatement(val variable: String) : Statement()
data class LetStatement(val variable: String, val expression: Expression) : Statement()
data class PrintStatement(val variable: String) : Statement()
data class GotoStatement(val lineNumber: Int) : Statement()
data class IfGotoStatement(val condition: Condition, val lineNumber: Int) : Statement()
object EndStatement : Statement()

sealed class Expression : ASTNode()

data class Variable(val name: String) : Expression()
data class IntegerLiteral(val value: Int) : Expression()
data class BinaryOperation(val left: Expression, val operator: String, val right: Expression) : Expression()

data class Condition(val left: Expression, val operator: String, val right: Expression)
