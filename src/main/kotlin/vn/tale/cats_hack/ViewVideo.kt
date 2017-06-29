package vn.tale.cats_hack

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.awt.Point
import java.util.concurrent.TimeUnit

/**
 * Created by Giang Nguyen on 6/19/17.
 */
class ViewVideo(val device: Device, val output: (String) -> Unit = {}) {
  private val SKIP_BUTTON = Point(1200, 980)
  private val OK_BUTTON = Point(930, 900)

  private val commands: Observable<() -> Unit>
  private val duration: Long = 37
  private var disposable: Disposable? = null

  init {
    val skipCommand = Observable.interval(2, duration, TimeUnit.SECONDS, Schedulers.single())
        .doOnNext { print("Skip") }
        .map { SKIP_BUTTON }
        .map { { device.tap(it) } }

    val closeCommand = Observable.interval(35, duration, TimeUnit.SECONDS, Schedulers.single())
        .doOnNext { print("CLOSE") }
        .map { { device.back() } }

    val okCommand = Observable.interval(37, duration, TimeUnit.SECONDS, Schedulers.single())
        .doOnNext { print("OK") }
        .map { OK_BUTTON }
        .map { { device.tap(it) } }

    commands = Observable.merge(skipCommand, closeCommand, okCommand)
  }

  fun print(message: String) {
    output(message)
  }

  fun start(): Unit {
    disposable = commands
        .subscribe { it.invoke() }
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
