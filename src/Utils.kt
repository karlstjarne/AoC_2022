import java.io.File
import java.math.BigInteger
import java.security.MessageDigest

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "inputs/$name")
    .readLines()

fun printMatrix(matrix: List<List<String>>) {
    println(matrix.map {
        it.toString().replace(",", "")
    }
        .toString()
        .replace("], [", "\n")
        .replace("[[", "")
        .replace("]]", ""))
}
