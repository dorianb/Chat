import java.util.ArrayList;
import java.util.List;

public class ChatRoom 
{
	private String name;
    private List<String> connectedUserList;
    
    public ChatRoom(){
    	this.connectedUserList = new ArrayList<String>();
    }
    
    public ChatRoom(String name, List<String> connectedUserList)
    {
   	  this.name = name;
   	  this.connectedUserList = connectedUserList;
    }
    
    //getters
    public String getName()
    {
   	 return this.name;
    }
    public List<String> getConnectedUserList()
    {
   	 return this.connectedUserList;
    }
    
    //setters
    public void setName(String name)
    {
   	 this.name = name;
    }
    public void setConnectedUserList(List<String> connectedUserList)
    {
   	 this.connectedUserList = connectedUserList;
    }
    
    @Override
    public String toString()
    {
   	    String str = this.getName() + "::";
   	    
   	    for(int i=0; i<this.getConnectedUserList().size(); i++)
   	    {
   	    	str += this.getConnectedUserList().get(i) + "::";
   	    }
   	    
   	    return str;
    }
    
    public void toChatRoom(String str)
    {
    	String[] chatRoom = str.split("::");
    	
    	this.name = chatRoom[0];
    	
    	for(int i=1; i<chatRoom.length; i++)
    	{
    		this.getConnectedUserList().add(chatRoom[i]);
    	}
    }
}