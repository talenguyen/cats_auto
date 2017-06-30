package vn.tale.cats_hack

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.awt.Point
import java.util.concurrent.TimeUnit

/**
 * Created by Giang Nguyen on 6/19/17.
 */
class ViewVideo(val device: Device, val log: (String) -> Unit = {}) {
  private val SKIP_BUTTON = Point(1200, 980)
  private val COLLECT_PRICE_BUTTON = Point(955, 800)
  private val UNLOCK_BUTTON = Point(950, 920)

  private val commands: Observable<() -> Unit>
  private val duration: Long = 37
  private var disposable: Disposable? = null

  init {
    val skipCommand = Observable.interval(2, duration, TimeUnit.SECONDS, Schedulers.single())
        .doOnNext { log("Skip") }
        .map { SKIP_BUTTON }
        .map { { device.tap(it) } }

    val closeCommand = Observable.interval(35, duration, TimeUnit.SECONDS, Schedulers.single())
        .doOnNext { log("CLOSE") }
        .map { { device.back() } }

    val okCommand = Observable.interval(37, duration, TimeUnit.SECONDS, Schedulers.single())
        .doOnNext { log("OK") }
        .map { { device.back() } }

    val viewVideoLoopCommand = Observable.merge(skipCommand, closeCommand, okCommand)
        .take(4)

    val tapCollectPricesCommand = Observable.interval(
        0,
        1,
        TimeUnit.SECONDS,
        Schedulers.single())
        .doOnNext { log("Collect Prices $it") }
        .map { COLLECT_PRICE_BUTTON }
        .map { { device.tap(it) } }
        .take(6)


    val tapUnlockCommand = Observable.just(UNLOCK_BUTTON)
        .doOnNext { log("Unlock") }
        .map { { device.tap(it) } }

    commands = Observable.concat(tapUnlockCommand, viewVideoLoopCommand, tapCollectPricesCommand)
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
