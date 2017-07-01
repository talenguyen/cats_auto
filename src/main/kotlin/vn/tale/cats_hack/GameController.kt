package vn.tale.cats_hack

import java.awt.Point

/**
 * Created by Giang Nguyen on 7/1/17.
 */
class GameController(private val device: Device, val log: (String) -> Unit = {}) {

  companion object Button {
    val QUICK_FIGHT = Point(1054, 875)
    val END_FIGHT_OK = Point(988, 875)
    val BOX_1 = Point(336, 374)
    val BOX_2 = Point(590, 374)
    val UNLOCK = Point(942, 972)
    val SKIP = Point(1074, 976)
    val COLLECT_PRICES = Point(1062, 866)
    val CONFIRM_NO = Point(1006, 942)
  }

  fun goHome() {
    repeat(5, {
      log("BACK")
      device.back()
      waitFor(1.second())
    })
    log("Confirm No")
    device.tap(CONFIRM_NO)
    waitFor(1.second())
  }

  fun runAFightLoop() {
    log("Quick Fight")
    device.tap(QUICK_FIGHT)
    waitFor(1.second())
  }

  fun stopTheFighting() {
    repeat(10, {
      log("OK")
      device.tap(END_FIGHT_OK)
      waitFor(1.second())
    })
  }

  fun viewAVideo() {
    log("Box 1")
    device.tap(BOX_2)
    waitFor(1.second())
    log("Unlock")
    device.tap(UNLOCK)
    waitFor(1.second())
    log("Skip")
    device.tap(SKIP)
    waitFor(40.second())
    goHome()
    repeat(10, {
      log("Collect Prices")
      device.tap(COLLECT_PRICES)
      waitFor(0.5.second())
    })
  }
}