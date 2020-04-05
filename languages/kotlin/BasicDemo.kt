/*https://www.kotlincn.net/docs/reference/basic-syntax.html*/
object class BasicDemo {

    init {
        println("----init----")
    }

    object @JvmStatic
    fun main(args: Array<String>) {
        println("-----main-----")
    }

}