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
        setSize(1200, 600);
        setTitle("Package List");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ss = new ShippingStore().readDatabase();
        packages = (ArrayList) ss.getPackages();

        packagesTable = new JTable();
        packagesTable.setModel(new DefaultTableModel(new Object[][] {}, columnNames));

        addRowToJTable();

        add(new JScrollPane(packagesTable));

        setVisible(true);
    }

    public void addRowToJTable() {

        DefaultTableModel tableModel = (DefaultTableModel) packagesTable.getModel();
        Object rowData[] = new Object[5];

        for (int i = 0; i < packages.size(); i++) {
            rowData[0] = packages.get(i).getPtn();
            rowData[1] = splitFormattedText(packages.get(i).getFormattedText(), 0);
            rowData[2] = packages.get(i).getSpecification();
            rowData[3] = packages.get(i).getMailingClass();
            rowData[4] = getOtherPackageInfo(packages.get(i).getFormattedText());
            tableModel.addRow(rowData);
        }
    }

    public String splitFormattedText(String line, int index) {
        String temp[] = line.trim().split(" ");

        return temp[index];
    }

    public String getOtherPackageInfo(String line) {
        String temp[] = line.trim().split(" ");

        switch (temp[0]) {
            case "Envelope":
                return "Height: " + temp[4] + ", Width: " + temp[5];
            case "Box":
                return "Dimension: " + temp[4] + ", Volume: " + temp[5];
            case "Crate":
                return "Load Weight: " + temp[4] + ", Content: " + temp[5];
            case "Drum":
                return "Material: " + temp[4] + ", Diameter: " + temp[5];
        }
        return "";
    }
}

