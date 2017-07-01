package vn.tale.cats_hack;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import java.util.concurrent.TimeUnit;

/**
 * Created by Giang Nguyen on 6/20/17.
 */
public class MainForm {
  private final QuickFight quickFight;
  private final ViewVideo viewVideo;
  private final GoHome goHome;
  private final SlowFight slowFight;
  private final Restart restart;

  private JPanel container;
  private JButton btViewVideo;
  private JButton btQuickFight;
  private JTextArea taOutput;
  private JButton btGoHome;
  private JButton btAutoAll;

  private MainForm() {
    final Processor processor = new Processor();
    final Device device = new Device(processor, "3100a5aba8142373");
    final Function1<String, Unit> output = s -> {
      taOutput.setText(s);
      return null;
    };
    quickFight = new QuickFight(device, output);
    viewVideo = new ViewVideo(device, output);
    goHome = new GoHome(device, output);
    slowFight = new SlowFight(device, output);
    restart = new Restart(device, output);

    btGoHome.addActionListener(e -> {
      if (!goHome.isRunning()) {
        goHome.start();
      }
    });

    btQuickFight.addActionListener(e -> {
      if (quickFight.isRunning()) {
        btQuickFight.setText("Quick Fight");
        quickFight.stop();
        setAllButtonEnabled(true);
      } else {
        quickFight.start();
        setAllButtonEnabled(false);
        btQuickFight.setText("Stop");
        btQuickFight.setEnabled(true);
      }
    });

    btViewVideo.addActionListener(e -> {
      if (quickFight.isRunning()) {
        btViewVideo.setText("View Video");
        viewVideo.stop();
        setAllButtonEnabled(true);
      } else {
        viewVideo.start();
        setAllButtonEnabled(false);
        btViewVideo.setText("Stop");
        btViewVideo.setEnabled(true);
      }
    });

    btAutoAll.addActionListener(e -> {
      if (btAutoAll.getText() == "Stop") {
        btAutoAll.setText("Auto Everything");
        quickFight.stop();
        viewVideo.stop();
        goHome.start();
        setAllButtonEnabled(true);
      } else {
        setAllButtonEnabled(false);
        btAutoAll.setText("Stop");
        btAutoAll.setEnabled(true);

        try {
          System.out.println("Restart app");
          restart.start();
          TimeUnit.SECONDS.sleep(11);

          System.out.println("Go home");
          goHome.start();
          TimeUnit.SECONDS.sleep(2);

          while (btAutoAll.getText() == "Stop") {
            System.out.println("Quick fight");
            quickFight.start();
            TimeUnit.SECONDS.sleep(40);
            quickFight.stop();

            System.out.println("Slow fight");
            slowFight.start();
            TimeUnit.SECONDS.sleep(12);
            slowFight.stop();

            System.out.println("Go home");
            // Try to go home
            goHome.start();
            TimeUnit.SECONDS.sleep(2);

            System.out.println("Go home");
            // Try to go home
            goHome.start();
            TimeUnit.SECONDS.sleep(2);

            System.out.println("View video");
            viewVideo.start();
            TimeUnit.SECONDS.sleep(64);
          }
        } catch (InterruptedException e1) {
          e1.printStackTrace();
        }

      }
    });
  }

  public static void main(String[] args) {
    JFrame frame = new JFrame("Auto Tap");
    frame.setContentPane(new MainForm().container);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  private void setAllButtonEnabled(boolean enabled) {
    btViewVideo.setEnabled(enabled);
    btQuickFight.setEnabled(enabled);
    btGoHome.setEnabled(enabled);
    btAutoAll.setEnabled(enabled);
  }
}
