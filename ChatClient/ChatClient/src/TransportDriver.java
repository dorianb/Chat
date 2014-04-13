import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.View;
import org.jgroups.protocols.UDP;


public class TransportDriver implements Receiver{
	private String clusterName;
	private TransportReceiver trReceiver;
	private JChannel channel;
	
	public TransportDriver(String clusterName, TransportReceiver trReceiver){
		this.clusterName=clusterName;
		this.trReceiver=trReceiver;
	}
	
	public void connect(){
		List<String> ipList = this.getIps();
   	 
   	 System.setProperty("java.net.preferIPv4Stack", "true");

   	 //System.setProperty("jgroups.bind_addr", ipList.get(0) );

   	 //System.setProperty("jgroups.bind_addr", ipList.get(0) );
     System.setProperty("jgroups.bind_addr", "127.0.0.1" );

   	 
   	 try
   	 {
   		 this.channel = new JChannel();
   		 UDP protocol = (UDP)this.channel.getProtocolStack().getTransport();
   		 
   		 if (getPort() !=0)
   		 {
   			 protocol.setMulticastPort(getPort());
   		 }
   		 else
   		 {
   			 //JOptionPane.showMessageDialog(null, "can't find a port, default is used");
   		 }
   		 
   		 this.channel.connect(this.getClusterName());
   		 this.channel.setReceiver(this);
   		 //this.channel.setDiscardOwnMessages(true);
   	  }
   	  catch (Exception e)
   	  {
   		 System.out.println("<<class Abstract Channel Driver>>can not create channel ");
   	  }
    }
	

	@Override
	public void getState(OutputStream arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receive(Message arg0) 
	{
		MessageChat message = new MessageChat();
		message.toMessageChat(arg0.getObject().toString());
		this.trReceiver.receiveChatMessage(message);		
	}

	@Override
	public void setState(InputStream arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void block() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void suspect(Address arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unblock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void viewAccepted(View arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public String getClusterName(){
		return clusterName;
	}
	
	public int getPort(){
		return 9888;
	}
	
	public List<String> getIps(){
		List<String> ips = new ArrayList<String>();
	      
     	try
     	{
     		Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
      
              while (interfaces.hasMoreElements()) 
              {  
            	 // carte reseau trouvee
                 NetworkInterface interfaceN = (NetworkInterface)interfaces.nextElement();                 
                 Enumeration<InetAddress> ienum = interfaceN.getInetAddresses();
                 
                 while (ienum.hasMoreElements()) 
                 {  
                	 // retourne l adresse IPv4 et IPv6
                     InetAddress ia = ienum.nextElement();
                     String adress = ia.getHostAddress().toString();
      
                     if( adress.length() < 16)
                     {          
                    	 //On s'assure ainsi que l'adresse IP est bien IPv4
                         if(adress.startsWith("127"))
                         {  
                        	 
                         }
                         else if(adress.indexOf(":") > 0)
                         {
                            
                         }
                         else
                         {
                        	 ips.add(adress);  
                         }
                     }          
                 }
             }
         }
         catch(Exception e)
         {
             System.out.println("pas de carte reseau");
             e.printStackTrace();
         }
      
         return ips;
     }
	
	public void sendMessage(MessageChat message)
    {
   	try 
   	{
   		this.channel.send(new Message(null, null, message.toString()));
	} 
   	catch (Exception e) 
   	{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
	
}
