import matrix.MatrixObject;

import javax.swing.*;
import java.awt.*;

public class Snake extends JFrame {

    private static final Integer WINDOW_SIZE = 400;
    public static final String NAME = "Snake";

    public Snake() throws HeadlessException {
        super(NAME);
        setContentPane(new MainPanel(WINDOW_SIZE));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);
        pack();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            new Snake();
            }
        });
    }
}