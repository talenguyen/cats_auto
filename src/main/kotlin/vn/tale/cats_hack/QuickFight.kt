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
  private val G_BUTTON = Point(1054, 875)

  val duration: Long = 1
  var disposable: Disposable? = null
  val commands: Observable<() -> Unit>

  init {
    commands = Observable.interval(0, duration, TimeUnit.SECONDS, Schedulers.single())
        .doOnNext { print("Supper G-Button $it") }
        .map { G_BUTTON }
        .map { { device.tap(it) } }
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
