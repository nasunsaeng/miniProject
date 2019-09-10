import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class MainForm extends JFrame{
	MainForm() {
		setLocationRelativeTo(null);
		setSize(300, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.BLUE);
		panel.setBounds(0, 0, 284, 100);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.YELLOW);
		panel_1.setBounds(0, 100, 284, 262);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JButton btnSelectmember = new JButton("SelectMember");
		btnSelectmember.setBounds(140, 200, 120, 30);
		panel_1.add(btnSelectmember);
		
		JTable table = new JTable();
		DefaultTableModel dtm = (DefaultTableModel) table.getModel();
		dtm.setColumnCount(4);
		dtm.addColumn("ID");
		dtm.addColumn("PW");
		dtm.addColumn("GENDER");
		dtm.addColumn("commentA");
		dtm.addRow(new Object[] {"aaa","bbb","ccc","ddd"});
		
		table.setBounds(10, 10, 260, 100);
		panel_1.add(table);
	}

}
