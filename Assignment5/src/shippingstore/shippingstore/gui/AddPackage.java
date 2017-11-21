package shippingstore.shippingstore.gui;

import shippingstore.Package;
import shippingstore.ShippingStore;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;


public class AddPackage extends JFrame implements ItemListener, ActionListener {
    private static final String[] packageTypeOptions = {"Box", "Crate", "Drum", "Envelope"};
    private static final String[] specificationOptions = {"Fragile", "Books", "Catalogs", "Do-Not-Bend", "N/A"};
    private static final String[] mailingClassOptions = {"First-Class", "Priority", "Retail", "Ground", "Metro"};
    private static final String errorMessage = "Invalid entry: (Empty fields not allowed) Please check the form and try again.";
    private static final String successMessage = "Success: New package added to the database";
    private JComboBox comboBoxPackageType;
    private JTextField textFieldTrackingNumber;
    private JComboBox comboBoxSpecification;
    private JComboBox comboBoxMailingClass;
    private JPanel panelOtherDetails;
    private JPanel panelSubmit;
    private JTextField textFieldOtherDetails1;
    private JTextField textFieldOtherDetails2;
    private JLabel labelOtherDetails1;
    private JLabel labelOtherDetails2;
    private ShippingStore ss;
    private JButton btnSubmit;
    private JButton btnReset;
    private String trackingNumber;
    private String type;
    private String specification;
    private String mailingClass;
    private String otherdetails1;
    private String otherdetails2;

    ArrayList<Package> packages;

    AddPackage() {
        setSize(625, 225);
        setTitle("Add a New Package");
        setLayout(new FormLayout());

        // shipping store object;
        ss = new ShippingStore().readDatabase();
        packages = (ArrayList<Package>) ss.getPackages();

        // tracking number field
        add(new JLabel("Tracking Number"));
        textFieldTrackingNumber = new JTextField(10);
        textFieldTrackingNumber.setText(randomlyGeneratedTrackingNumber());
        textFieldTrackingNumber.addActionListener(this);
        add(textFieldTrackingNumber);

        // package type field
        add(new JLabel("Package Type"));
        comboBoxPackageType = new JComboBox(packageTypeOptions);
        comboBoxPackageType.setSelectedIndex(-1);
        comboBoxPackageType.addItemListener(this);
        add(comboBoxPackageType);


        // specification field
        add(new JLabel("Specification"));
        comboBoxSpecification = new JComboBox(specificationOptions);
        comboBoxSpecification.setSelectedIndex(-1);
        add(comboBoxSpecification);

        // mailing class field
        add(new JLabel("Mailing Class"));
        comboBoxMailingClass = new JComboBox(mailingClassOptions);
        comboBoxMailingClass.setSelectedIndex(-1);
        add(comboBoxMailingClass);

        //other details fields
        add(new JLabel("Other Details"));
        panelOtherDetails = new JPanel();
        add(panelOtherDetails);

        // submit panel
        add(new JLabel(""));
        panelSubmit = new JPanel();
        add(panelSubmit);

        // buttons
        btnSubmit = new JButton("Add Package");
        btnSubmit.addActionListener(this);
        btnReset = new JButton("Reset");
        btnReset.addActionListener(this);

        System.out.println((isDuplicateTrackingNumber("0000000")));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setVisible(true);
    }


