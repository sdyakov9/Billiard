import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;

public class Billiard extends JPanel {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static final int BALLS = 13;
    public static Ball[] ball = new Ball[BALLS];

    private double nextHit;
    private Ball first;
    private Ball second;

    private static boolean paused = true;
    private static boolean queuedHitUpdate = false;

    public Billiard() {
        super();
        setOpaque(true);
        setBackground(new Color(255, 255, 255));

        double ballRadius = 30;
        double ballMass = 10;

        int rows = (int) Math.sqrt(BALLS);
        int columns = (int) Math.ceil((double) BALLS / rows);
        int first_row = BALLS - (rows - 1) * columns;
        int count = 0;

        for (int i = 0; i < rows; i++) {
            int j = 0;
            if (i == 0)
                j = columns - first_row;

            for (; j < columns; j++) {
                ball[count++] = new Ball((j + 0.5) * WIDTH / columns,
                        (i + 0.5) * HEIGHT / rows,
                        ballRadius,
                        ballMass,
                        new Speed(Math.random() * 8 - 4, Math.random() * 8 - 4));
            }
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        super.paintComponent(g);

        if (!paused) {
            double passed = 0.0;
            while (passed + nextHit < 1.0) {
                for (int i = 0; i < BALLS; i++) {
                    if (ball[i] == first) {
                        ball[i].hit(second, nextHit);
                    } else if (ball[i] != second) {
                        ball[i].move(nextHit);
                    }
                }
                passed += nextHit;
                hitUpdate();
            }
            nextHit += passed;
            nextHit -= 1.0;
            for (int i = 0; i < BALLS; i++) {
                ball[i].move(1.0 - passed);
            }
        }

        for (int i = 0; i < BALLS; i++) {
            ball[i].paint(g2d);
        }

        if (queuedHitUpdate)
            hitUpdate();
    }

    public static void toggle() {
        paused = !paused;
    }

    public static boolean isPaused() {
        return paused;
    }

    public static void queueHitUpdate() {
        queuedHitUpdate = true;
    }

    public void hitUpdate() {
        nextHit = Double.POSITIVE_INFINITY;
        for (int i = 0; i < BALLS - 1; i++) {
            for (int j = i + 1; j < BALLS; j++) {
                double min = ball[i].nextHit(ball[j]);
                if (min < nextHit) {
                    nextHit = min;
                    first = ball[i];
                    second = ball[j];
                }
            }
        }

        queuedHitUpdate = false;
    }
}
