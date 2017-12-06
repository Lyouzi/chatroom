package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import view.MainFrame;

import view.MainFrame.MessageReciverThread;

import model.MessageBox;

import view.ChatFrame;

import model.User;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import java.awt.Panel;
import javax.swing.JTree;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {

	//定义一个集合存储所有和我聊过天的好友信息
	private Map<String,ChatFrame>  allFrames=new HashMap<>();
	
	private ObjectInputStream  in;
	private ObjectOutputStream  out;
	private JPanel contentPane;
	private JLabel label;
	private JLabel lblUsername;
	private JTextArea txtrAboutDescriptions;
    private JTabbedPane tabbedPane;
    private Panel panel;
    private Panel panel_1;
    private Panel panel_2;
    private JScrollPane scrollPane;

	private User user;
	private JTree friendTree,groupTree;

	/**
	 * Create the frame.
	 * @param u 
	 */
	public MainFrame(User user,ObjectInputStream  in,ObjectOutputStream  out) {
		
		this.in=in;
		this.out=out;
		this.user=user;
		
		setIconImage(Toolkit.getDefaultToolkit().createImage(("resource//image//logo.jpg")));
		setResizable(false);
		setTitle("主菜单");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(900,60, 300,630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label = new JLabel();
		label.setBorder(new LineBorder(new Color(128, 128, 128), 1, true));
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setIcon(new ImageIcon(Toolkit.getDefaultToolkit().createImage(user.getImagePath()).getScaledInstance(128, 128, Image.SCALE_DEFAULT)));
		label.setBounds(0, 0, 108, 111);
		contentPane.add(label);
						
		lblUsername = new JLabel(user.getNickname());
		lblUsername.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblUsername.setBounds(118, 10, 84, 23);
		contentPane.add(lblUsername);
						
		txtrAboutDescriptions = new JTextArea();
		txtrAboutDescriptions.setEditable(false);
		txtrAboutDescriptions.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		txtrAboutDescriptions.setText(user.getSignatrue());
		txtrAboutDescriptions.setLineWrap(true);
		txtrAboutDescriptions.setBounds(120, 58, 154, 53);
		contentPane.add(txtrAboutDescriptions);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 121, 264, 460);
		contentPane.add(tabbedPane);
		
		panel = new Panel();
		tabbedPane.addTab("好友", null, panel, null);
        panel.setLayout(new BorderLayout(0, 0));
		
		DefaultMutableTreeNode  root=new DefaultMutableTreeNode("root");//定义一个jtree根节点，所有的好友分组和好友都在这个根节点上往上放
		
		/**
		 * 解析用户里面的所有好友和分组信息，并生成到jtree上对吧
		 */
		Map<String, HashSet<User>>  allFriends=user.getFriends();
		 
		Set<String>  allGroupNames=allFriends.keySet();//获取所有的分组名
		
		for(String groupName:allGroupNames) {
			DefaultMutableTreeNode  group=new DefaultMutableTreeNode(groupName);//构造出每个组名的对应的TreeNode对象
			HashSet<User>  friendsOfGroup=allFriends.get(groupName);
			for(User u:friendsOfGroup) {
				DefaultMutableTreeNode  friend=new DefaultMutableTreeNode(u.getNickname());
				group.add(friend);
			}
			
			root.add(group);
		}
		
		friendTree = new JTree(root);
		friendTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==1&&e.getClickCount()==2) {
					TreePath  path=friendTree.getSelectionPath();
					DefaultMutableTreeNode lastNode=(DefaultMutableTreeNode)path.getLastPathComponent();
					if(lastNode.isLeaf()) {
						//上面是解析用户双击之后判断是不是双击的某一个用户名上的这个Node
						String username=lastNode.toString();
						String num=username.substring(username.lastIndexOf("[")+1,username.length()-1);
						//
						User your=new User();
						your.setUsername(num);
						for(String firendNum:allFrames.keySet())
						{
							if(firendNum.equals(num))
							{
								allFrames.get(firendNum).setVisible(true);
								return;
							}
						}
						ChatFrame   chat=new ChatFrame(MainFrame.this.user,your,MainFrame.this.in,MainFrame.this.out);
						chat.setVisible(true);
						allFrames.put(num, chat);
					}
				}
			}
		});
		friendTree.setRootVisible(false);
		scrollPane= new JScrollPane(friendTree);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		
		/**
		 * 解析该用户的所有群信息，并显式到jtree
		 */
		DefaultMutableTreeNode  root1=new DefaultMutableTreeNode("root");
		
		for(String groupName :user.getMyGroups().keySet())
		{
			DefaultMutableTreeNode  group=new DefaultMutableTreeNode(groupName);
			root1.add(group);
		}
		groupTree=new JTree(root1);
		groupTree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2&&e.getButton()==1) {
					ChatFrame  c=new ChatFrame(MainFrame.this.user,groupTree.getSelectionPath().getLastPathComponent().toString(),MainFrame.this.in,MainFrame.this.out);
					c.setVisible(true);
				}
			}
		});
		
		groupTree.setRootVisible(false);
		panel_1 = new Panel();
		panel_1.setLayout(new BorderLayout());
		panel_1.add(groupTree);
		tabbedPane.addTab("群组", null, panel_1, null);
		
		panel_2 = new Panel();
		tabbedPane.addTab("最近联系人", null, panel_2, null);
		
		//我们在主界面的构造器最后一行（构造器里面的代码是负责呈现界面的，也就数界面都显式完整了，我们可以让后台的那个线程悄悄开始工作了）

				MessageReciverThread  t=new MessageReciverThread();
				t.start();
	
		
	}
	//主界面这个类应该定义一个线程，该线程运行时创建一个线程实例，在主界面单独运行，用来时时刻刻接受"服务器"给我的消息
	
			class  MessageReciverThread  extends Thread{
				@Override
				public void run() {
					MessageBox  recivedMessage=null;
					try {
						A:while((recivedMessage=(MessageBox)in.readObject())!=null) {
								
								for(String firendNum:allFrames.keySet())
								{
									if(firendNum.equals(recivedMessage.getFrom().getUsername()))
									{
										if(recivedMessage.getType().equals("shakeMessage"))
										{
											allFrames.get(firendNum).setVisible(true);
											allFrames.get(firendNum).shakeWindow();
											
										}else
										{
											allFrames.get(firendNum).getTextArea().append(recivedMessage.getFrom().getNickname()+"  :  "+recivedMessage.getTime()+"\t\t\r\n"+recivedMessage.getContent()+"\r\n\r\n");
											allFrames.get(firendNum).setVisible(true);
										}
										continue A;
									}
								}
								ChatFrame  c=new ChatFrame(user,recivedMessage.getFrom() , in, out);
								
								if(recivedMessage.getType().equals("shakeMessage"))
								{
									c.setVisible(true);
									c.shakeWindow();
								}else
								{
									c.getTextArea().append(recivedMessage.getFrom().getNickname()+"  :  "+recivedMessage.getTime()+"\t\t\r\n"+recivedMessage.getContent()+"\r\n\r\n");
									c.setVisible(true);
								}
								allFrames.put(recivedMessage.getFrom().getUsername(), c);
						}
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}	
}
}