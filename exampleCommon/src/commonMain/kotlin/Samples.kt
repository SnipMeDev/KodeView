import dev.snipme.highlights.model.SyntaxLanguage

class Samples {
    companion object {
        val c = """
        #include <stdio.h>
        
        int main() {
            int abcd = 100;
            printf("Value: %d\n", abcd);
            return 0;
        }
        """.trimIndent()

        val cpp = """
        #include <iostream>
        using namespace std;
        
        int main() {
            int abcd = 100;
            cout << "Value: " << abcd << endl;
            return 0;
        }
        """.trimIndent()

        val dart = """
        void main() {
          int abcd = 100;
          print("Value: ${'$'}abcd");
        }
        """.trimIndent()

        val java = """
        class Main {
            public static void main(String[] args) {
                int abcd = 100;
                System.out.println("Value: " + abcd);
            }
        }
        """.trimIndent()

        val javaStarter = """
        class Main {
            public static void main(String[] args) {
                int abcd = 100;
                System.out.println("Value: " + abcd);
            }
        }
        """.trimIndent()

        val kotlin = """
        fun main() {
            val abcd = 100
            println("Value: ${'$'}abcd")
        }
        """.trimIndent()

        val rust = """
        fn main() {
            let abcd: i32 = 100;
            println!("Value: {}", abcd);
        }
        """.trimIndent()

        val csharp = """
        using System;
        
        class Program {
            static void Main() {
                int abcd = 100;
                Console.WriteLine("Value: " + abcd);
            }
        }
        """.trimIndent()

        val coffeescript = """
        abcd = 100
        console.log "Value: #{abcd}"
        """.trimIndent()

        val javascript = """
        let abcd = 100;
        console.log("Value: " + abcd);
        """.trimIndent()

        val perl = """
        my \${'$'}abcd = 100;
        print "Value: ${'$'}abcd\n";
        """.trimIndent()

        val python = """
        abcd = 100
        print("Value:", abcd)
        """.trimIndent()

        val ruby = """
        abcd = 100
        puts "Value: #{abcd}"
        """.trimIndent()

        val shell = """
        #!/bin/bash
        abcd=100
        echo "Value: ${'$'}abcd"
        """.trimIndent()

        val swift = """
        import Foundation

        let abcd = 100
        print("Value: \(abcd)")
        """.trimIndent()

        val typescript = """
        let abcd: number = 100;
        console.log("Value: " + abcd);
        """.trimIndent()

        val go = """
        package main
        import "fmt"

        func main() {
            abcd := 100
            fmt.Println("Value:", abcd)
        }
        """.trimIndent()

        val php = """
        <?php
        ${'$'}abcd = 100;
        echo "Value: ${'$'}abcd\n";
        ?>
        """.trimIndent()

        fun getSampleCode(language: SyntaxLanguage): String = when (language) {
                    SyntaxLanguage.DEFAULT -> kotlin
                    SyntaxLanguage.C -> c
                    SyntaxLanguage.CPP -> cpp
                    SyntaxLanguage.DART -> dart
                    SyntaxLanguage.JAVA -> java
                    SyntaxLanguage.KOTLIN -> kotlin
                    SyntaxLanguage.RUST -> rust
                    SyntaxLanguage.CSHARP -> csharp
                    SyntaxLanguage.COFFEESCRIPT -> coffeescript
                    SyntaxLanguage.JAVASCRIPT -> javascript
                    SyntaxLanguage.PERL -> perl
                    SyntaxLanguage.PYTHON -> python
                    SyntaxLanguage.RUBY -> ruby
                    SyntaxLanguage.SHELL -> shell
                    SyntaxLanguage.SWIFT -> swift
                    SyntaxLanguage.TYPESCRIPT -> typescript
                    SyntaxLanguage.GO -> go
                    SyntaxLanguage.PHP -> php
            }
    }
}
