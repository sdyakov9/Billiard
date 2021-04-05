/*
 *  Copyright (C) 2010  Luca Wehrstedt
 *
 *  This file is released under the GPLv2
 *  Read the file 'COPYING' for more information
 */

import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Font;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Path2D;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Overlay extends JPanel implements MouseListener {
    private Path2D buttons[];
    private Path2D selector;
    private Font font;

    private int active_ball = 0;

    private boolean is_dragged = false;

    private int count = 0;

    public Overlay() {
        super();
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));

        buttons = new Path2D.Double[13];

        buttons[0] = new Path2D.Double(new RoundRectangle2D.Double(10, 10, 160, 160, 10, 10));
        buttons[1] = new Path2D.Double(new RoundRectangle2D.Double(20, 20, 45, 15, 7.5, 7.5));
        buttons[2] = new Path2D.Double(new RoundRectangle2D.Double(115, 20, 45, 15, 7.5, 7.5));
        buttons[3] = new Path2D.Double(new Rectangle2D.Double(45, 60, 90, 20));

        buttons[4] = new Path2D.Double();
        buttons[4].moveTo(30, 60);
        buttons[4].lineTo(20, 70);
        buttons[4].lineTo(30, 80);
        buttons[4].closePath();

        buttons[5] = new Path2D.Double();
        buttons[5].moveTo(150, 60);
        buttons[5].lineTo(160, 70);
        buttons[5].lineTo(150, 80);
        buttons[5].closePath();

        buttons[6] = new Path2D.Double();
        buttons[6].moveTo(30, 100);
        buttons[6].lineTo(20, 110);
        buttons[6].lineTo(30, 120);
        buttons[6].closePath();

        buttons[7] = new Path2D.Double();
        buttons[7].moveTo(75, 100);
        buttons[7].lineTo(85, 110);
        buttons[7].lineTo(75, 120);
        buttons[7].closePath();

        buttons[8] = new Path2D.Double();
        buttons[8].moveTo(105, 100);
        buttons[8].lineTo(95, 110);
        buttons[8].lineTo(105, 120);
        buttons[8].closePath();

        buttons[9] = new Path2D.Double();
        buttons[9].moveTo(150, 100);
        buttons[9].lineTo(160, 110);
        buttons[9].lineTo(150, 120);
        buttons[9].closePath();

        buttons[10] = new Path2D.Double(new RoundRectangle2D.Double(75, 20, 30, 15, 7.5, 7.5));

        buttons[11] = new Path2D.Double(new Rectangle2D.Double(85, 22.5, 4, 10));
        buttons[11].append(new Rectangle2D.Double(91, 22.5, 4, 10), false);

        buttons[12] = new Path2D.Double();
        buttons[12].moveTo(86, 22.5);
        buttons[12].lineTo(95, 27.5);
        buttons[12].lineTo(86, 32.5);
        buttons[12].closePath();

        addMouseListener(this);
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_SPEED);
        super.paintComponent(g);

        g2d.setFont(font);

        count++;

        g2d.setColor(new Color(0, 0, 0, 150));
        g2d.fill(buttons[0]);

        g2d.setColor(new Color(255, 255, 255, 200));
        g2d.fill(buttons[1]);
        g2d.fill(buttons[2]);
        g2d.fill(buttons[4]);
        g2d.fill(buttons[5]);
        g2d.fill(buttons[6]);
        g2d.fill(buttons[7]);
        g2d.fill(buttons[8]);
        g2d.fill(buttons[9]);

        g2d.setColor(new Color(255, 0, 0));
        g2d.fill(buttons[3]);

        g2d.setColor(new Color(255, 255, 255));
        g2d.draw(buttons[3]);

        g2d.drawString("Color", (int) buttons[4].getBounds2D().getMinX(), (int) buttons[4].getBounds2D().getMinY() - 5);
        g2d.drawString("Speed (X, Y)", (int) buttons[6].getBounds2D().getMinX(), (int) buttons[6].getBounds2D().getMinY() - 5);
        g2d.setColor(new Color(0, 0, 0));
        drawString(g2d, "Prev", buttons[1].getBounds2D().getCenterX(), buttons[1].getBounds2D().getCenterY() - 2);
        drawString(g2d, "Next", buttons[2].getBounds2D().getCenterX(), buttons[2].getBounds2D().getCenterY() - 2);

        if (Billiard.isPaused()) {
            g2d.setColor(new Color(138, 226, 52));
            g2d.fill(buttons[10]);
            g2d.setColor(Color.WHITE);
            g2d.fill(buttons[12]);
        } else {
            g2d.setColor(new Color(239, 41, 41));
            g2d.fill(buttons[10]);
            g2d.setColor(Color.WHITE);
            g2d.fill(buttons[12]);
        }

        g2d.setColor(new Color(255, 255, 255));
        drawString(g2d, (int) (Billiard.ball[active_ball].getSpeed().getX() * 100) / 100.0 + "",
                buttons[6].getBounds2D().getMaxX() + 22.5, buttons[6].getBounds2D().getCenterY() - 2);
        drawString(g2d, (int) (Billiard.ball[active_ball].getSpeed().getY() * -100) / 100.0 + "",
                buttons[8].getBounds2D().getMaxX() + 22.5, buttons[8].getBounds2D().getCenterY() - 2);

        g2d.setColor(Ball.colors[Billiard.ball[active_ball].getColorId()]);
        g2d.fill(buttons[3]);
    }

    public void mousePressed(MouseEvent e) {
        if (buttons[1].contains(e.getX(), e.getY())) {
            active_ball += Billiard.BALLS - 1;
            active_ball %= Billiard.BALLS;
        }
        if (buttons[2].contains(e.getX(), e.getY())) {
            active_ball++;
            active_ball %= Billiard.BALLS;
        }

        if (buttons[4].contains(e.getX(), e.getY())) {
            Billiard.ball[active_ball].setColorId(Billiard.ball[active_ball].getColorId() - 1);
        }
        if (buttons[5].contains(e.getX(), e.getY())) {
            Billiard.ball[active_ball].setColorId(Billiard.ball[active_ball].getColorId() + 1);
        }

        if (buttons[6].contains(e.getX(), e.getY())) {
            Billiard.ball[active_ball].getSpeed().addX(-1.0);
            Billiard.queueHitUpdate();
        }
        if (buttons[7].contains(e.getX(), e.getY())) {
            Billiard.ball[active_ball].getSpeed().addX(1.0);
            Billiard.queueHitUpdate();
        }

        if (buttons[8].contains(e.getX(), e.getY())) {
            Billiard.ball[active_ball].getSpeed().addY(1.0);
            Billiard.queueHitUpdate();
        }
        if (buttons[9].contains(e.getX(), e.getY())) {
            Billiard.ball[active_ball].getSpeed().addY(-1.0);
            Billiard.queueHitUpdate();
        }

        if (buttons[10].contains(e.getX(), e.getY())) {
            Billiard.toggle();
        }

    }

    private void drawString(Graphics2D g, String str, double x, double y) {
        Rectangle2D bounds = g.getFont().getStringBounds(str, g.getFontRenderContext());
        g.drawString(str, (int) (x - bounds.getWidth() / 2), (int) (y + bounds.getHeight() / 2));
    }

    public void mouseReleased(MouseEvent e) {
        is_dragged = false;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
