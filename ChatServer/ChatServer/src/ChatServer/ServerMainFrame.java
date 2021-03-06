package ChatServer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.WindowConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import ChatServer.ServerController.ServerMainFrameListener;

@SuppressWarnings("serial")
public class ServerMainFrame extends JFrame
{
	/** Strong Properties **/
	private List<ChatRoomController> chatRoomControllerList;
	
	private List<String> ConnectedUserList;
	private List<ChatRoom> ChatRoomList;
	
	/** Graphical Properties **/
	private JMenuBar menuBar;
	private JMenu edit;
	
	private JMenuItem create;
	private JMenuItem delete;
	
	private JPanel home;
	private JPanel PanelWest;
	private JPanel PanelEast;
	
	private JPanel connectedUserPanel;
	private JPanel chatRoomPanel;
	
	private JScrollPane connectedUserScrollPanel;
	private JScrollPane chatRoomScrollPanel;
	
	private JTree chatRoomList;
	private DefaultMutableTreeNode root;
	private DefaultTreeModel treeModel;
	
	private JList<String> connectedUserList;
	private DefaultListModel<String> dlm1 = new DefaultListModel<String>();
	
	public ServerMainFrame()
	{
		this.chatRoomControllerList = new LinkedList<ChatRoomController>();
		
		this.ConnectedUserList = new ArrayList<String>();
		this.ChatRoomList = new ArrayList<ChatRoom>();
		
		this.initializeComponents();
	}
	
	//Accessors
	public List<ChatRoomController> getChatRoomControllerList()
	{
		return this.chatRoomControllerList;
	}
	public List<String> getConnectedUserList()
	{
		return this.ConnectedUserList;
	}
	public List<ChatRoom> getChatRoomList()
	{
		return this.ChatRoomList;
	}
	
	//mutators
	public void setChatRoomControllerList(List<ChatRoomController> chatRoomControllerList)
	{
		this.chatRoomControllerList = chatRoomControllerList;
	}
	public void setConnectedUserList(List<String> connectedUserList)
	{
		this.ConnectedUserList = connectedUserList;
	}
	public void setChatRoomList(List<ChatRoom> chatRoomList)
	{
		this.ChatRoomList = chatRoomList;
	}
	
	private void initializeComponents()
 	{
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setTitle("Chat Application Server");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setMinimumSize(new Dimension(1000,500));
        this.setLocationRelativeTo(null);
        
        this.root = new DefaultMutableTreeNode("Chat Rooms");
        this.treeModel = new DefaultTreeModel(this.root);
        this.chatRoomList = new JTree(this.treeModel);
        
        ChatRenderer renderer = new ChatRenderer();
        this.chatRoomList.setCellRenderer(renderer);
        
    	this.dlm1 = new DefaultListModel<String>();
    	this.connectedUserList = new JList<String>(this.dlm1);
        
        this.menuBar = new JMenuBar();
        this.edit = new JMenu("Edit");
        
        this.create = new JMenuItem("Create a Chat Room");
        this.delete = new JMenuItem("Delete a Chat Room");
        
        this.menuBar.add(this.edit);
        
        this.edit.add(this.create);
        this.edit.add(this.delete);
        
        this.setJMenuBar(this.menuBar);
        
        this.home = new JPanel();
        this.PanelWest = new JPanel();
        this.PanelEast = new JPanel();
        this.connectedUserPanel = new JPanel(new BorderLayout());
        this.chatRoomPanel = new JPanel(new BorderLayout());
        
        this.connectedUserScrollPanel = new JScrollPane(this.connectedUserPanel);
        this.chatRoomScrollPanel = new JScrollPane(this.chatRoomPanel);
        
        this.connectedUserPanel.setBackground(Color.WHITE);       
        this.chatRoomPanel.setBackground(Color.WHITE);
        this.PanelEast.setBackground(Color.DARK_GRAY);
        
        this.home.setLayout(new BoxLayout(this.home, BoxLayout.LINE_AXIS));
        this.home.add(this.PanelWest);
        this.home.add(this.PanelEast);
        
        this.PanelWest.setLayout(new BoxLayout(this.PanelWest, BoxLayout.PAGE_AXIS));
        this.PanelWest.add(this.chatRoomScrollPanel);
        this.PanelWest.add(this.connectedUserScrollPanel);

        this.PanelWest.setPreferredSize(new Dimension(-800, ServerMainFrame.HEIGHT));
        
        this.connectedUserPanel.add(this.connectedUserList, BorderLayout.CENTER);
        this.chatRoomPanel.add(this.chatRoomList, BorderLayout.CENTER);
 		
        this.setContentPane(this.home);
        
 		this.setVisible(true);
 	}
	
