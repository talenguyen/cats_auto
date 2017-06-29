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
  val events: Observable<Point>

  init {
    val quickFight = Observable.interval(2, duration, TimeUnit.SECONDS, Schedulers.single())
        .map { QUICK_FIGHT_BUTTON }
        .doOnNext { print("Quick Fight") }

    val center = Observable.interval(4, duration, TimeUnit.SECONDS, Schedulers.single())
        .map { CENTER }
        .doOnNext { print("Center") }

    val ok = Observable.interval(11, duration, TimeUnit.SECONDS, Schedulers.single())
        .map { OK_BUTTON }
        .doOnNext { print("OK") }

    events = Observable.merge(quickFight, center, ok)
  }

  fun print(message: String) {
    output(message)
  }

  fun start(): Unit {
    disposable = events
        .subscribe { device.tap(it) }
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
