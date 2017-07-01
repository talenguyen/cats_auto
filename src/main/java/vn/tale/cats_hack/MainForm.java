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

/**
 * Created by Giang Nguyen on 6/20/17.
 */
public class MainForm {
  private final QuickFight quickFight;
  private final ViewVideo viewVideo;
  private JPanel container;
  private JButton btViewVideo;
  private JButton btQuickFight;
  private JTextArea taOutput;
  private JButton btFullAuto;

  private MainForm() {
    final Processor processor = new Processor();
    final Device device = new Device(processor, "0540e20f252725a6");
    final Function1<String, Unit> output = s -> {
      taOutput.setText(s);
      return null;
    };
    quickFight = new QuickFight(device, output);
    viewVideo = new ViewVideo(device, output);
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
      if (viewVideo.isRunning()) {
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
  }
}
