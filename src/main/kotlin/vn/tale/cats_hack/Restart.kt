package vn.tale.cats_hack

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.awt.Point
import java.util.concurrent.TimeUnit

/**
 * Created by Đăng Nguyễn on 6/30/17.
 */
class Restart(val device: Device, val output: (String) -> Unit = {}) {
  val duration: Long = 10
  var disposable: Disposable? = null

  private var commands: Observable<() -> Unit>

  init {
    val restartCommand = Observable.interval(0, duration, TimeUnit.MILLISECONDS, Schedulers.single())
      .doOnNext { print("Restart") }
      .map { { device.restart() } }

    commands = restartCommand
  }

  fun print(message: String) {
    output(message)
  }

  fun start(): Unit {
    disposable = commands
      .take(1)
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
