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
          goHome.start();
          TimeUnit.SECONDS.sleep(2);

          while (btAutoAll.getText() == "Stop") {
            System.out.println("Just trying");

              // Fight for 150 minute to make sure to got streak of 5
              quickFight.start();
              TimeUnit.SECONDS.sleep(150);
              quickFight.stop();

              // Try to go home
              goHome.start();
              TimeUnit.SECONDS.sleep(2);
              goHome.start();
              TimeUnit.SECONDS.sleep(2);

              // View video 1 time
              viewVideo.start();
              TimeUnit.SECONDS.sleep(39);
              viewVideo.stop();

              // View video once more
              viewVideo.start();
              TimeUnit.SECONDS.sleep(39);
              viewVideo.stop();
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
