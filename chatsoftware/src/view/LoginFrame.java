package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;



import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JComboBox;

public class LoginFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginFrame() {
		setIconImage(Toolkit.getDefaultToolkit().createImage(("resource//image//logo.jpg")));
		setTitle("聊天室");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(470, 200, 425, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton button = new JButton("登录");
		button.setBounds(104, 216, 93, 23);
		contentPane.add(button);
		
		JButton button_1 = new JButton("注册");
		button_1.setBounds(235, 216, 93, 23);
		contentPane.add(button_1);
		
		JLabel label = new JLabel("账号");
		label.setFont(new Font("宋体", Font.PLAIN, 15));
		label.setBounds(58, 136, 36, 29);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("密码");
		label_1.setFont(new Font("宋体", Font.PLAIN, 15));
		label_1.setBounds(58, 175, 36, 38);
		contentPane.add(label_1);
		
		JLabel lblNewLabel = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resource//image//yu2.png")));
		lblNewLabel.setBounds(0, 0, 413, 261);
		contentPane.add(lblNewLabel);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(104, 179, 224, 23);
		contentPane.add(textPane_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(104, 136, 224, 25);
		contentPane.add(comboBox);
	}
}
