package javaCoursework;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GUI extends JFrame {
    private static final long serialVersionUID = 1L;
   
    private JPanel contentPane;
    public GUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 791, 539);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        contentPane.setBackground(new Color(18, 18, 18));
        contentPane.setOpaque(true);
        JButton adminButton = createButton("Admin Center", 40);
        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                AdminPanel adminPanel = new AdminPanel();
                adminPanel.setVisible(true);
                dispose();
            }
        });

        JButton userButton = createButton("Player Hub", 160);
        userButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UserLoginPanel loginPanel = new UserLoginPanel();
                loginPanel.setVisible(true);
                dispose();
            }
        });

        JButton exitButton = createButton("Exit", 280);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

  
    private JButton createButton(String text, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 45));
        button.setBackground(new Color(33, 33, 33)); 
        button.setForeground(Color.WHITE);
        button.setBorder(new LineBorder(new Color(180, 180, 180), 2)); 
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorder(new RoundedBorder(10)); 

        // Button hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(50, 50, 50)); 
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(33, 33, 33)); 
            }
        });

        button.setBounds(158, y, 455, 97);
        contentPane.add(button);
        return button;
    }

    @SuppressWarnings("serial")
    private static class RoundedBorder extends AbstractBorder {
        private final int radius;

   
        RoundedBorder(int radius) {
            this.radius = radius;
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 2, radius + 2, radius + 2, radius + 2);
        }

        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.setColor(new Color(180, 180, 180)); // Border color
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }
}