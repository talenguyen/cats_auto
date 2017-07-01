package vn.tale.cats_hack

import java.util.concurrent.TimeUnit

/**
 * Created by Giang Nguyen on 6/19/17.
 */
fun Int.second(): Long {
  return this * 1000L
}

fun Double.second(): Long {
  return (this * 1000L).toLong()
}

fun waitFor(value: Long, timeUnit: TimeUnit = TimeUnit.MILLISECONDS) {
  timeUnit.sleep(value)
}