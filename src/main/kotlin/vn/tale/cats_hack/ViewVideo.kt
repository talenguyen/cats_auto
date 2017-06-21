package vn.tale.cats_hack

import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.awt.Point
import java.util.concurrent.TimeUnit

/**
 * Created by Giang Nguyen on 6/19/17.
 */
class ViewVideo(val device: Device) {
  enum class CloseButton(val point: Point) {
    ONE(Point(1700, 90)),
    TWO(Point(1890, 45))
  }

  private val SKIP_BUTTON = Point(1200, 980)
  private val OK_BUTTON = Point(930, 900)
  private val CLOSE_BUTTON = Point(1870, 45)
  private val CLOSE2_BUTTON = Point(1700, 90)

  private val events: Observable<Point>
  private val duration: Long = 39
  private var disposable: Disposable? = null
  private var closeButton: CloseButton = CloseButton.ONE

  init {
    val skip = Observable.interval(2, duration, TimeUnit.SECONDS, Schedulers.single())
        .map { SKIP_BUTTON }
        .doOnNext { println("Skip") }

    val close = Observable.interval(33, duration, TimeUnit.SECONDS, Schedulers.single())
        .map { closeButton }
        .doOnNext { println(it) }
        .map { closeButton.point }
        .doOnNext { println("Close") }

    val ok = Observable.interval(36, duration, TimeUnit.SECONDS, Schedulers.single())
        .map { OK_BUTTON }
        .doOnNext { println("OK") }

    events = Observable.merge(skip, close, ok)
  }

  fun changeClosePosition(closeButton: CloseButton) {
    this.closeButton = closeButton
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
