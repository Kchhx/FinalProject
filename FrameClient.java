import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class FrameClient{
    private static int clickCount;

    public static void hotelFrame(JFrame frame){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();//full screen
        frame.setSize(screenSize);
        frame.add(LogIn.login());
        frame.getContentPane();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();


    }

    public static JPanel clientInfo() throws Exception {

        JPanel panel = new JPanel();

        JLabel labelRoom = new JLabel("Manage Client");
        labelRoom.setFont(new Font("Serif", Font.BOLD, 30));
        labelRoom.setBounds(530,10,320,50);
        panel.add(labelRoom);


        JLabel clientID = new JLabel("Client ID");
        clientID.setFont(new Font("Serif", Font.BOLD, 14));
        clientID.setBounds(80,125,300,50);
        panel.add(clientID);
        JTextField FieldClient = new JTextField();
        FieldClient.setBounds(200,140,180,25);
        panel.add(FieldClient);

        JTextField FieldName = new JTextField();
        JLabel NameLabel = new JLabel("Client Name");
        NameLabel.setFont(new Font("Serif", Font.BOLD, 14));
        NameLabel.setBounds(80,175,320,50);
        panel.add(NameLabel);
        FieldName.setBounds(200,190,180,25);
        FieldName.setBackground(Color.WHITE);
        panel.add(FieldName);

        JTextField FieldRoom = new JTextField();
        JLabel RoomNO = new JLabel("Room NO");
        RoomNO.setFont(new Font("Serif", Font.BOLD, 14));
        RoomNO.setBounds(80,225,320,50);
        panel.add(RoomNO);
        FieldRoom.setBounds(200,240,180,25);
        //FieldBed.setBackground(Color.WHITE);
        panel.add(FieldRoom);

        JLabel telLabel = new JLabel("Telephone");
        telLabel.setFont(new Font("Serif", Font.BOLD, 14));
        telLabel.setBounds(80,275,320,50);
        panel.add(telLabel);
        JTextField FieldTel = new JTextField();
        FieldTel.setBounds(200,290,180,25);
        panel.add(FieldTel);

        JTextField FieldCheckIn = new JTextField();
        JLabel CheckInLabel = new JLabel("Check in date");
        CheckInLabel.setFont(new Font("Serif", Font.BOLD, 14));
        CheckInLabel.setBounds(80,325,320,50);
        panel.add(CheckInLabel);
        FieldCheckIn.setBounds(200,340,180,25);
        FieldCheckIn.setBackground(Color.WHITE);
        panel.add(FieldCheckIn);

        DefaultTableModel header = new DefaultTableModel();
        JTable table = new JTable(header);
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        header.addColumn("Client ID");
        header.addColumn("Client Name");
        header.addColumn("Room NO");
        header.addColumn("Telephone");
        header.addColumn("Check in date");

        table.setRowSelectionAllowed(true);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(440, 105, 780, 450);
        panel.add(scroll);

        JButton buttonAdd = new JButton("Add New");
        buttonAdd.setBounds(70, 600, 220, 25);
        buttonAdd.setActionCommand("click");
        buttonAdd.addActionListener((ActionListener) new ActionListener(){
            public void actionPerformed(ActionEvent e) {

                Connection connection = null;
                try {
                    connection = DatabaseConnection.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * from client ORDER BY clientid");

                    String ClientID = FieldClient.getText();
                    String ClientName = FieldName.getText();
                    String RoomNO = FieldRoom.getText();
                    String Tele = FieldTel.getText();
                    String CheckIn = FieldCheckIn.getText();

                    if (ClientID.equals("") || ClientName.equals("") || RoomNO.equals("") || Tele.equals("") || CheckIn.equals("")) {
                        JOptionPane.showMessageDialog(panel, "Please fill all field!");
                    }
//                    else if(RNO == resultSet.getInt(0) ){
//                        JOptionPane.showMessageDialog(panel, "Room exit!");
//                    }
                    else {
                        int clientID = Integer.parseInt(ClientID);
                        int roomNO = Integer.parseInt(RoomNO);
                        int cTele = Integer.parseInt(Tele);
                        try {
                            //Statement statement = connection.createStatement();
                            String sql = "INSERT INTO client VALUE(" + clientID + ",'" + ClientName + "','" + roomNO + "'," + cTele + ",'" + CheckIn + "')";
                            statement.executeUpdate(sql);
                            JOptionPane.showMessageDialog(panel, "Add a new client successfully!");
                            connection.close();
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        panel.add(buttonAdd);

        JButton buttonView = new JButton("View");
        buttonView.setBounds(350, 600, 120, 25);
        buttonView.setActionCommand("click");
        buttonView.addActionListener((ActionListener) new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = DatabaseConnection.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * from client ");

                    while (resultSet.next()) {
                        int cID = resultSet.getInt(1);
                        String cId = Integer.toString(cID);
                        String cName = resultSet.getString(2);
                        int cRoomNo = resultSet.getInt(3);
                        String croomNO = Integer.toString(cRoomNo);
                        int Tele = resultSet.getInt(4);
                        String tele = Integer.toString(Tele);
                        String checkIn = resultSet.getString(5);
                        String[] row = {cId,cName,croomNO,tele,checkIn};
                        model.addRow(row);

                    }
                    connection.close();
                } catch (ClassNotFoundException | SQLException ex) {
                    ex.printStackTrace();
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        });
        panel.add(buttonView);

        JButton buttonRemove = new JButton("Remove");
        buttonRemove.setBounds(530, 600, 120, 25);
        buttonRemove.setActionCommand("click");
        buttonRemove.addActionListener((ActionListener) new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

                clickCount++; // Increment click count

                // Check if click count is even
                if (clickCount % 2 == 0) {
                    int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove?", "Remove", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {

                        String sql = "DELETE FROM client WHERE clientid = ? AND name = ? AND roomNO = ? AND tel = ? AND checkIn = ?";
                        String ClientID = FieldClient.getText();
                        String ClientName = FieldName.getText();
                        String RoomNO = FieldRoom.getText();
                        String Tele = FieldTel.getText();
                        String CheckIn = FieldCheckIn.getText();

                        try (Connection conn = DatabaseConnection.getConnection();
                             PreparedStatement pstmt = conn.prepareStatement(sql)) {
                            // set the corresponding param
                            int clientID = Integer.parseInt(ClientID);
                            int roomNO = Integer.parseInt(RoomNO);
                            int cTele = Integer.parseInt(Tele);
                            pstmt.setInt(1, clientID);
                            pstmt.setString(2, ClientName);
                            pstmt.setInt(3, roomNO);
                            pstmt.setInt(4, cTele);
                            pstmt.setString(5, CheckIn);
                            // execute the delete CheckIn
                            pstmt.executeUpdate();
                            JOptionPane.showMessageDialog(panel, "Remove a client successfully!");

                            FieldClient.setText(null);
                            FieldName.setText(null);
                            FieldRoom.setText(null);
                            FieldTel.setText(null);
                            FieldCheckIn.setText(null);

                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }else{

                        FieldClient.setText(null);
                        FieldName.setText(null);
                        FieldRoom.setText(null);
                        FieldTel.setText(null);
                        FieldCheckIn.setText(null);

                    }
                }
                else{
                    int selectRow = table.getSelectedRow();
                    int selectCol = 0;
                    String value = table.getModel().getValueAt(selectRow,selectCol).toString();
                    model.getDataVector().elementAt(table.convertRowIndexToModel(table.getSelectedRow()));
                    FieldClient.setText(model.getValueAt(selectRow,0).toString());
                    FieldName.setText(model.getValueAt(selectRow,1).toString());
                    FieldRoom.setText(model.getValueAt(selectRow,2).toString());
                    FieldTel.setText(model.getValueAt(selectRow,3).toString());
                    FieldCheckIn.setText(model.getValueAt(selectRow,4).toString());
                }
            }
        });
        panel.add(buttonRemove);

        JButton buttonUpdate = new JButton("Update");
        buttonUpdate.setBounds(710, 600, 120, 25);
        buttonUpdate.setActionCommand("click");
        buttonRemove.addActionListener((ActionListener) new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ///update
            }
        });
        panel.add(buttonRemove);
        panel.add(buttonUpdate);

        JButton buttonRefresh = new JButton("Clear");
        buttonRefresh.setBounds(890, 600, 120, 25);
        buttonRefresh.setActionCommand("click");
        buttonRefresh.addActionListener((ActionListener) new ActionListener() {
            public void actionPerformed(ActionEvent e){
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                while(model.getRowCount() > 0)
                {
                    model.setRowCount(0);
                }
            }
        });
        panel.add(buttonRefresh);

        JButton backButt = new JButton("Back");
        backButt.setBounds(1070, 600, 120, 25);
        backButt.setActionCommand("click");
        panel.add(backButt);
        backButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                frame.setContentPane(Main.homePage());
                frame.revalidate();
                frame.repaint();
            }
        });

        String[] searchStrings = {"", "Cleint ID", "Client Name","Room NO","Telephone","Check In Date"};
        JComboBox FieldOption = new JComboBox(searchStrings);
        FieldOption.setBounds(130,420,130,25);
        panel.add(FieldOption);

        JTextField searchField = new JTextField();
        searchField.setBounds(130,450,190,25);
        panel.add(searchField);

        JButton buttonSearch = new JButton("Search");
        buttonSearch.setBounds(175, 490, 90, 25);
        buttonSearch.setActionCommand("click");
        buttonSearch.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {

                String searchoption = (String) FieldOption.getSelectedItem();
                String url = "jdbc:mysql://localhost:3306/myhotel";
                String username = "root";
                String password = "";

                String search= searchField.getText();

                if(searchoption == "Cleint ID") {
                    String sql = "SELECT FROM client WHERE clientid = ? ";

                    if (search.equals("")) {
                        JOptionPane.showMessageDialog(panel, "Please fill NO of room!");
                    }
                    else {
                        try (Connection conn = DatabaseConnection.getConnection();
                             PreparedStatement pstmt = conn.prepareStatement(sql)) {

                            int SearchID = Integer.parseInt(search);
                            pstmt.setInt(1, SearchID);
                            Class.forName("com.mysql.cj.jdbc.Driver");

                            Connection connection = DriverManager.getConnection(url, username, password);
                            Statement statement = connection.createStatement();

                            ResultSet resultSet = statement.executeQuery("select * from client");
                            while (resultSet.next()) {
                                if (SearchID == resultSet.getInt(1)) {
                                    int cID = resultSet.getInt(1);
                                    String cId = Integer.toString(cID);
                                    String cName = resultSet.getString(2);
                                    int cRoomNo = resultSet.getInt(3);
                                    String croomNO = Integer.toString(cRoomNo);
                                    int Tele = resultSet.getInt(4);
                                    String tele = Integer.toString(Tele);
                                    String checkIn = resultSet.getString(5);
                                    String[] row = {cId,cName,croomNO,tele,checkIn};
                                    model.addRow(row);
                                }
                            }
                            pstmt.executeUpdate();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
                else if(searchoption == "Client Name"){
                    String sql = "SELECT FROM room WHERE Type = ?";

                    if (search.equals("")) {
                        JOptionPane.showMessageDialog(panel, "Please fill all field!");
                    } else {
                        try (Connection conn = DatabaseConnection.getConnection();
                             PreparedStatement pstmt = conn.prepareStatement(sql)) {

                            pstmt.setString(2, search);
                            Class.forName("com.mysql.cj.jdbc.Driver");

                            Connection connection = DriverManager.getConnection(url, username, password);
                            Statement statement = connection.createStatement();

                            ResultSet resultSet = statement.executeQuery(sql);
                            while (resultSet.next()) {
                                if (search == resultSet.getString(2)) {
                                    int cID = resultSet.getInt(1);
                                    String cId = Integer.toString(cID);
                                    String cName = resultSet.getString(2);
                                    int cRoomNo = resultSet.getInt(3);
                                    String croomNO = Integer.toString(cRoomNo);
                                    int Tele = resultSet.getInt(4);
                                    String tele = Integer.toString(Tele);
                                    String checkIn = resultSet.getString(5);
                                    String[] row = {cId,cName,croomNO,tele,checkIn};
                                    model.addRow(row);
                                }
                            }
                            pstmt.executeUpdate();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
                else if(searchoption == "Bed"){
                    String sql = "SELECT FROM room WHERE Bed = ? ";

                    if (search.equals("")) {
                        JOptionPane.showMessageDialog(panel, "Please fill all field!");
                    } else {
                        try (Connection conn = DatabaseConnection.getConnection();
                             PreparedStatement pstmt = conn.prepareStatement(sql)) {

                            pstmt.setString(3, search);
                            Class.forName("com.mysql.cj.jdbc.Driver");

                            Connection connection = DriverManager.getConnection(url, username, password);
                            Statement statement = connection.createStatement();

                            ResultSet resultSet = statement.executeQuery("select * from room");
                            while (resultSet.next()) {
                                if (search == resultSet.getString(3)) {
                                    model.addRow(new Object[]{resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), resultSet.getString(5)});
                                }
                            }
                            pstmt.executeUpdate();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
                else if(searchoption == "Price"){
                    String sql = "SELECT FROM room WHERE Price = ? ";

                    if (search.equals("")) {
                        JOptionPane.showMessageDialog(panel, "Please fill Price of room!");
                    }
                    else {
                        try (Connection conn = DatabaseConnection.getConnection();
                             PreparedStatement pstmt = conn.prepareStatement(sql)) {

                            int SearchNO = Integer.parseInt(search);
                            pstmt.setInt(4, SearchNO);
                            Class.forName("com.mysql.cj.jdbc.Driver");

                            Connection connection = DriverManager.getConnection(url, username, password);
                            Statement statement = connection.createStatement();

                            ResultSet resultSet = statement.executeQuery("select * from room");
                            while (resultSet.next()) {
                                if (SearchNO == resultSet.getInt(4)) {
                                    int no = resultSet.getInt(1);
                                    String No = Integer.toString(no);
                                    String type = resultSet.getString(2);
                                    String bed = resultSet.getString(3);
                                    int price = resultSet.getInt(4);
                                    String Price = Integer.toString(price);
                                    String status = resultSet.getString(5);
                                    String[] row = {No,type,bed,Price,status};
                                    model.addRow(row);
                                }
                            }
                            pstmt.executeUpdate();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
                else if(searchoption == "Status"){
                    String sql = "SELECT FROM room WHERE Status = ? ";

                    DefaultTableModel header = new DefaultTableModel();
                    JTable table = new JTable(header);
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    header.addColumn("Room NO");
                    header.addColumn("Type of room");
                    header.addColumn("Number of bed");
                    header.addColumn("Price of room");
                    header.addColumn("Status");

                    table.setRowSelectionAllowed(true);
                    JScrollPane scroll = new JScrollPane(table);
                    scroll.setBounds(440, 105, 780, 450);
                    panel.add(scroll);

                    if (search.equals("")) {
                        JOptionPane.showMessageDialog(panel, "Please fill all field!");
                    } else {
                        try (Connection conn = DatabaseConnection.getConnection();
                             PreparedStatement pstmt = conn.prepareStatement(sql)) {

                            pstmt.setString(5, search);
                            Class.forName("com.mysql.cj.jdbc.Driver");

                            Connection connection = DriverManager.getConnection(url, username, password);
                            Statement statement = connection.createStatement();

                            ResultSet resultSet = statement.executeQuery("select * from room");
                            while ( resultSet.next()) {
                                if (search.equals(resultSet.getString(5)) ) {
                                    model.addRow(new Object[]{resultSet.getString(5),resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4)});
                                }
                            }
                            pstmt.executeUpdate();
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                }
                else{
                    JOptionPane.showMessageDialog(panel, "Please choose an option!");
                }
            }
        });
        panel.add(buttonSearch);
        panel.setLayout(null);
        panel.setVisible(true);
        return panel;
    }
}
