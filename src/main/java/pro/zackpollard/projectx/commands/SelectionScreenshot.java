package pro.zackpollard.projectx.commands;

import pro.zackpollard.projectx.ProjectX;
import pro.zackpollard.projectx.io.image.ImageFormat;
import pro.zackpollard.projectx.io.image.SaveImage;
import pro.zackpollard.projectx.utils.Utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Created by Zack on 18/09/2014.
 */
public class SelectionScreenshot extends Command {

    public SelectionScreenshot(ProjectX projectX, String name) {

        super(projectX, name);
    }

    public static Rectangle getVirtualBounds() {

        Rectangle bounds = new Rectangle(0, 0, 0, 0);

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice lstGDs[] = ge.getScreenDevices();
        for (GraphicsDevice gd : lstGDs) {

            bounds.add(gd.getDefaultConfiguration().getBounds());

        }

        return bounds;

    }

    @Override
    public void execute() {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame();
                frame.setUndecorated(true);
                // This works differently under Java 6
                frame.setBackground(new Color(0, 0, 0, 0));
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setLayout(new BorderLayout());
                frame.add(new SnipItPane());
                frame.setBounds(getVirtualBounds());
                frame.setVisible(true);
            }
        });
    }

    public class SnipItPane extends JPanel {

        private Point mouseAnchor;
        private Point dragPoint;
        ;

        private SelectionPane selectionPane;

        public SnipItPane() {
            setOpaque(false);
            setLayout(null);
            selectionPane = new SelectionPane();
            add(selectionPane);
            MouseAdapter adapter = new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    mouseAnchor = e.getPoint();
                    dragPoint = null;
                    selectionPane.setLocation(mouseAnchor);
                    selectionPane.setSize(0, 0);
                }

                @Override
                public void mouseDragged(MouseEvent e) {
                    dragPoint = e.getPoint();
                    int width = dragPoint.x - mouseAnchor.x;
                    int height = dragPoint.y - mouseAnchor.y;

                    int x = mouseAnchor.x;
                    int y = mouseAnchor.y;

                    if (width < 0) {
                        x = dragPoint.x;
                        width *= -1;
                    }
                    if (height < 0) {
                        y = dragPoint.y;
                        height *= -1;
                    }
                    selectionPane.setBounds(x, y, width, height);
                    System.out.println(x + "-" + y + "-" + width + "-" + height);

                    selectionPane.revalidate();
                    repaint();
                }
            };
            addMouseListener(adapter);
            addMouseMotionListener(adapter);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();

            Rectangle bounds = new Rectangle(0, 0, getWidth(), getHeight());
            Area area = new Area(bounds);
            area.subtract(new Area(selectionPane.getBounds()));

            g2d.setColor(new Color(192, 192, 192, 64));
            g2d.fill(area);

        }
    }

    public class SelectionPane extends JPanel {

        private JButton button;
        private JLabel label;

        public SelectionPane() {
            button = new JButton("Close");
            setOpaque(false);

            label = new JLabel("Rectangle");
            label.setOpaque(true);
            label.setBorder(new EmptyBorder(4, 4, 4, 4));
            label.setBackground(Color.GRAY);
            label.setForeground(Color.WHITE);
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 0;
            gbc.gridy = 0;
            add(label, gbc);

            gbc.gridy++;
            add(button, gbc);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Rectangle rectangle = new Rectangle(getX(), getY(), getWidth(), getHeight());
                    SwingUtilities.getWindowAncestor(SelectionPane.this).dispose();
                    BufferedImage image = ProjectX.getRobot().createScreenCapture(rectangle);
                    File file = new File("./images/" + Utils.generateFileName("png"));
                    SaveImage.saveImage(image, file, ImageFormat.PNG);
                }
            });

            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    label.setText("Rectangle " + getX() + "x" + getY() + "x" + getWidth() + "x" + getHeight());
                }
            });

        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            // I've chosen NOT to fill this selection rectangle, so that
            // it now appears as if you're "cutting" away the selection
//            g2d.setColor(new Color(128, 128, 128, 64));
//            g2d.fillRect(0, 0, getWidth(), getHeight());

            float dash1[] = {10.0f};
            BasicStroke dashed =
                    new BasicStroke(3.0f,
                            BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER,
                            10.0f, dash1, 0.0f);
            g2d.setColor(Color.BLACK);
            g2d.setStroke(dashed);
            g2d.drawRect(0, 0, getWidth() - 3, getHeight() - 3);
            g2d.dispose();
        }
    }
}