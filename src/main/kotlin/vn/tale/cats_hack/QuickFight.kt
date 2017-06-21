package vn.tale.cats_hack

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.awt.Point
import java.util.concurrent.TimeUnit

/**
 * Created by Giang Nguyen on 6/19/17.
 */
class QuickFight(val device: Device) {
  private val QUICK_FIGHT_BUTTON = Point(1380, 950)
  private val OK_BUTTON = Point(950, 990)
  private val CENTER = Point(950, 450)

  val duration: Long = 19
  var disposable: Disposable? = null
  val events: Observable<Point>

  init {
    val quickFight = Observable.interval(1, duration, TimeUnit.SECONDS, Schedulers.single())
        .map { QUICK_FIGHT_BUTTON }
        .doOnNext { println("Quick Fight") }

    val center = Observable.interval(4, duration, TimeUnit.SECONDS, Schedulers.single())
        .map { CENTER }
        .doOnNext { println("Center") }

    val ok = Observable.interval(11, duration, TimeUnit.SECONDS, Schedulers.single())
        .map { OK_BUTTON }
        .doOnNext { println("OK") }

    events = Observable.merge(quickFight, center, ok)
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
