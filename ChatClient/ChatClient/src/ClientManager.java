import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;


public class ClientManager implements TransportReceiver{
	private ClientMainFrame clientFrame;
	private TransportDriver trDriver;
	private List<ChatRoom> chatRoomList;
	
	public ClientManager(ClientMainFrame clientFrame){
		this.clientFrame = clientFrame;
		this.chatRoomList = new ArrayList<ChatRoom>();
		this.clientFrame.connectListener(new Connect());
		this.clientFrame.createChatRoomListener(new NewCluster());
		this.clientFrame.chatConnectionListener(new ChatConnection());
		
		trDriver = new TransportDriver("ControlCluster", this);
	}
	
public class Connect implements ActionListener{
	public void actionPerformed(ActionEvent e){
		clientFrame.setName(clientFrame.getPseudo());
		trDriver.connect();
		trDriver.sendMessage(new MessageChat(MessageType.ConnectionEstablishment, null, "server", clientFrame.getName()));
	}
}

public class NewCluster implements ActionListener{
	public void actionPerformed(ActionEvent e){
		trDriver.sendMessage(new MessageChat(MessageType.ChatRoomCreationRequest,clientFrame.getName(),"server",clientFrame.getNewClusterName()));
	}
}


public class ChatConnection implements ActionListener{
	public void actionPerformed(ActionEvent e){
		trDriver.sendMessage(new MessageChat(MessageType.ChatRoomJoiningRequest,clientFrame.getName(),"server",clientFrame.getSelectedNode()));
	}
}

public void receiveChatMessage(MessageChat msg){
	switch(msg.getType())
  	 {
  	 case ConnectionEstablishment:   		 
  		 break;
  		 
  	 case ConnectionAcknowledgment:	 
  		 if(msg.getTarget().equals(clientFrame.getPseudo()))
  		 {
  			 if(msg.getContent().equals("true"))
  			 {
  				 trDriver.sendMessage(new MessageChat(MessageType.ChatRoomsInformationRequest,clientFrame.getName(),"server", null));
  				 
  			 }
  			 else if(msg.getContent().equals("false"))
  			 {
  				clientFrame.connection.setToolTipText("Pseudo already exist, choose an other one");
  			 }
  			
  		 }
  	 break;
  	 
  	 case ChatRoomsInformationRequest:
  		 break;
  		 
  	 case AvailableChatRooms:
 
  		 for(int i = chatRoomList.size()-1;i>=0;i--){
  			 chatRoomList.remove(i);
  		 }
  		 String[] message = msg.getContent().split(";;");
  		 for(int i=0;i<message.length;i++){
  			 chatRoomList.add(new ChatRoom());
  			 chatRoomList.get(i).toChatRoom(message[i]);
  		 }
  		 clientFrame.setHome(chatRoomList);
  		 if(clientFrame.getContentPane().equals(clientFrame.connection)){
  			clientFrame.onglets.addTab("Home", clientFrame.home);
			clientFrame.setTitle(clientFrame.getName());
  		 }
  		 clientFrame.setContentPane(clientFrame.onglets);
  		 clientFrame.setVisible(true);
  		 break;
  		 
  	 case ConnectedUsers:
  		 break;
  		 
  	 case ChatRoomCreationRequest:
  		 break;
  		 
  	 case ChatRoomCreationAcknowledgment:
  		 
  		if(msg.getTarget().equals(clientFrame.getName())){
	  		 if(msg.getContent().equals("false")){
	  			 
	  		 }
	  		 else{
	  			trDriver.sendMessage(new MessageChat(MessageType.ChatRoomsInformationRequest,clientFrame.getName(),"server", null));
	  			clientFrame.chatLaunch(msg.getContent());
	  			clientFrame.setVisible(true);
	  		 }
  		}
  		 break;
  		 
  	 case ChatRoomJoiningRequest:
  		 break;
  		 
  	 case ChatRoomJoiningAcknowledgment:
  		 if(msg.getTarget().equals(clientFrame.getName())){
  			 if(!msg.getContent().equals("false")){
		  		 clientFrame.chatLaunch(msg.getContent());
		  		 clientFrame.setVisible(true);
  			 }
  		 }
  		 break;
  		 
  	 case ChatRoomLeavingRequest:
  		 break;
  		 
  	 case UserLoggingOut:
  		 break;
  		 
  	 case UserDataMessage:
  		 break;
  		 
  	 case ServerClosingDown:
  		 break;
  	     		
  	 }
}

}
