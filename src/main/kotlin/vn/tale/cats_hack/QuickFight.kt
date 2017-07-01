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
  private val QUICK_FIGHT_BUTTON = Point(709, 600)
  private val COLLECT_PRIZES_BUTTON = Point(709, 560)

  val duration: Long = 500
  var disposable: Disposable? = null

  private var commands: Observable<() -> Unit>

  init {
    val quickFightCommand = Observable.interval(0, duration, TimeUnit.MILLISECONDS, Schedulers.single())
      .doOnNext { print("Quick Fight") }
      .map { QUICK_FIGHT_BUTTON }
      .map { { device.tap(it) } }

    val collectPrizeCommand = Observable.interval(250, duration, TimeUnit.MILLISECONDS, Schedulers.single())
      .doOnNext { print("Quick Fight") }
      .map { COLLECT_PRIZES_BUTTON }
      .map { { device.tap(it) } }

    commands = Observable.merge(quickFightCommand, collectPrizeCommand)
  }

  fun print(message: String) {
    output(message)
  }

  fun start(): Unit {
    disposable = commands
      .subscribe { it() }
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
