package vn.tale.cats_hack;

import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;

/**
 * Created by Giang Nguyen on 6/20/17.
 */
public class MainForm {
  private final QuickFight quickFight;
  private final ViewVideo viewVideo;
  private JPanel container;
  private JButton btViewVideo;
  private JButton btQuickFight;
  private JRadioButton rbClose1;
  private JRadioButton rbClose2;

  private MainForm() {
    final Processor processor = new Processor();
    final Device device = new Device(processor, "0540e20f252725a6");
    quickFight = new QuickFight(device);
    viewVideo = new ViewVideo(device);
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
    final ActionListener closeCheckChangeListener = e -> {
      if (e.getSource() == rbClose1) {
        viewVideo.changeClosePosition(ViewVideo.CloseButton.ONE);
      } else {
        viewVideo.changeClosePosition(ViewVideo.CloseButton.TWO);
      }
    };
    rbClose1.addActionListener(closeCheckChangeListener);
    rbClose2.addActionListener(closeCheckChangeListener);
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
