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
  private val SKIP_OPEN_BUTTON = Point(673, 650)
  private val OK_BUTTON = Point(627, 600)
  private val BOX_BUTTON = Point(229, 245)

  private val commands: Observable<() -> Unit>
  private val duration: Long = 38
  private var disposable: Disposable? = null

  init {
    val boxCommand = Observable.interval(0, duration, TimeUnit.SECONDS, Schedulers.single())
            .doOnNext { print("Click to box") }
            .map { BOX_BUTTON }
            .map { { device.tap(it) } }

    val openCommand = Observable.interval(2, duration, TimeUnit.SECONDS, Schedulers.single())
            .doOnNext { print("Open box") }
            .map { SKIP_OPEN_BUTTON }
            .map { { device.tap(it) } }

    val skipCommand = Observable.interval(4, duration, TimeUnit.SECONDS, Schedulers.single())
        .doOnNext { print("Skip") }
        .map { SKIP_OPEN_BUTTON }
        .map { { device.tap(it) } }

    val closeCommand = Observable.interval(35, duration, TimeUnit.SECONDS, Schedulers.single())
        .doOnNext { print("Close") }
        .map { { device.back() } }

    val closeAgainCommand = Observable.interval(36, duration, TimeUnit.SECONDS, Schedulers.single())
            .doOnNext { print("Close 2") }
            .map { { device.back() } }

    val closeMoreCommand = Observable.interval(37, duration, TimeUnit.SECONDS, Schedulers.single())
            .doOnNext { print("Close 3") }
            .map { { device.back() } }

    commands = Observable.merge(listOf(boxCommand, openCommand, skipCommand, closeCommand, closeAgainCommand, closeMoreCommand))
  }

  fun print(message: String) {
    output(message)
  }

  fun start(): Unit {
    disposable = commands
            .take(6)
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
