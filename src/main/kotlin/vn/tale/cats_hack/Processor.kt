package vn.tale.cats_hack

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by Giang Nguyen on 6/19/17.
 */
class Processor {

  fun exec(cmd: String, vararg arg1: String): Unit {
    val builder = ProcessBuilder(cmd, *arg1)
    builder.redirectErrorStream(true)
    val process = builder.start()
    watch(process)
  }

  private fun watch(process: Process) {
    object : Thread() {
      override fun run() {
        val input = BufferedReader(InputStreamReader(process.inputStream))
        try {
          var line: String? = input.readLine()
          while (line != null) {
            println(line)
            line = input.readLine()
          }
        } catch (e: IOException) {
          e.printStackTrace()
        }

      }
    }.start()
  }
}
