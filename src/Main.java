import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

class NotificationWindow extends JFrame {

    private JLabel titleLabel;
    private JLabel messageLabel;
    private JLabel iconLabel;
    private JButton closeButton;
    private JPanel contentPanel;

    public NotificationWindow(String title, String message, ImageIcon icon) {

        setUndecorated(true);
        setShape(new RoundRectangle2D.Double(0, 0, 300, 150, 20, 20)); // Rounded corners
        setSize(300, 150);
        setAlwaysOnTop(true); // Keep on top of other windows
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(240, 240, 240)); // Light gray background
        add(contentPanel);


        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(new Color(50, 50, 50)); // Dark gray text
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        contentPanel.add(titleLabel, BorderLayout.NORTH);


        messageLabel = new JLabel("<html><body style='width: 250px;'>" + message + "</body></html>"); // Wrap text
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        messageLabel.setForeground(new Color(80, 80, 80)); // Medium gray text
        messageLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        contentPanel.add(messageLabel, BorderLayout.CENTER);

        if (icon != null) {
            iconLabel = new JLabel(icon);
            iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            contentPanel.add(iconLabel, BorderLayout.WEST);
        }


        closeButton = new JButton("X");
        closeButton.setFont(new Font("Arial", Font.BOLD, 12));
        closeButton.setForeground(new Color(100, 100, 100)); // Darker gray
        closeButton.setBackground(new Color(220, 220, 220)); // Lighter gray
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the window
            }
        });
        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closePanel.setBackground(new Color(240, 240, 240));
        closePanel.add(closeButton);
        contentPanel.add(closePanel, BorderLayout.SOUTH);

        final Point[] initialClick = new Point[1];
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick[0] = e.getPoint();
                getComponentAt(initialClick[0]);
            }
        });

        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;
                int xMoved = e.getX() - initialClick[0].x;
                int yMoved = e.getY() - initialClick[0].y;
                int X = thisX + xMoved;
                int Y = thisY + yMoved;
                setLocation(X, Y);
            }
        });

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = screenSize.width - getWidth() - 20;
        int y = screenSize.height - getHeight() - 50;
        setLocation(x, y);
    }

    public static void main(String[] args) {
        try {

            ImageIcon icon = new ImageIcon("path/to/your/icon.png"); // Replace with your icon path
            NotificationWindow notification = new NotificationWindow("New Message", "You have a new notification!", icon);
            notification.setVisible(true);
        } catch (Exception e) {
            System.err.println("Error displaying notification: " + e.getMessage());
            e.printStackTrace();
        }
    }
}