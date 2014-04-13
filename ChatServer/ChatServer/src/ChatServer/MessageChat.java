package ChatServer;

public class MessageChat 
{
     private MessageType type;
     private String sender;
     private String target;
     private String content;
     
     //constructors
     public MessageChat()
     {
    	 
     }
     public MessageChat(MessageType type, String sender, String target, String content)
     {
    	 this.type=type;
    	 this.sender=sender;
    	 this.target=target;
    	 this.content=content;
     }
     
     //Accessors
     public MessageType getType()
     {
    	 return this.type;
     }
     public String getSender()
     {
    	 return this.sender;
     }
     public String getTarget()
     {
    	 return this.target;
     }
     public String getContent()
     {
    	 return this.content;
     }
     
     //mutators
     public void setType(MessageType type)
     {
    	 this.type=type;
     }
     public void setSender(String sender)
     {
    	 this.sender=sender;
     }
     public void setTarget(String target)
     {
    	 this.target=target;
     }
     public void setContent(String content)
     {
    	 this.content=content;
     }
     
     @Override
     public String toString()
     {
    	 return this.getType().toString() + "\\" + this.getSender() + "\\" + this.getTarget() + "\\" + this.getContent();
     }
     
     public void toMessageChat(String msg)
     {    	 
    	 String[] message = msg.split("\\\\");
    	 
    	 this.setType(MessageType.valueOf(message[0]));
    	 this.setSender(message[1]);
    	 this.setTarget(message[2]);
    	 this.setContent(message[3]);
     }
}
