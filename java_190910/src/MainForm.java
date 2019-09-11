import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;

public class MainForm extends JFrame{
	
	private JTable table = null;
	private DefaultTableModel dtModel = null;
	private Vector<String> vec_column = new Vector<>();
	
	MainForm() {
		setLocationRelativeTo(null);
		setTitle("mainForm");
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
	
		btnSelectmember.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dtModel.setDataVector(new Vector<String>(), vec_column);
				
				/*
				 * 1.ojdbc6.jar 추가
				 * 2.class.forName 오라클이면 오라클 드라이버 class 확인
				 * 3.Connection DB 연결
				 * 4.PrepareStatement sql 구문 작성
				 * 5.ResultSet rs에 테이블 내용 반환
				 * 6.connection pstmt re close 자원 읽기 속성 해제
				 */
				
				Connection conn = null;
				PreparedStatement pstmt = null;
				ResultSet rset = null;
				
				try {
					Class.forName("oracle.jdbc.driver.OracleDriver");
					conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "1234");
					pstmt = conn.prepareStatement("select * from member");
					rset = pstmt.executeQuery();
					while(rset.next()) {
						System.out.println(rset.getString(1));
						System.out.println(rset.getString(2));
						System.out.println(rset.getString(3));
						System.out.println(rset.getString(4));
						Object[] temp = new Object[] {
								rset.getString(1),
								rset.getString(2),
								rset.getString(3),
								rset.getString(4)
						};
						dtModel.addRow(temp);
						dtModel.fireTableDataChanged();
					}
							
				}catch(Exception ex){
					ex.printStackTrace();
				}finally {
					try {
						if(rset != null)
							rset.close();
						if(pstmt != null)
							pstmt.close();
						if(conn != null)
							conn.close();
					}catch(Exception ee) {
						ee.printStackTrace();
					}
				}
			}
		});
		vec_column.addElement("ID");
		vec_column.addElement("PW");
		vec_column.addElement("COMMENTA");
		vec_column.addElement("GENDER");
				
		dtModel = new DefaultTableModel(vec_column, 0);
		dtModel.addRow(new Object[] {"aaa","bbb","ccc","ddd"});
		
		table = new JTable();
		table.setModel(dtModel);
		table.setRowHeight(40);
		table.setBounds(0, 0, 240, 180);
				
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(10, 10, 260, 180);
		
		panel_1.add(sp);
				
		JLabel lblNewLabel = new JLabel("MainForm");
		lblNewLabel.setFont(new Font("궁서체", Font.BOLD, 30));
		lblNewLabel.setForeground(Color.white);
		lblNewLabel.setBounds(30, 10, 224, 58);
		panel.add(lblNewLabel);

	}
	public static void main(String[] args) {
		new MainForm();
	}

}
