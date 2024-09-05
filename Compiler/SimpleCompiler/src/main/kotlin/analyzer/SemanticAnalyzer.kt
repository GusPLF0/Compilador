package src.main.kotlin.analyzer

import lexer.SymbolTable
import src.main.kotlin.parser.*

class SemanticAnalyzer(private val program: Program, private val symbolTable: SymbolTable) {

    // OLHAR A LISTA DE TOKENS, SE FOR NUMERIC_CONSTANT(51) E COLUNA == 1, SALVA EM UMA LISTA
    // COMPARAR QUE A LISTA DEVE SER CRESCENTE!
    private fun verifyLines() {
    }


    // VERIFICAR O GOTO ESTÁ INDO PARA ALGUM DA LISTA GERADA NO MÉTODO ACIMA
    private fun verifyGoto() {

    }
}
