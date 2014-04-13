package ChatServer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class ServerController implements TransportReceiver
{
	private final static String chatControlCluster = "ControlCluster";	
	private TransportDriver transportDriver;
	private ServerMainFrame theServerMainFrame;
	
	public ServerController(ServerMainFrame theServerMainFrame)
	{
		this.theServerMainFrame = theServerMainFrame;
		this.transportDriver = new TransportDriver(chatControlCluster, this);
		
		this.theServerMainFrame.addServerMainFrameListener(new ServerMainFrameListener());
	}
	
	//accessors
	public ServerMainFrame getServerMainFrame()
	{
		return this.theServerMainFrame;
	}
	public TransportDriver getTransportDriver()
	{
		return this.transportDriver;
	}
	
	//mutators
	public void setServerMainFrame(ServerMainFrame theServerMainFrame)
	{
		this.theServerMainFrame = theServerMainFrame;
	}
	public void setTransportDriver(TransportDriver transportDriver)
	{
		this.transportDriver = transportDriver;
	}
	
	//mettre à jour la MainFrame
	private void updateView()
	{
		this.getServerMainFrame().updateMainPanel();
	}

	@Override
	public void receiveChatMessage(MessageChat message) 
	{
		// TODO Auto-generated method stub
		switch(message.getType())
	   	 {
	   	 case ConnectionEstablishment:
	   		 if(!this.theServerMainFrame.getConnectedUserList().contains(message.getContent()))
	   		 {
	   			 this.theServerMainFrame.getConnectedUserList().add(message.getContent());
	   			 
	   			 this.transportDriver.sendMessage(new MessageChat(MessageType.ConnectionAcknowledgment, "server", message.getContent(), "true"));
	   		 }
	   		 else
	   		 {
	   			this.transportDriver.sendMessage(new MessageChat(MessageType.ConnectionAcknowledgment, "server", message.getContent(), "false"));
	   		 }
	   		 
	   		 this.updateView();
	   		 
	   		 this.sendUpdate();
	   		 
	   		 break;
	   		 
	   	 case ConnectionAcknowledgment:	 
	   		 //Client Side
	   	 break;
	   	 
	   	 case ChatRoomsInformationRequest:  		
	   		 this.sendUpdate(); 
	   		 break;
	   		 
	   	 case AvailableChatRooms:
	   		 //Client Side
	   		 break;
	   		 
	   	 case ConnectedUsers:
	   		 //Client Side
	   		 break;
	   		 
	   	 case ChatRoomCreationRequest:
	   		 if(!this.theServerMainFrame.chatRoomExists(message.getContent()))
	   		 {
	   			 ChatRoom newChatRoom = new ChatRoom(message.getContent(), new ArrayList<String>());
	   			 this.theServerMainFrame.getChatRoomList().add(newChatRoom);
	   			 this.theServerMainFrame.getChatRoomList().get(this.theServerMainFrame.getChatRoomList().indexOf(newChatRoom)).getConnectedUserList().add(message.getSender());
	   			 
	   			 this.transportDriver.sendMessage(new MessageChat(MessageType.ChatRoomCreationAcknowledgment, "server", message.getSender(), message.getContent()));
	   		 
	   		     //create ChatRoomController
		   		 this.theServerMainFrame.getChatRoomControllerList().add(new ChatRoomController(message.getContent(), this.theServerMainFrame));
	   		 }
	   		 else
	   		 {
	   			 this.transportDriver.sendMessage(new MessageChat(MessageType.ChatRoomCreationAcknowledgment, "server", message.getSender(), "false"));
	   		 }
	   		 
	   		 this.sendUpdate();
	   		 
	   		 this.updateView();
	   		 
	   		 break;
	   		 
	   	 case ChatRoomCreationAcknowledgment:
	   		 //Client Side
	   		 break;
	   		 
	   	 case ChatRoomJoiningRequest:
	   		if(this.theServerMainFrame.chatRoomExists(message.getContent()))
	   		 {
	   			if(this.theServerMainFrame.getConnectedUserIndex(this.theServerMainFrame.getChatRoomList().get(this.theServerMainFrame.getChatRoomIndex(message.getContent())).getConnectedUserList(), message.getSender()) == -1)
	   			{
	   				this.theServerMainFrame.getChatRoomList().get(this.theServerMainFrame.getChatRoomIndex(message.getContent())).getConnectedUserList().add(message.getSender());
		   			 
		   			 this.transportDriver.sendMessage(new MessageChat(MessageType.ChatRoomJoiningAcknowledgment, "server", message.getSender(), message.getContent()));
	   			}
	   			else
	   			{
	   				this.transportDriver.sendMessage(new MessageChat(MessageType.ChatRoomJoiningAcknowledgment, "server", message.getSender(), "false"));
	   			}	
	   		 }
	   		 else
	   		 {
	   			 this.transportDriver.sendMessage(new MessageChat(MessageType.ChatRoomJoiningAcknowledgment, "server", message.getSender(), "false"));
	   		 }
	   		 
	   		 this.sendUpdate();
	   		
	   		 this.updateView();
	   		 break;
	   		 
	   	 case ChatRoomLeavingRequest:  
	   		 //Chat Room Controller Side
	   		 break;
	   		 
	   	 case UserLoggingOut:   		 
	   		 this.theServerMainFrame.getConnectedUserList().remove(this.theServerMainFrame.getConnectedUserIndex(this.theServerMainFrame.getConnectedUserList(), message.getSender()));
	   		 
	   		 for(int i=0; i<this.theServerMainFrame.getChatRoomList().size(); i++)
	   		 {
	   			 if( this.theServerMainFrame.getConnectedUserIndex(this.theServerMainFrame.getChatRoomList().get(i).getConnectedUserList(), message.getSender()) != -1)
	   			 {
	   				this.theServerMainFrame.getChatRoomList().get(i).getConnectedUserList().remove(this.theServerMainFrame.getConnectedUserIndex(this.theServerMainFrame.getChatRoomList().get(i).getConnectedUserList(), message.getSender()));
	   			 }
	   		 }
	   		 
	   		 this.sendUpdate();
	   		 
	   		 this.updateView();
	   		 
	   		 break;
	   		 
	   	 case UserDataMessage:
	   		 //Client Side
	   		 break;	   		 
	   	 case ServerClosingDown:
	   		 //Client side
	   		 break;
		case ChatRoomJoiningAcknowledgment:
			//Client Side
			break;
		case ChatRoomLeavingAcknowledgment:
			//Client Side
			break;
		case UserLoggingOutAcknowledgment:
			//Client Side
			break;
		default:
			break;
	   	     		
	   	 }
	}
	
	public void sendUpdate()
	{
		 this.theServerMainFrame.sendUpdate(this.transportDriver);
	}
	
	class ServerMainFrameListener implements WindowListener, ActionListener 
	{	
		public void windowActivated (WindowEvent e) 
        {
        	
        }
        public void windowClosed (WindowEvent e) 
        {
        	System.exit(0);
        }
        public void windowClosing (WindowEvent e) 
        {
            System.out.println("Server closing down");
        	transportDriver.sendMessage(new MessageChat(MessageType.ServerClosingDown, "server", null, null));
        }
        public void windowDeactivated (WindowEvent e) 
        {
        	
        }
        public void windowDeiconified (WindowEvent e) 
        {
        	
        }
        public void windowIconified (WindowEvent e) 
        {
        	
        }
        public void windowOpened (WindowEvent e) 
        {
        	
        }
        
		@Override
		public void actionPerformed(ActionEvent arg0) 
		{			
			if(arg0.getActionCommand().equals("Create a Chat Room"))
			{
				String newChatRoomName = JOptionPane.showInputDialog(null, "Enter the name of the new Chat room", "Create a chat Room", JOptionPane.QUESTION_MESSAGE);
				 
				if(!theServerMainFrame.chatRoomExists(newChatRoomName))
				{
					 ChatRoom newChatRoom = new ChatRoom( newChatRoomName, new ArrayList<String>());
		   			 theServerMainFrame.getChatRoomList().add(newChatRoom);
		   			 
		   			 if( !theServerMainFrame.getConnectedUserList().isEmpty())
		   			 {
		   				transportDriver.sendMessage(new MessageChat(MessageType.ChatRoomCreationAcknowledgment, "server", null, newChatRoomName));
		   			 }
		   			 
		   		     //create ChatRoomController
			   		 theServerMainFrame.getChatRoomControllerList().add(new ChatRoomController(newChatRoomName, theServerMainFrame));
		   			 
			   		 sendUpdate();
			   		 
		   			 updateView();
				}
			}
			else if(arg0.getActionCommand().equals("Delete a Chat Room"))
			{				 
				if(!theServerMainFrame.getChatRoomList().isEmpty())
				{
					String[] chatRoomNames = new String[theServerMainFrame.getChatRoomList().size()];
					for(int i=0; i<theServerMainFrame.getChatRoomList().size(); i++)
					{
						chatRoomNames[i]=theServerMainFrame.getChatRoomList().get(i).getName();
					}
					
					String chatRoomName = (String) JOptionPane.showInputDialog(null, "Enter the name of the Chat room to delete", "Delete a chat Room", JOptionPane.QUESTION_MESSAGE, null, chatRoomNames, chatRoomNames[0]);
		   			
					//Supprimer le chatRoomController
			   		theServerMainFrame.getChatRoomControllerList().remove(theServerMainFrame.getChatRoomIndex(chatRoomName));
			   		
					theServerMainFrame.getChatRoomList().remove(theServerMainFrame.getChatRoomIndex(chatRoomName));
					
					if( !theServerMainFrame.getConnectedUserList().isEmpty())
		   			{
		   				transportDriver.sendMessage(new MessageChat(MessageType.ChatRoomDeleteAcknowledgment, "server", null, chatRoomName));
		   			}
					
					sendUpdate();
					
		   			updateView();
				}
			}
		}
    }
}
