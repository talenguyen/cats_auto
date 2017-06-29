package vn.tale.cats_hack

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.awt.Point
import java.util.concurrent.TimeUnit

/**
 * Created by Giang Nguyen on 6/19/17.
 */
class QuickFight(val device: Device, val output: (String) -> Unit = {}) {
  private val QUICK_FIGHT_BUTTON = Point(1380, 950)
  private val OK_BUTTON = Point(950, 990)
  private val CENTER = Point(950, 450)

  val duration: Long = 15
  var disposable: Disposable? = null
  val commands: Observable<() -> Unit>

  init {
    val quickFightCommand = Observable.interval(2, duration, TimeUnit.SECONDS, Schedulers.single())
        .doOnNext { print("Quick Fight") }
        .map { QUICK_FIGHT_BUTTON }
        .map { { device.tap(it) } }

    val centerCommand = Observable.interval(4, duration, TimeUnit.SECONDS, Schedulers.single())
        .doOnNext { print("Center") }
        .map { CENTER }
        .map { { device.tap(it) } }

    val okCommand = Observable.interval(11, duration, TimeUnit.SECONDS, Schedulers.single())
        .doOnNext { print("OK") }
        .map { { device.back() } }

    commands = Observable.merge(quickFightCommand, centerCommand, okCommand)
  }

  fun print(message: String) {
    output(message)
  }

  fun start(): Unit {
    disposable = commands.subscribe { it() }
  }

  fun stop(): Unit {
    disposable?.dispose()
  }

  fun isRunning(): Boolean {
    if (disposable == null) {
      return false
    }
    return disposable?.isDisposed?.not() ?: false
  }

}
