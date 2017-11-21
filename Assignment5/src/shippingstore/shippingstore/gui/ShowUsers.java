package shippingstore.shippingstore.gui;

import shippingstore.*;
import shippingstore.Box;
import shippingstore.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ShowUsers extends JFrame {
    //JFrame tableFrame = new JFrame();

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    JTable usersTable;
    String[] columnNames = {"User ID", "First Name", "Last Name", "Other Details"};
    ArrayList<User> users;


    ShowUsers() {
        setSize(905, 500);
        setTitle("Show All Users");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ShippingStore ss = new ShippingStore().readDatabase();
        users = (ArrayList) ss.getUsers();

        usersTable = new JTable();
        usersTable.setModel(new DefaultTableModel(new Object[][]{}, columnNames));
        usersTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        usersTable.getColumnModel().getColumn(0).setPreferredWidth(80);
        usersTable.getColumnModel().getColumn(1).setPreferredWidth(160);
        usersTable.getColumnModel().getColumn(2).setPreferredWidth(160);
        usersTable.getColumnModel().getColumn(3).setPreferredWidth(500);

        addRowToJTable();

//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
//        JButton okButton = new JButton("Ok");
//        okButton.setSize(30, 30);
//        buttonPanel.add(okButton);

        add(new JScrollPane(usersTable));
        //tableFrame.add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);


//        okButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                tableFrame.dispose();
//            }
//        });

    }

    public void addRowToJTable() {

        DefaultTableModel tableModel = (DefaultTableModel) usersTable.getModel();
        Object rowData[] = new Object[4];

        for (User u : users) {
            rowData[0] = u.getId();
            rowData[1] = u.getFirstName();
            rowData[2] = u.getLastName();
            rowData[3] = getOtherUserInfo(u);

            tableModel.addRow(rowData);
        }
    }

    public String getOtherUserInfo(User u) {
        if(u instanceof Employee)
            return "Social: " + ((Employee) u).getSocialSecurityNumber() + ", Salary: " + ((Employee) u).getMonthlySalary() + ", Bank #:" + ((Employee) u).getBankAccountNumber();
        else
            return "Phone Number: " + ((Customer) u).getPhoneNumber() + ", Address: " + ((Customer) u).getAddress();
        }
}

