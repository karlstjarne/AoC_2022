package ks.aoc

import java.io.File

object FileUtil {

    @JvmStatic
    fun loadResourceFile(classLoader: ClassLoader?, fileName: String): List<String> {
        return File(classLoader?.getResource(fileName)?.path).readLines()
    }
}