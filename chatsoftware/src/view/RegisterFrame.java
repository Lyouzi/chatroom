package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;

public class RegisterFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterFrame frame = new RegisterFrame();
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
	public RegisterFrame() {
		setIconImage(Toolkit.getDefaultToolkit().createImage(("resource//image//logo.jpg")));
		setTitle("注册");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(520, 150, 305, 399);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("昵称");
		label.setBounds(23, 10, 34, 22);
		contentPane.add(label);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(67, 10, 138, 21);
		contentPane.add(textPane);
		
		JTextPane textPane_1 = new JTextPane();
		textPane_1.setBounds(67, 41, 138, 21);
		contentPane.add(textPane_1);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setBounds(67, 72, 138, 21);
		contentPane.add(textPane_2);
		
		JTextPane textPane_3 = new JTextPane();
		textPane_3.setBounds(67, 103, 138, 21);
		contentPane.add(textPane_3);
		
		JLabel label_1 = new JLabel("账号");
		label_1.setBounds(23, 42, 34, 22);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("密码");
		label_2.setBounds(23, 72, 34, 22);
		contentPane.add(label_2);
		
		JLabel label_3 = new JLabel("确认密码");
		label_3.setBounds(0, 103, 57, 22);
		contentPane.add(label_3);
		
		JLabel label_4 = new JLabel("性别");
		label_4.setBounds(23, 135, 34, 22);
		contentPane.add(label_4);
		
		JLabel label_5 = new JLabel("年龄");
		label_5.setBounds(23, 167, 34, 22);
		contentPane.add(label_5);
		
		JLabel label_6 = new JLabel("个性签名");
		label_6.setBounds(0, 273, 57, 22);
		contentPane.add(label_6);
		
		JRadioButton radioButton = new JRadioButton("男");
		radioButton.setBounds(67, 135, 50, 23);
		contentPane.add(radioButton);
		
		JRadioButton radioButton_1 = new JRadioButton("女");
		radioButton_1.setBounds(150, 135, 55, 23);
		contentPane.add(radioButton_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setBounds(67, 168, 68, 21);
		contentPane.add(comboBox);
		
		JTextPane textPane_4 = new JTextPane();
		textPane_4.setBounds(67, 273, 212, 77);
		contentPane.add(textPane_4);
		
		JLabel label_7 = new JLabel("头像");
		label_7.setBounds(23, 199, 34, 22);
		contentPane.add(label_7);
		
		JComboBox comboBox_1 = new JComboBox();
		comboBox_1.setBounds(67, 199, 68, 56);
		contentPane.add(comboBox_1);
	}
}
