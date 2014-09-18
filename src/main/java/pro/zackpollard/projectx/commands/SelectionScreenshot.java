package pro.zackpollard.projectx.commands;

import pro.zackpollard.projectx.ProjectX;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

/**
 * Created by Zack on 18/09/2014.
 */
public class SelectionScreenshot extends Command {

    public SelectionScreenshot(ProjectX projectX, String name) {

        super(projectX, name);
    }

    @Override
    public void execute() {
        final Dimension screenSize = Toolkit.getDefaultToolkit().
                getScreenSize();
        final BufferedImage screen = ProjectX.getRobot().createScreenCapture(
                new Rectangle(screenSize));
        this.loadScreen(screen);
    }

    Rectangle captureRect;

    public void loadScreen(final BufferedImage screen) {

        final BufferedImage screenCopy = new BufferedImage(
                screen.getWidth(),
                screen.getHeight(),
                screen.getType());
        final JLabel screenLabel = new JLabel(new ImageIcon(screenCopy));
        //JScrollPane screenScroll = new JScrollPane(screenLabel);

        /*screenScroll.setPreferredSize(new Dimension(
                (int) (screen.getWidth()),
                (int) (screen.getHeight())));
        */
        screenLabel.setBorder(new EmptyBorder(0, 0, 0, 0));
        JPanel panel = new JPanel(new CardLayout(0, 0));
        panel.setBorder(new EmptyBorder(0, 0, 0, 0));
        panel.add(screenLabel);

        /*final JLabel selectionLabel = new JLabel(
                "Drag a rectangle in the screen shot!");
        panel.add(selectionLabel, BorderLayout.SOUTH);
        */
        repaint(screen, screenCopy);
        screenLabel.repaint();
        panel.setVisible(true);

        screenLabel.addMouseMotionListener(new MouseMotionAdapter() {

            Point start = new Point();

            @Override
            public void mouseMoved(MouseEvent me) {
                start = me.getPoint();
                repaint(screen, screenCopy);
                //selectionLabel.setText("Start Point: " + start);
                screenLabel.repaint();
            }

            @Override
            public void mouseDragged(MouseEvent me) {
                Point end = me.getPoint();
                captureRect = new Rectangle(start,
                        new Dimension(end.x - start.x, end.y - start.y));
                repaint(screen, screenCopy);
                screenLabel.repaint();
                //selectionLabel.setText("Rectangle: " + captureRect);
            }
        });

        //JOptionPane.showMessageDialog(null, panel);

        System.out.println("Rectangle of interest: " + captureRect);
    }

    public void repaint(BufferedImage orig, BufferedImage copy) {
        Graphics2D g = copy.createGraphics();
        g.drawImage(orig, 0, 0, null);
        if (captureRect != null) {
            g.setColor(Color.RED);
            g.draw(captureRect);
            g.setColor(new Color(255, 255, 255, 150));
            g.fill(captureRect);
        }
        g.dispose();
    }
}
