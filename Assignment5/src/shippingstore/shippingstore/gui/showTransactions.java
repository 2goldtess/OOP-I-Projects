package shippingstore.shippingstore.gui;

import shippingstore.*;
import shippingstore.Box;
import shippingstore.Transaction;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class showTransactions extends JFrame {
        //JFrame transactionFrame = new JFrame();

        JTable transactionsTable;
        String[] columnNames = {"Customer ID", "Employee ID", "Tracking Number", "Shipping Date", "Delivery Date", "Price"};
        ArrayList<Transaction> transactions;

        ShippingStore ss;

        showTransactions() {
            setSize(1000, 500);
            setTitle("Transaction List");
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            ss = new ShippingStore().readDatabase();
            transactions = (ArrayList) ss.getTransactions();

            transactionsTable = new JTable();
            transactionsTable.setModel(new DefaultTableModel(new Object[][] {}, columnNames));
            transactionsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            transactionsTable.getColumnModel().getColumn(0).setPreferredWidth(80);
            transactionsTable.getColumnModel().getColumn(1).setPreferredWidth(80);
            transactionsTable.getColumnModel().getColumn(2).setPreferredWidth(160);
            transactionsTable.getColumnModel().getColumn(3).setPreferredWidth(250);
            transactionsTable.getColumnModel().getColumn(4).setPreferredWidth(250);
            transactionsTable.getColumnModel().getColumn(5).setPreferredWidth(80);

            addRowToJTable();

            JPanel buttonPanel = new JPanel();
            buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
            JButton okButton = new JButton("Ok");
            okButton.setSize(30, 30);
            buttonPanel.add(okButton);

            add(new JScrollPane(transactionsTable));
            add(buttonPanel, BorderLayout.SOUTH);

            setVisible(true);


//
//            okButton.addActionListener(new ActionListener() {
//                @Override
//                public void actionPerformed(ActionEvent e) {
//                    transactionFrame.dispose();
//                }
//            });

        }

        public void addRowToJTable() {

            DefaultTableModel tableModel = (DefaultTableModel) transactionsTable.getModel();
            Object rowData[] = new Object[6];

            for (Transaction t: transactions) {
                rowData[0] = t.getCustomerId();
                rowData[1] = t.getEmployeeId();
                rowData[2] = t.getPtn();
                rowData[3] = t.getShippingDate();
                rowData[4] = t.getDeliveryDate();
                rowData[5] = t.getPrice();

                tableModel.addRow(rowData);
            }
        }
    }
