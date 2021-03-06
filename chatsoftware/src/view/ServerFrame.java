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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class ServerFrame extends JFrame {
    private Map<String, ObjectOutputStream>  allClient=new HashMap<>();//记录每个客户端登陆的账号和它对应使用的输出流
	
	//封装，根据实际情况，我们为了编程的便利性，我们将UI和后台控制Socket的代码整合到这个一个类中，
    private ServerSocket  server;
	private JPanel contentPane;
	private JScrollPane scrollPane;
	private JButton button;
	private JButton button_1;
	private JLabel label;
	private JLabel label_1;
	private JScrollPane scrollPane_1;
	private JTextArea textArea_1;
	private AllButtonListener listener;//内部监听对象
	private JTable table;
	private TableModel model;
	private Object[] tableTitle=new Object[]{"登陆IP","用户昵称"};

	//定义第一个动态代码块，做一些其他的初始化业务，比如说初始化本类需要使用的监听对象(除了ui组件的之外的活)
		{
			
			listener=new AllButtonListener();
		}
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ServerFrame frame = new ServerFrame();
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
		
		label = new JLabel("在线用户列表");
		label.setBorder(BorderFactory.createLineBorder(Color.gray));
		label.setBounds(27, 10, 224, 23);
		contentPane.add(label);
		
		model=new DefaultTableModel(tableTitle,0) ;
		table = new JTable(model);
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(27, 30, 224, 252);
		contentPane.add(scrollPane);
		
		/*lblNewLabel_1 = new JLabel("用户昵称");
		lblNewLabel_1.setBorder(BorderFactory.createLineBorder(Color.gray));
		lblNewLabel_1.setBounds(139, 43, 112, 23);
		contentPane.add(lblNewLabel_1);*/
		
		/*table = new JTable();
		table.setBorder(BorderFactory.createLineBorder(Color.gray));
		table.setBounds(27, 33, 224, 249);
		contentPane.add(table);
		*/
		label_1 = new JLabel("所有用户发送的消息列表");
		label_1.setBorder(BorderFactory.createLineBorder(Color.gray));
		label_1.setBounds(278, 10, 279, 23);
		contentPane.add(label_1);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(278, 33, 279, 249);
		contentPane.add(scrollPane_1);
		textArea_1 = new JTextArea();
		scrollPane_1.setViewportView(textArea_1);
		
		button = new JButton("启动服务器");
		button.addActionListener(listener);	
		button.setBounds(93, 292, 116, 23);
		contentPane.add(button);
		
		button_1 = new JButton("停止服务器");
		button_1.setEnabled(false);
		button_1.addActionListener(listener);
		button_1.setBounds(340, 292, 116, 23);
		contentPane.add(button_1);
		
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
								
								ClientMessageReciveThread  thisClientThread=new ClientMessageReciveThread(out, in);
								thisClientThread.start();//启动这个线程，让他独立运行
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
				 * 这是处理群消息的方法
				 * @param m
				 */
				private void  processQunMessage(MessageBox  m) {
				//1.先查出来，这个群有多少人
					User u=DBOperator.login(m.getFrom().getUsername(), m.getFrom().getPassword());
					Set<User>  thisGroupFriends=u.getMyGroups().get(m.getTo().getUsername());
					for(User  uu:thisGroupFriends)
					{
						//当服务器接收到这个用户发送过来的文本消息的时候，我们就要遍历那个全局的集合，找到这个消息接收方的对应的输出流，把消息写给他
						for (String username:allClient.keySet()) {
							
							if(username.equals(uu.getUsername())) {
								m.setTime(new Date().toLocaleString());//在即将转发消息之前，将服务器上取到的时间设置到该消息对象里面，方便接收方显式正确的消息
								try {
									allClient.get(username).writeObject(m);
									allClient.get(username).flush();
									System.out.println("zhaodaole .这个群里面的一个好友，发送给他 ");
								} catch (IOException e) {
									e.printStackTrace();
								}
								break;
							}
						}
					}
				}
				
				/**
				 * 处理普通转发的文本消息的方法
				 * @param m
				 */
				private  void processTextMessage(MessageBox  m) {
					//当服务器接收到这个用户发送过来的文本消息的时候，我们就要遍历那个全局的集合，找到这个消息接收方的对应的输出流，把消息写给他
					for (String username:allClient.keySet()) {
						
						if(username.equals(m.getTo().getUsername())) {
							m.setTime(new Date().toLocaleString());//在即将转发消息之前，将服务器上取到的时间设置到该消息对象里面，方便接收方显式正确的消息
							try {
								allClient.get(username).writeObject(m);
								allClient.get(username).flush();
								System.out.println("zhaodaole .xiechuqu ");
							} catch (IOException e) {
								e.printStackTrace();
							}
							break;
						}
					}
					
					
					//服务器再给消息发送方回发一个消息，把事件传递回去，让发送方可以显式正确的事件
					MessageBox  timeMessage=new MessageBox();
					timeMessage.setTime(new Date().toLocaleString());
					try {
						out.writeObject(timeMessage);
						out.flush();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
				 * 定义一个处理登录消息的方法
				 * @param m
				 */
				private void processLoginMessage(MessageBox  m) {
					//链接数据库判断用户登陆信息是否正确
					User loginedUser=DBOperator.login(m.getFrom().getUsername(), m.getFrom().getPassword());
					
					if(loginedUser!=null) {
						allClient.put(loginedUser.getUsername(), out);//在登陆成功后将该登陆的号码和对应的通讯流存储到服务器的这个全局集合里
					//如果登陆成功，需要更新服务器窗口上显式的用户列表信息
					model=new DefaultTableModel(new Object[][] {{loginedUser.getUsername(),loginedUser.getNickname()}}, tableTitle);
					table.setModel(model);
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
