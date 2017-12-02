package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.MessageBox;
import view.ChatFrame;

import model.User;

import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class ChatFrame extends JFrame {

	private JPanel contentPane;
	private ObjectInputStream  in;
	private ObjectOutputStream  out;
	private User my,your;
	
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JTextArea textArea_1;
	private JTextPane textPane;
	private JTextArea textArea;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JButton button;
    private JButton button_1;
    private JButton button_2;
    private String qunMing;

    
	

	/**
	 * @return the textArea
	 */
	public JTextArea getTextArea() {
		return textArea;
	}

	/**
	 * Create the frame.
	 */
	public ChatFrame() {
		setIconImage(Toolkit.getDefaultToolkit().createImage(("resource//image//logo.jpg")));
		setTitle("与某人聊天中");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 160, 577, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 417, 187);
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		
		lblNewLabel = new JLabel(new ImageIcon(Toolkit.getDefaultToolkit().createImage("")));
		lblNewLabel.setBounds(415, 0, 146, 187);
		contentPane.add(lblNewLabel);
		
		lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(415, 200, 146, 177);
		contentPane.add(lblNewLabel_1);
			
		textArea_1 = new JTextArea();
		scrollPane_1 = new JScrollPane(textArea_1);
		scrollPane_1.setBounds(0, 215, 417, 162);
		contentPane.add(scrollPane_1);
		
		button = new JButton("发送");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//这里面是处理当用户点击发送按钮式要执行的业务代码
				//1.先获取编辑文本框里面的输入的要发送的文本
				String  willSendMessage=textArea_1.getText();
				
				
				//2.先将消息封装成MessageBox发送给服务器
				MessageBox  b=new MessageBox();
				b.setContent(willSendMessage);
				b.setFrom(ChatFrame.this.my);
				
				if(ChatFrame.this.your==null)//判断是群聊窗口
				{	String qunming;
					User user=new User();
					user.setUsername(qunMing);
					b.setTo(user);
					b.setType("qunMessage");
				}else
				{
					b.setType("textMessage");
					b.setTo(ChatFrame.this.your);
				}
				
				//3.用从登录界面传过来的输出流写给服务器，让服务器帮我转发给我我的好友
				try {
					ChatFrame.this.out.writeObject(b);
					ChatFrame.this.out.flush();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
				//4.接受服务器给我回发的时间
				
//					MessageBox  time=(MessageBox)ChatFrame.this.in.readObject();
//					
					//5.将即将发送的消息设置到上面的这个聊天信息框里面
					
					String nowTime=new Date().toLocaleString();
					
					textArea.append(ChatFrame.this.my.getNickname()+"  :  "+nowTime+"\t\t\r\n"+willSendMessage+"\r\n\r\n");
				//6.将消息发送狂里面的内容清空
				textArea_1.setText("");
				
				
			}
		});
		button.setBounds(186, 378, 93, 23);
		contentPane.add(button);
		
		button_1 = new JButton("关闭");
		button_1.setBounds(324, 378, 93, 23);
		contentPane.add(button_1);
		
		button_2 = new JButton("抖动");
        button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				MessageBox  m=new MessageBox();
				
				m.setFrom(ChatFrame.this.my);
				m.setTo(ChatFrame.this.your);
				m.setType("shakeMessage");
				shakeWindow();
			}
		});
		button_2.setBounds(68, 192, 69, 23);
		contentPane.add(button_2);
	}
	
	/**
	 * Create the frame.
	 */
	public ChatFrame(User my,User your,ObjectInputStream  in,ObjectOutputStream  out) {
		this();
		this.in=in;
		this.out=out;
		this.my=my;
		this.your=your;				
	}
	
	
	public void  shakeWindow() {
		new Thread() {
			public void run() {
				int pianyi=3;
				int waitTime=50;
				int lastX=ChatFrame.this.getX();
				int lasty=ChatFrame.this.getY();
				for(int n=0;n<100;n++)
				{
					ChatFrame.this.setLocation(lastX-pianyi, lasty);
					try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					ChatFrame.this.setLocation(lastX, lasty-pianyi);
					try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					ChatFrame.this.setLocation(lastX+pianyi, lasty);
					try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					ChatFrame.this.setLocation(lastX, lasty+pianyi);
					try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
				ChatFrame.this.setLocation(lastX, lasty);
			};
			
		}.start();
	}
	
}
