package shippingstore.shippingstore.gui;

import shippingstore.ShippingStore;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class AddPackage extends JFrame implements ItemListener, ActionListener {
    private static final String[] packageTypeOptions = {"Box", "Crate", "Drum", "Envelope"};
    private static final String[] specificationOptions = {"Fragile", "Books", "Catalogs", "Do-Not-Bend", "N/A"};
    private static final String[] mailingClassOptions = {"First-Class", "Priority", "Retail", "Ground", "Metro"};
    private static final String errorMessage = "Invalid entry: (Empty fields not allowed) Please check the form and try again.";
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

    AddPackage() {
        setSize(650, 250);
        setTitle("Add a New Package");
        setLayout(new FormLayout());

        // shipping store object;
        ss = new ShippingStore().readDatabase();

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

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

//            if (checkForDuplicateTrackingNumbers(trackingNumber));
//                JOptionPane.showMessageDialog(new Frame(), "Package with the specified tracking number already exists. Please change your entry and try again.");
            switch (comboBoxPackageType.getSelectedIndex()) {
                case 0:
                    try {
                        ss.addBox(trackingNumber, specification, mailingClass, Integer.parseInt(otherdetails1), Integer.parseInt(otherdetails2));
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(new Frame(), errorMessage);
                    }
                    break;
                case 1:
                    try {
                        ss.addCrate(trackingNumber, specification, mailingClass,Float.parseFloat(otherdetails1), otherdetails2);
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(new Frame(), errorMessage);
                    }
                    break;
                case 2:
                    try {
                        ss.addDrum(trackingNumber, specification, mailingClass, otherdetails1, Float.parseFloat(otherdetails2));
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(new Frame(), errorMessage);
                    }
                    break;
                case 3:
                    try {
                        ss.addEnvelope(trackingNumber, specification, mailingClass, Integer.parseInt(otherdetails1), Integer.parseInt(otherdetails2));
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(new Frame(), errorMessage);
                    }
                    break;
            }
            ss.writeDatabase();
            System.out.println(trackingNumber + " " + type + " " + specification + " " + mailingClass + " " + otherdetails1 + " " + otherdetails2) ;
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

//    public boolean checkForDuplicateTrackingNumbers(String trackingNumber) {
//        Package p = ss.findPackage(trackingNumber);
//
//        if (p.getPtn().equalsIgnoreCase(trackingNumber))
//            return true;
//        return false;
//    }
}


