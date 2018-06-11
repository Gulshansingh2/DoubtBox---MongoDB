package clientChat;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientSide extends JFrame{

    /** Creates a new instance of ClientSide */
    private JTextArea ChatBox=new JTextArea(10,45);
    private JScrollPane myChatHistory=new JScrollPane(ChatBox,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
            JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    private JTextArea UserText = new JTextArea(5,47);
    private JScrollPane myUserHistory=new JScrollPane(UserText,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
            JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    private JButton Send = new JButton("Send");
    private JButton Start = new JButton("Connect");
    private Client ChatClient; // client is a class which extends Thread
    private ReadThread myRead; //ReadThread is a class used to read messages
    private JTextField Server=new JTextField(20);
    private JLabel myLabel=new JLabel("Server Name :");
    private JTextField User=new JTextField(20);
    private String ServerName;
    private String UserName;
    private JLabel chatHistory = new JLabel("Chat History");
    private JLabel chatBox = new JLabel("Chat Box : ");
    
    Font myFont = new Font("Aerial",Font.PLAIN,20);
    Font myFont1 = new Font("Aerial",Font.PLAIN,15);


    public ClientSide(String ip) {
   
        setResizable(false);
        setTitle("Client");
        setSize(700,550);
        Container cp=getContentPane();
        FlowLayout f = new FlowLayout();
        f.setHgap(10);
        f.setVgap(10);
        cp.setLayout(f);
       
        Server.setText(ip);
        
        Send.setBackground(Color.pink);
        Start.setBackground(Color.pink);
        
        Send.setFont(myFont);
        Start.setFont(myFont);
        ChatBox.setFont(myFont1);
        ChatBox.setFont(myFont1);
        chatHistory.setFont(myFont);
        chatBox.setFont(myFont);
        myLabel.setFont(myFont);
        Server.setFont(myFont);
        User.setFont(myFont);
        
        ChatBox.setEditable(false);
        
        cp.add(chatHistory);
        cp.add(myChatHistory);
        cp.add(chatBox);
        cp.add(myUserHistory);
        cp.add(Send);
        cp.add(Start);
        cp.add(myLabel);
        cp.add(Server);
        cp.add(User);
       
        Send.addActionListener(new ActionListener()
        {
        	
            public void actionPerformed(ActionEvent e) {
                if(ChatClient!=null) {

                    if(!(UserText.getText() == ""))
                    {
                    System.out.println(UserText.getText());
                    ChatClient.SendMassage(UserText.getText());
                    ChatBox.append("\nYou: " + UserText.getText()+" ");
                    }

                    UserText.setText("");
                }
            }
        });
        Start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ChatClient=new Client();
                ChatClient.start();
            }
        });
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);


    }


    public class Client extends Thread {
        private static final int PORT=9999;
        private LinkedList Clients;
        private ByteBuffer ReadBuffer;
        private ByteBuffer writeBuffer;
        private SocketChannel SChan;
        private Selector ReadSelector;
        private CharsetDecoder asciiDecoder;

       
        public Client() {
            Clients=new LinkedList();
            ReadBuffer=ByteBuffer.allocateDirect(300);
            writeBuffer=ByteBuffer.allocateDirect(300);
            asciiDecoder = Charset.forName( "US-ASCII").newDecoder();
        }
        

        public void run() {
        	myRead=new ReadThread();
            ServerName=Server.getText();
            System.out.println(ServerName);
            UserName=User.getText();

            Connect(ServerName);
            myRead.start();
            while (true) {

                ReadMassage();

                try {
                    Thread.sleep(30);
                } catch (InterruptedException ie){
                }
            }

        }
        
               
        public void Connect(String hostname) {
            try {
                ReadSelector = Selector.open();
                InetAddress addr = InetAddress.getByName(hostname);
                SChan = SocketChannel.open(new InetSocketAddress(addr, PORT));
                SChan.configureBlocking(false);

                SChan.register(ReadSelector, SelectionKey.OP_READ, new StringBuffer());
            }

            catch (Exception e) {
            	ChatBox.setText("Server not started yet. Wait!");
            }
        }
        
        
        
        public void SendMassage(String messg) {
            prepareBuffer(UserName+" says: "+messg);
            channelWrite(SChan);
                	
        }
        

        public void prepareBuffer(String massg) {
            writeBuffer.clear();
            writeBuffer.put(massg.getBytes());
            writeBuffer.putChar('\n');
            writeBuffer.flip();
        }
        
     
        public void channelWrite(SocketChannel client) {
            long num=0;
            long len=writeBuffer.remaining();
            while(num!=len) {
                try {
                    num+=SChan.write(writeBuffer);

                    Thread.sleep(5);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch(InterruptedException ex) {

                }

            }
            writeBuffer.rewind();
        }
 
        public void ReadMassage() {

            try {

                ReadSelector.selectNow();

                Set readyKeys = ReadSelector.selectedKeys();

                Iterator i = readyKeys.iterator();

                while (i.hasNext()) {

                    SelectionKey key = (SelectionKey) i.next();
                    i.remove();
                    SocketChannel channel = (SocketChannel) key.channel();
                    ReadBuffer.clear();


                    long nbytes = channel.read(ReadBuffer);

                    if (nbytes == -1) {
                        ChatBox.append("You logged out !\n");
                        channel.close();
                    } else {

                        StringBuffer sb = (StringBuffer)key.attachment();
                        ReadBuffer.flip( );
                        String str = asciiDecoder.decode( ReadBuffer).toString( );
                        sb.append( str );
                        ReadBuffer.clear( );


                        String line = sb.toString();
                        if ((line.indexOf("\n") != -1) || (line.indexOf("\r") != -1)) {
                            line = line.trim();

                            ChatBox.append("> "+ line);
                            ChatBox.append("\n");
                            sb.delete(0,sb.length());
                        }
                    }
                }
            } catch (IOException ioe) {
            } catch (Exception e) {
            }
        }
    }
    
    
    class ReadThread extends Thread {
 
        public void run() {
            ChatClient.ReadMassage();
        }
    }
}