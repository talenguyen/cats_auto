package vn.tale.cats_hack

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import sun.util.locale.provider.TimeZoneNameUtility
import java.awt.Point
import java.util.concurrent.TimeUnit

/**
 * Created by Giang Nguyen on 6/30/17.
 */
class FullAuto(val device: Device, private val log: (String) -> Unit = {}) {
  private val G_BUTTON = Point(1054, 875)

  val stream: Observable<String>

  private val quickFight: Observable<() -> Unit>

  init {
    val first = interval(1)
        .map { "First $it" }
        .take(4)
    val second = interval(1)
        .map { "Second $it" }
        .take(2)
    val third = interval(1)
        .map { "Three $it" }
        .take(3)
    stream = Observable.concat(first, second, third)

    quickFight = interval(1)
        .doOnNext { print("Supper G-Button $it") }
        .map { G_BUTTON }
        .map { { device.tap(it) } }
        .take(1, TimeUnit.MINUTES, Schedulers.single())


  }



  private fun interval(period: Long) = Observable.interval(
      0,
      period,
      TimeUnit.SECONDS,
      Schedulers.single())

  fun start() {
    interval(7)
        .doOnNext { log("START") }
        .flatMap { stream }
        .subscribe { log(it) }
  }
}