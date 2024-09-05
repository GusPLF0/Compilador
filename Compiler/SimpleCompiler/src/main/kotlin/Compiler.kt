package src.main.kotlin.compiler

import lexer.Lexer
import lexer.SymbolTable
import lexer.Token
import src.main.kotlin.lexer.TokenType
import java.io.BufferedReader
import java.io.File
import java.io.FileReader

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Por favor, informe o arquivo a ser compilado!")
        return
    }

    val source: BufferedReader = try {
        BufferedReader(FileReader(File(args[0])))
    } catch (exception: Exception) {
        exception.printStackTrace()
        return
    }

    println("Início da análise léxica")

    val symbolTable = SymbolTable()
    val lexer = Lexer(source.readText(), symbolTable)

    val tokens = mutableListOf<Token>()
    while (true) {
        val token = lexer.getNextToken()
        if (token.type == TokenType.EOF) break
        tokens.add(token)
    }

    lexer.errors.forEach {
        println(it)
    }

    //println("\nTokens:")
    //for (token in tokens) {
    //    println(token)
    //}

    //println("Tabela de Símbolos:")
    //println(symbolTable)

    println("Fim da análise léxica")
}
