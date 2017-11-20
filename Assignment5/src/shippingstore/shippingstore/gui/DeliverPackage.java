package shippingstore.shippingstore.gui;

import shippingstore.BadInputException;
import shippingstore.Package;
import shippingstore.ShippingStore;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;

public class DeliverPackage extends JFrame {

    private Package packageFound;

    DeliverPackage() {
        ShippingStore ss;
        ss = new ShippingStore().readDatabase();

        Date currentDate = new Date(System.currentTimeMillis());

        JFrame deliverPackageFrame = new JFrame("Shipping Store Database");
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Deliver Package Menu");

        titlePanel.add(titleLabel);
        deliverPackageFrame.add(titlePanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);

        JTextField customerIdField = new JTextField();
        JLabel customerIdLabel = new JLabel("Customer ID:");
        customerIdField.setPreferredSize(new Dimension(120, 20));
        c.gridx = 0;
        c.gridy = 0;
        centerPanel.add(customerIdLabel, c);
        c.gridx = 1;
        c.gridy = 0;
        centerPanel.add(customerIdField, c);

        JTextField employeeIdField = new JTextField();
        JLabel employeeIdLabel = new JLabel("Employee ID:");
        employeeIdField.setPreferredSize(new Dimension(120, 20));
        c.gridx = 0;
        c.gridy = 1;
        centerPanel.add(employeeIdLabel, c);
        c.gridx = 1;
        c.gridy = 1;
        centerPanel.add(employeeIdField, c);

        JTextField trackingNumberField = new JTextField();
        JLabel trackingNumberLabel = new JLabel("Tracking Number:");
        trackingNumberField.setPreferredSize(new Dimension(120, 20));
        c.gridx = 0;
        c.gridy = 2;
        centerPanel.add(trackingNumberLabel, c);
        c.gridx = 1;
        c.gridy = 2;
        centerPanel.add(trackingNumberField, c);

        JTextField transactionCostField = new JTextField();
        JLabel transactionCostLabel = new JLabel("Transaction Cost:");
        transactionCostField.setPreferredSize(new Dimension(120, 20));
        c.gridx = 0;
        c.gridy = 3;
        centerPanel.add(transactionCostLabel, c);
        c.gridx = 1;
        c.gridy = 3;
        centerPanel.add(transactionCostField, c);

        deliverPackageFrame.add(centerPanel, BorderLayout.WEST);

        JPanel deliverPackageButtonPanel = new JPanel(new GridBagLayout());
        deliverPackageButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton nextButton = new JButton("Next");
        JButton cancelButton = new JButton("Cancel");
        JButton showCurrentUsersButton = new JButton("Show Current Users");
        JButton showCurrentPackagesButton = new JButton("Show Current Packages");
        deliverPackageButtonPanel.add(showCurrentPackagesButton);
        deliverPackageButtonPanel.add(showCurrentUsersButton);
        deliverPackageButtonPanel.add(cancelButton);
        deliverPackageButtonPanel.add(nextButton);
        deliverPackageFrame.add(deliverPackageButtonPanel, BorderLayout.SOUTH);

        deliverPackageFrame.pack();
        deliverPackageFrame.setVisible(true);

        showCurrentPackagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowPackages show = new ShowPackages();
            }
        });

        showCurrentUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowUsers show = new ShowUsers();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deliverPackageFrame.dispose();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean completeTransaction = true;

                String employeeId = employeeIdField.getText();
                String customerId = customerIdField.getText();
                String trackingNumber = trackingNumberField.getText();
                String transactionCost = transactionCostField.getText();

                Integer employeeIdInt = Integer.parseInt(employeeId);
                Integer customerIdInt = Integer.parseInt(customerId);
                Float cost = Float.parseFloat(transactionCost);

                if(!ss.userExists(employeeIdInt)) {
                    JOptionPane.showMessageDialog(new JFrame(), "Employee does not exist.");
                    completeTransaction = false;
                }

                if(!ss.userExists(customerIdInt)) {
                    JOptionPane.showMessageDialog(new JFrame(), "Customer does not exist.");
                    completeTransaction = false;
                }

                if (cost < 0.0f) {
                    JOptionPane.showMessageDialog(new JFrame(), "Price cannot be negative.");
                    completeTransaction = false;
                }

                if(ss.findPackage(trackingNumber) == null) {
                    JOptionPane.showMessageDialog(new JFrame(), "Package not found. Please check the tracking number and try again.");
                    completeTransaction = false;
                }

                if(completeTransaction) {
                    ss.addShppingTransaction(customerIdInt, employeeIdInt, trackingNumber, currentDate, currentDate, cost);
                    ss.deletePackage(trackingNumber);
                    ss.writeDatabase();

                    JFrame successWindow = new JFrame();
                    JLabel successLabel = new JLabel("Transaction Successfully Completed!");
                    JButton okButton = new JButton("Ok");
                    okButton.setSize(30, 30);

                    JPanel successPanel = new JPanel(new GridBagLayout());
                    GridBagConstraints c = new GridBagConstraints();

                    c.gridx = 0;
                    c.gridy = 0;
                    successPanel.add(successLabel, c);

                    c.gridx = 0;
                    c.gridy = 1;
                    successPanel.add(okButton, c);

                    successWindow.add(successPanel, BorderLayout.CENTER);
                    successWindow.pack();
                    successWindow.setVisible(true);
                    okButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            successWindow.dispose();
                        }
                    });
                }
            deliverPackageFrame.dispose();
            }
        });

    }
}
