import javax.crypto.Cipher;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileReader;
import java.sql.*;


public class FrameHotel{
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

    public static JPanel roomInfo() throws Exception {

        JPanel panel = new JPanel();

        Color colorHotel = new Color(236, 107, 86);


        JLabel labelRoom = new JLabel("Manage Room");
        labelRoom.setFont(new Font("Serif", Font.BOLD, 30));
        labelRoom.setBounds(550,10,320,50);
        labelRoom.setForeground(colorHotel);
        panel.add(labelRoom);


        JLabel RoomNumber = new JLabel("Room NO");
        RoomNumber.setFont(new Font("Serif", Font.BOLD, 14));
        RoomNumber.setBounds(80,125,300,50);
        panel.add(RoomNumber);
        JTextField FieldNumber = new JTextField();
        FieldNumber.setBounds(200,140,180,25);
        panel.add(FieldNumber);

        String[] typeStrings = {"", "King", "Queen", "Twin", "Family","Couple"};
        JComboBox FieldType = new JComboBox(typeStrings);
        JLabel RoomType = new JLabel("Type of room");
        RoomType.setFont(new Font("Serif", Font.BOLD, 14));
        RoomType.setBounds(80,175,320,50);
        panel.add(RoomType);
        FieldType.setBounds(200,190,180,25);
        FieldType.setBackground(Color.WHITE);
        panel.add(FieldType);

        String[] bedStrings = {"", "Single Bed", "Double Bed", "Triple Bed", "Twin Bed"};
        JComboBox FieldBed = new JComboBox(bedStrings);
        JLabel RoomBed = new JLabel("Number of bed");
        RoomBed.setFont(new Font("Serif", Font.BOLD, 14));
        RoomBed.setBounds(80,225,320,50);
        panel.add(RoomBed);
        FieldBed.setBounds(200,240,180,25);
        FieldBed.setBackground(Color.WHITE);
        panel.add(FieldBed);

        JLabel RoomPrice = new JLabel("Price of room");
        RoomPrice.setFont(new Font("Serif", Font.BOLD, 14));
        RoomPrice.setBounds(80,275,320,50);
        panel.add(RoomPrice);
        JTextField FieldPrice = new JTextField();
        FieldPrice.setBounds(200,290,180,25);
        panel.add(FieldPrice);

        String[] statusStrings = {"", "Available", "Unavailable"};
        JComboBox FieldStatus = new JComboBox(statusStrings);
        JLabel RoomStatus = new JLabel("Status of room");
        RoomStatus.setFont(new Font("Serif", Font.BOLD, 14));
        RoomStatus.setBounds(80,325,320,50);
        panel.add(RoomStatus);
        FieldStatus.setBounds(200,340,180,25);
        FieldStatus.setBackground(Color.WHITE);
        panel.add(FieldStatus);

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

        JButton buttonAdd = new JButton("Add New");
        buttonAdd.setBounds(70, 600, 220, 25);
        buttonAdd.setActionCommand("click");
        buttonAdd.setBackground(colorHotel);
        buttonAdd.addActionListener((ActionListener) new ActionListener(){
            public void actionPerformed(ActionEvent e) {

                Connection connection = null;
                try {
                    connection = DatabaseConnection.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * from room");

                    String roomNO = FieldNumber.getText();
                    String type = (String) FieldType.getSelectedItem();
                    String bed = (String) FieldBed.getSelectedItem();
                    String price = FieldPrice.getText();
                    String status = (String) FieldStatus.getSelectedItem();

                    if (roomNO.equals("") || type.equals("") || bed.equals("") || price.equals("") || status.equals("")) {
                        JOptionPane.showMessageDialog(panel, "Please fill all field!");
                    }
//                    else if(RNO == resultSet.getInt(0) ){
//                        JOptionPane.showMessageDialog(panel, "Room exit!");
//                    }
                    else {
                        int RNO = Integer.parseInt(roomNO);
                        int priceRoom = Integer.parseInt(price);
                        try {
                            //Statement statement = connection.createStatement();
                            String sql = "INSERT INTO room VALUE(" + RNO + ",'" + type + "','" + bed + "'," + priceRoom + ",'" + status + "')";
                            statement.executeUpdate(sql);
                            JOptionPane.showMessageDialog(panel, "Add a new room successfully!");
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
        buttonView.setBackground(colorHotel);
        buttonView.addActionListener((ActionListener) new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Connection connection = DatabaseConnection.getConnection();
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery("SELECT * from room ORDER BY RoomNO");

                    while (resultSet.next()) {
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
        buttonRemove.setBackground(colorHotel);
        buttonRemove.addActionListener((ActionListener) new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {

                clickCount++; // Increment click count

                // Check if click count is even
                if (clickCount % 2 == 0) {
                    int result = JOptionPane.showConfirmDialog(null, "Are you sure you want to remove?", "Remove", JOptionPane.YES_NO_OPTION);
                    if (result == JOptionPane.YES_OPTION) {

                        String sql = "DELETE FROM room WHERE RoomNO = ? AND Type = ? AND Bed = ? AND Price = ? AND Status = ?";

                        String roomNO = FieldNumber.getText();
                        String type = (String) FieldType.getSelectedItem();
                        String bed = (String) FieldBed.getSelectedItem();
                        String price = FieldPrice.getText();
                        String status = (String) FieldStatus.getSelectedItem();

                        try (Connection connection = DatabaseConnection.getConnection();
                             PreparedStatement pstmt = connection.prepareStatement(sql)) {
                            Statement statement = connection.createStatement();

                            // set the corresponding param
                            int RNO = Integer.parseInt(roomNO);
                            int priceRoom = Integer.parseInt(price);
                            pstmt.setInt(1, RNO);
                            pstmt.setString(2, type);
                            pstmt.setString(3, bed);
                            pstmt.setInt(4, priceRoom);
                            pstmt.setString(5, status);
                            // execute the delete statement
                            pstmt.executeUpdate();
                            JOptionPane.showMessageDialog(panel, "Remove a room successfully!");

                            FieldNumber.setText(null);
                            FieldType.setSelectedItem(null);
                            FieldBed.setSelectedItem(null);
                            FieldPrice.setText(null);
                            FieldStatus.setSelectedItem(null);

                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }else{

                        FieldNumber.setText(null);
                        FieldType.setSelectedItem(null);
                        FieldBed.setSelectedItem(null);
                        FieldPrice.setText(null);
                        FieldStatus.setSelectedItem(null);

                    }
                }
                else{
                    int selectRow = table.getSelectedRow();
                    int selectCol = 0;
                    String value = table.getModel().getValueAt(selectRow,selectCol).toString();
                    model.getDataVector().elementAt(table.convertRowIndexToModel(table.getSelectedRow()));
                    FieldNumber.setText(model.getValueAt(selectRow,0).toString());
                    FieldType.setSelectedItem(model.getValueAt(selectRow,1).toString());
                    FieldBed.setSelectedItem(model.getValueAt(selectRow,2).toString());
                    FieldPrice.setText(model.getValueAt(selectRow,3).toString());
                    FieldStatus.setSelectedItem(model.getValueAt(selectRow,4).toString());
                }
            }
        });
        panel.add(buttonRemove);

        JButton buttonUpdate = new JButton("Update");
        buttonUpdate.setBounds(710, 600, 120, 25);
        buttonUpdate.setActionCommand("click");
        buttonUpdate.setBackground(colorHotel);
        buttonRemove.addActionListener((ActionListener) new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
//                try {
//
//                    Connection conn = DatabaseConnection.getConnection();
//
//                    // Get existing score for the student in the course
//                    String query = "SELECT * FROM room WHERE RoomNO = ? ";
//                    String roomNO = FieldNumber.getText();
//                    int RNO = Integer.parseInt(roomNO);
//                    PreparedStatement pstmt = conn.prepareStatement(query);
//                    pstmt.setInt(1, RNO);
//
//                    ResultSet resultSet = pstmt.executeQuery();
////                    if (!resultSet.next()) {
////                        JOptionPane.showMessageDialog(panel, "No room found for NO " + RNO , "Error", JOptionPane.ERROR_MESSAGE);
////                    }
//
//                    // Create input fields for new score values
//                    //JPanel panel = new JPanel(new GridLayout(5, 2));
//                    FieldNumber = resultSet.getString("Room NO");
//                    JTextField TypeField = resultSet.getString("Type");
//                    JTextField BedField = resultSet.getString("Bed");
//                    JTextField PriceField = resultSet.getString("Price");
//                    JTextField StatusField = resultSet.getString("Status");
//
//
//                    panel.add(new JLabel("Room NO :"));
//                    panel.add(RoomNOField);
//                    panel.add(new JLabel("Type :"));
//                    panel.add(TypeField);
//                    panel.add(new JLabel("Bed :"));
//                    panel.add(BedField);
//                    panel.add(new JLabel("Price : "));
//                    panel.add(PriceField);
//                    panel.add(new JLabel("Status : "));
//                    panel.add(StatusField);
//
//                    int result = JOptionPane.showConfirmDialog(panel, panel, "Update Room Information" , JOptionPane.OK_CANCEL_OPTION);
//
//                    if (result == JOptionPane.OK_OPTION) {
//
//                        String RoomNO = RoomNOField.getText();
//                        int rNO = Integer.parseInt(RoomNO);
//                        String Type = TypeField.getText();
//                        String Bed = BedField.getText();
//                        String Price = PriceField.getText();
//                        int price = Integer.parseInt(Price);
//                        String Status = StatusField.getText();
//
//
//                        // Update the score in the database
//                        query = "UPDATE room SET Type = ?, Bed = ?, Price = ?, Status = ? WHERE RoomNO = ? ";
//                        PreparedStatement pstmt2 = conn.prepareStatement(query);
//                        pstmt2.setInt(1, rNO);
//                        pstmt2.setString(1+1, Type);
//                        pstmt2.setString(1+2, Bed);
//                        pstmt2.setInt(1+3, price);
//                        pstmt2.setString(1+4, Status);
//
//                        int rowsUpdated = pstmt2.executeUpdate();
//                        if (rowsUpdated == 0) {
//                            JOptionPane.showMessageDialog(panel, "No room found for NO " + rNO , "Error", JOptionPane.ERROR_MESSAGE);
//                        } else {
//                            JOptionPane.showMessageDialog(panel, "Student Information updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
//
//                            //parentFrame.setContentPane(FrameHotel.roomInfo());
//                            panel.revalidate();
//                            panel.repaint();
//
//                        }
//                    }
//                } catch (Exception ex) {
//                    throw new RuntimeException(ex);
//                }


                        Connection connection = null;
                        DefaultTableModel model = (DefaultTableModel) table.getModel();
                        try {
                            connection = DatabaseConnection.getConnection();
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery("SELECT *FROM room");
                            while (resultSet.next()){
                                Object[] row = new Object[table.getColumnCount()];
                                row[0] =resultSet.getInt("Room NO");
                                row[1] =resultSet.getString("Type of room");
                                row[2] =resultSet.getString("Number of bed");
                                row[3] =resultSet.getString("Price of room");
                                row[4] =resultSet.getString("Status");
                                model.addRow(row);

                            }

                            for (int i = 0; i < model.getRowCount(); i++) {
                                int roomNO = Integer.valueOf(model.getValueAt(i, 0).toString());
                                String type = model.getValueAt(i, 1).toString();
                                String bed = model.getValueAt(i, 2).toString();
                                int price = Integer.valueOf(model.getValueAt(i, 3).toString());
                                String status = model.getValueAt(i, 4).toString();

                                String sql = "UPDATE `room` SET `Type`='" + type + "',`Bed`='" + bed + "',`Price`='" + price + "',`Status`='" + status + "' WHERE `RoomNO`='" + roomNO + "'";
                                PreparedStatement pstmt = connection.prepareStatement(sql);

                                pstmt.executeQuery();
                            }
                                JOptionPane.showMessageDialog(panel, "Update a room successfully!");

                            int[] updateRow = statement.executeBatch();
                            System.out.println(updateRow.length);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                        }
                    }
                });

        panel.add(buttonUpdate);

        JButton buttonRefresh = new JButton("Clear");
        buttonRefresh.setBounds(890, 600, 120, 25);
        buttonRefresh.setActionCommand("click");
        buttonRefresh.setBackground(colorHotel);
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
        backButt.setBackground(colorHotel);
        panel.add(backButt);
        backButt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(panel);
                frame.setContentPane(Main.homePage());
                frame.revalidate();
                frame.repaint();
            }
        });

        String[] searchStrings = {"", "RoomNO", "Type","Number","Price","Status"};
        JComboBox FieldOption = new JComboBox(searchStrings);
        FieldOption.setBounds(130,420,130,25);
        panel.add(FieldOption);

        JTextField searchField = new JTextField();
        searchField.setBounds(130,450,190,25);
        panel.add(searchField);

        JButton buttonSearch = new JButton("Search");
        buttonSearch.setBounds(175, 490, 90, 25);
        buttonSearch.setActionCommand("click");
        buttonSearch.setBackground(colorHotel);
        buttonSearch.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent evt) {

                String searchoption = (String) FieldOption.getSelectedItem();
                String url = "jdbc:mysql://localhost:3306/myhotel";
                String username = "root";
                String password = "";

                String search= searchField.getText();

                    if(searchoption == "RoomNO") {
                        String sql = "SELECT FROM room WHERE RoomNO = ? ";

                        if (search.equals("")) {
                            JOptionPane.showMessageDialog(panel, "Please fill NO of room!");
                        }
                        else {
                            try (Connection conn = DatabaseConnection.getConnection();
                                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                                int SearchRNO = Integer.parseInt(search);
                                pstmt.setInt(1, SearchRNO);
                                Class.forName("com.mysql.cj.jdbc.Driver");

                                Connection connection = DriverManager.getConnection(url, username, password);
                                Statement statement = connection.createStatement();

                                ResultSet resultSet = statement.executeQuery("select * from room");
                                while (resultSet.next()) {
                                    if (SearchRNO == resultSet.getInt(1)) {
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
                    else if(searchoption == "Type"){
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
                            JOptionPane.showMessageDialog(panel, "Please fill price of room!");
                        }
                        else {
                            try (Connection conn = DatabaseConnection.getConnection();
                                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                                int SearchPrice = Integer.parseInt(search);
                                pstmt.setInt(4, SearchPrice);
                                Class.forName("com.mysql.cj.jdbc.Driver");

                                Connection connection = DriverManager.getConnection(url, username, password);
                                Statement statement = connection.createStatement();

                                ResultSet resultSet = statement.executeQuery("select * from room WHERE Price = ?");
                                while (resultSet.next()) {
                                    if (SearchPrice == resultSet.getInt(4)) {
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
