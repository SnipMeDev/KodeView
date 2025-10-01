class Samples {
    companion object {
        val javaStarter =
            """
    class Main {
        public static void main(String[] args) {
            int abcd = 100;
        }
    }
    """.trimIndent()

        val kotlin = """
        package dev.example

        import kotlin.random.Random

        // Class declaration with primary constructor
        class SampleClass(private val name: String, var age: Int) {

            // Companion object
            companion object {
                const val CONSTANT = "This is a constant"
                fun staticMethod() = println("This is a static method")
            }

            // Properties
            val isAdult: Boolean
                get() = age >= 18

            // Secondary constructor
            constructor(name: String) : this(name, 0)

            // Enum class
            enum class Status {
                ACTIVE, INACTIVE, PENDING
            }

            // Nested class
            class Nested {
                fun nestedMethod() = "Nested class method"
            }

            // Inner class
            inner class Inner {
                fun innerMethod() = "Inner class method, name: ${'$'}name"
            }

            // Data class
            data class Data(val id: Int, val value: String)

            // Sealed class
            sealed class Result {
                data class Success(val data: String) : Result()
                data class Failure(val error: String) : Result()
            }

            // Object declaration
            object Singleton {
                fun doSomething() = "Singleton object method"
            }

            // Function with default arguments
            fun greet(greeting: String = "Hello") {
                println('${'$'}greeting, ${'$'}name!')
            }

            // Inline function
            inline fun inlineFunction(action: () -> Unit) {
                action()
            }

            // Higher-order function
            fun higherOrderFunction(operation: (Int, Int) -> Int): Int {
                return operation(5, 10)
            }

            // Lambda expression
            val lambda: (Int, Int) -> Int = { a, b -> a + b }

            // Extension function
            fun String.addExclamation(): String = "${'$'}this!"

            // Operator overloading
            operator fun plus(other: SampleClass): SampleClass {
                return SampleClass(this.name + other.name, this.age + other.age)
            }

            // Inline class
            @JvmInline
            value class InlineClass(val value: String)

            // Try-catch-finally
            fun riskyOperation() {
                try {
                    val result = 10 / Random.nextInt(0, 2)
                    println("Result: ${'$'}result")
                } catch (e: ArithmeticException) {
                    println("Caught exception: ${'$'}{e.message}")
                } finally {
                    println("Finally block executed")
                }
            }

            // When expression
            fun checkStatus(status: Status): String {
                return when (status) {
                    Status.ACTIVE -> "Active"
                    Status.INACTIVE -> "Inactive"
                    Status.PENDING -> "Pending"
                }
            }

            // Loops
            fun printNumbers() {
                for (i in 1..5) {
                    println(i)
                }
                var count = 0
                while (count < 3) {
                    println("Count: ${'$'}count")
                    count++
                }
            }

            // Annotations
            @Deprecated("This method is deprecated")
            fun deprecatedMethod() {
                println("Deprecated method")
            }

            // Generics
            fun <T> genericMethod(item: T): T {
                return item
            }

            // Vararg
            fun printAll(vararg items: String) {
                items.forEach { println(it) }
            }
        }
    """.trimIndent()
    }
}