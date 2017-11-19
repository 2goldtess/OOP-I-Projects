package shippingstore.shippingstore.gui;

import shippingstore.Package;
import shippingstore.ShippingStore;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

public class ShowPackages extends JFrame{
    JTable packagesTable;
    String[] columnNames = {"Tracking #", "Package Type", "Specification", "Mailing Class", "Other Details"};
    ArrayList<Package> packages;

    ShippingStore ss;



    ShowPackages() {
        setSize(300, 400);
        setTitle("Package List");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ss = new ShippingStore().readDatabase();
        packages = (ArrayList) ss.getPackages();

        packagesTable = new JTable();
        packagesTable.setModel(new DefaultTableModel(new Object[][] {}, columnNames));

        addRowToJTable();

        add(packagesTable);







        setVisible(true);

    }

    public void addRowToJTable() {

        DefaultTableModel tableModel = (DefaultTableModel) packagesTable.getModel();
        Object rowData[] = new Object[5];

        for (int i = 0; i < packages.size(); i++) {
            rowData[0] = packages.get(i).getPtn();
            rowData[1] = "";
            rowData[2] = packages.get(i).getSpecification();
            rowData[3] = packages.get(i).getMailingClass();
            rowData[4] = "";
            tableModel.addRow(rowData);
        }
//        System.out.println(packages);

    }
}

