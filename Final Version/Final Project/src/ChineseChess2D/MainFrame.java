/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChineseChess2D;


import java.awt.Color;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ChineseChess3D.*;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.io.File;
import java.util.Map.Entry;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.media.opengl.GLCanvas;
import scenegraph.GLRenderableNode;

/**
 *
 * @author konatan
 */
public class MainFrame extends JFrame implements ActionListener{
    public JButton singleGame;
    public JButton onlineGame;
    public JButton BuildRoom;
    public JButton JoinRoom;
    private JButton ready;
    public JPanel panelMenu;   
    public JPanel panelChess;
    public JPanel panelButtonComponent;
    public JPanel panelTime;
    public JButton buttonUndo;
    public JButton buttonDraw;
    public JButton buttonGiveup;
    public JButton buttonMenu;
    public JToggleButton Button2D;
    public JToggleButton Button3D;
    public Player p1,p2;
    public LoginFrame login;
    public ChessBoard2D CB2D;
    public TimeRecorder TR;
    public Database db;
    public ChineseChess CH3D;
    private JFrame jf;//waitingwindow
    public playerPanel player1,player2;
    public boolean onlineMode = false;
    private boolean startRecieve = false;
    private JPanel panelText;
    private JTextArea ta;
    private JTextField textfield;
    private JButton button;    //watingwindow
    private JLabel message; //watingwindow
    private JFrame frame; //onlinepane
    private threadSocket ts;
    private ImageIcon bg;
    private JLabel bglabel;
    public static ServerSocket ssocket = null;
    public static Socket socket = null;
    public ObjectOutputStream oos;
    public ObjectInputStream ois;
    private String Turn1,Turn2;
    private static int port = 8000;
    private Object sendObject = null,recieveObject = null;
    private JPanel loading;
    private JDialog jd;
    private boolean socketCheck;
    private static int portCount;
    public MainFrame(){
        db = new Database("jdbc:derby://localhost:1527/ChineseChess");
        panelMenu = new JPanel();        
        panelChess = new JPanel(); 
        panelButtonComponent = new JPanel();
        panelTime = new JPanel();
        
        bg = new ImageIcon("image/mainbackground.jpg");
        bglabel = new JLabel(bg);
        bglabel.setBounds(0,0, bg.getIconWidth(),bg.getIconHeight());
        this.getLayeredPane().add(bglabel,new Integer(Integer.MIN_VALUE));
        ((JPanel)this.getContentPane()).setOpaque(false);
        
        this.setLayout(null);
        this.setSize(1300, 900);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                if(onlineMode){
                    try{
                        onlineMode = false;
                        startRecieve = false;
                        recieveObject = null;
                        sendObject = null;
                        oos.close();
                        ois.close();
                        socket.close();
                        if(ssocket != null){
                            ssocket.close();
                        }
                        oos = null;
                        ois = null;
                        socket = null;
                        ssocket = null; 
                    }
                    catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
                db.deleteTable("delete from chessmanual where username ='"+p1.getName()+"'");
                db.updateTable("update USERDATA set islogin = false where username = '"+p1.getName()+"'");
                System.exit(0);
            }
        });
        this.addWindowFocusListener(new WindowFocusListener(){
            public void windowGainedFocus(WindowEvent e) {
                if(login != null)
                    login.setAlwaysOnTop(true);
                if(frame != null)
                    frame.setAlwaysOnTop(true);;
                if(jf != null)
                    jf.setAlwaysOnTop(true);
            }

            public void windowLostFocus(WindowEvent e) {
                if(login != null)
                    login.setAlwaysOnTop(false);
                if(frame != null)
                    frame.setAlwaysOnTop(false);
                if(jf != null)
                    jf.setAlwaysOnTop(false);
            }
        });
        this.setVisible(true);
        this.setTitle("Chinese Chess");
        this.setResizable(false);
        //this.setAlwaysOnTop(true);
        
        this.setEnabled(false);
        initMenuPanel();
        this.add(panelChess);
        this.add(panelMenu);
        login = new LoginFrame(this);
        while(!this.isEnabled()){
            System.out.print("");
        }
        p1 = login.getPlayerInfo(login.getPlayID()); 
    }
    
    public void initMenuPanel(){
        panelMenu.setBounds(0,0,this.getWidth(),this.getHeight());
        panelMenu.setBackground(Color.red);
        panelMenu.setLayout(null);
        panelMenu.setBorder((new EmptyBorder(5,5,5,5)));
        panelMenu.setOpaque(false);
        
        ImageIcon icon = new ImageIcon("image/singleButton.png");
        singleGame = new JButton();
        singleGame.setBounds(440, 450, 200, 84);
        singleGame.addActionListener(this);
        singleGame.setIcon(icon);
        icon = new ImageIcon("image/singleButton_press.png");
        singleGame.setPressedIcon(icon);
        singleGame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        singleGame.setOpaque(false);  
        singleGame.setContentAreaFilled(false);  
        singleGame.setMargin(new Insets(0, 0, 0, 0));  
        singleGame.setFocusPainted(false);  
        singleGame.setBorderPainted(false);  
        singleGame.setBorder(null);  
        icon = new ImageIcon("image/onlineButton.png");
        onlineGame = new JButton();
        onlineGame.setBounds(660, 450, 200, 84);
        onlineGame.addActionListener(this);
        onlineGame.setIcon(icon);
        icon = new ImageIcon("image/onlineButton_press.png");
        onlineGame.setPressedIcon(icon);
        onlineGame.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        onlineGame.setOpaque(false);  
        onlineGame.setContentAreaFilled(false);  
        onlineGame.setMargin(new Insets(0, 0, 0, 0));  
        onlineGame.setFocusPainted(false);  
        onlineGame.setBorderPainted(false);  
        onlineGame.setBorder(null);  
        
        Button2D = new JToggleButton("",true);
        icon = new ImageIcon("image/2Dbutton_selected.png");
        Button2D.setBounds(450, 325, 200, 84);
        Button2D.setIcon(icon);
        Button2D.addActionListener(this);
        Button2D.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Button2D.setOpaque(false);  
        Button2D.setContentAreaFilled(false);  
        Button2D.setMargin(new Insets(0, 0, 0, 0));  
        Button2D.setFocusPainted(false);  
        Button2D.setBorderPainted(false);  
        Button2D.setBorder(null);  
       
        Button3D = new JToggleButton("",false);
        icon = new ImageIcon("image/3Dbutton_normal.png");
        Button3D.setBounds(650, 325, 200, 84);
        Button3D.setIcon(icon);
        Button3D.addActionListener(this);
        Button3D.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Button3D.setOpaque(false);  
        Button3D.setContentAreaFilled(false);  
        Button3D.setMargin(new Insets(0, 0, 0, 0));  
        Button3D.setFocusPainted(false);  
        Button3D.setBorderPainted(false);  
        Button3D.setBorder(null);  
        
        panelMenu.add(singleGame);
        panelMenu.add(onlineGame);  
        panelMenu.add(Button2D); 
        panelMenu.add(Button3D);

    }
    
    public void initChessPanel(){
        panelChess.setBounds(0,0,this.getWidth(),this.getHeight());
        panelChess.setBorder((new EmptyBorder(5,5,5,5)));
        panelChess.setLayout(null);
        panelChess.setVisible(false);
        panelChess.setOpaque(false);
        
        ImageIcon icon = new ImageIcon("image/Ready.png");
        ready = new JButton();
        ready.setBounds(550, 815, 200, 50);
        ready.addActionListener(this);
        ready.setIcon(icon);
        ready.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ready.setOpaque(false);  
        ready.setContentAreaFilled(false);  
        ready.setMargin(new Insets(0, 0, 0, 0));  
        ready.setFocusPainted(false);  
        ready.setBorderPainted(false);  
        ready.setBorder(null);  
        panelChess.add(ready);
        
        initButtonComponent();
        initTextComponent();
        initPanelTime();  
    }
    
    public void initPanelTime(){
        panelTime.setBounds(0, 10, 250, 890);
        panelTime.setLayout(null);
        panelTime.setOpaque(false);
        TR = new TimeRecorder(this);
        if(!onlineMode)
            TR.SetBlackRed();
//        TR.Start();
        player1 = new playerPanel(p1,Turn1);
        player1.setBottomLocation();
        if(onlineMode){
            try {
                p2 = login.getPlayerInfo((int)ois.readObject());
                player2 = new playerPanel(p2,Turn2);
                if(player1.turn.equals("red")) 
                    TR.SetBlackRed();
                else
                    TR.SetRedBlack();
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            player2 = new playerPanel(Turn2);  
        }
        player2.setTopLocation();
        panelTime.add(player1);
        panelTime.add(player2);
        panelTime.add(TR);
    }
    
    public void init3DChessPanel(){

    }
    
    public void initTextComponent(){
        panelText = new JPanel();
        panelText.setBorder(BorderFactory.createEmptyBorder(10,5,0,5));
        panelText.setOpaque(false);
        JScrollPane jsp=new JScrollPane();
        ta = new JTextArea();
        textfield = new JTextField();
        textfield.addKeyListener(new KeyAdapter(){
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    ta.append(p1.getName()+" :"+textfield.getText()+"\n");
                    if(onlineMode){
                        sendObject = (Object)("#m#"+p1.getName()+": "+textfield.getText()+"\n");
                        sendObject(sendObject);
                    }
                    textfield.setText("");
                }
            }
        });
        panelText.setBounds(1050, 410, 250, 400);
        panelText.setLayout(new BorderLayout());
        //panelText.setBackground(Color.blue);
        
        ta.setBackground(Color.white);
        ta.setEditable(false);
        ta.setLineWrap(true);
        jsp.setViewportView(ta);
        jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        panelText.add(jsp, BorderLayout.CENTER);
        panelText.add(textfield,BorderLayout.SOUTH);
    }
    
    public void appendText(String str){
        ta.append(str);
    }
    public void giveUp(String color){
        if(color.equals("red")){
            JOptionPane.showMessageDialog(this,"The red gives up. The victory belongs to Black!", "Victory",JOptionPane.NO_OPTION);
            CB2D.selectPiece = null;
            if(CB2D.flickerCheck){
                CB2D.flickerCheck = false;
                CB2D.k.stop();
            }
            CB2D.clickNum = -1;
            CB2D.turn = null;
            TR.stop();
            
        }
        else{
            JOptionPane.showMessageDialog(this,"The black gives up. The victory belongs to Red!", "Victory", JOptionPane.NO_OPTION);
            CB2D.selectPiece = null;
            if(CB2D.flickerCheck){
                CB2D.flickerCheck = false;
                CB2D.k.stop();
            }
            CB2D.clickNum = -1;
            CB2D.turn = null;
            TR.stop();
        }
    }
    
    public void initButtonComponent(){
        
        buttonUndo = new JButton();
        buttonDraw = new JButton();
        buttonGiveup = new JButton();
        buttonMenu = new JButton();
        panelButtonComponent.setBounds(1050, 10, 250, 390);
        panelButtonComponent.setLayout(null);
        panelButtonComponent.setOpaque(false);
        panelButtonComponent.setBorder((new EmptyBorder(5,5,5,5)));
        
        ImageIcon icon = new ImageIcon("image/Undo.png");
        buttonUndo.setBounds(50, 40, 150, 50);
        buttonUndo.setIcon(icon);
        buttonUndo.setOpaque(false);  
        buttonUndo.setContentAreaFilled(false);  
        buttonUndo.setMargin(new Insets(0, 0, 0, 0));  
        buttonUndo.setFocusPainted(false);  
        buttonUndo.setBorderPainted(false);  
        buttonUndo.setBorder(null);  
        buttonUndo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        icon = new ImageIcon("image/Draw.png");
        buttonDraw.setBounds(50, 130, 150, 50);
        buttonDraw.setIcon(icon);
        buttonDraw.setOpaque(false);  
        buttonDraw.setContentAreaFilled(false);  
        buttonDraw.setMargin(new Insets(0, 0, 0, 0));  
        buttonDraw.setFocusPainted(false);  
        buttonDraw.setBorderPainted(false);  
        buttonDraw.setBorder(null);  
        buttonDraw.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        icon = new ImageIcon("image/Give Up.png");
        buttonGiveup.setBounds(50, 220, 150, 50);
        buttonGiveup.setIcon(icon);
        buttonGiveup.setOpaque(false);  
        buttonGiveup.setContentAreaFilled(false);  
        buttonGiveup.setMargin(new Insets(0, 0, 0, 0));  
        buttonGiveup.setFocusPainted(false);  
        buttonGiveup.setBorderPainted(false);  
        buttonGiveup.setBorder(null);  
        buttonGiveup.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        icon = new ImageIcon("image/Main Menu.png");
        buttonMenu.setBounds(50, 310, 150, 50);
        buttonMenu.setIcon(icon);
        buttonMenu.setOpaque(false);  
        buttonMenu.setContentAreaFilled(false);  
        buttonMenu.setMargin(new Insets(0, 0, 0, 0));  
        buttonMenu.setFocusPainted(false);  
        buttonMenu.setBorderPainted(false);  
        buttonMenu.setBorder(null); 
        buttonMenu.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        buttonMenu.addActionListener(this);
        buttonGiveup.addActionListener(this);
        buttonDraw.addActionListener(this);
        buttonUndo.addActionListener(this);
        panelButtonComponent.add(buttonUndo);
        panelButtonComponent.add(buttonDraw);
        panelButtonComponent.add(buttonGiveup);
        panelButtonComponent.add(buttonMenu);
        
    }
    
    public void loadingPanel(){
        loading = new JPanel();
        loading.setBounds(0, 0, this.getWidth(), this.getHeight());
        loading.setBackground(Color.BLACK);
        loading.setVisible(true);
    }
    public void waitingWindow(String str){
        jf = new JFrame();
        button = new JButton("Cancel");
        message = new JLabel(str,JLabel.CENTER);
        message.setForeground(Color.white);
        jf.setSize(300, 100);
        jf.setLocationRelativeTo(frame);
        jf.setUndecorated(true);
        jf.setVisible(true);
        //jf.setAlwaysOnTop(true);
        ImageIcon bg1 = new ImageIcon("image/background.jpg");
        JLabel bglabel1 = new JLabel(bg1);
        bglabel1.setBounds(0,0, bg1.getIconWidth(),bg1.getIconHeight());
        jf.getLayeredPane().add(bglabel1,new Integer(Integer.MIN_VALUE));
        ((JPanel)jf.getContentPane()).setOpaque(false);
        this.setEnabled(false);       
        button.addActionListener((ActionEvent e) -> {
            try {
                if(button.getText().equals("Cancel")){
                    if(socket!=null){
                        socket.close();
                    }
                    if(ssocket!=null){
                        ssocket.close();
                    }
                    ts.stop();
                    frame.setEnabled(true);
                    jf.dispose();
                }
                else if(button.getText().equals("Ok")){
                    frame.setEnabled(true);
                    jf.dispose();
                }
                else if (button.getText().equals("Enter")){
                    jf.dispose();
                    frame.dispose();
                    this.setEnabled(true);
                    this.requestFocus();
                    initChessPanel();
                    CB2D = new ChessBoard2D(TR,this);
                    if(Turn1.equals("red"))
                        CB2D.RedBottomBlackTop();
                    else
                        CB2D.RedTopBlackBottom();
                    panelChess.add(CB2D);
                    panelChess.add(panelButtonComponent);
                    panelChess.add(panelTime);
                    panelChess.add(panelText);
                    bg = new ImageIcon("image/background.jpg");
                    bglabel = new JLabel(bg);
                    bglabel.setBounds(0,0, bg.getIconWidth(),bg.getIconHeight());
                    panelChess.add(bglabel,new Integer(Integer.MIN_VALUE));
                    ready.setVisible(true);
                    panelMenu.setVisible(false);
                    panelChess.setVisible(true);
                    startRecieve = true;
                    Thread t = new Thread(() -> {
                        streamStart();
                    });
                    t.start();

                } 
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        jf.add(message,BorderLayout.CENTER);
        jf.add(button,BorderLayout.SOUTH);
    }
    
    public void initOnlinePane(){
        frame = new JFrame();
        ImageIcon bg = new ImageIcon("image/onlinePaneBack.jpg");
        JLabel bglabel = new JLabel(bg);
        bglabel.setBounds(0,0, bg.getIconWidth(),bg.getIconHeight());
        frame.getLayeredPane().add(bglabel,new Integer(Integer.MIN_VALUE));
        ((JPanel)frame.getContentPane()).setOpaque(false);
        JToggleButton buildR = new JToggleButton("",true);
        buildR.setBounds(98, 15, 102, 33);
        ImageIcon icon = new ImageIcon("image/BuildR_select.png");
        buildR.setIcon(icon);
        buildR.setOpaque(false);  
        buildR.setContentAreaFilled(false);  
        buildR.setMargin(new Insets(0, 0, 0, 0));  
        buildR.setFocusPainted(false);  
        buildR.setBorderPainted(false);  
        buildR.setBorder(null);  
        buildR.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JToggleButton joinR = new JToggleButton("",false);
        joinR.setBounds(200, 15, 102, 33);
        icon = new ImageIcon("image/JoinR.png");
        joinR.setIcon(icon);
        joinR.setOpaque(false);  
        joinR.setContentAreaFilled(false);  
        joinR.setMargin(new Insets(0, 0, 0, 0));  
        joinR.setFocusPainted(false);  
        joinR.setBorderPainted(false);  
        joinR.setBorder(null);  
        joinR.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        JLabel label1 = new JLabel("",JLabel.CENTER);
        label1.setBounds(0, 3, 400, 93);
        JLabel label2 = new JLabel("Please input IP of the Room ",JLabel.CENTER);
        label2.setBounds(0, 3, 400, 43);
        JPanel panel1 = new JPanel();
        panel1.setBounds(0, 50, 400, 150);
        panel1.setOpaque(false);
        panel1.setLayout(null);
        JPanel panel2 = new JPanel();
        panel2.setBounds(0, 50, 400, 150);
        panel2.setOpaque(false);
        panel2.setLayout(null);
        
        JButton build = new JButton();
        build.setBounds(80, 106, 104, 34);
        icon = new ImageIcon("image/build.png");
        build.setIcon(icon);
        build.setOpaque(false);  
        build.setContentAreaFilled(false);  
        build.setMargin(new Insets(0, 0, 0, 0));  
        build.setFocusPainted(false);  
        build.setBorderPainted(false);  
        build.setBorder(null);  
        build.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JButton join = new JButton();
        join.setBounds(80, 106, 104, 34);
        icon = new ImageIcon("image/join.png");
        join.setIcon(icon);
        join.setOpaque(false);  
        join.setContentAreaFilled(false);  
        join.setMargin(new Insets(0, 0, 0, 0));  
        join.setFocusPainted(false);  
        join.setBorderPainted(false);  
        join.setBorder(null);  
        join.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        JButton cancel1 = new JButton();
        cancel1.setBounds(216, 106, 104, 34);
        icon = new ImageIcon("image/Cancel1.png");
        cancel1.setIcon(icon);
        cancel1.setOpaque(false);  
        cancel1.setContentAreaFilled(false);  
        cancel1.setMargin(new Insets(0, 0, 0, 0));  
        cancel1.setFocusPainted(false);  
        cancel1.setBorderPainted(false);  
        cancel1.setBorder(null);  
        cancel1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JButton cancel2 = new JButton();
        cancel2.setBounds(216, 106, 104, 34);
        icon = new ImageIcon("image/cancel2.png");
        cancel2.setIcon(icon);
        cancel2.setOpaque(false);  
        cancel2.setContentAreaFilled(false);  
        cancel2.setMargin(new Insets(0, 0, 0, 0));  
        cancel2.setFocusPainted(false);  
        cancel2.setBorderPainted(false);  
        cancel2.setBorder(null);  
        cancel2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        
        JTextField tf = new JTextField(20);
        tf.setBounds(100, 53, 200, 30);
        this.setEnabled(false);
        frame.setSize(400, 200);
        frame.setLocationRelativeTo(this);
        frame.setResizable(false);
        frame.setUndecorated(true);
        frame.setLayout(null);
        frame.setVisible(true);
        
//        frame.setAlwaysOnTop(true);
        label1.setText("<html><body>Build a room and wait for a client<br><br></body></html>");
        label1.setForeground(Color.white);
        label2.setForeground(Color.white);
        
        panel1.setOpaque(false);
        panel1.setLayout(null);
        panel1.add(label1);
        panel1.add(build);
        panel1.add(cancel1);
        
        panel2.setOpaque(false);
        panel2.setVisible(false);
        panel2.setLayout(null);
        panel2.add(label2);
        panel2.add(tf);
        panel2.add(join);
        panel2.add(cancel2);
        
        buildR.addActionListener((ActionEvent e) -> {
            panel1.setVisible(true);
            panel2.setVisible(false);
            buildR.setSelected(true);
            joinR.setSelected(false);
            buildR.setIcon(new ImageIcon("image/BuildR_select.png")); 
            joinR.setIcon(new ImageIcon("image/JoinR.png"));
        });
        joinR.addActionListener((ActionEvent e) -> {
            panel1.setVisible(false);
            panel2.setVisible(true);
            buildR.setSelected(false);
            joinR.setSelected(true);
            buildR.setIcon(new ImageIcon("image/BuildR.png")); 
            joinR.setIcon(new ImageIcon("image/JoinR_select.png"));
        });
        build.addActionListener((ActionEvent e) -> {
            boolean ssocketCheck = true;
            //port = 1;
            waitingWindow("Wating for a player.");
            frame.setEnabled(false);
            while(ssocketCheck){
                ssocketCheck = false;
                try {
                    ssocket = new ServerSocket(port);
                    //ssocket.setReuseAddress(true);
                } catch (IOException ex) {
                    //port++;
                    ssocketCheck = true;
                }
            }
            ts = new threadSocket(socket,ssocket);
            ts.start();
        });
        join.addActionListener((ActionEvent e) -> {
            socketCheck = true;
            portCount = 0;
            //port = 1;
            if(tf.getText().isEmpty()){
                label2.setText("Please enter correct IP Address of the Room ");
            }
            waitingWindow("Connecting.");
            frame.setEnabled(false);
            while(socketCheck && portCount<3){
                portCount++;
                socketCheck = false;
                try {
                    socket = new Socket(tf.getText(),port);
                    //socket.setReuseAddress(true);
                } catch (IOException ex) {
                    //port++;
                    socketCheck = true;
                }
            }
            ts = new threadSocket(socket);
            ts.start();
            tf.setText("");
   
        });
        cancel1.addActionListener((ActionEvent e) -> {
            this.setEnabled(true);
            onlineMode = false;
            frame.dispose();
        });
        cancel2.addActionListener((ActionEvent e) -> {
            this.setEnabled(true);
            onlineMode = false;
            frame.dispose();
        });
        frame.add(buildR);
        frame.add(joinR);
        frame.add(panel1);
        frame.add(panel2);
    }
    public void streamStart(){
        boolean start = true;
        try {
            while(start){
                if(onlineMode && startRecieve){
                    recieveObject = ois.readObject();
                    if(recieveObject != null){
                        String s = (String)recieveObject;
                        String str = s.substring(0, 3);
                        String string = s.substring(3, s.length());
                        switch(str){
                            case "#o#"://Sign of System message
                                if(string.equals("ready")){
                                    p2.setReadyStauts(true);
                                    player2.handupForReady();
                                    if(p1.getReadyStauts() && p2.getReadyStauts()){
                                        ready.setVisible(false);
                                        if(Turn1.equals("red")){
                                            TR.setTime();
                                        }
                                        if(Turn1.equals("black")){
                                            Thread waitingThread = new Thread(() -> {
                                                jd = new JDialog(this,"",true); 
                                                jd.add(new JLabel("Wait for host setting the time",JLabel.CENTER));
                                                jd.setSize(200, 80);
                                                jd.setLocationRelativeTo(this);
                                                jd.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                                                jd.setVisible(true);
                                            });
                                            waitingThread.start();
                                        }
                                        player1.handupForReady();
                                        player2.handupForReady();
                                        p1.setReadyStauts(false);
                                        p2.setReadyStauts(false);
                                        CB2D.turn = "red";
                                    }
                                }
                                else if(string.equals("notready")){
                                    p2.setReadyStauts(false);
                                    player2.handupForReady();
                                }
                                else if(string.equals("over")){//leave room
                                    sendObject("#o#Over");
                                    CB2D.turn = null;
                                    JOptionPane.showMessageDialog(this,p2.getName()+" leaves the room.", "Victory", JOptionPane.NO_OPTION);
                                    start = false;
                                }
                                else if(string.equals("Over")){
                                    CB2D.turn = null;
                                    start = false;
                                }
                                else if(string.equals("settime")){
                                    jd.dispose();
                                    int[] time = new int[6];
                                    String S = null;
                                    String number = "";
                                    int m = 0;
                                    recieveObject = ois.readObject();
                                    if(recieveObject != null)
                                        S = (String)recieveObject;
                                    for(int i = 0;i<S.length();i++){
                                        if(S.charAt(i) == ','){
                                            time[m]=Integer.parseInt(number);
                                            m++;
                                            number = "";
                                        }
                                        else
                                            number = number+S.charAt(i);     
                                    }
                                    TR.SetTime(time[0], time[1], time[2], time[3], time[4], time[5]);
                                    TR.Start();
                                }
                                else if(string.equals("changeTurn")){
                                    try {
                                        ResultSet rs;
                                        String id = "";
                                        int startX=0,startY=0;
                                        int endX=0,endY=0;
                                        boolean check = false;
                                        String eatid = "";
                                        if(CB2D.k.isAlive())
                                            CB2D.k.stop();
                                        rs = db.selectTable("select * from CHESSMANUAL where username = '"+p2.getName()+"' order by step desc");
                                        if(rs.next()){
                                            id = rs.getString(2);
                                            startX = rs.getInt(5);
                                            startY = 11-rs.getInt(6);
                                            endX = rs.getInt(7);
                                            endY = 11-rs.getInt(8);
                                            eatid = rs.getString(9);
                                        }
                                        rs.close();
                                        for(Piece2D p:CB2D.listPiece){
                                            if(p.getID().equals(eatid)){
                                                CB2D.point[endX][endY].removePiece(p, CB2D);
                                                check = true;
                                                CB2D.stepmusic = new Music("eat.wav",0);
                                                break;
                                            }
                                        }
                                        for(Piece2D p:CB2D.listPiece){
                                            if(p.getID().equals(id)){
                                                CB2D.point[startX][startY].removePiece(p, CB2D);
                                                CB2D.point[endX][endY].setPiece(p, CB2D); 
                                                CB2D.stepmusic = new Music("go.wav",0);
                                                break;
                                            }   
                                        }
                                        CB2D.repaint();
                                        if(CB2D.turn.equals("red")){
                                            CB2D.turn = "black";
                                            CB2D.step++;
                                            TR.Suspend("Red");
                                        }
                                        else{
                                            CB2D.turn = "red";
                                            CB2D.step++;
                                            TR.Suspend("Black");
                                        }
                                    } catch (SQLException ex) {
                                        Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                        
                                }
                                else if(string.equals("draw")){
                                    int option = JOptionPane.showConfirmDialog(this, "Are you willing to accept draw request ?", "Draw", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                                    switch (option) {
                                        case JOptionPane.YES_NO_OPTION: {
                                            CB2D.selectPiece = null;
                                            if(CB2D.flickerCheck){
                                                CB2D.flickerCheck = false;
                                                CB2D.k.stop();
                                            }
                                            CB2D.clickNum = -1;
                                            CB2D.turn = null;
                                            TR.stop();
                                            db.updateTable("update userdata set draw = "+(p1.getDrawNum()+1)+", matchesnum = "+(p1.getmatchSum()+1)+"where username = '"+p1.getName()+"'");
                                            Object sendObject = (Object)("#o#draw_yes");
                                            sendObject(sendObject);
                                            break;
                                        }
                                        case JOptionPane.NO_OPTION:{
                                            Object sendObject = (Object)("#o#draw_no");
                                            sendObject(sendObject);
                                            break;
                                        }
                                    }
                                }
                                else if(string.equals("draw_yes") || string.equals("draw_no")){
                                    jd.dispose();
                                    if(string.equals("draw_yes")){
                                        CB2D.selectPiece = null;
                                        if(CB2D.flickerCheck){
                                            CB2D.flickerCheck = false;
                                            CB2D.k.stop();
                                        }
                                        CB2D.clickNum = -1;
                                        CB2D.turn = null;
                                        TR.stop();
                                        db.updateTable("update userdata set draw = "+(p1.getDrawNum()+1)+",matchesnum = "+(p1.getmatchSum()+1)+"where username = '"+p1.getName()+"'");
                                        JOptionPane.showMessageDialog(this,"Opponent accepts the draw request. Draw!", "Draw",JOptionPane.NO_OPTION);
                                    }
                                    else if (string.equals("draw_no")){
                                        JOptionPane.showMessageDialog(this,"Opponent refuces the draw request.", "Draw",JOptionPane.NO_OPTION);
                                    }
                                }
                                else if(string.equals("giveup")){
                                    CB2D.selectPiece = null;
                                    if(CB2D.flickerCheck){
                                        CB2D.flickerCheck = false;
                                        CB2D.k.stop();
                                    }
                                    CB2D.clickNum = -1;
                                    CB2D.turn = null;
                                    TR.stop();
                                    db.updateTable("update userdata set win = "+(p1.getWinNum()+1)+",score = "+(p1.getScore()+100)+", matchesnum = "
                                            +(p1.getmatchSum()+1)+",winrate = "+(float)(p1.getWinNum()+1)/(p1.getmatchSum()+1)+"where username = '"+p1.getName()+"'");
                                    JOptionPane.showMessageDialog(this,p2.getName()+" gives up. You win!", "Victory",JOptionPane.NO_OPTION);
                                }
                                else if(string.equals("giveupTime")){
                                    CB2D.selectPiece = null;
                                    if(CB2D.flickerCheck){
                                        CB2D.flickerCheck = false;
                                        CB2D.k.stop();
                                    }
                                    CB2D.clickNum = -1;
                                    CB2D.turn = null;
                                    TR.stop();
                                    db.updateTable("update userdata set win = "+(p1.getWinNum()+1)+",score = "+(p1.getScore()+100)+", matchesnum = "
                                            +(p1.getmatchSum()+1)+",winrate = "+(float)(p1.getWinNum()+1)/(p1.getmatchSum()+1)+"where username = '"+p1.getName()+"'");
                                    db.updateTable("update userdata set lose = "+(p2.getLoseNum()+1)+",score = "+(p2.getScore()-100)+", matchesnum = "
                                            +(p2.getmatchSum()+1)+",winrate = "+(float)(p2.getWinNum())/(p2.getmatchSum()+1)+"where username = '"+p2.getName()+"'");
                                    JOptionPane.showMessageDialog(this,p2.getName()+" gives up. You win!", "Victory",JOptionPane.NO_OPTION);
                                }
                                else if(string.equals("undo")){
                                    int option = JOptionPane.showConfirmDialog(this, "Are you willing to accept undo request ?", "Draw", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                                    switch (option) {
                                        case JOptionPane.YES_NO_OPTION: {
                                            try{
                                                ResultSet rs;
                                                rs = db.selectTable("select * from CHESSMANUAL where username = '"+p1.getName()+"' and step = "+CB2D.step);
                                                if(rs.next()){
                                                    Point2D pointEnd = CB2D.point[rs.getInt(7)][11-rs.getInt(8)];
                                                    Point2D pointStart = CB2D.point[rs.getInt(5)][11-rs.getInt(6)];
                                                    Piece2D piece = pointEnd.getPiece();
                                                    pointEnd.removePiece(piece, CB2D);
                                                    pointStart.setPiece(piece, CB2D);
                                                    if(rs.getString(9) != null && rs.getString(10) != null && rs.getString(11) != null){
                                                        for(Piece2D p2d:CB2D.listPiece){
                                                            if(p2d.getID().equals(rs.getString(9))){
                                                                pointEnd.setPiece(p2d, CB2D);
                                                            }
                                                        }
                                                    }   
                                                }
                                                rs.close();
                                                CB2D.step--;
                                                this.repaint();
                                                rs = db.selectTable("select * from CHESSMANUAL where username = '"+p2.getName()+"' and step = "+CB2D.step);
                                                if(rs.next()){
                                                    Point2D pointEnd = CB2D.point[rs.getInt(7)][rs.getInt(8)];
                                                    Point2D pointStart = CB2D.point[rs.getInt(5)][rs.getInt(6)];
                                                    Piece2D piece = pointEnd.getPiece();
                                                    pointEnd.removePiece(piece, CB2D);
                                                    pointStart.setPiece(piece, CB2D);
                                                    if(rs.getString(9) != null && rs.getString(10) != null && rs.getString(11) != null){
                                                        for(Piece2D p2d:CB2D.listPiece){
                                                            if(p2d.getID().equals(rs.getString(9))){
                                                                pointEnd.setPiece(p2d, CB2D);
                                                            }
                                                        }
                                                    }   
                                                }
                                                rs.close();
                                                CB2D.step--;
                                                this.repaint();
                                            }catch (SQLException ex){
                                                System.err.println("SQL Exception: " + ex.getMessage());
                                            }
                                            Object sendObject = (Object)("#o#undo_yes");
                                            sendObject(sendObject);
                                            break;
                                        }
                                        case JOptionPane.NO_OPTION:{
                                            Object sendObject = (Object)("#o#undo_no");
                                            sendObject(sendObject);
                                            break;
                                        }
                                    }
                                }
                                else if(string.equals("undo_yes") || string.equals("undo_no")){
                                    jd.dispose();
                                    if(string.equals("undo_yes")){
                                        try{
                                            ResultSet rs;
                                            rs = db.selectTable("select * from CHESSMANUAL where username = '"+p2.getName()+"' and step = "+CB2D.step);
                                            if(rs.next()){
                                                Point2D pointEnd = CB2D.point[rs.getInt(7)][11-rs.getInt(8)];
                                                Point2D pointStart = CB2D.point[rs.getInt(5)][11-rs.getInt(6)];
                                                Piece2D piece = pointEnd.getPiece();
                                                pointEnd.removePiece(piece, CB2D);
                                                pointStart.setPiece(piece, CB2D);
                                                if(rs.getString(9) != null && rs.getString(10) != null && rs.getString(11) != null){
                                                    for(Piece2D p2d:CB2D.listPiece){
                                                        if(p2d.getID().equals(rs.getString(9))){
                                                            pointEnd.setPiece(p2d, CB2D);
                                                        }
                                                    }
                                                }   
                                            }
                                            rs.close();
                                            db.deleteTable("delete from CHESSMANUAL where username = '"+p2.getName()+"' and step = "+CB2D.step);
                                            CB2D.step--;
                                            this.repaint();
                                            rs = db.selectTable("select * from CHESSMANUAL where username = '"+p1.getName()+"' and step = "+CB2D.step);
                                            if(rs.next()){
                                                Point2D pointEnd = CB2D.point[rs.getInt(7)][rs.getInt(8)];
                                                Point2D pointStart = CB2D.point[rs.getInt(5)][rs.getInt(6)];
                                                Piece2D piece = pointEnd.getPiece();
                                                pointEnd.removePiece(piece, CB2D);
                                                pointStart.setPiece(piece, CB2D);
                                                if(rs.getString(9) != null && rs.getString(10) != null && rs.getString(11) != null){
                                                    for(Piece2D p2d:CB2D.listPiece){
                                                        if(p2d.getID().equals(rs.getString(9))){
                                                            pointEnd.setPiece(p2d, CB2D);
                                                        }
                                                    }
                                                }   
                                            }
                                            rs.close();
                                            db.deleteTable("delete from CHESSMANUAL where username = '"+p1.getName()+"' and step = "+CB2D.step);
                                            CB2D.step--;
                                            this.repaint();
                                        }catch (SQLException ex){
                                            System.err.println("SQL Exception: " + ex.getMessage());
                                        }
                                        JOptionPane.showMessageDialog(this,"Opponent accepts the undo request. Draw!", "Draw",JOptionPane.NO_OPTION);
                                        
                                    }
                                    else if (string.equals("undo_no")){
                                        JOptionPane.showMessageDialog(this,"Opponent refuces the undo request.", "Draw",JOptionPane.NO_OPTION);
                                    }
                                }
                                else if(string.equals("flicker")){
                                    String S = null;
                                    String number = "";
                                    int x = 0;
                                    int y = 0;
                                    recieveObject = ois.readObject();
                                    if(recieveObject != null)
                                        S = (String)recieveObject;
                                     for(int i = 0;i<S.length();i++){
                                        if(S.charAt(i) == ','){
                                            x = Integer.parseInt(number);
                                            number = "";
                                        }
                                        else if(S.charAt(i) == '.'){
                                            y = Integer.parseInt(number);
                                            number = "";
                                        }
                                        else
                                            number = number+S.charAt(i);     
                                    }
                                    Piece2D selectPiece = CB2D.point[x][11-y].getPiece();
                                    CB2D.flickerCheck = true;
                                    if(CB2D.k!=null){
                                        if(CB2D.k.isAlive())
                                            CB2D.k.stop();
                                    }
                                    CB2D.k = CB2D.new flicker(selectPiece,CB2D);
                                    CB2D.k.start();
                                }
                                else if (string.equals("win")){
                                    if(CB2D.k!=null){
                                        if(CB2D.k.isAlive())
                                            CB2D.k.stop();
                                    }
                                    JOptionPane.showMessageDialog(this,"The victory belongs to"+p2.getName(), "Victory", JOptionPane.NO_OPTION);
                                    db.updateTable("update userdata set lose = "+(p1.getLoseNum()+1)+",score = "+(p1.getScore()-100)+", matchesnum = "
                                            +(p1.getmatchSum()+1)+",winrate = "+(float)(p1.getWinNum())/(p1.getmatchSum()+1)+"where username = '"+p1.getName()+"'");
                                    CB2D.turn = null;
                                    TR.stop();
                                }
                                break;
                            case "#m#"://Sign for System message
                                appendText(string);
                                break;
                        }
                        
                        recieveObject = null;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try{
                onlineMode = false;
                startRecieve = false;
                recieveObject = null;
                sendObject = null;
                oos.close();
                ois.close();
                socket.close();
                if(ssocket != null){
                    ssocket.close();
                    
                }
                oos = null;
                ois = null;
                socket = null;
                ssocket = null; 
            }
            catch (IOException e){
                e.printStackTrace();
            }
        } 
            
         
    }

    private class threadSocket extends Thread{
        private int i;//judge stauts
        public threadSocket(Socket socket, ServerSocket ssocket){
            i = 0;
        }
        public threadSocket(Socket socket){
            i = 1;
        }
        public void run(){
            if(i==0){
                try {
                    while(true){
                        socket = ssocket.accept();
                        if(socket.isConnected()){
                            button.setText("Enter");
                            message.setText("A player is connected.");
                            Turn1 = "red";
                            Turn2 = "black";
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            oos.writeObject(login.getPlayID());
                            ois = new ObjectInputStream(socket.getInputStream());
                            break;
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else if(i == 1){
                try {
                    if(socket != null){
                        if(socket.isConnected() && !socket.isClosed()){
                            button.setText("Enter");
                            message.setText("Connect successfully.");
                            Turn2 = "red";
                            Turn1 = "black";
                            oos = new ObjectOutputStream(socket.getOutputStream());
                            oos.writeObject(login.getPlayID());
                            ois = new ObjectInputStream(socket.getInputStream());
                        }
                        else{
                            button.setText("Ok");
                            message.setText("Connection failed.");
                        }
                    }
                } catch (IOException ex) {
                    button.setText("Ok");
                    message.setText("Connection failed.");
                }
            }
        }
    }
    
    public void sendObject(Object o){
        try {
            if(o!=null)
                oos.writeObject(o);
            o = null;
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void test(){
        CH3D = null;
        panelChess.removeAll();
        panelChess.setVisible(false);
        panelTime.removeAll();
        panelButtonComponent.removeAll();
        panelMenu.setVisible(true);
        db.deleteTable("delete from chessmanual where username = '"+p1.getName()+"'");
        if(TR.getStauts())
            TR.stop();
        panelTime.remove(TR);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == singleGame){
            onlineMode = false;
            Turn1 = "red";
            Turn2 = "black";
            initChessPanel();
            if(Button2D.isSelected()){
                CB2D = new ChessBoard2D(TR,this);
                CB2D.RedBottomBlackTop();
                panelChess.add(CB2D);
                panelMenu.setVisible(false);
                ProgressBar p = new ProgressBar(CB2D,this);
            }
            else if(Button3D.isSelected()){
                CH3D = new ChineseChess(new GLCanvas(),TR,this);
                CH3D.init();
                panelChess.add(CH3D.ICPanel);
                panelMenu.setVisible(false);
                ProgressBar p = new ProgressBar(CH3D,this);
            }
            
            panelChess.add(panelButtonComponent);
            panelChess.add(panelTime);
            panelChess.add(panelText);
            bg = new ImageIcon("image/background.jpg");
            bglabel = new JLabel(bg);
            bglabel.setBounds(0,0, bg.getIconWidth(),bg.getIconHeight());
            panelChess.add(bglabel,new Integer(Integer.MIN_VALUE));
            //((JPanel)this.getContentPane()).setOpaque(false);
            ready.setVisible(true);
            panelChess.setVisible(true);
            
        }
        if(e.getSource() == onlineGame){
            onlineMode = true;
            initOnlinePane(); 
        }
        if(e.getSource() == Button2D){
            ImageIcon icon = new ImageIcon("image/2Dbutton_selected.png");
            Button2D.setSelected(true);
            Button2D.setIcon(icon);
            icon = new ImageIcon("image/3Dbutton_normal.png");
            Button3D.setSelected(false);
            Button3D.setIcon(icon);
        }
        if(e.getSource() == Button3D){
            ImageIcon icon = new ImageIcon("image/3Dbutton_selected.png");
            Button3D.setSelected(true);
            Button3D.setIcon(icon);
            icon = new ImageIcon("image/2Dbutton_normal.png");
            Button2D.setSelected(false);
            Button2D.setIcon(icon);

        }
        if(e.getSource() == ready){
            if(onlineMode){
                player1.handupForReady();
                if(!p1.getReadyStauts()){
                    p1.setReadyStauts(true);
                    sendObject = (Object)("#o#ready");
                }
                else{
                    p1.setReadyStauts(false);
                    sendObject = (Object)("#o#notready");
                }
                sendObject(sendObject);
                if(p1.getReadyStauts() && p2.getReadyStauts()){
                    ready.setVisible(false);
                    if(Turn1.equals("red")){
                        TR.setTime();
                    }
                    if(Turn1.equals("black")){
                        Thread waitingThread = new Thread(() -> {
                            jd = new JDialog(this,"",true); 
                            jd.add(new JLabel("Wait for host setting the time",JLabel.CENTER));
                            jd.setSize(200, 80);
                            jd.setLocationRelativeTo(this);
                            jd.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                            jd.setVisible(true);
                        });
                        waitingThread.start();
                    }
                    player1.handupForReady();
                    player2.handupForReady();
                    p1.setReadyStauts(false);
                    p2.setReadyStauts(false);
                    CB2D.turn = "red";
                }
            }
            else{
                this.setEnabled(false);
                ready.setVisible(false);
                if(Button2D.isSelected()){
                    CB2D.turn = "red";
                }
                if(Button3D.isSelected()){
                    CH3D.round = "red";
                }
                TR.setTime();  
            }
            
        }
        if(e.getSource() == buttonUndo){
            if(!onlineMode){
                if(Button2D.isSelected()){
                    if(CB2D.turn != null){
                        String s;
                        if(CB2D.turn.equals("red")){
                            s = "black";
                        }
                        else{
                            s = "red";
                        }
                        int option = JOptionPane.showConfirmDialog(this, "Do you agree the "+s+" to undo the step?", "Undo", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null);
                        switch (option) {
                            case JOptionPane.YES_NO_OPTION: {
                                try{
                                    ResultSet rs;
                                    rs = db.selectTable("select * from CHESSMANUAL where username = '"+p1.getName()+"' and step = "+CB2D.step);
                                    if(rs.next()){
                                        Point2D pointEnd = CB2D.point[rs.getInt(7)][rs.getInt(8)];
                                        Point2D pointStart = CB2D.point[rs.getInt(5)][rs.getInt(6)];
                                        Piece2D piece = pointEnd.getPiece();
                                        pointEnd.removePiece(piece, CB2D);
                                        pointStart.setPiece(piece, CB2D);
                                        if(rs.getString(9) != null && rs.getString(10) != null && rs.getString(11) != null){
                                            for(Piece2D p2d:CB2D.listPiece){
                                                if(p2d.getID().equals(rs.getString(9))){
                                                    pointEnd.setPiece(p2d, CB2D);
                                                }
                                            }
                                        }   
                                    }
                                    rs.close();
                                    db.deleteTable("delete from CHESSMANUAL where username = '"+p1.getName()+"' and step = "+CB2D.step);
                                    CB2D.step--;
                                    rs = db.selectTable("select * from CHESSMANUAL where username = '"+p1.getName()+"' and step = "+CB2D.step);
                                    if(rs.next()){
                                        Point2D pointEnd = CB2D.point[rs.getInt(7)][rs.getInt(8)];
                                        Point2D pointStart = CB2D.point[rs.getInt(5)][rs.getInt(6)];
                                        Piece2D piece = pointEnd.getPiece();
                                        pointEnd.removePiece(piece, CB2D);
                                        pointStart.setPiece(piece, CB2D);
                                        if(rs.getString(9) != null && rs.getString(10) != null && rs.getString(11) != null){
                                            for(Piece2D p2d:CB2D.listPiece){
                                                if(p2d.getID().equals(rs.getString(9))){
                                                    pointEnd.setPiece(p2d, CB2D);
                                                }
                                            }
                                        }   
                                    }
                                    rs.close();
                                    db.deleteTable("delete from CHESSMANUAL where username = '"+p1.getName()+"' and step = "+CB2D.step);
                                    CB2D.step--;
                                    this.repaint();
                                }catch (SQLException ex){
                                    System.err.println("SQL Exception: " + ex.getMessage());
                                }
                                break;
                            }
                            case JOptionPane.NO_OPTION:{
                                JOptionPane.showMessageDialog(this,s+" refuces the undo request.", "Undo",JOptionPane.NO_OPTION);
                                break;
                            }
                        }
                    }
                }
                else if(Button3D.isSelected()){
                }     
            }
            else{
                if(CB2D.turn!=null){
                    int option = JOptionPane.showConfirmDialog(this, "Are you sure to send undo request ?", "Undo", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                    switch (option) {
                        case JOptionPane.YES_NO_OPTION: {
                            Thread waitingThread = new Thread(() -> {
                                jd = new JDialog(this,"",true); 
                                jd.add(new JLabel("Waiting",JLabel.CENTER));
                                jd.setSize(200, 80);
                                jd.setLocationRelativeTo(this);
                                jd.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                                jd.setVisible(true);
                            });
                            waitingThread.start();
                            Object sendObject = (Object)("#o#undo");
                            sendObject(sendObject);
                            break;
                        }
                        case JOptionPane.NO_OPTION:{
                        }
                    }
                }
            }
        }
        if(e.getSource() == buttonMenu){
            int option = -1;
            if(Button2D.isSelected()){
                if(CB2D.turn != null)
                    option = JOptionPane.showConfirmDialog(this, "Do you want to return to the menu?\n The system would judge you lose.", "Giveup", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                else
                    option = JOptionPane.showConfirmDialog(this, "Do you want to return to the menu?", "Giveup", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
            }
            else if (Button3D.isSelected()){
                if(CH3D.round != null)
                    option = JOptionPane.showConfirmDialog(this, "Do you want to return to the menu?\n The system would judge you lose.", "Giveup", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                else
                    option = JOptionPane.showConfirmDialog(this, "Do you want to return to the menu?", "Giveup", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
            }
            switch (option) {
                case JOptionPane.YES_NO_OPTION: {
                    
                    if(onlineMode){
                        sendObject("#o#over");
                        if(CB2D.turn!=null){
                            db.updateTable("update userdata set lose = "+(p1.getLoseNum()+1)+",score = "+(p1.getScore()-100)+", matchesnum = "
                                    +(p1.getmatchSum()+1)+",winrate = "+(float)(p1.getWinNum())/(p1.getmatchSum()+1)+"where username = '"+p1.getName()+"'");
                            db.updateTable("update userdata set win = "+(p2.getWinNum()+1)+",score = "+(p2.getScore()+100)+", matchesnum = "
                                    +(p2.getmatchSum()+1)+",winrate = "+(float)(p2.getWinNum()+1)/(p2.getmatchSum()+1)+"where username = '"+p2.getName()+"'");
                        }
                    }
                    CB2D = null;
                    CH3D = null;
                    panelChess.removeAll();
                    panelChess.setVisible(false);
                    panelTime.removeAll();
                    panelButtonComponent.removeAll();
                    panelMenu.setVisible(true);
                    db.deleteTable("delete from chessmanual where username = '"+p1.getName()+"'");
                    if(TR.getStauts())
                        TR.stop();
                    panelTime.remove(TR);
                    break;
                }
                case JOptionPane.NO_OPTION:{
                }
            }
            p1 = login.getPlayerInfo(login.getPlayID()); 
        }
        if(e.getSource() == buttonGiveup){
            if(!onlineMode){
                if(Button2D.isSelected()){
                    if(CB2D.turn != null){
                        if(CB2D.turn.equals("red")){
                            CB2D.selectPiece = null;
                            if(CB2D.flickerCheck){
                                CB2D.flickerCheck = false;
                                CB2D.k.stop();
                            }
                            CB2D.clickNum = -1;
                            CB2D.turn = null;
                            TR.stop();
                            JOptionPane.showMessageDialog(this,"The red gives up. The victory belongs to Black!", "Victory",JOptionPane.NO_OPTION);
                        }
                        else{
                            CB2D.selectPiece = null;
                            if(CB2D.flickerCheck){
                                CB2D.flickerCheck = false;
                                CB2D.k.stop();
                            }
                            CB2D.clickNum = -1;
                            CB2D.turn = null;
                            TR.stop();
                            JOptionPane.showMessageDialog(this,"The black gives up. The victory belongs to Red!", "Victory", JOptionPane.NO_OPTION);
                        }
                    }
                }
                else if (Button3D.isSelected()){
                    if(CH3D.round != null){
                        if(CH3D.round.equals("red")){
                            CH3D.round = null;
                            CH3D.doPicking = false;
                            CH3D.checkstep = false;
                            CH3D.copyNode.setEnabled(true);
                            TR.stop();
                            JOptionPane.showMessageDialog(this,"The red gives up. The victory belongs to Black!", "Victory",JOptionPane.NO_OPTION);
                        }
                        else{
                            CH3D.round = null;
                            CH3D.doPicking = false;
                            CH3D.checkstep = false;
                            CH3D.copyNode.setEnabled(true);
                            TR.stop();
                            JOptionPane.showMessageDialog(this,"The black gives up. The victory belongs to Red!", "Victory", JOptionPane.NO_OPTION);
                        }
                    }
                }
            }
            else{
                if(CB2D.turn != null){
                    int option = JOptionPane.showConfirmDialog(this, "Are you sure to send giveup request ?", "Giveup", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                    switch (option) {
                        case JOptionPane.YES_NO_OPTION: {
                            CB2D.selectPiece = null;
                            if(CB2D.flickerCheck){
                                CB2D.flickerCheck = false;
                                CB2D.k.stop();
                            }
                            CB2D.clickNum = -1;
                            CB2D.turn = null;
                            TR.stop();
                            Object sendObject = (Object)("#o#giveup");
                            sendObject(sendObject);
                            db.updateTable("update userdata set lose = "+(p1.getLoseNum()+1)+",score = "+(p1.getScore()-100)+", matchesnum = "
                                                +(p1.getmatchSum()+1)+",winrate = "+(float)(p1.getWinNum())/(p1.getmatchSum()+1)+"where username = '"+p1.getName()+"'");
                            JOptionPane.showMessageDialog(this,p1.getName()+" lose, "+p2.getName()+" wins the game.", "Draw",JOptionPane.NO_OPTION);
                            break;
                        }
                        case JOptionPane.NO_OPTION:{
                        }
                    }    
                }
            }
        }
        if(e.getSource() == buttonDraw){
            if(!onlineMode){
                if(Button2D.isSelected()){
                    if(CB2D.turn != null){
                        String s;
                        if(CB2D.turn.equals("red")){
                            s = "black";
                        }
                        else{
                            s = "red";
                        }
                        int option = JOptionPane.showConfirmDialog(this, "Are you willing to accept draw request from the "+CB2D.turn+"?", "Draw", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null);
                        switch (option) {
                            case JOptionPane.YES_NO_OPTION: {
                                CB2D.selectPiece = null;
                                if(CB2D.flickerCheck){
                                    CB2D.flickerCheck = false;
                                    CB2D.k.stop();
                                }
                                CB2D.clickNum = -1;
                                CB2D.turn = null;
                                TR.stop();
                                JOptionPane.showMessageDialog(this,s+" accepts the draw request. Draw!", "Draw",JOptionPane.NO_OPTION);
                                break;
                            }
                            case JOptionPane.NO_OPTION:{
                                JOptionPane.showMessageDialog(this,s+" refuces the draw request.", "Draw",JOptionPane.NO_OPTION);
                                break;
                            }
                        }
                    }
                }
                else if (Button3D.isSelected()){
                    if(CH3D.round != null){
                        String s;
                        if(CH3D.round.equals("red")){
                            s = "black";
                        }
                        else{
                            s = "red";
                        }
                        int option = JOptionPane.showConfirmDialog(this, "Are you willing to accept draw request from the "+CH3D.round+"?", "Draw", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE, null);
                        switch (option) {
                            case JOptionPane.YES_NO_OPTION: {
                                CH3D.round = null;
                                CH3D.doPicking = false;
                                CH3D.checkstep = false;
                                //CH3D.copyNode.setEnabled(true);
                                JOptionPane.showMessageDialog(this,s+" accepts the draw request. Draw!", "Draw",JOptionPane.NO_OPTION);
                                TR.stop();
                                break;
                            }
                            case JOptionPane.NO_OPTION:{
                                JOptionPane.showMessageDialog(this,s+" refuces the draw request.", "Draw",JOptionPane.NO_OPTION);
                                break;
                            }
                        }
                    }
                }  
            }
            else{
                if(CB2D.turn!=null){
                    int option = JOptionPane.showConfirmDialog(this, "Are you sure to send draw request ?", "Draw", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
                    switch (option) {
                        case JOptionPane.YES_NO_OPTION: {
                            Thread waitingThread = new Thread(() -> {
                                jd = new JDialog(this,"",true); 
                                jd.add(new JLabel("Waiting",JLabel.CENTER));
                                jd.setSize(200, 80);
                                jd.setLocationRelativeTo(this);
                                jd.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
                                jd.setVisible(true);
                            });
                            waitingThread.start();
                            Object sendObject = (Object)("#o#draw");
                            sendObject(sendObject);
                            break;
                        }
                        case JOptionPane.NO_OPTION:{
                        }
                    }  
                }
            }
        }
    }
    
    public static void main(String[] args){
        new MainFrame();
    }

    
   
}
