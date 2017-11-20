package shippingstore.shippingstore.gui;

import shippingstore.ShippingStore;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;




public class AddUser extends JFrame {
    //user selects 'add a new user

    AddUser() {
        ShippingStore ss;
        ss = new ShippingStore().readDatabase();

        JFrame addUserFrame = new JFrame("Shipping Store Database");

        addUserFrame.setSize(250, 250);
        addUserFrame.setLayout(new BorderLayout());
        addUserFrame.setResizable(false);

        //create a panel for the Top of the menu
        JPanel titlePanel = new JPanel(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        JLabel textLabel = new JLabel("Add User Menu");


        JLabel instructionLabel = new JLabel("<html>Which type of user would<br>   you like to add?</html>");
        c.gridx = 0;
        c.gridy = 1;
        titlePanel.add(instructionLabel, c);
        c.gridx = 0;
        c.gridy = 0;
        titlePanel.add(textLabel, c);
        addUserFrame.add(titlePanel, BorderLayout.NORTH);


        //create buttons for the customer option
        JPanel buttonPanel = new JPanel();

        JButton customerButton = new JButton("Customer");
        JButton employeeButton = new JButton("Employee");

        customerButton.setSize(40, 25);
        employeeButton.setSize(40, 25);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(customerButton);
        buttonPanel.add(employeeButton);

        addUserFrame.add(buttonPanel, BorderLayout.CENTER);
        addUserFrame.pack();
        addUserFrame.setVisible(true);

        //add listeners for the two buttons
        customerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //create frame
                JFrame addCustomerFrame = new JFrame("Shipping Store Database");
                addCustomerFrame.setSize(400, 400);

                // create panel for title
                JPanel topPanel = new JPanel();
                JLabel titleLabel = new JLabel("Add Customer Menu", JLabel.CENTER);
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

                        ss.addCustomer(firstName, lastName, phoneNumber, address);
                        ss.writeDatabase();

                        JFrame successWindow = new JFrame();
                        JLabel successLabel = new JLabel("Customer successfully added!");
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
                        addUserFrame.dispose();

                    }
                });

                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addCustomerFrame.dispose();
                    }
                });
            }
        });
        employeeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame addEmployeeFrame = new JFrame("Shipping Store Database");
                JPanel titlePanel = new JPanel();
                JLabel titleLabel = new JLabel("Add Employee Menu");

                titlePanel.add(titleLabel);
                addEmployeeFrame.add(titlePanel, BorderLayout.NORTH);

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
                addEmployeeFrame.add(centerPanel, BorderLayout.WEST);

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
                addEmployeeFrame.add(centerPanel, BorderLayout.WEST);

                //create next and cancel buttons
                JPanel buttonPanel = new JPanel();
                buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
                JButton nextButton = new JButton("Next");
                JButton cancelButton = new JButton("Cancel");
                buttonPanel.add(cancelButton);
                buttonPanel.add(nextButton);
                addEmployeeFrame.add(buttonPanel, BorderLayout.SOUTH);

                addEmployeeFrame.pack();
                addEmployeeFrame.setVisible(true);

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

                        ss.addEmployee(firstName, lastName, socialInt, salaryFloat, bankNumberInt);
                        ss.writeDatabase();

                        JFrame successWindow = new JFrame();
                        JLabel successLabel = new JLabel("Employee successfully added!");
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

                        addEmployeeFrame.dispose();
                        addUserFrame.dispose();
                    }
                });
                cancelButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addEmployeeFrame.dispose();
                    }
                });
            }
        });

    }
}
