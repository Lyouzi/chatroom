package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import view.ChatFrame;

import model.User;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import java.awt.Panel;
import javax.swing.JTree;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame {

	private JPanel contentPane;
	private JLabel label;
	private JLabel lblUsername;
	private JTextArea txtrAboutDescriptions;
    private JTabbedPane tabbedPane;
    private Panel panel;
    private Panel panel_1;
    private Panel panel_2;
    private JScrollPane scrollPane;
	private JTree tree;

	/**
	 * Create the frame.
	 * @param u 
	 */
	public MainFrame(User user) {
		//this.user=user;
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
		
		tree = new JTree(root);
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton()==1&&e.getClickCount()==2) {
					TreePath  path=tree.getSelectionPath();
					DefaultMutableTreeNode lastNode=(DefaultMutableTreeNode)path.getLastPathComponent();
					if(lastNode.isLeaf()) {
						//上面是解析用户双击之后判断是不是双击的某一个用户名上的这个Node
						String username=lastNode.toString();
						System.out.println(username);
						ChatFrame   chat=new ChatFrame();
						chat.setVisible(true);
					}
				}
			}
		});
		tree.setRootVisible(false);
		scrollPane= new JScrollPane(tree);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		panel_1 = new Panel();
		tabbedPane.addTab("群组", null, panel_1, null);
		
		panel_2 = new Panel();
		tabbedPane.addTab("最近联系人", null, panel_2, null);
	}
}
