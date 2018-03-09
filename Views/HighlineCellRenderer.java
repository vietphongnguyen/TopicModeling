package Views;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class HighlineCellRenderer extends DefaultTableCellRenderer 
{
	private static final long serialVersionUID = 1L;

	float RedThreadhold;
	float YellowThreadhold;
	
	HighlineCellRenderer(float f, float yellowThreadhold ){
		RedThreadhold = f;
		YellowThreadhold = yellowThreadhold;
	}
	
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (Double.parseDouble(value.toString()) > RedThreadhold) {
			cellComponent.setBackground(Color.RED);
			cellComponent.setForeground(Color.WHITE);
		} else if (Double.parseDouble(value.toString()) > YellowThreadhold) {
			cellComponent.setBackground(Color.YELLOW);
			cellComponent.setForeground(Color.BLACK);
		} else {
			cellComponent.setBackground(Color.WHITE);
			cellComponent.setForeground(Color.BLACK);
		}
		return cellComponent;
    }
}
