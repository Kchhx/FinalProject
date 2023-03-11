import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static JPanel homePage() {

        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        panel.setLayout(null);

        Color color = new Color(236, 107, 86);

        JLabel labelHotel = new JLabel("HOTEL MANAGEMENT SYSTEM");
        labelHotel.setBounds(330, 40, 900, 50);
        labelHotel.setFont(new Font("Serif", Font.BOLD, 40));
        //labelHotel.setForeground(color);
        panel.add(labelHotel);

        JLabel labelHome = new JLabel("Home Page");
        labelHome.setBounds(580, 120, 320, 50);
        labelHome.setFont(new Font("Serif", Font.BOLD, 30));
        //labelHome.setForeground(color);
        panel.add(labelHome);

        JButton room_management = new JButton("Room Management");
        room_management.setBounds(70, 250, 250, 250);
        room_management.setFont(new Font("Times new roman", Font.PLAIN, 25));
        room_management.setBackground(color);
        panel.add(room_management);
        room_management.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                try {
                    frame.setContentPane(FrameHotel.roomInfo());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                frame.revalidate();
                frame.repaint();
            }
        });

        JButton booking_management = new JButton("Reservation Management");
        booking_management.setBounds(370, 250, 250, 250);
        booking_management.setFont(new Font("Times new roman", Font.PLAIN, 25));
        booking_management.setBackground(color);
        panel.add(booking_management);
        booking_management.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                    try {
                        frame.setContentPane(FrameReservation.ReservationInfo());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                    frame.revalidate();
                    frame.repaint();
            }
        });

        JButton client_management = new JButton("Client Management");
        client_management.setBounds(675, 250, 250, 250);
        client_management.setFont(new Font("Times new roman", Font.PLAIN, 25));
        client_management.setBackground(color);
        panel.add(client_management);
        client_management.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                try {
                    frame.setContentPane(FrameClient.clientInfo());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                frame.revalidate();
                frame.repaint();
            }
        });

        JButton exit = new JButton("Quit");
        exit.setBounds(980, 250, 250, 250);
        exit.setFont(new Font("Times new roman", Font.PLAIN, 25));
        exit.setBackground(color);
        panel.add(exit);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit Program", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
        return panel;
    }

    public static void main(String[] args){
        JFrame frame = new JFrame("Hotel Management System");
        FrameHotel.hotelFrame(frame);
    }


}