package vn.tale.cats_hack

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.awt.Point
import java.util.concurrent.TimeUnit

/**
 * Created by Đăng Nguyễn on 6/30/17.
 */
class GoHome(val device: Device, val output: (String) -> Unit = {}) {
  private val CLICK_NO_BUTTON = Point(664, 605)
  private val CLICK_CENTER = Point(654, 565)
  val duration: Long = 1500
  var disposable: Disposable? = null

  private var commands: Observable<() -> Unit>

  init {
    val backCommand = Observable.interval(0, duration, TimeUnit.MILLISECONDS, Schedulers.single())
      .doOnNext { print("Back") }
      .map { CLICK_CENTER }
      .map { { device.tap(it) } }

    val backAgain = Observable.interval(250, duration, TimeUnit.MILLISECONDS, Schedulers.single())
      .doOnNext { print("Back 2") }
      .map { { device.back() } }

    val backMore = Observable.interval(500, duration, TimeUnit.MILLISECONDS, Schedulers.single())
      .doOnNext { print("Back 3") }
      .map { { device.back() } }


    val clickNo = Observable.interval(1000, duration, TimeUnit.MILLISECONDS, Schedulers.single())
      .doOnNext { print("Click no") }
      .map { CLICK_NO_BUTTON }
      .map { { device.tap(it) } }

    commands = Observable.merge(backCommand, backAgain, backMore, clickNo)
  }

  fun print(message: String) {
    output(message)
  }

  fun start(): Unit {
    disposable = commands
      .take(4)
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
