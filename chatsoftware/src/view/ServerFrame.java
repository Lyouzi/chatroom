package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import control.DBOperator;
import model.MessageBox;
import model.User;

import control.ServerFrameUIConfig;
import view.ServerFrame;
//import view.ServerFrame.ClientMessageReciveThread;

import view.ServerFrame.AllButtonListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;

public class ServerFrame extends JFrame {

	private JPanel contentPane;
	private JButton button;
	private JButton button_1;
	private JLabel label;
	private JLabel label_1;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JTextPane textPane;
	private ServerSocket server;
	private AllButtonListener listener;

	//定义第一个动态代码块，做一些其他的初始化业务，比如说初始化本类需要使用的监听对象(除了ui组件的之外的活)
		{
			
			listener=new AllButtonListener();
		}
	
	public static void main(String[] args) {
		ServerFrame frame = new ServerFrame();
		frame.setVisible(true);
	}
	
	/**
	 * Create the frame.
	 */
	public ServerFrame() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().createImage(("resource//image//fuwu.png")));
		setTitle("服务器");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(390, 160, 598, 393);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		button = new JButton("启动服务器");
		button.addActionListener(listener);	
		button.setBounds(93, 292, 116, 23);
		contentPane.add(button);
		
		 button_1 = new JButton("停止服务器");
		//button_1.setEnabled(false);
		button_1.addActionListener(listener);
		button_1.setBounds(340, 292, 116, 23);
		contentPane.add(button_1);
		
		label = new JLabel("在线用户列表");
		label.setBorder(BorderFactory.createLineBorder(Color.gray));
		label.setBounds(27, 10, 224, 23);
		contentPane.add(label);
		
		lblNewLabel = new JLabel("登录ip");
		lblNewLabel.setBorder(BorderFactory.createLineBorder(Color.gray));
		lblNewLabel.setBounds(27, 43, 112, 23);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("用户昵称");
		lblNewLabel_1.setBorder(BorderFactory.createLineBorder(Color.gray));
		lblNewLabel_1.setBounds(139, 43, 112, 23);
		contentPane.add(lblNewLabel_1);
		
		lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setBorder(BorderFactory.createLineBorder(Color.gray));
		lblNewLabel_2.setBounds(27, 65, 224, 217);
		contentPane.add(lblNewLabel_2);
		
		label_1 = new JLabel("所有用户发送的消息列表");
		label_1.setBorder(BorderFactory.createLineBorder(Color.gray));
		label_1.setBounds(278, 10, 279, 23);
		contentPane.add(label_1);
		
	    textPane = new JTextPane();
		textPane.setBorder(BorderFactory.createLineBorder(Color.gray));
		textPane.setBounds(278, 43, 279, 239);
		contentPane.add(textPane);
		
		setLocationRelativeTo(null);
	}

	
	//本类中有若干个按钮要实现监听事件，所以，我可以定义一个内部类来实现监听，体现了更好的封装	
	class AllButtonListener implements ActionListener{
	

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==button){
			try {
				server=new ServerSocket(ServerFrameUIConfig.serverPort);
				button.setEnabled(false);//设置启动按钮为不可用
				button_1.setEnabled(true);
				JOptionPane.showMessageDialog(ServerFrame.this, "服务器启动成功!", "温馨提示", JOptionPane.INFORMATION_MESSAGE);
				//启动按钮除了要创建server对象之外，还要开启对外服务，accept,
				new Thread() {
					public void run() {
						while(true)
						{
							try {
								Socket  c=server.accept();
								System.out.println(c.getInetAddress());
								ObjectOutputStream  out=new ObjectOutputStream(c.getOutputStream());
								ObjectInputStream  in=new ObjectInputStream(c.getInputStream());
								//应该在有一个客户端链接进来之后，我就开启一个线程，针对他单独和服务器通讯
								
								//ClientMessageReciveThread  thisClientThread=new ClientMessageReciveThread(out, in);
								//thisClientThread.start();//启动这个线程，让他独立运行
							} catch (Exception e2) {
							}
						}
					}
				}.start();
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(ServerFrame.this, "服务器启动失败!", "温馨提示", JOptionPane.ERROR_MESSAGE);
			}	
		}else if(e.getSource()==button_1){
			  int n=JOptionPane.showConfirmDialog(ServerFrame.this, "您确认要关闭服务器吗?", "温馨提示", JOptionPane.OK_CANCEL_OPTION);
				if(n==0)
				{
					try {
						server.close();
						button.setEnabled(true);
						button_1.setEnabled(false);
						JOptionPane.showMessageDialog(ServerFrame.this, "服务器已经关闭!", "温馨提示", JOptionPane.WARNING_MESSAGE);
					} catch (IOException e1) {
						e1.printStackTrace();					
					}						
				}									  		 
		  }			 		 
		  }			
	}
		@SuppressWarnings("unused")
		//server  many  thread  service for  all  client 多线程针对多个客户端服务的这样服务模式
			//定义一个独立的线程类，这个类处理某一个客户端和服务器的通讯
			class ClientMessageReciveThread extends Thread{
				private ObjectOutputStream  out;
				private ObjectInputStream in;
				
				public ClientMessageReciveThread(ObjectOutputStream out, ObjectInputStream in) {
					super();
					this.out = out;
					this.in = in;
				}
				//服务端的代码都在这里了，
				@Override
				public void run() {
					try {
						while(true)//不停的读取客户端发送过来的消息
						{
							MessageBox  m=(MessageBox)in.readObject();//当前这个线程接收到这个客户端发送过来的一个Message对象
							System.out.println(m);
							if(m.getType().equals("login")) {
								processLoginMessage(m);
							}else if(m.getType().equals("register")) {
								processRegisterMessage(m);
							}else if(m.getType().equals("addFriend")) {
								
							}else if(m.getType().equals("search")) {
								
							}else if(m.getType().equals("update")) {
								
							}
							

						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				/**
				 * 这是处理注册消息的代码
				 * @param m
				 */
				private void processRegisterMessage(MessageBox m) {
					
					User  willResgisterUser=m.getFrom();
					Boolean result=DBOperator.register(willResgisterUser);
					
					MessageBox  registerResultMessage=new MessageBox();
					registerResultMessage.setContent(result.toString());
					registerResultMessage.setType("registerResult");
					try {
						out.writeObject(registerResultMessage);
						out.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				/**
				 * 定義一個處理登陸消息的方法
				 * @param m
				 */
				private void processLoginMessage(MessageBox  m) {
					//链接数据库判断用户登陆信息是否正确
					User loginedUser=DBOperator.login(m.getFrom().getUsername(), m.getFrom().getPassword());
					
					if(loginedUser!=null) {
					//如果登陆成功，需要更新服务器窗口上显式的用户列表信息
					//model=new DefaultTableModel(new Object[][] {{loginedUser.getUsername(),loginedUser.getNickname()}}, tableTitle);
					//table.setModel(model);
					}
					//当服务器根据传过来的用户名和密码查询完数据库之后，无论登陆成功还失败都要给用户回一个消息(都要封装成MessageBox)
					MessageBox  loginResult=new MessageBox();
					loginResult.setFrom(loginedUser);
					loginResult.setType("loginResult");
					try {
						out.writeObject(loginResult);
						out.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
	   }
   }
