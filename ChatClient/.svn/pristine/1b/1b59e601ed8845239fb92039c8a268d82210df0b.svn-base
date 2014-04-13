import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ChatPanel extends JPanel{
	private String name;
	private JButton send;
	private JTextField writting;
	private JTextArea discussion;
	private JButton disconnect;
	
	public ChatPanel(String name){
		this.name=name;
		this.send = new JButton("Send");
		this.writting = new JTextField(20);
		this.discussion = new JTextArea();
		this.disconnect = new JButton("Disconnect");
		this.setLayout(new BorderLayout());
		JPanel north = new JPanel();
		JPanel south = new JPanel();
		
		this.add(discussion , BorderLayout.CENTER);
		south.add(writting);
		south.add(send);
		north.add(disconnect);
		this.add(south , BorderLayout.SOUTH);
		this.add(north, BorderLayout.NORTH);
		
	}
	
	public void sendListener (ActionListener l){
		send.addActionListener(l);
	}
	
	public void disconnectListener (ActionListener l){
		disconnect.addActionListener(l);
	}
	
	public void setDiscussion(String message){
		discussion.append(message);
	}
	
	public String getMessage(){
		return writting.getText();
	}
	
	public String getName(){
		return this.name;
	}
}
