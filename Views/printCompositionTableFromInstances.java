package Views;

import java.awt.Color;
import java.awt.Component;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import cc.mallet.topics.ParallelTopicModel;
import cc.mallet.topics.TopicInferencer;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;

public class printCompositionTableFromInstances {

	public static List<RowSorter.SortKey> sortKeys;
	public static TableRowSorter<TableModel> sorter;
	
	private static String Get_File_Name(String string) {
		int vt = string.lastIndexOf('/');
		String filename = string.substring(vt+1);
		return filename;
	}
	
	private String alighTextWidth(int width, double d) {
		String s = String.format("%.3f", d);
		return alighTextWidth(width, s);
	}
	private static String alighTextWidth(int width, int i) {
		String s = Integer.toString(i);
		return alighTextWidth(width, s);
	}
	private static String alighTextWidth(int width, String string) {
		String s = string;
		if (width<=0) return s;
		while (s.length()<width) s = " " +s;
		while (s.length()>width) s = s.substring(1);
		return s;
	}

	public static void Do() throws InterruptedException {
		
		// Print out the composition of the InstanceList
		
		// reset the TopicModeling.compositionTable. delete all the old value from previous run.
		TopicModeling.compositionTable.setRowCount(0);
		TopicModeling.compositionTable.setColumnCount(3);
		
		TopicModeling.table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		for (int i=1;i<generateTopics.numTopics;i++)
		TopicModeling.compositionTable.addColumn("Topic "+i);
		TopicModeling.table.setAutoResizeMode(0);
		TopicModeling.table.getColumnModel().getColumn(0).setPreferredWidth(30);
		TopicModeling.table.getColumnModel().getColumn(0).setMaxWidth(100);
		TopicModeling.table.getColumnModel().getColumn(1).setPreferredWidth(250);
		TopicModeling.table.getColumnModel().getColumn(1).setMinWidth(100);
		TopicModeling.table.getColumnModel().getColumn(1).setMaxWidth(1000);
		for (int i = 0; i < generateTopics.numTopics; i++) {
			TopicModeling.table.getColumnModel().getColumn(2+i).setPreferredWidth(60);
			TopicModeling.table.getColumnModel().getColumn(2+i).setMinWidth(30);
			TopicModeling.table.getColumnModel().getColumn(2+i).setMaxWidth(100);
		}
		
        System.out.printf(" %80.75s","Composition of the InstanceList:");
        TopicModeling.Out(alighTextWidth(85,"Composition of the InstanceList:"));
        
        for (int i=0; i<generateTopics.numTopics; i++) {
       		System.out.printf(" %3d", i);
        	TopicModeling.Out(alighTextWidth(7, i));
        }
        System.out.println("\n");
        TopicModeling.Outln("\n");
        
        TopicModeling.Outln("Printing composition table ... ");
		int MaxProgress = generateTopics.instances.size();
        TopicModeling.progressBarMallet.setMaximum(MaxProgress);
		TopicModeling.progressBarMallet.setValue(0);
		TopicModeling.progressBarMallet.setStringPainted(true);
		long currentTime,traveredTime,remainingTime;
		String s;
        int count=0;
        
        long startTime = System.nanoTime(); // start counting time
        int instanceID = 0;
		for (Instance I : generateTopics.instances) {
        	
        	TopicModeling.progressBarMallet.setValue(count);
			double percent = 100* count/  MaxProgress;
			
			currentTime =  System.nanoTime(); // get current time
			traveredTime = (currentTime - startTime) ;
			remainingTime = (long) (traveredTime * ((100-percent) /percent ));
			remainingTime = remainingTime / 1000000000; // converted from nanoseconds to second
			TopicModeling.progressBarMallet.setString("Doing " + (count) + " / " + MaxProgress + " ( " + percent  
					+ " % )  Time remaining: " + remainingTime/60 +" m "  + remainingTime % 60 + " s" 
					);
        	
			// Cancel the process by user
			if (TopicModeling.processPrintCompositionTable_running == false) { 
				TopicModeling.Out("The process of printing the composition table had been canceled by user  \n"); 
				TopicModeling.btnGetCompositionTable.setEnabled(true); 
				TopicModeling.btnEstimateTopics.setEnabled(true);
				//TopicModeling.processPrintCompositionTable_running = false;
				break; 
			}
			
        	Vector<String> row = new Vector<String>();
        	System.out.print(count + "  ");
        	TopicModeling.Out(alighTextWidth(5,count));
			row.add(StringWidthAlign(MaxProgress, Integer.toString(count)));

			s = Get_File_Name(I.getName().toString().replaceAll("%20", " "));
			System.out.printf(" %80.75s ",
					// "\t" +I.getData().toString() + // Content of the file
					// "\t" +I.getTarget().toString() + // Folder name
					// Get_File_Name(I.getName().toString().replaceAll("%20", " ") ) // Full file
					// name and directory path
					s
			// + "\t"
			// + "\t" + I.getSource()==null ? "null" :I.getSource().toString()
			);
			TopicModeling.Out(alighTextWidth(80, s));
			row.add(s);

			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			double[] Probabilities = generateTopics.model.getTopicProbabilities(instanceID);

			for (int topicID = 0; topicID < generateTopics.numTopics; topicID++) {
				s = String.format("%.3f", Probabilities[topicID]);
				System.out.printf(s + "  ");
				TopicModeling.Out(alighTextWidth(7, s));
				row.add(s);
			}

			count++;
			System.out.println("");
			TopicModeling.Outln("");
			TopicModeling.compositionTable.addRow(row);
			instanceID++;
        }
		System.out.println();

		// Highline the big value in the column
		for (int i=0; i<generateTopics.numTopics; i++) 
			TopicModeling.table.getColumnModel().getColumn(i+2).setCellRenderer(new HighlineCellRenderer(0.6f,0.19f));
		
		// Sort the table when click on column title 
		sorter = new TableRowSorter<TableModel>(TopicModeling.table.getModel());
		TopicModeling.table.setRowSorter(sorter);
        sortKeys = new ArrayList<>(count +3);
        sortKeys.add(new RowSorter.SortKey(0, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
		
		// Completed
		TopicModeling.progressBarMallet
				.setString("Done " + (count) + " / " + MaxProgress + " ( Printing composition table finished )");
		TopicModeling.btnCancelMallet.setEnabled(false);
		TopicModeling.btnGetCompositionTable.setEnabled(true);
		TopicModeling.btnEstimateTopics.setEnabled(true);

		TopicModeling.Outln("Printing composition table has been completed successfully !");
		TopicModeling.processPrintCompositionTable_running = false;
		
		// refresh the table to make sure the cell color take effect
		TopicModeling.table.repaint();
		
	}

	private static String StringWidthAlign(int maxProgress, String string) {
		String s = string;
		
		int sopt =0;
		int bac10 =1;
		do {
			bac10 *=10;
			sopt ++;
		}
		while (bac10 < maxProgress);   	// Example: maxProgress = 120 => bac10 = 1000	=> sopt= 3  => length of string will be fixed to 3.
			
			
		
		while (s.length() < sopt) s = "0" + s;	// s="0xx"
		
		return s;
	}
}
/*class HighlineCellRenderer extends DefaultTableCellRenderer 
{
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
        Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

		if (Double.parseDouble(value.toString()) > 0.6) {
			cellComponent.setBackground(Color.RED);
			cellComponent.setForeground(Color.WHITE);
		} else if (Double.parseDouble(value.toString()) > 0.19) {
			cellComponent.setBackground(Color.YELLOW);
			cellComponent.setForeground(Color.BLACK);
		} else {
			cellComponent.setBackground(Color.WHITE);
			cellComponent.setForeground(Color.BLACK);
		}
		return cellComponent;
    }
}*/
