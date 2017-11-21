package shippingstore.shippingstore.gui;

import shippingstore.ShippingStore;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Date;
import java.util.logging.Logger;

public class DeliverPackage extends JFrame {

    private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    DeliverPackage() {
        ShippingStore ss;
        ss = new ShippingStore().readDatabase();

        Date currentDate = new Date(System.currentTimeMillis());

        JFrame deliverPackageFrame = new JFrame("Deliver a Package");
        JPanel titlePanel = new JPanel();

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
        JButton completeTransactionButton = new JButton("Complete Transaction");
        JButton finishButton = new JButton("Finish");
        JButton showCurrentUsersButton = new JButton("Show All Users");
        JButton showCurrentPackagesButton = new JButton("Show All Packages");
        deliverPackageButtonPanel.add(showCurrentPackagesButton);
        deliverPackageButtonPanel.add(showCurrentUsersButton);
        deliverPackageButtonPanel.add(completeTransactionButton);
        deliverPackageButtonPanel.add(finishButton);
        deliverPackageFrame.add(deliverPackageButtonPanel, BorderLayout.SOUTH);

        deliverPackageFrame.pack();
        deliverPackageFrame.setVisible(true);

        showCurrentPackagesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowPackages sp = new ShowPackages();
                sp.setLocation(deliverPackageFrame.getX(), deliverPackageFrame.getY()+192);
                LOGGER.info("User selects: Show All Packages option");
            }
        });

        showCurrentUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                ShowUsers su = new ShowUsers();
                su.setLocation(deliverPackageFrame.getX(), deliverPackageFrame.getY()+192);
                LOGGER.info("User selects: Show All User option");
            }
        });

        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deliverPackageFrame.dispose();
                LOGGER.info("User selects: Cancel deliver package option");
            }
        });

        completeTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean completeTransaction = true;

                String employeeId = employeeIdField.getText();
                String customerId = customerIdField.getText();
                String trackingNumber = trackingNumberField.getText();
                String transactionCost = transactionCostField.getText();

                try {
                    Integer employeeIdInt = Integer.parseInt(employeeId);
                    Integer customerIdInt = Integer.parseInt(customerId);
                    Float cost = Float.parseFloat(transactionCost);

                    if (!ss.userExists(employeeIdInt)) {
                        JOptionPane.showMessageDialog(new JFrame(), "Employee does not exist.");
                        completeTransaction = false;
                    }

                    if (!ss.userExists(customerIdInt)) {
                        JOptionPane.showMessageDialog(new JFrame(), "Customer does not exist.");
                        completeTransaction = false;
                    }

                    if (cost < 0.0f) {
                        JOptionPane.showMessageDialog(new JFrame(), "Price cannot be negative.");
                        completeTransaction = false;
                    }

                    if (ss.findPackage(trackingNumber) == null) {
                        JOptionPane.showMessageDialog(new JFrame(), "Package not found. Please check the tracking number and try again.");
                        completeTransaction = false;
                    }

                    if (completeTransaction) {
                        try {
                            ss.addShppingTransaction(customerIdInt, employeeIdInt, trackingNumber, currentDate, currentDate, cost);
                            LOGGER.info("Package delivered: Shipping transactions completed, the transactions was added to the transactions list.");
                            ss.deletePackage(trackingNumber);
                            LOGGER.warning("Package with tracking number: '" + trackingNumber +"' was deleted from the packages list.");
                            ss.writeDatabase();
                            LOGGER.info("Saving changes to ShippingStore db.");
                        } catch (Exception e1) {
                            JOptionPane.showMessageDialog(new JFrame(), "An error occurred while attempting to complete the transaction.");
                            LOGGER.info( "An error occurred while attempting to complete the transaction.");
                        }

                        JOptionPane.showMessageDialog(new JFrame(), "Success: Transaction was completed.");
                    }
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(new JFrame(), "Empty Field or Invalid Entry detected. Please check the information you entered and try again.");
                    LOGGER.severe("A NumberFormatException occurred while attempting to add save the package to the packages");
                }
            }
        });
    }
}
