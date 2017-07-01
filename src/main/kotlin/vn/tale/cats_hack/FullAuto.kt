package vn.tale.cats_hack

/**
 * Created by Giang Nguyen on 7/1/17.
 */
class FullAuto(private val gameController: GameController) {
  var running: Boolean = false

  fun start() {
    running = true
    while (running) {
      repeat(120, { gameController.runAFightLoop() }) // Run Quick-Fight for 120 times is about 60s
      gameController.stopTheFighting()
      gameController.goHome()
      gameController.viewAVideo() // Start ViewVideo
    }
  }

  fun stop() {
    running = false
  }
}