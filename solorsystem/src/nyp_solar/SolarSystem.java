package nyp_solar;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class SolarSystem extends JFrame {

	private JLabel lblSolar = new JLabel("Sun");
	private JLabel lblEarth = new JLabel("Earth");
	private JLabel lblMoon = new JLabel("Moon");	
	private JLabel lblMercury = new JLabel("Mercury");
	private JLabel lblVenus = new JLabel("Venus");
	JButton btn_Start = new JButton("Start");
	JButton btn_Pause = new JButton("Pause");
	JButton btn_Exit = new JButton("Exit");

	// 배경까는 클래스
	private Background frames = new Background();

	double earthAngle = 0, moonAngle = 0, mercuryAngle=0, venusAngle=0;
	boolean a = true;
	
	{ // 초기화 블럭으로 프레임 구성
		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.BLACK);

		setTitle("공전과 자전은 멋져");
		setSize(1800, 1000);
		setVisible(true);
		setResizable(false); // 창크기 수정 불가
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	void setSystem() {
		getContentPane().add(btn_Start);
		getContentPane().add(btn_Pause);
		getContentPane().add(btn_Exit);
		getContentPane().add(lblSolar);
		getContentPane().add(lblMercury);
		getContentPane().add(lblVenus);
		getContentPane().add(lblEarth);
		getContentPane().add(lblMoon);
		getContentPane().add(frames);
		
		// 버튼들
		btn_Start.setBounds(50, 40, 111, 50);
		btn_Pause.setBounds(50, 101, 111, 50);
		btn_Exit.setBounds(50, 161, 111, 50);
		
		// 항성과 행성들
		lblSolar.setBounds(840, 440, 120, 120);
		lblSolar.setIcon(new ImageIcon("src/nyp_solar//sun(120,120).jpg"));
		lblMercury.setBounds(lblSolar.getLocation().x + 200, lblSolar.getLocation().y + 200, 30, 30);
		lblMercury.setIcon(new ImageIcon("src/nyp_solar//mercury1.png"));
		lblVenus.setBounds(lblSolar.getLocation().x + 250, lblSolar.getLocation().y + 250, 30, 30);
		lblVenus.setIcon(new ImageIcon("src/nyp_solar//Venus1.png"));
		lblEarth.setBounds(lblSolar.getLocation().x + 300, lblSolar.getLocation().y + 300, 90, 90);
		lblEarth.setIcon(new ImageIcon("src/nyp_solar//earth(90,90).png"));
		lblMoon.setBounds(lblEarth.getLocation().x + 100, lblEarth.getLocation().y + 100, 45, 45);
		lblMoon.setIcon(new ImageIcon("src/nyp_solar//moon(45,45).jpg"));
		frames.setSize(1800, 1000);
	
	}

	void runSystem() {

		ActionListener ac = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Object src = e.getSource();
				
				Thread Mercury = new Thread(new Runnable() {

					@Override
					public void run() {
						while (a) {
							int left = lblSolar.getLocation().x + (int) (172 * Math.cos(mercuryAngle));
							int top = lblSolar.getLocation().y + (int) (160 * Math.sin(mercuryAngle));
							lblMercury.setLocation(left, top);
							
							if(mercuryAngle >= 360) mercuryAngle=0;
							else mercuryAngle += 0.0000002;
						}
					}
				});
				
				Thread Venus = new Thread(new Runnable() {

					@Override
					public void run() {
						while (a) {
							int left = lblSolar.getLocation().x + (int) (253 * Math.cos(venusAngle));
							int top = lblSolar.getLocation().y + (int) (257 * Math.sin(venusAngle));
							lblVenus.setLocation(left, top);
							
							if(venusAngle >= 360) venusAngle=0;
							else venusAngle += 0.00000011;
						}
					}
				});
				
				
				Thread Earth = new Thread(new Runnable() {
					
					@Override
					public void run() {
						while(a) {
							int left=lblSolar.getLocation().x+(int)(450*Math.cos(earthAngle));
							int top= lblSolar.getLocation().y+(int)(450*Math.sin(earthAngle));
							lblEarth.setLocation(left,top);
							System.out.println(lblEarth.getLocation().x);
							
							if(earthAngle >= 360) earthAngle=0;
							else earthAngle+= 0.0000001;
					}
					}
				});
			
				Thread Moon = new Thread(new Runnable() {

					@Override
					public void run() {
						while(a) {
							
							int left = lblEarth.getLocation().x+(int)(105*Math.cos(moonAngle));
							int top =  lblEarth.getLocation().y+(int)(105*Math.sin(moonAngle));
							lblMoon.setLocation(left, top);
							
							if(moonAngle >= 360) moonAngle = 0;
							else moonAngle += 0.0000003;
							
						}
					}
				});
				if (src == btn_Start) {
					a = true;
					Mercury.start();
					Venus.start();
					Earth.start();
					Moon.start();

				} else if (src == btn_Pause) {
					a = false;
				}

				else if (src == btn_Exit) {
					System.exit(0);
				}
			}
		};
		
		btn_Start.addActionListener(ac);
		btn_Pause.addActionListener(ac);
		btn_Exit.addActionListener(ac);
	}
	
	public SolarSystem() {
		setSystem();
		runSystem();
	}

	class Background extends JPanel { // 배경을 깔아 보았다....

		ImageIcon imgspace = new ImageIcon("src/nyp_solar//space(1800,1000).png");

		public void paintComponent(Graphics g) {
			g.drawImage(imgspace.getImage(), 0, 0, null);
			setOpaque(false);
			super.paintComponent(g);
		}
	}

	public static void main(String[] args) {
		new SolarSystem();
	}
}
