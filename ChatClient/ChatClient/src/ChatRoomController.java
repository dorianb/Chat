import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class ChatRoomController implements TransportReceiver
{
     private ClientMainFrame clientFrame;
	 private String clusterName;
     private TransportDriver transportDriver;
     private ChatPanel chatPanel;
     
	 public ChatRoomController(String clusterName, ChatPanel chatPanel , ClientMainFrame clientFrame)
     {
    	 this.clientFrame = clientFrame;
    	 this.chatPanel = chatPanel;
		 this.clusterName = clusterName;
    	 this.transportDriver = new TransportDriver(clusterName, this);
    	 this.transportDriver.connect();
    	 this.chatPanel.sendListener(new SendMessage());
    	 this.chatPanel.disconnectListener(new Disconnect());
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
	 
	 //mutators
	 public void setClusterName(String clusterName)
	 {
		 this.clusterName = clusterName;
	 }
	 public void setTransportDriver(TransportDriver transportDriver)
	 {
		 this.transportDriver = transportDriver;
	 }
	 
	 public class SendMessage implements ActionListener{
		 public void actionPerformed(ActionEvent evt){
			 transportDriver.sendMessage(new MessageChat(MessageType.UserDataMessage,clientFrame.getName(),null,chatPanel.getMessage()));
		 }
	 }
	 
	 public class Disconnect implements ActionListener{
		 public void actionPerformed(ActionEvent evt){
			 transportDriver.sendMessage(new MessageChat(MessageType.ChatRoomLeavingRequest,clientFrame.getName(),"server",chatPanel.getName()));
		 }
	 }
	 
	@Override
	public void receiveChatMessage(MessageChat message) 
	{
		// TODO Auto-generated method stub
		switch(message.getType())
		{
		   	 case ConnectionEstablishment:
		   		 
		   		 break;
			   		 
		   	 case ConnectionAcknowledgment:
			   	 break;
			   	 
		   	 case ChatRoomsInformationRequest:
			   		 break;
			   		 
		   	 case AvailableChatRooms:
			   		 break;
			   		 
			 case ConnectedUsers:
			   		 break;
			   		 
		 	 case ChatRoomCreationRequest:
			   		 break;
			   		 
		   	 case ChatRoomCreationAcknowledgment:
			   		 break;
			   		 
		   	 case ChatRoomJoiningRequest:
			   		 break;
			   		 
		   	 case ChatRoomLeavingRequest:
			   		 break;
			   		 
		   	 case UserLoggingOut:
			   		 break;
			   		 
		   	 case UserDataMessage:
		   		 chatPanel.setDiscussion(message.getSender() + " : " + message.getContent() + "\n");
		   		 clientFrame.setVisible(true);
		   		 
			   		 break;
			   		 
		   	 case ServerClosingDown:
			   		 break;
		   	 case ChatRoomJoiningAcknowledgment:
		   		 break;
		   	 case ChatRoomLeavingAcknowledgment:
		   		 clientFrame.onglets.remove(clientFrame.onglets.getSelectedComponent());
		   		 clientFrame.setVisible(true);
		   		 break;
		   	 case UserLoggingOutAcknowledgment:
		   		 break;
		   	 default:
		   		 break;	   	     		
	   	 }
	}
}