    @Override
    public void itemStateChanged(ItemEvent e) {
        if (comboBoxPackageType.getSelectedIndex() != -1) {

            switch (comboBoxPackageType.getSelectedIndex()) {
                case 0:
                    //other detail 1
                    labelOtherDetails1 = new JLabel("Height (inches)");
                    panelOtherDetails.add(labelOtherDetails1);
                    textFieldOtherDetails1 = new JTextField(10);
                    panelOtherDetails.add(textFieldOtherDetails1);

                    //other detail 2
                    labelOtherDetails2 = new JLabel("Width (inches)");
                    panelOtherDetails.add(labelOtherDetails2);
                    textFieldOtherDetails2 = new JTextField(10);
                    panelOtherDetails.add(textFieldOtherDetails2);

                    break;
                case 1:
                    //other detail 1
                    labelOtherDetails1 = new JLabel("Load Weight (inches)");
                    panelOtherDetails.add(labelOtherDetails1);
                    textFieldOtherDetails1 = new JTextField(10);
                    panelOtherDetails.add(textFieldOtherDetails1);

                    //other detail 2
                    labelOtherDetails2 = new JLabel("Content");
                    panelOtherDetails.add(labelOtherDetails2);
                    textFieldOtherDetails2 = new JTextField(10);
                    panelOtherDetails.add(textFieldOtherDetails2);
                    break;
                case 2:
                    //other detail 1
                    labelOtherDetails1 = new JLabel("Material (Plastic, Fiber)");
                    panelOtherDetails.add(labelOtherDetails1);
                    textFieldOtherDetails1 = new JTextField(10);
                    panelOtherDetails.add(textFieldOtherDetails1);

                    //other detail 2
                    labelOtherDetails2 = new JLabel("Diameter (inches)");
                    panelOtherDetails.add(labelOtherDetails2);
                    textFieldOtherDetails2 = new JTextField(10);
                    panelOtherDetails.add(textFieldOtherDetails2);
                    break;
                case 3:
                    //other detail 1
                    labelOtherDetails1 = new JLabel("Height (inches)");
                    panelOtherDetails.add(labelOtherDetails1);
                    textFieldOtherDetails1 = new JTextField(10);
                    panelOtherDetails.add(textFieldOtherDetails1);

                    //other detail 2
                    labelOtherDetails2 = new JLabel("Width (inches)");
                    panelOtherDetails.add(labelOtherDetails2);
                    textFieldOtherDetails2 = new JTextField(10);
                    panelOtherDetails.add(textFieldOtherDetails2);
                    break;
            }

            comboBoxPackageType.setEnabled(false);
            panelSubmit.add(btnSubmit);
            panelSubmit.add(btnReset);
            repaint();
            setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSubmit) {
            trackingNumber = textFieldTrackingNumber.getText();
            type = (String) comboBoxPackageType.getItemAt(comboBoxPackageType.getSelectedIndex());
            specification = (String) comboBoxSpecification.getItemAt(comboBoxSpecification.getSelectedIndex());
            mailingClass = (String) comboBoxMailingClass.getItemAt(comboBoxMailingClass.getSelectedIndex());
            otherdetails1 =  textFieldOtherDetails1.getText();
            otherdetails2 =  textFieldOtherDetails2.getText();

            //validating tracking number
            if (isDuplicateTrackingNumber(trackingNumber)) {
                JOptionPane.showMessageDialog(new JFrame(), "Package with the specified tracking number already exists. Please change the tracking number and try again.");
                return;
            }

            if (!trackingNumber.matches("[A-Za-z0-9]{5}")) {
                JOptionPane.showMessageDialog(new JFrame(), "Invalid entry for the tracking number. (Must be of length 5)");
                return;
            }

            switch (comboBoxPackageType.getSelectedIndex()) {
                case 0:
                    try {
                        ss.addBox(trackingNumber, specification, mailingClass, Integer.parseInt(otherdetails1), Integer.parseInt(otherdetails2));
                        JOptionPane.showMessageDialog(new JFrame(), successMessage);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(new JFrame(), errorMessage);
                    }
                    break;
                case 1:
                    try {
                        ss.addCrate(trackingNumber, specification, mailingClass,Float.parseFloat(otherdetails1), otherdetails2);
                        JOptionPane.showMessageDialog(new JFrame(), successMessage);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(new JFrame(), errorMessage);
                    }
                    break;
                case 2:
                    try {
                        //validate that details entered is plastic or fiber only
                        if ((otherdetails1.equalsIgnoreCase("Plastic")) || otherdetails1.equalsIgnoreCase("Fiber")) {
                            ss.addDrum(trackingNumber, specification, mailingClass, otherdetails1, Float.parseFloat(otherdetails2));
                            JOptionPane.showMessageDialog(new JFrame(), successMessage);
                        } else {
                            JOptionPane.showMessageDialog(new JFrame(), "Please enter 'Plastic or Fiber'");
                            return;
                        }
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(new JFrame(), errorMessage);
                    }
                    break;
                case 3:
                    try {
                        ss.addEnvelope(trackingNumber, specification, mailingClass, Integer.parseInt(otherdetails1), Integer.parseInt(otherdetails2));
                        JOptionPane.showMessageDialog(new JFrame(), successMessage);

                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(new JFrame(), errorMessage);
                    }
                    break;
            }
            ss.writeDatabase();
        }
        if (e.getSource() == btnReset) {
            textFieldTrackingNumber.setText(randomlyGeneratedTrackingNumber());
            comboBoxPackageType.setSelectedIndex(-1);
            comboBoxSpecification.setSelectedIndex(-1);
            comboBoxMailingClass.setSelectedIndex(-1);
            textFieldOtherDetails1.setText("");
            textFieldOtherDetails2.setText("");

            comboBoxPackageType.setEnabled(true);
            panelOtherDetails.remove(textFieldOtherDetails1);
            panelOtherDetails.remove(textFieldOtherDetails2);
            panelOtherDetails.remove(labelOtherDetails1);
            panelOtherDetails.remove(labelOtherDetails2);
            repaint();
            setVisible(true);
        }
    }

    public String randomlyGeneratedTrackingNumber() {
        String text = "";
        String possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        long randomNumber = 0;
           for (int i = 0; i < 5; i++) {
               text += possible.charAt((int) Math.floor(Math.random() * possible.length()));
           }
        return text;
    }

    public boolean isDuplicateTrackingNumber(String trackingNumber) {
            for (Package p: packages) {
                if (p.getPtn().equalsIgnoreCase(trackingNumber))
                    return true;
            }
            return false;
    }
}


