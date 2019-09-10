import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import oracle.net.aso.p;

public class LoginForm extends JFrame implements ActionListener {
	private JTextField txt_Id = new JTextField();
	private JTextField txt_Pw = new JTextField();
	private JButton btn_Cancle = new JButton("CANCLE");
	private JButton btn_Login = new JButton("LOGIN");
	LoginForm() {
		
		setLocationRelativeTo(null); // JFrame 중간으로 실행
		
		setTitle("LoginForm");
		setSize(300, 400);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JPanel pnl_Top = new JPanel();
		pnl_Top.setBackground(Color.BLUE);
		pnl_Top.setBounds(0, 0, 284, 100);
		getContentPane().add(pnl_Top);
		pnl_Top.setLayout(null);
		
		JLabel lbl_LoginForm = new JLabel("Login Form");
		lbl_LoginForm.setForeground(Color.WHITE);
		lbl_LoginForm.setFont(new Font("Berlin Sans FB", Font.PLAIN, 25));
		lbl_LoginForm.setBounds(85, 20, 125, 57);
		pnl_Top.add(lbl_LoginForm);

		JPanel pnl_main = new JPanel();
		pnl_main.setBackground(Color.YELLOW);
		pnl_main.setBounds(0, 100, 284, 262);
		getContentPane().add(pnl_main);
		pnl_main.setLayout(null);
		
		
		btn_Cancle.setFont(new Font("Bell MT", Font.PLAIN, 12));
		btn_Cancle.setBounds(45, 180, 90, 30);
		pnl_main.add(btn_Cancle);
		
		btn_Login.setFont(new Font("Bell MT", Font.PLAIN, 12));
		btn_Login.setBounds(145, 180, 90, 30);
		pnl_main.add(btn_Login);
		
		JLabel lbl_Id = new JLabel("id");
		lbl_Id.setBounds(10, 30, 90, 30);
		pnl_main.add(lbl_Id);
		
		JLabel lbl_Pw = new JLabel("pw");
		lbl_Pw.setBounds(10, 70, 90, 30);
		pnl_main.add(lbl_Pw);
		
		txt_Id.setBounds(100, 30, 160, 30);
		pnl_main.add(txt_Id);
		txt_Id.setColumns(10);
		
		txt_Pw.setBounds(100, 70, 160, 30);
		pnl_main.add(txt_Pw);
		txt_Pw.setColumns(10);
		
		btn_Cancle.addActionListener(this);
		btn_Login.addActionListener(this);
	}
	
	public static void main(String[] args) {
		new LoginForm();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("e.getSource() = "+e.getSource());
		if(e.getSource() == btn_Cancle) {
			System.exit(0);
		}
		else {
			/* DB연결 해가지고 ID PW 같으면
			 * 
			 * 1. ojdbc6.jar파일 가져와서 프로젝트 빌드패스 설정
			 * 2. class.forname 함수로 class 추가 되었는지 확인
			 * 3. connection DriverManager.getConnection DB 연결
			 * 4. PrepareStatement pstmt sql 구문 지정
			 * 5. Resultset 테이블 내용 담기
			 * 
			 * insert update delete -> excuteUpdate();로 실행
			 * select -> executeQuery();로 실행 -> 반환값이 테이블 -> Resultset으로 ..
			 */
			Connection conn = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "1234");
				pstmt = conn.prepareStatement("select * from member where id=? and pw=?");
				pstmt.setString(1, txt_Id.getText());
				pstmt.setString(2, txt_Pw.getText());
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
//				while(rs.next()) {
//					System.out.println("id = "+rs.getString(1));
//					System.out.println("pw = "+rs.getString(2));
					setVisible(false);
					new MainForm();
				}else {
					JOptionPane.showMessageDialog(null, "로그인 정보를 확인하세요.");
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}finally {
				try {
				rs.close();
				pstmt.close();
				conn.close();
				}catch(SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
