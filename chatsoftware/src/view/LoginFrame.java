package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;

import view.RegisterFrame;

import control.ServerFrameUIConfig;
import model.MessageBox;
import model.User;
import view.LoginFrame;
import view.MainFrame;

import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JComboBox;

public class LoginFrame extends JFrame {

	
	private Socket  client;
	private ObjectOutputStream  out;
	private ObjectInputStream  in;
	private JPasswordField passwordField;
	private JPanel contentPane;
	private JComboBox comboBox;
	private JButton button;
	private JButton button_1;
	private JLabel label;
	private JLabel label_1;
	private JLabel lblNewLabel;

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
		
		button = new JButton("登录");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//当用户点击了登陆按钮后，这个按钮事件要做的业务代码分如下几步
				//1.先做表单验证
				//trim是String类的方法，去字符串的前后空白符
				String  yourINputUsername=comboBox.getSelectedItem().toString().trim();
				String yourInputPassword=passwordField.getText().toString().trim();
				if(yourINputUsername.length()<2) {
					JOptionPane.showMessageDialog(LoginFrame.this, "用户名长度不够!","温馨提示",JOptionPane.ERROR_MESSAGE);
					comboBox.requestFocus();
					return ;
				}else
				{
					if(yourInputPassword.length()<2) {
						JOptionPane.showMessageDialog(LoginFrame.this, "密码长度不够!","温馨提示",JOptionPane.ERROR_MESSAGE);
						passwordField.requestFocus();
						return ;
					}else
					{
						//2.建立和服务器的链接(Socket链接)
						
						
						try {
							if(client==null)
							{
								client=new Socket(ServerFrameUIConfig.serverIP, ServerFrameUIConfig.serverPort);
								  out=new ObjectOutputStream(client.getOutputStream());
								    in=new ObjectInputStream(client.getInputStream());
							}
						} catch (Exception e1) {
							e1.printStackTrace();
							JOptionPane.showMessageDialog(LoginFrame.this, "无法连接服务器，请检查网络!","温馨提示",JOptionPane.ERROR_MESSAGE);
						}
						
						//3.在socket的基础上获取输入输出流，然后用输出流将消息发送给服务器，让服务器校验我们的账号和密码是否成功
						
						try {
							//消息要封装成对象，所以，要传递消息要用Object流
							
							//因为我们将消息封装成特定的类型，所以，每次再给服务器发送消息时，都要封装成特定的消息对象才可以
							
							
							MessageBox  loginMessage=new MessageBox();
							User willLoginUser=new User(yourINputUsername,yourInputPassword);
							loginMessage.setFrom(willLoginUser);
							loginMessage.setType("login");
							
							//封装完消息后使用序列化流将消息对象写向网络网络另外一段
							out.writeObject(loginMessage);
							out.flush();
							
							//当客户端把登陆消息发送出去后，应该立马读取服务器回发的登陆结果消息
							
							MessageBox  result=(MessageBox)in.readObject();
							if(result.getFrom()==null) {
								JOptionPane.showMessageDialog(LoginFrame.this, "登陆失败,请检查用户名和密码!","温馨提示",JOptionPane.ERROR_MESSAGE);
							}else
							{
								User u=result.getFrom();//登录程序到的用户资料，存储在服务器给我发过来的消息里面的From属性里面的
								MainFrame  m=new MainFrame(u);
								m.setVisible(true);
								//com.sun.awt.AWTUtilities.setWindowOpacity(m, 0.9f);
								LoginFrame.this.setVisible(false);
							}
							
							
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
						
					}
				}
				
			}
		});
		button.setBounds(104, 216, 93, 23);
		contentPane.add(button);
		
		button_1 = new JButton("注册");
        button_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if(client==null)
					{
						client=new Socket(ServerFrameUIConfig.serverIP, ServerFrameUIConfig.serverPort);
						out=new ObjectOutputStream(client.getOutputStream());
						in=new ObjectInputStream(client.getInputStream());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(LoginFrame.this, "无法连接服务器，请检查网络!","温馨提示",JOptionPane.ERROR_MESSAGE);
					return ;
				}
				
				RegisterFrame  r=new RegisterFrame(out,in,LoginFrame.this);
				r.setVisible(true);
				LoginFrame.this.setVisible(false);
				
			}
		});
		button_1.setBounds(235, 216, 93, 23);
		contentPane.add(button_1);
		
		label = new JLabel("账号");
		label.setFont(new Font("宋体", Font.PLAIN, 15));
		label.setBounds(58, 136, 36, 29);
		contentPane.add(label);
		
		label_1 = new JLabel("密码");
		label_1.setFont(new Font("宋体", Font.PLAIN, 15));
		label_1.setBounds(58, 175, 36, 38);
		contentPane.add(label_1);
		
		lblNewLabel = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage("resource//image//yu2.png")));
		lblNewLabel.setBounds(0, 0, 413, 261);
		contentPane.add(lblNewLabel);
		
		
		passwordField = new JPasswordField("222");
		passwordField.setBounds(104, 179, 224, 23);
		contentPane.add(passwordField);
		
		comboBox = new JComboBox(new Object[]{"111","222","333"});
		comboBox.setEditable(true);
		comboBox.setBounds(104, 136, 224, 25);
		contentPane.add(comboBox);
	}
}
