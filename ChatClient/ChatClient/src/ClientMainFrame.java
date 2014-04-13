
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;
import javax.swing.event.MenuListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;


@SuppressWarnings("serial")
public class ClientMainFrame extends JFrame {
	
	
	private JLabel title;
	private String name;
	private JTextField pseudo;
	private JButton apply;
	
	public JPanel connection;
	public JPanel chat;
	public JPanel subConnection;
	public JPanel home;
	
	

	private JTextArea discussion;
	private JTextField writting;
	private JButton send;
	private JButton createChatRoom;
	private JButton connect;
	private JTextField newClusterName;
	
	private JTree chatRoomList;
	private DefaultMutableTreeNode root;
	private DefaultTreeModel treeModel;
	
	private List<ChatRoomController> chatControllerList;
	private List<ChatPanel> chatPanelList;
	public JTabbedPane onglets;
    
	public ClientMainFrame()
	{
		
		this.initializeComponents();
	}
	
	private void initializeComponents()
	{
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Chat application");
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        
        this.root = new DefaultMutableTreeNode("Chat Rooms");
        this.treeModel = new DefaultTreeModel(this.root);
        this.chatRoomList = new JTree(this.treeModel);
        
        this.onglets = new JTabbedPane(SwingConstants.TOP);
        
        this.chatControllerList = new ArrayList<ChatRoomController>();
        this.chatPanelList = new ArrayList<ChatPanel>();
        
		this.title = new JLabel();
		this.pseudo = new JTextField();
		this.apply = new JButton();
		send =new JButton("Send");
		
		this.connection = new JPanel();
		this.subConnection = new JPanel();
		this.chat = new JPanel();
		this.setContentPane(connection);	
		
		this.title.setText("Please, enter your Pseudo");
		this.apply.setText("Connect");
		this.pseudo.setSize(50, 50);
		
		
		this.subConnection.setLayout(new BorderLayout());
		this.subConnection.add(pseudo, BorderLayout.CENTER);
		this.subConnection.add(apply, BorderLayout.SOUTH);
		this.subConnection.add(title, BorderLayout.NORTH);
		
		//Home constructor
		this.home = new JPanel();
		this.home.setLayout(new BoxLayout(home, BoxLayout.LINE_AXIS));
		JPanel homeLeft = new JPanel();
		JPanel homeRight = new JPanel();
		homeRight.setBackground(Color.black);
		createChatRoom = new JButton("Create a new chatroom");
		connect = new JButton("Connect to chatroom");
		newClusterName = new JTextField(20);
		homeRight.setLayout(new GridLayout(2,1));
		homeLeft.setLayout(null);
		homeLeft.setBorder(new BevelBorder(BevelBorder.RAISED));
		homeRight.setBorder(new BevelBorder(BevelBorder.RAISED));
		newClusterName.setBounds(100,300,200,30);
		createChatRoom.setBounds(100,345,200,30);
		homeLeft.add(newClusterName);
		homeLeft.add(createChatRoom);
		homeRight.add(chatRoomList);
		homeRight.add(connect);
		this.home.add(homeLeft);
		this.home.add(homeRight);
		
		
		this.setLayout(new BorderLayout());
		this.getContentPane().add(subConnection, BorderLayout.SOUTH);
		this.setVisible(true);
		
		
	}
	
	public void chatConnectionListener (ActionListener l){
		connect.addActionListener(l);
	}
	
	public void connectListener (ActionListener l){
		apply.addActionListener(l);
	}
	
	
	public void createChatRoomListener(ActionListener l){
		createChatRoom.addActionListener(l);
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	
	
	public void setHome(List<ChatRoom> chatRoomList){
		
		//Clear the tree
				for(int i=this.treeModel.getChildCount(this.root)-1; i>=0;i--)
			    { 
			    	for(int j=this.treeModel.getChildCount((DefaultMutableTreeNode)this.treeModel.getChild(this.root, i))-1; j>=0; j--)
				    {
				    	 this.treeModel.removeNodeFromParent((DefaultMutableTreeNode)this.treeModel.getChild((DefaultMutableTreeNode)this.treeModel.getChild(this.root, i), j));
				    }
			    	
			    	this.treeModel.removeNodeFromParent((DefaultMutableTreeNode)this.treeModel.getChild(this.root, i));
			    }
				
				//Fill the tree
				if(!chatRoomList.get(0).getName().equals("null")){
					for(int i=0; i < chatRoomList.size(); i++){       	
				         DefaultMutableTreeNode child = new DefaultMutableTreeNode(chatRoomList.get(i).getName());
				         this.treeModel.insertNodeInto( child, this.root, this.root.getChildCount());
					     this.chatRoomList.scrollPathToVisible(new TreePath(child.getPath()));
					     
					     for(int j=0; j<chatRoomList.get(i).getConnectedUserList().size(); j++)
					     {
					    	 DefaultMutableTreeNode childOfChild = new DefaultMutableTreeNode(chatRoomList.get(i).getConnectedUserList().get(j));
					         this.treeModel.insertNodeInto(childOfChild, child, j);
					         this.chatRoomList.scrollPathToVisible(new TreePath(childOfChild.getPath()));
					     }
				    }
				}
		
				
	}
	
	public String getName(){
		return name;
	}
	
	public String getSelectedNode(){
		return this.chatRoomList.getLastSelectedPathComponent().toString();
	}
	
	public String getPseudo(){
		return pseudo.getText();
	}
	
	public String getNewClusterName(){
		return newClusterName.getText();
	}
	
	public void chatLaunch(String clusterName){
		chatPanelList.add(new ChatPanel(clusterName));
		chatControllerList.add(new ChatRoomController(clusterName,chatPanelList.get(chatPanelList.size()-1),this));
		this.onglets.addTab(clusterName, chatPanelList.get(chatPanelList.size()-1));
		
		//this.setContentPane(chatPanelList.get(chatPanelList.size()-1));
		this.setContentPane(onglets);
		
	}
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub

		/*variables*/
        ClientMainFrame clientFrame =new ClientMainFrame();
        new ClientManager(clientFrame);
	}
}
