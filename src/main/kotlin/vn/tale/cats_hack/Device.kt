package vn.tale.cats_hack

import java.awt.Point

/**
 * Created by Giang Nguyen on 6/19/17.
 */
class Device(private val processor: Processor, private val deviceId: String? = null) {
  fun tap(point: Point) {
    tap(point.x, point.y)
  }

  fun tap(x: Int, y: Int): Unit {
    if (deviceId == null) {
      processor.exec("adb", "shell", "input", "tap", "$x", "$y")
    } else {
      processor.exec("adb", "-s", deviceId, "shell", "input", "tap", "$x", "$y")
    }
  }
}
