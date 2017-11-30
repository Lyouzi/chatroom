package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import view.ServerFrame.AllButtonListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
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
			System.out.println("start");	
		}else
		  {if(e.getSource()==button_1){
			System.out.println("stop");}
		}
	
	
	}
	}
}
