package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.User;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;
import java.awt.Panel;
import javax.swing.JTree;

public class MainFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 * @param u 
	 */
	public MainFrame(User user) {
		setIconImage(Toolkit.getDefaultToolkit().createImage(("resource//image//logo.jpg")));
		setTitle("主菜单");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(900,60, 300,630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("头像");
		label.setBounds(0, 0, 108, 111);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("昵称");
		label_1.setBounds(118, 10, 84, 23);
		contentPane.add(label_1);
		
		JLabel lblNewLabel = new JLabel("个签");
		lblNewLabel.setBounds(120, 58, 154, 53);
		contentPane.add(lblNewLabel);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 121, 264, 460);
		contentPane.add(tabbedPane);
		
		Panel panel = new Panel();
		tabbedPane.addTab("好友", null, panel, null);
		
		JTree tree = new JTree();
		panel.add(tree);
		
		Panel panel_1 = new Panel();
		tabbedPane.addTab("群组", null, panel_1, null);
		
		Panel panel_2 = new Panel();
		tabbedPane.addTab("最近联系人", null, panel_2, null);
	}
}
