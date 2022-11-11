package ui.menu;

import model.Category;
import model.Entry;

import javax.swing.table.AbstractTableModel;

import java.util.ArrayList;
import java.util.List;


// CITATION: The following codes are modeled after TableDemoProject provided by Oracle Java Swing tutorial
public class EntryTableModel extends AbstractTableModel {
    private List<Entry> data = new ArrayList<>();
    private String[] columnNames = {"DATE",
            "AMOUNT",
            "CATEGORY",
            "DESCRIPTION"
    };

    // EFFECTS: return the number of rows
    @Override
    public int getRowCount() {
        return data.size();
    }

    // EFFECTS: return the number of columns
    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    // EFFECTS: return the column name
    public String getColumnName(int col) {
        return columnNames[col];
    }

    // EFFECTS: return the value of a specific cell
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object entryAttribute = null;
        Entry entry = data.get(rowIndex);
        switch (columnIndex) {
            case 0 :
                entryAttribute = entry.getDate();
                break;
            case 1 :
                entryAttribute = entry.getAmount();
                break;
            case 2 :
                entryAttribute = entry.getCategory();
                break;
            default:
                entryAttribute = entry.getDescription();
                break;
        }
        return entryAttribute;
    }

    // EFFECTS: return true if the column is editable, false otherwise
    public boolean isCellEditable(int row, int col) {
        if (col == 0 || col == 2) {
            return false;
        } else {
            return true;
        }
    }

    // MODIFIES: this
    // EFFECTS: add an entry into the table
    public void addEntry(Entry entry) {
        data.add(entry);
        fireTableDataChanged();
    }

}
