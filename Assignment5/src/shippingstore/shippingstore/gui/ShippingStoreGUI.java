package shippingstore.shippingstore.gui;

import oracle.jvm.hotspot.jfr.JFR;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class ShippingStoreGUI  extends JFrame implements ListSelectionListener {

    private JPanel panelShowPackages;
    private JFrame frameAddUser;
    private JPanel panelMenu;
    private JList  listMenu;
    private static final List<String> menuOptions = Arrays.asList("Show All Packages", "Add a New Package",
                                                    "Delete a Package", "Search for a Package", "Show All Users",
                                                    "Add a New User", "Update an Existing User", "Deliver a Package",
                                                    "Show All Transactions") ;

    private final DefaultListModel menuModel;
    private ListSelectionModel listSelectionModel;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               ShippingStoreGUI app = new ShippingStoreGUI();
               //app.pack();
               app.setVisible(true);
            }
        });
    }


    private ShippingStoreGUI() {
        //setting up main window
        setTitle("Shipping Store");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(0,0));


        //setting up main display
        panelShowPackages = new JPanel();
        //panelMainDisplay.setBackground(Color.GREEN);


        //setting up a panel for the sidebar menu
        panelMenu = new JPanel();
        panelMenu.setLayout(new BorderLayout());
        panelMenu.setPreferredSize(new Dimension(200, 600));
        panelMenu.setBackground(Color.WHITE);


        // setting up menu list model
        menuModel = new DefaultListModel();
        for (String mn: menuOptions) {
            menuModel.addElement(mn);
        }

        // setting up list for sidebar menu
        listMenu = new JList();
        listMenu.setModel(menuModel);
        listMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listMenu.setLayoutOrientation(JList.VERTICAL);
        listMenu.addListSelectionListener(this);
        listMenu.setSelectedIndex(0);
//        listMenu.setVisibleRowCount(2);

        // adding list of menu options to scrollable sidebar
        JScrollPane listMenuScroller = new JScrollPane(listMenu);
        listMenu.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createCompoundBorder(
                                BorderFactory.createTitledBorder("Store Main Menu"),
                                BorderFactory.createEmptyBorder(5,5,5,5)),
                        listMenuScroller.getBorder()));
        panelMenu.add(listMenuScroller);


        // adding child components to main window
        add(panelMenu, BorderLayout.WEST);
        add(panelShowPackages, BorderLayout.CENTER);
    }

    class ListListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {


        }


    }

    @Override
    public void valueChanged(ListSelectionEvent lse) {
        if (!lse.getValueIsAdjusting()) {
            switch (listMenu.getSelectedIndex()){
                case 0:
                    // user selects show all packages,
                    //add(panelMainDisplay, BorderLayout.SOUTH);
                    panelShowPackages.add(new JButton("b1"));
                    System.out.println(listMenu.getSelectedIndex());
                    break;
                case 1:
                    //user selects 'add a new package'
//                    frameAddUser = new JFrame();
//                    frameAddUser.setBackground(Color.BLUE);
//                    frameAddUser.add(new Button("b2"));
//                    frameAddUser.pack();
//                    frameAddUser.setVisible(true);
                    System.out.println(listMenu.getSelectedIndex());
                    break;
                case 2:
                    //user selects 'delete a package'
                  //  panelMainDisplay.add(new JButton("b3"));
                    panelShowPackages.setBackground(Color.LIGHT_GRAY);
                    System.out.println(listMenu.getSelectedIndex());
                    break;
                case 3:
                    //user selects 'search for a package'
                    System.out.println(listMenu.getSelectedIndex());
                    break;
                case 4:
                    //user selects 'show all users
                    System.out.println(listMenu.getSelectedIndex());
                    break;
                case 5:
                    //user selects 'add a new user
                    System.out.println(listMenu.getSelectedIndex());
                    break;
                case 6:
                    //user selects 'update an existing user
                    System.out.println(listMenu.getSelectedIndex());
                    break;
                case 7:
                    //user selects 'deliver a package
                    System.out.println(listMenu.getSelectedIndex());
                    break;
                case 8:
                    //user selects 'show all transactions
                    System.out.println(listMenu.getSelectedIndex());
                    break;
            }
        }
//        else {
//            System.out.println("FOR DEBUGGING: Value is adjusting");
//        }

    }









}
