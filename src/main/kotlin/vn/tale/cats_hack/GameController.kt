package vn.tale.cats_hack

import java.awt.Point

/**
 * Created by Giang Nguyen on 7/1/17.
 */
class GameController(private val device: Device) {

  companion object Button {
    val QUICK_FIGHT = Point()
    val END_FIGHT_OK = Point()
    val BOX_1 = Point()
    val UNLOCK = Point()
    val SKIP = Point()
    val COLLECT_PRICES = Point()
    val CONFIRM_NO = Point()
  }

  fun goHome() {
    repeat(5, {
      device.back()
      waitFor(1.second())
    })
    device.tap(CONFIRM_NO)
    waitFor(1.second())
  }

  fun runAFightLoop() {
    device.tap(QUICK_FIGHT)
    waitFor(0.5.second())
  }

  fun stopTheFighting() {
    repeat(10, { device.tap(END_FIGHT_OK) })
  }

  fun viewAVideo() {
    device.tap(BOX_1)
    waitFor(1.second())
    device.tap(UNLOCK)
    waitFor(1.second())
    device.tap(SKIP)
    waitFor(40.second())
    repeat(10, {
      device.tap(COLLECT_PRICES)
      waitFor(0.5.second())
    })
  }
}