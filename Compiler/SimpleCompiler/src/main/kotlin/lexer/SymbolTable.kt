package lexer

class SymbolTable {
    private val symbols = mutableMapOf<String, Int>()
    private var nextIndex = 0

    fun addSymbol(symbol: String): Int {
        return symbols.computeIfAbsent(symbol) {
            nextIndex++
            nextIndex - 1
        }
    }

    fun getSymbol(symbol: String): Int? {
        return symbols[symbol]
    }

    override fun toString(): String {
        return symbols.entries.joinToString("\n") { "${it.value} : ${it.key}" }
    }
}
