package vn.tale.cats_hack

/**
 * Created by Giang Nguyen on 7/1/17.
 */
class Auto(private val gameController: GameController) {

  private var running: Boolean = false
  private var thread: Thread? = null

  fun isRunning() = running

  fun full() {
    exec {
      repeat(30,
          { gameController.runAFightLoop() }) // Run Quick-Fight for 120 times is about 60s
      gameController.stopTheFighting()
      gameController.goHome()
      gameController.viewAVideo() // Start ViewVideo
    }
  }

  fun stop(): Unit {
    running = false
    thread?.interrupt()

  }

  fun viewVideo() {
    exec {
      gameController.viewAVideo()
    }
  }

  private fun exec(func: () -> Unit) {
    thread = Thread {
      running = true
      while (running) {
        func()
      }
    }
    thread?.start()
  }
}