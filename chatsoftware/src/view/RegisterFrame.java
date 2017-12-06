package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import model.MessageBox;
import model.User;
import view.RegisterFrame;

import view.LoginFrame;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class RegisterFrame extends JFrame {

	private Socket  client;
	private ObjectOutputStream  out;
	private  ObjectInputStream  in;
	private LoginFrame  login;//has-a  
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;
	private JPasswordField passwordField_1;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private JComboBox comboBox;
	private JComboBox comboBox_1;
	private JButton button;
	private JButton button_1;
	private JLabel label;
	private JTextArea textArea;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	private JLabel label_7;
	
	/**
	 * Create the frame.
	 */
	public RegisterFrame(ObjectOutputStream  out, ObjectInputStream  in,LoginFrame  login) {
		this.login=login;
		this.out=out;
		this.in=in;
		setIconImage(Toolkit.getDefaultToolkit().createImage(("resource//image//logo.jpg")));
		setResizable(false);
		setTitle("注册");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(520, 150, 305, 439);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label = new JLabel("昵称");
		label.setBounds(23, 10, 34, 22);
		contentPane.add(label);
		
		textField_1 = new JTextField();
		textField_1.setBounds(67, 10, 138, 21);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		label_1 = new JLabel("账号");
		label_1.setBounds(23, 42, 34, 22);
		contentPane.add(label_1);
		
		textField = new JTextField();
		textField.setBounds(67, 41, 138, 21);
		contentPane.add(textField);
		textField.setColumns(10);
		
		label_2 = new JLabel("密码");
		label_2.setBounds(23, 72, 34, 22);
		contentPane.add(label_2);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(67, 72, 138, 21);
		contentPane.add(passwordField);

		label_3 = new JLabel("确认密码");
		label_3.setBounds(0, 103, 57, 22);
		contentPane.add(label_3);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(67, 103, 138, 21);
		contentPane.add(passwordField_1);
							
		label_4 = new JLabel("性别");
		label_4.setBounds(23, 135, 34, 22);
		contentPane.add(label_4);
					
		radioButton = new JRadioButton("男");
		radioButton.setBounds(67, 135, 50, 23);
		contentPane.add(radioButton);
		
		radioButton_1 = new JRadioButton("女");
		radioButton_1.setBounds(150, 135, 55, 23);
		contentPane.add(radioButton_1);
		ButtonGroup  g=new ButtonGroup();
		g.add(radioButton);
		g.add(radioButton_1);
		
		label_5 = new JLabel("年龄");
		label_5.setBounds(23, 167, 34, 22);
		contentPane.add(label_5);
		
		comboBox = new JComboBox();
		for (int i = 1; i <101; i++) {
			comboBox.addItem(i+"");
		}
		comboBox.setBounds(67, 168, 68, 21);
		contentPane.add(comboBox);
		
		label_7 = new JLabel("头像");
		label_7.setBounds(23, 199, 34, 22);
		contentPane.add(label_7);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(67, 199, 68, 56);
		contentPane.add(comboBox_1);
		
		label_6 = new JLabel("个性签名");
		label_6.setBounds(0, 273, 57, 22);
		contentPane.add(label_6);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setBounds(67, 273, 212, 77);
		contentPane.add(textArea);
		
		button = new JButton("提交");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				System.out.println("点击了提交按钮");
				//1.先提取界面上用户输入的数据
				String zhanghao=textField.getText().trim();
				String mima=passwordField.getText();
				String sex=radioButton.isSelected()?"男":"女";
				int age=Integer.parseInt(comboBox.getSelectedItem().toString());
				
				String nicheng=textField_1.getText().trim();
				String touxiang="";
				String qianming=textArea.getText().toString();
				//2.表单验证
				
				//3.封装成MessageBox
				User  u=new User(zhanghao, mima, sex, age, nicheng, qianming, touxiang);
				System.out.println(u);
				MessageBox  registerMessage=new MessageBox();
				registerMessage.setFrom(u);
				registerMessage.setType("register");
				
				//4.使用序列化写给服务器，让服务器注册
				try {
					RegisterFrame.this.out.writeObject(registerMessage);
					RegisterFrame.this.out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				System.out.println("send end");
				//5.根据服务器给我的回复的注册消息进一步跳转界面
				MessageBox result=null;
				try {
					result = (MessageBox)RegisterFrame.this.in.readObject();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				JOptionPane.showMessageDialog(RegisterFrame.this, "注册"+(result.getContent().equals("true")?"成功":"失败"),"温馨提示",JOptionPane.INFORMATION_MESSAGE);
				
			}
		});
		button.setBounds(34, 367, 93, 23);
		contentPane.add(button);
		
		button_1 = new JButton("登录");
		button_1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				RegisterFrame.this.login.setVisible(true);
				RegisterFrame.this.setVisible(false);
				
			}
		});
		button_1.setBounds(172, 367, 93, 23);
		contentPane.add(button_1);
	}
}
