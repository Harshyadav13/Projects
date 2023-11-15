import java.net.*;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class Client extends JFrame {
    Socket socket;

    BufferedReader br;
    PrintWriter out;

    //componet declaration
    private JLabel heading = new JLabel("Client Area");
    private JTextArea messageArea = new JTextArea();
    private JTextField messageInput = new JTextField();
    private Font font = new Font("Roboto",Font.PLAIN,20);
    
    
    public Client() {

        try {

             System.out.println("Sending request to server ");
            socket = new Socket("127.0.0.1",7778);
            System.out.println("Connection done...");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            createGUI();
            handleEvents();

            startReading();
            startWriting();
        }
        catch (Exception e) {
            //Todo: Handle Exception
        }
    }

        private void handleEvents() {
            messageInput.addKeyListener(new KeyListener() {

                @Override
                public void keyTyped(KeyEvent e) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void keyPressed(KeyEvent e) {
                    // TODO Auto-generated method stub
                    
                }

                @Override
                public void keyReleased(KeyEvent e) {
                    // TODO Auto-generated method stub
                    // System.out.println("Key released "+e.getKeyCode());
                    if(e.getKeyCode() == 10) {
                        // System.out.println("you have pressed enter button");
                        String contentToSend = messageInput.getText();
                        messageArea.append("me : "+contentToSend + "\n");
                        out.println(contentToSend);
                        out.flush();
                        messageInput.setText("");
                        messageInput.requestFocus();
                    }
                }
                
            });
    }

        private void createGUI() {
            //gui code...
            this.setTitle("Client Messager[END]");
            this.setSize(450,600);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //coding for component
            heading.setFont(font);
            messageArea.setFont(font);
            messageInput.setFont(font);

            // here a logo has been set
            // heading.setIcon(new ImageIcon("th.jpg"));

            heading.setHorizontalAlignment(SwingConstants.CENTER);
            heading.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

            //here we are setting frame layout

            this.setLayout(new BorderLayout());

            //addding  the components to frame
            this.add(heading,BorderLayout.NORTH);
            this.add(messageArea,BorderLayout.CENTER);
            this.add(messageInput,BorderLayout.SOUTH);


            this.setVisible(true);
        }

    public void startReading() {
        // thread-read krke deta rahega.
        Runnable r1 = ()-> {
            System.out.println(" Reader started...");
            try{
            while(true) {
               
                String msg = br.readLine();
                if(msg.equals("exit"))
                {
                    System.out.println("Server terminated the chat..");
                    JOptionPane.showMessageDialog(this, "Server Terminated the chat");
                    messageInput.setEnabled(false);
                    socket.close();

                    break;
                }
                // System.out.println("Server : "+msg);
                messageArea.append("Server : "+ msg +"\n");
            }
        }
            catch(Exception e) {
                // e.printStackTrace();
                System.out.println("Connection is close...");
            }
            
        };

        new Thread(r1).start();
    }

    public void startWriting() {
        // thread-data user se lega aur client ko send karega.
        
        Runnable r2 = ()-> {
            System.out.println("Writer started...");
            try{
            while(true && !socket.isClosed()) {
               
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    if(content.equals("exit") ) {
                        socket.close();
                        break;
                    }

                }

                System.out.println("Connection is close...");

                }
                catch(Exception e) {
                    e.printStackTrace();
                }
            
        };

        new Thread(r2).start();

    }


    public static void main(String[] args) {
        System.out.println("this is client...");
        new Client();
    }
}