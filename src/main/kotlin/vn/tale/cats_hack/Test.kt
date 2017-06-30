package vn.tale.cats_hack

import io.reactivex.Observable
import java.util.concurrent.TimeUnit

/**
 * Created by Giang Nguyen on 6/30/17.
 */
fun main(args: Array<String>) {
  val first = Observable.interval(0, 200, TimeUnit.MILLISECONDS)
      .map { "First" }
      .take(4)
  val second = Observable.interval(0, 200, TimeUnit.MILLISECONDS)
      .map { "Second" }
      .take(2)
  val third = Observable.interval(0, 200, TimeUnit.MILLISECONDS)
      .map { "Three" }
      .take(3)
  val stream = Observable.concat(first, second, third)

  stream
      .repeat()
      .subscribe { println(it) }
}