	protected static ImageIcon createImageIcon(String path) 
	{
        java.net.URL imgURL = ServerMainFrame.class.getResource(path);
        if (imgURL != null) 
        {
            return new ImageIcon(imgURL);
        } 
        else 
        {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }
	
	public void addServerMainFrameListener(ServerMainFrameListener e)
	{
		this.addWindowListener(e);
		this.create.addActionListener(e);
		this.delete.addActionListener(e);
	}
	
	public boolean isInTree(Object parent, String chatRoomName)
	{
		for(int i=0; i < this.treeModel.getChildCount(parent); i++)
		{
			if((DefaultMutableTreeNode)this.treeModel.getChild(parent, i) != null)
			{
				if(chatRoomName.equals(((DefaultMutableTreeNode)this.treeModel.getChild(parent, i)).getUserObject()))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	public void updateMainPanel()
	{		  	        
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
		for(int i=0; i < this.getChatRoomList().size(); i++)
	    {       	
	         DefaultMutableTreeNode child = new DefaultMutableTreeNode(this.getChatRoomList().get(i).getName());
	         this.treeModel.insertNodeInto( child, this.root, this.root.getChildCount());
		     this.chatRoomList.scrollPathToVisible(new TreePath(child.getPath()));
		     
		     for(int j=0; j<this.getChatRoomList().get(i).getConnectedUserList().size(); j++)
		     {
		    	 DefaultMutableTreeNode childOfChild = new DefaultMutableTreeNode(this.getChatRoomList().get(i).getConnectedUserList().get(j));
		         this.treeModel.insertNodeInto(childOfChild, child, j);
		         this.chatRoomList.scrollPathToVisible(new TreePath(childOfChild.getPath()));
		     }
	    }        
	        
	    this.dlm1.clear();
	        
	    for(int i=0; i < this.getConnectedUserList().size(); i++)
	    {
	        this.dlm1.addElement(this.getConnectedUserList().get(i));
	    }
	}
	
	public boolean chatRoomExists(String name)
	{
		for(int i=0; i<this.getChatRoomList().size(); i++)
		{
			if(this.getChatRoomList().get(i).getName().equals(name))
			{
				return true;
			}
		}
		
		return false;
	}
	
	public int getChatRoomIndex(String name)
	{
		for(int i=0; i<this.getChatRoomList().size(); i++)
		{
			if(this.getChatRoomList().get(i).getName().equals(name))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public int getConnectedUserIndex(List<String> userList, String name)
	{
		for(int i=0; i<userList.size(); i++)
		{
			if( userList.get(i).equals(name) )
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public void sendUpdate(TransportDriver transportDriver)
	{
		 String str = "";
   		 
   		 for(int i=0; i<this.getChatRoomList().size(); i++)
   		 {
   			 str += this.getChatRoomList().get(i).toString() + ";;";
   		 }
   		 
   		 if( this.getChatRoomList().size() ==0 )
   		 {
   			 str = "null";
   		 }
   		 
   		 transportDriver.sendMessage(new MessageChat(MessageType.AvailableChatRooms, "server", null, str));
   		 
   		 str = "";
   		 
   		 for(int i=0; i<this.getConnectedUserList().size(); i++)
   		 {
   			 str += this.getConnectedUserList().get(i) + ";;";
   		 }
   		 
   		 if( this.getConnectedUserList().size() ==0 )
   		 {
   			 str = "null";
   		 }
   		 
   		 transportDriver.sendMessage(new MessageChat(MessageType.ConnectedUsers, "server", null, str));
	}
	
	class ChatRenderer extends DefaultTreeCellRenderer 
	{
	    Icon icon;

	    public ChatRenderer() 
	    {
	    	
	    }

	    public Component getTreeCellRendererComponent(JTree tree,Object value,boolean sel,boolean expanded,boolean leaf,int row,boolean hasFocus) 
	    {
	    	super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
	    	
	        if (isChatRoom(value)) 
	        {
	            setIcon(createImageIcon("/chatRoom.gif"));
	        }
	        else if(leaf && !isRoot(value))
	        {
	        	setIcon(createImageIcon("/user.gif"));
	        }
	        else if(isRoot(value))
	        {
	        	setIcon(createImageIcon("/root.gif"));
	        } 

	        return this;
	    }

	    protected boolean isChatRoom(Object value) 
	    {
	        DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
	        
	        return isInTree(root,(String) node.getUserObject());
	    }
	    
	    protected boolean isRoot(Object value)
	    {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
	        
	        if(node.equals(root))
	        {
	        	return true;
	        }
	        
	        return false;
	    }
	}
	
	public static void main(String[] args) 
	{
		// TODO Auto-generated method stub
		ServerMainFrame theServerMainFrame = new ServerMainFrame();
		new ServerController(theServerMainFrame);
  	}
}
