package shippingstore.shippingstore.gui;

import shippingstore.ShippingStore;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class UpdateUser extends JFrame {
    UpdateUser() {
        ShippingStore ss;
        ss = new ShippingStore().readDatabase();

        JFrame updateUserFrame = new JFrame("Shipping Store Database");
        GridBagConstraints c = new GridBagConstraints();

        c.insets = new Insets(5, 5, 5, 5);
        JPanel userFrameTitlePanel = new JPanel(new GridBagLayout());
        JLabel userFrameTitle = new JLabel("Update User Menu");
        JLabel userFrameInstructions = new JLabel("<html>Please search for the user who's <br>details are to be updated by User ID<html>");
        c.gridx = 0;
        c.gridy = 0;
        userFrameTitlePanel.add(userFrameTitle, c);
        c.gridx = 0;
        c.gridy = 1;
        userFrameTitlePanel.add(userFrameInstructions, c);
        updateUserFrame.add(userFrameTitlePanel, BorderLayout.NORTH);

        JPanel userFrameCenterPanel = new JPanel(new GridBagLayout());
        JLabel enterUserId = new JLabel("Enter User ID");
        JTextField enterUserIdText = new JTextField();
        enterUserIdText.setPreferredSize(new Dimension(80, 20));
        c.gridx = 0;
        c.gridy = 0;
        userFrameCenterPanel.add(enterUserId, c);
        c.gridx = 1;
        c.gridy = 0;
        userFrameCenterPanel.add(enterUserIdText, c);
        updateUserFrame.add(userFrameCenterPanel, BorderLayout.CENTER);

        JPanel userFrameButtonPanel = new JPanel(new GridBagLayout());
        userFrameButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JButton nextButton = new JButton("Next");
        JButton cancelButton = new JButton("Cancel");
        JButton showCurrentUsersButton = new JButton("Show Current Users");
        userFrameButtonPanel.add(showCurrentUsersButton);
        userFrameButtonPanel.add(cancelButton);
        userFrameButtonPanel.add(nextButton);
        updateUserFrame.add(userFrameButtonPanel, BorderLayout.SOUTH);

        updateUserFrame.pack();
        updateUserFrame.setVisible(true);

        showCurrentUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowUsers show = new ShowUsers();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateUserFrame.dispose();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = enterUserIdText.getText();
                Integer userIdInt = Integer.parseInt(userId);


                if (!ss.userExists(userIdInt)) {
                    JFrame notFoundFrame = new JFrame();
                    JPanel notFoundPanel = new JPanel();
                    JLabel userNotFound = new JLabel("User not found!");
                    notFoundPanel.add(userNotFound);
                    notFoundFrame.add(notFoundPanel, BorderLayout.CENTER);

                    JPanel buttonPanel = new JPanel();
                    JButton okButton = new JButton("Ok");
                    buttonPanel.add(okButton);
                    notFoundFrame.add(buttonPanel, BorderLayout.SOUTH);

                    notFoundFrame.pack();
                    notFoundFrame.setVisible(true);

                    okButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            notFoundFrame.dispose();
                        }
                    });

                }
                else if (ss.isCustomer(userIdInt)) {
                    JFrame addCustomerFrame = new JFrame("Shipping Store Database");
                    addCustomerFrame.setSize(400, 400);

                    // create panel for title
                    JPanel topPanel = new JPanel();
                    JLabel titleLabel = new JLabel("Update Customer Menu", JLabel.CENTER);
                    topPanel.add(titleLabel);
                    addCustomerFrame.add(topPanel, BorderLayout.NORTH);

                    //create grid for labels and fields
                    JPanel centerPanel = new JPanel(new GridBagLayout());
                    GridBagConstraints c = new GridBagConstraints();
                    c.insets = new Insets(5, 5, 5, 5);

                    //first name
                    JLabel firstNameLabel = new JLabel("First Name: ");
                    JTextField firstNameField = new JTextField();
                    firstNameField.setPreferredSize(new Dimension(120, 20));
                    c.gridx = 0;
                    c.gridy = 0;
                    centerPanel.add(firstNameLabel, c);
                    c.gridx = 1;
                    c.gridy = 0;
                    centerPanel.add(firstNameField, c);

                    //last name
                    JLabel lastNameLabel = new JLabel("Last Name: ", JLabel.CENTER);
                    JTextField lastNameField = new JTextField();
                    lastNameField.setPreferredSize(new Dimension(120, 20));
                    c.gridx = 0;
                    c.gridy = 1;
                    centerPanel.add(lastNameLabel, c);
                    c.gridx = 1;
                    c.gridy = 1;
                    centerPanel.add(lastNameField, c);

                    //phone number
                    JLabel phoneNumberLabel = new JLabel("Phone Number: ");
                    JTextField phoneNumberField = new JTextField();
                    phoneNumberField.setPreferredSize(new Dimension(120, 20));
                    c.gridx = 0;
                    c.gridy = 2;
                    centerPanel.add(phoneNumberLabel, c);
                    c.gridx = 1;
                    c.gridy = 2;
                    centerPanel.add(phoneNumberField, c);

                    //address
                    JLabel addressLabel = new JLabel("Address: ");
                    JTextField addressField = new JTextField();
                    addressField.setPreferredSize(new Dimension(200, 20));
                    c.gridx = 0;
                    c.gridy = 3;
                    centerPanel.add(addressLabel, c);
                    c.gridx = 1;
                    c.gridy = 3;
                    centerPanel.add(addressField, c);
                    addCustomerFrame.add(centerPanel, BorderLayout.WEST);

                    //create next and cancel buttons
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                    JButton nextButton = new JButton("Next");
                    JButton cancelButton = new JButton("Cancel");
                    buttonPanel.add(cancelButton);
                    buttonPanel.add(nextButton);
                    addCustomerFrame.add(buttonPanel, BorderLayout.SOUTH);

                    addCustomerFrame.pack();
                    addCustomerFrame.setVisible(true);

                    nextButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String firstName = firstNameField.getText();
                            String lastName = lastNameField.getText();
                            String phoneNumber = phoneNumberField.getText();
                            String address = addressField.getText();

                            ss.updateCustomer(userIdInt, firstName, lastName, phoneNumber, address);
                            ss.writeDatabase();

                            JFrame successWindow = new JFrame();
                            JLabel successLabel = new JLabel("Customer successfully updated!");
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

                            addCustomerFrame.dispose();
                            updateUserFrame.dispose();

                        }
                    });

                    cancelButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            addCustomerFrame.dispose();
                        }
                    });

                }
                else {
                    JFrame updateEmployeeFrame = new JFrame("Shipping Store Database");
                    JPanel titlePanel = new JPanel();
                    JLabel titleLabel = new JLabel("Update Employee Menu");

                    titlePanel.add(titleLabel);
                    updateEmployeeFrame.add(titlePanel, BorderLayout.NORTH);

                    JPanel centerPanel = new JPanel(new GridBagLayout());
                    GridBagConstraints c = new GridBagConstraints();

                    c.insets = new Insets(5, 5, 5, 5);

                    //first name
                    JLabel firstNameLabel = new JLabel("First Name: ");
                    JTextField firstNameField = new JTextField();
                    firstNameField.setPreferredSize(new Dimension(120, 20));
                    c.gridx = 0;
                    c.gridy = 0;
                    centerPanel.add(firstNameLabel, c);
                    c.gridx = 1;
                    c.gridy = 0;
                    centerPanel.add(firstNameField, c);

                    //last name
                    JLabel lastNameLabel = new JLabel("Last Name: ", JLabel.CENTER);
                    JTextField lastNameField = new JTextField();
                    lastNameField.setPreferredSize(new Dimension(120, 20));
                    c.gridx = 0;
                    c.gridy = 1;
                    centerPanel.add(lastNameLabel, c);
                    c.gridx = 1;
                    c.gridy = 1;
                    centerPanel.add(lastNameField, c);

                    //social
                    JLabel socialLabel = new JLabel("<html>Social:<br>(must be 9 digits)<html>");
                    JTextField socialField = new JTextField();
                    socialField.setPreferredSize(new Dimension(120, 20));
                    c.gridx = 0;
                    c.gridy = 2;
                    centerPanel.add(socialLabel, c);
                    c.gridx = 1;
                    c.gridy = 2;
                    centerPanel.add(socialField, c);

                    //salary
                    JLabel salaryLabel = new JLabel("Salary: ");
                    JTextField salaryField = new JTextField();
                    salaryField.setPreferredSize(new Dimension(120, 20));
                    c.gridx = 0;
                    c.gridy = 3;
                    centerPanel.add(salaryLabel, c);
                    c.gridx = 1;
                    c.gridy = 3;
                    centerPanel.add(salaryField, c);
                    updateEmployeeFrame.add(centerPanel, BorderLayout.WEST);

                    //bank number
                    JLabel bankNumberLabel = new JLabel("Bank #: ");
                    JTextField bankNumberField = new JTextField();
                    bankNumberField.setPreferredSize(new Dimension(120, 20));
                    c.gridx = 0;
                    c.gridy = 4;
                    centerPanel.add(bankNumberLabel, c);
                    c.gridx = 1;
                    c.gridy = 4;
                    centerPanel.add(bankNumberField, c);
                    updateEmployeeFrame.add(centerPanel, BorderLayout.WEST);

                    //create next and cancel buttons
                    JPanel buttonPanel = new JPanel();
                    buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                    JButton nextButton = new JButton("Next");
                    JButton cancelButton = new JButton("Cancel");
                    buttonPanel.add(cancelButton);
                    buttonPanel.add(nextButton);
                    updateEmployeeFrame.add(buttonPanel, BorderLayout.SOUTH);

                    updateEmployeeFrame.pack();
                    updateEmployeeFrame.setVisible(true);

                    nextButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String firstName = firstNameField.getText();
                            String lastName = lastNameField.getText();
                            String social = socialField.getText();
                            String salary = salaryField.getText();
                            String bankNumber = bankNumberField.getText();

                            Integer socialInt = new Integer(Integer.valueOf(social));
                            Float salaryFloat = new Float(Float.valueOf(salary));
                            Integer bankNumberInt = new Integer(Integer.valueOf(bankNumber));

                            ss.updateEmployee(userIdInt, firstName, lastName, socialInt, salaryFloat, bankNumberInt);
                            ss.writeDatabase();

                            JFrame successWindow = new JFrame();
                            JLabel successLabel = new JLabel("Employee successfully updated!");
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

                            updateEmployeeFrame.dispose();
                            updateUserFrame.dispose();
                        }
                    });
                    cancelButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            updateEmployeeFrame.dispose();
                        }
                    });

                    cancelButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            updateUserFrame.dispose();
                        }
                    });
                }
            }
        });
    }
}
