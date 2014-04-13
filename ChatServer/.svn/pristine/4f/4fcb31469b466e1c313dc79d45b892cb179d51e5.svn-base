package ChatServer;

public class ChatRoomController implements TransportReceiver
{
   	 private ServerMainFrame theServerMainFrame;
	 private String clusterName;
     private TransportDriver transportDriver;
     
	 public ChatRoomController(String clusterName, ServerMainFrame theServerMainFrame)
     {
    	 this.theServerMainFrame = theServerMainFrame;
		 this.clusterName = clusterName;
    	 this.transportDriver = new TransportDriver(clusterName, this);
     }
	 
	 //accessors
	 public String getClusterName()
	 {
		 return this.clusterName;
	 }
	 public TransportDriver getTransportDriver()
	 {
		 return this.transportDriver;
	 }
	 public ServerMainFrame getServerMainFrame()
	{
			return this.theServerMainFrame;
	}
	 
	 //mutators
	 public void setClusterName(String clusterName)
	 {
		 this.clusterName = clusterName;
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
		   		 //Server Controller Side
		   		 break;
			   		 
		   	 case ConnectionAcknowledgment:
		   	//Client Side
			   	 break;
			   	 
		   	 case ChatRoomsInformationRequest:
		   		 //Server Controller Side
			   		 break;
			   		 
		   	 case AvailableChatRooms:
		   		 //Server Controller Side
			   		 break;
			   		 
			 case ConnectedUsers:
				 //Server Controller Side
			   		 break;
			   		 
		 	 case ChatRoomCreationRequest:
		 		 //Server Controller Side
			   		 break;
			   		 
		   	 case ChatRoomCreationAcknowledgment:
		      	//Client Side
			   	break;
			   		 
		   	 case ChatRoomJoiningRequest:
		   		 //Server controller Side
			   		 break;
			   		 
		   	 case ChatRoomLeavingRequest:
		   		if( this.theServerMainFrame.chatRoomExists(message.getContent()))
		   		{
		   			this.theServerMainFrame.getChatRoomList().get(this.theServerMainFrame.getChatRoomIndex(message.getContent())).getConnectedUserList().remove(this.theServerMainFrame.getConnectedUserIndex(this.theServerMainFrame.getChatRoomList().get(this.theServerMainFrame.getChatRoomIndex(message.getContent())).getConnectedUserList(), message.getSender()));
			   		this.transportDriver.sendMessage(new MessageChat(MessageType.ChatRoomLeavingAcknowledgment, "server", message.getSender(), message.getContent()));
		   		}
		   		else
		   		{
		   			this.transportDriver.sendMessage(new MessageChat(MessageType.ChatRoomLeavingAcknowledgment, "server", message.getSender(), "false"));
		   		}
		   		 
		   		this.sendUpdate();
		   		 
		   		this.updateView();
		   		 
			    break;
			   		 
		   	 case UserLoggingOut:
		   		 //Server Controller Side
			   	 break;
			   		 
		   	 case UserDataMessage:
			   		 break;
			   		 
		   	 case ServerClosingDown:
		   		 //Client Side
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
}
