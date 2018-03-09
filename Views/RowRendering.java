package Views;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;


public class RowRendering {

    private static Object[] columnName = {"Yes", "No"};
    private static Object[][] data = {
            {"0.1", "0.2"},
            {"0.4", "0.3"},
            {"0.5", "0.6"}
    };


    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {

                JFrame frame = new JFrame();
                JTable table = new JTable(data, columnName);
                table.getColumnModel().getColumn(0).setCellRenderer(new CustomRenderer());
                table.getColumnModel().getColumn(1).setCellRenderer(new CustomRenderer());

                frame.add(new JScrollPane(table));
                frame.setTitle("Rendering in JTable");
                frame.pack();
                frame.setVisible(true);
            }
        };

        EventQueue.invokeLater(r);
    }
}


class CustomRenderer extends DefaultTableCellRenderer 
{
private static final long serialVersionUID = 6703872492730589499L;

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if(value.toString() == "0.3"){
        	cellComponent.setBackground(Color.YELLOW);
        } else 
        	cellComponent.setBackground(Color.WHITE);
        
        
        return cellComponent;
    }
}