import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LogIn {

    public static JPanel login(){

        JPanel panel = new JPanel();
        Color color = new Color(172, 59, 97);
        //panel.setBackground(color);
        JLabel label = new JLabel();
        panel.setLayout(null);

        JLabel labelHotel = new JLabel("HOTEL MANAGEMENT SYSTEM");
        labelHotel.setBounds(330,40,900,50);
        labelHotel.setFont(new Font("Serif", Font.BOLD, 40));
        labelHotel.setForeground(color);
        panel.add(labelHotel);

        JLabel labelLogin = new JLabel("Login");
        labelLogin.setBounds(590,120,320,50);
        labelLogin.setFont(new Font("Serif", Font.BOLD, 30));
        Color color1 = new Color(172, 59, 97);
        labelLogin.setForeground(color);
        panel.add(labelLogin);

        JLabel labelUser = new JLabel("Username");
        labelUser.setBounds(485,202,320,30);
        labelUser.setFont(new Font("Serif", Font.BOLD, 15));
        labelUser.setForeground(Color.darkGray);
        panel.add(labelUser);
        JTextField usernameField = new JTextField();
        usernameField.setBounds(485,230,300,30);
        panel.add(usernameField);

        JLabel labelPass = new JLabel("Password");
        labelPass.setBounds(485,282,300,30);
        labelPass.setFont(new Font("Serif", Font.BOLD, 15));
        labelPass.setForeground(Color.darkGray);
        panel.add(labelPass);
        JTextField passwordField = new JTextField();
        passwordField.setBounds(485,310,300,30);
        panel.add(passwordField );

        JButton loginButt = new JButton("Login");
        loginButt.setBounds(655,375,100,30);
        loginButt.setFont(new Font("Serif", Font.BOLD, 15));
        Color colorLogin = new Color(236, 107, 86);
        loginButt.setBackground(colorLogin);
        panel.add(loginButt);
        loginButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String usernameInput = usernameField.getText();
                String passwordInput = passwordField.getText();

                String query = "SELECT * FROM user WHERE username = ? AND password = ?";

                try {
                    PreparedStatement preState = DatabaseConnection.getConnection().prepareStatement(query);
                    preState.setString(1, usernameInput);
                    preState.setString(2, passwordInput);
                    ResultSet resultSet = preState.executeQuery();
                    if (((ResultSet) resultSet).next()) {
                        boolean status = resultSet.getBoolean("status");
                        if (status) {
                            JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                            frame.setContentPane(Main.homePage());
                            frame.revalidate();
                            frame.repaint();
                        } else {
                            JOptionPane.showMessageDialog(panel, "Your account must be approved by an admin!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(panel, "Invalid username or password!");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        JButton registerButt = new JButton("Register");
        registerButt.setBounds(520,375,100,30);
        registerButt.setFont(new Font("Serif", Font.BOLD, 15));
        Color colorRegister = new Color(236, 107, 86);
        registerButt.setBackground(colorRegister);
        panel.add(registerButt);
        registerButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                frame.setContentPane(register());
                frame.revalidate();
                frame.repaint();
            }
        });

        return panel;
    }
    public static JPanel register(){

        JPanel panel = new JPanel();
        JLabel label = new JLabel();
        Color color = new Color(172, 59, 97);
        panel.setLayout(null);

        JLabel labelHotel = new JLabel("HOTEL MANAGEMENT SYSTEM");
        labelHotel.setBounds(330,40,900,50);
        labelHotel.setFont(new Font("Serif", Font.BOLD, 40));
        labelHotel.setForeground(color);
        panel.add(labelHotel);

        JLabel labelRegister = new JLabel("Register");
        labelRegister.setBounds(590,120,320,50);
        labelRegister.setFont(new Font("Serif", Font.BOLD, 30));
        labelRegister.setForeground(color);
        panel.add(labelRegister);

        JLabel labelUSername = new JLabel("Username");
        labelUSername.setBounds(485,202,320,30);
        labelUSername.setFont(new Font("Serif", Font.BOLD, 15));
        panel.add(labelUSername);
        JTextField usernameRegis = new JTextField("");
        usernameRegis.setBounds(485,230,300,30);
        usernameRegis.setFont(new Font("Serif", Font.BOLD, 15));
        panel.add(usernameRegis);

        JLabel labelEmail = new JLabel("Email Address");
        labelEmail.setBounds(485,272,300,30);
        labelEmail.setFont(new Font("Serif", Font.BOLD, 15));
        panel.add(labelEmail);
        JTextField email = new JTextField("");
        email.setBounds(485,300,300,30);
        email.setFont(new Font("Serif", Font.BOLD, 15));
        panel.add(email);

        JLabel labelPassword = new JLabel("Password");
        labelPassword.setBounds(485,342,300,30);
        labelPassword.setFont(new Font("Serif", Font.BOLD, 15));
        panel.add(labelPassword);
        JTextField passwordRegis = new JTextField("");
        passwordRegis.setBounds(485,370,300,30);
        passwordRegis.setFont(new Font("Serif", Font.BOLD, 15));
        panel.add(passwordRegis);


        JLabel labelCfPassword = new JLabel("Confirm Password");
        labelCfPassword.setBounds(485,412,300,30);
        labelCfPassword.setFont(new Font("Serif", Font.BOLD, 15));
        panel.add(labelCfPassword);
        JTextField confirmPassword = new JTextField("");
        confirmPassword.setBounds(485,440,300,30);
        confirmPassword.setFont(new Font("Serif", Font.BOLD, 15));
        panel.add(confirmPassword);

        JButton registerButt = new JButton("Register");
        registerButt.setBounds(510,500,100,30);
        registerButt.setFont(new Font("Serif", Font.BOLD, 15));
        Color colorRegister = new Color(236, 107, 86);
        registerButt.setBackground(colorRegister);
        panel.add(registerButt);

        JButton loginButt = new JButton("Login");
        loginButt.setBounds(665,500,100,30);
        loginButt.setFont(new Font("Serif", Font.BOLD, 15));
        Color colorlogin = new Color(236, 107, 86);
        loginButt.setBackground(colorlogin);
        panel.add(loginButt);

        registerButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String usernameInput = usernameRegis.getText();
                String emailInput = email.getText();
                String passwordInput = passwordRegis.getText();
                String confirmPasswordInput = confirmPassword.getText();

                if (usernameInput.equals("") || emailInput.equals("") || passwordInput.equals("") || confirmPasswordInput.equals("")) {
                    JOptionPane.showMessageDialog(panel, "Please fill all field!");
                } else if (!passwordInput.equals(confirmPasswordInput)) {
                    JOptionPane.showMessageDialog(panel, "Password do not match!");
                } else {
                    try {
                        Connection connection = DatabaseConnection.getConnection();
                        String query = "INSERT INTO user (username, email, password ,comfirmpassword, status) VALUES (?,?, ?, ?, ?)";
                        PreparedStatement preState = connection.prepareStatement(query);
                        preState.setString(1, usernameInput);
                        preState.setString(2, emailInput);
                        preState.setString(3, passwordInput);
                        preState.setString(4, confirmPasswordInput);
                        preState.setBoolean(5, false);
                        preState.executeUpdate();
                        JOptionPane.showMessageDialog(panel, "Registration Successfully. Wait for approving by an admin.");
                        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                        frame.setContentPane(login());
                        frame.revalidate();
                        frame.repaint();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(panel, "Registration failed!");
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        loginButt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);

                frame.setContentPane(login());
                frame.revalidate();
                frame.repaint();
            }
        });
        return panel;
    }
}