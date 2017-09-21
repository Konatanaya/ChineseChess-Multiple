/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChineseChess2D;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

/**
 *
 * @author konatan
 */
public class LoginFrame extends JFrame implements ActionListener{
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JLabel RegisterUsernameLabel;
    private JLabel RegisterPasswordLabel;
    private JLabel ConfirmPasswordLabel;
    private JLabel usernameCheck;
    private JLabel passwordCheck;
    private JLabel confirmCheck;
    private JLabel iconLabel;
    private JLabel imageLabel;
    private JLabel imageCheck;
    private JComboBox combobox;
    private JTextField username;
    private JTextField RegisterUsername;
    private JPasswordField RegisterPassword;
    private JPasswordField ConfirmPassword;
    private JPasswordField password;
    private JButton login;
    private JButton exit;
    private JButton register;
    private JButton ok;
    private JPanel Login;
    private JFrame Register;
    private MainFrame mf;
    private Database db;
    private ImageIcon ico;
    private boolean checkname = false,checkpass = false,checkconfirm = false,checkimg = false;
    private int PlayID = 0;
    private ImageIcon bg;
    private JLabel bglabel;
    public LoginFrame(MainFrame mf){
        this.mf = mf;
        db = new Database("jdbc:derby://localhost:1527/ChineseChess");
        init();
        this.add(Login);
        this.setLayout(null);
        this.setSize(400, 200);
        this.setBackground(Color.yellow);
        this.setLocationRelativeTo(mf);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Login");
        this.setResizable(false);
        bg = new ImageIcon("image/background.jpg");
        bglabel = new JLabel(bg);
        bglabel.setBounds(0,0, bg.getIconWidth(),bg.getIconHeight());
        this.getLayeredPane().add(bglabel,new Integer(Integer.MIN_VALUE));
        ((JPanel)this.getContentPane()).setOpaque(false);
        //this.setUndecorated(true);
        this.setVisible(true);     
    }
    
    private void init(){
        Login = new JPanel();
        //Login.setBackground(Color.black);
        Login.setBounds(0, 0, 400, 200);
        Login.setVisible(true);
        Login.setLayout(null);
        Login.setOpaque(false);
        
        usernameLabel = new JLabel("Username",JLabel.CENTER);
        usernameLabel.setBounds(45, 20, 100, 30);
        usernameLabel.setForeground(Color.white);
        passwordLabel = new JLabel("Password",JLabel.CENTER);
        passwordLabel.setBounds(45, 70, 100, 30);
        passwordLabel.setForeground(Color.white);
        username = new JTextField();
        username.setBounds(155, 20, 200, 30);
        password = new JPasswordField();
        password.setBounds(155, 70, 200, 30);
        password.setEchoChar('*');
        
        ImageIcon icon = new ImageIcon("image/Login.png");
        login = new JButton();
        login.setBounds(25, 120, 100, 30);
        login.setIcon(icon);
        login.setOpaque(false);  
        login.setContentAreaFilled(false);  
        login.setMargin(new Insets(0, 0, 0, 0));  
        login.setFocusPainted(false);  
        login.setBorderPainted(false);  
        login.setBorder(null);  
        login.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        login.addActionListener(this);
        icon = new ImageIcon("image/Register.png");
        register = new JButton();
        register.setBounds(150, 120, 100, 30);
        register.setIcon(icon);
        register.setOpaque(false);  
        register.setContentAreaFilled(false);  
        register.setMargin(new Insets(0, 0, 0, 0));  
        register.setFocusPainted(false);  
        register.setBorderPainted(false);  
        register.setBorder(null);  
        register.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        register.addActionListener(this);
        exit = new JButton();
        exit.setBounds(275, 120, 100, 30);
        icon = new ImageIcon("image/Exit.png");
        exit.setIcon(icon);
        exit.setOpaque(false);  
        exit.setContentAreaFilled(false);  
        exit.setMargin(new Insets(0, 0, 0, 0));  
        exit.setFocusPainted(false);  
        exit.setBorderPainted(false);  
        exit.setBorder(null);  
        exit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        exit.addActionListener(this);
        
        Login.add(usernameLabel);
        Login.add(username);
        Login.add(passwordLabel);
        Login.add(password);
        Login.add(login);
        Login.add(register);
        Login.add(exit);
    }
    private void RegisterFrame(){
        JPanel RegisterPanel = new JPanel();
        Register = new JFrame();
        ImageIcon bg1 = new ImageIcon("image/background.jpg");
        JLabel bglabel1 = new JLabel(bg1);
        bglabel1.setBounds(0,0, bg1.getIconWidth(),bg1.getIconHeight());
        Register.getLayeredPane().add(bglabel1,new Integer(Integer.MIN_VALUE));
        ((JPanel)Register.getContentPane()).setOpaque(false);
        Register.setSize(660, 460);
        Register.setLayout(null);
        Register.setLocationRelativeTo(Login);
        Register.setAlwaysOnTop(true);
        //Register.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Register.setTitle("Login");
        Register.setResizable(false);
        Register.setVisible(true);
        
        RegisterPanel.setBounds(0, 0, Register.getWidth(), Register.getHeight());
        RegisterPanel.setBackground(Color.white);
        RegisterPanel.setLayout(null);
        RegisterPanel.setOpaque(false);
        RegisterPanel.requestFocus();
        RegisterUsernameLabel = new JLabel("Username",JLabel.CENTER);
        //RegisterUsernameLabel.setFont(new Font("Arial", Font.BOLD, 10));
        RegisterUsernameLabel.setBounds(25, 60 , 100, 30);
        RegisterUsernameLabel.setForeground(Color.white);
        RegisterUsername = new JTextField();
        RegisterUsername.setBounds(130, 60, 200, 30);
        RegisterUsername.addFocusListener(new focusListener());
        usernameCheck = new JLabel("<html><body>The length should be 6 to 20 characters<br><br></body></html>");
        usernameCheck.setBounds(345, 60, 300, 40);
        usernameCheck.setForeground(Color.white);
        
        RegisterPasswordLabel = new JLabel("Password",JLabel.CENTER);
        RegisterPasswordLabel.setBounds(25, 110 , 100, 30);
        RegisterPasswordLabel.setForeground(Color.white);
        RegisterPassword = new JPasswordField();
        RegisterPassword.setEchoChar('*');
        RegisterPassword.setBounds(130, 110, 200, 30);
        RegisterPassword.addFocusListener(new focusListener());
        passwordCheck = new JLabel("<html><body>The length should be 8 to 20 characters.<br><br></body></html>");
        passwordCheck.setBounds(345, 110, 300, 40);
        passwordCheck.setForeground(Color.white);
        
        ConfirmPasswordLabel = new JLabel("Confirm",JLabel.CENTER);
        ConfirmPasswordLabel.setBounds(25, 160, 100, 30);
        ConfirmPasswordLabel.setForeground(Color.white);
        ConfirmPassword = new JPasswordField();
        ConfirmPassword.setEchoChar('*');
        ConfirmPassword.setBounds(130, 160, 200, 30);
        ConfirmPassword.addFocusListener(new focusListener());
        confirmCheck = new JLabel("<html><body>Please input the password again.<br><br></body></html>");
        confirmCheck.setBounds(345, 160, 300, 40);
        confirmCheck.setForeground(Color.white);
        
        imageLabel = new JLabel("Profile Image",JLabel.CENTER);
        imageLabel.setBounds(25, 210, 100, 30);
        imageLabel.setForeground(Color.white);
        iconLabel = new JLabel();
        iconLabel.setBounds(130, 210, 100, 100);
        iconLabel.setBorder(BorderFactory.createLineBorder(Color.white));
        String[] str = {"pikachu","coolboy","cuteboy","cutegirl"};
        combobox = new JComboBox(str);
        combobox.setBounds(250, 210, 80, 20);
        combobox.setSelectedIndex(-1);
        combobox.addActionListener((ActionEvent e) -> {
            String s = (String)combobox.getSelectedItem();
            ico = new ImageIcon("image/"+s+".jpg");
            ico.setImage(ico.getImage().getScaledInstance(100, 100, 1));
            iconLabel.setIcon(ico);
            ImageIcon correct = new ImageIcon("correct.png");
            correct.setImage(correct.getImage().getScaledInstance(40, 40, 1));
            imageCheck.setText("");
            imageCheck.setIcon(correct);
            checkimg = true;
        });
        imageCheck = new JLabel("<html><body>Please select your profile image.<br><br></body></html>");
        imageCheck.setBounds(345, 210, 300, 40);
        imageCheck.setForeground(Color.white);
        
        ok = new JButton();
        ok.setBounds(130, 340, 148, 48);
        ImageIcon icon = new ImageIcon("image/Register2.png");
        ok.setIcon(icon);
        ok.setOpaque(false);  
        ok.setContentAreaFilled(false);  
        ok.setMargin(new Insets(0, 0, 0, 0));  
        ok.setFocusPainted(false);  
        ok.setBorderPainted(false);  
        ok.setBorder(null);  
        ok.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        ok.addActionListener((ActionEvent e) -> {
            int count=1;
            if(checkname && checkpass && checkconfirm && checkimg){
                try{
                    ResultSet rs;
                    rs = db.selectTable("select * from USERDATA");
                    while(rs.next()){
                        count++;
                    }
                    rs.close();
                    db.insertTable("insert into USERDATA values("+count+",'"+RegisterUsername.getText()+"','"
                            +RegisterPassword.getText()+"','"+(String)combobox.getSelectedItem()+"',0,'LV1',0,0,0,0,0.0,false)");
                    JOptionPane.showMessageDialog(Register,"Register successfully!", "Register", JOptionPane.NO_OPTION);
                    Register.dispose();
                }catch(SQLException ex){
                    System.err.println("SQL Exception: " + ex.getMessage());
                }
            }
            else{
                String message = "Please check information again according the prompt.";
                if(!checkname){
                    if(!usernameCheck.getForeground().equals(Color.RED)){
                        usernameCheck.setForeground(Color.red);
                    }
                }
                if(!checkpass){
                    if(!passwordCheck.getForeground().equals(Color.RED)){
                        passwordCheck.setForeground(Color.red);
                    }
                }
                if(!checkconfirm){
                    if(!confirmCheck.getForeground().equals(Color.RED)){
                        confirmCheck.setForeground(Color.red);
                    }
                }
                if(!checkimg){
                    if(!imageCheck.getForeground().equals(Color.RED)){
                        imageCheck.setForeground(Color.red);
                    }
                }
                JOptionPane.showMessageDialog(Register, message, "Register", JOptionPane.NO_OPTION);
            }
        });

        
        Register.add(RegisterPanel);
        //Register.pack();
        RegisterPanel.add(RegisterUsernameLabel);
        RegisterPanel.add(RegisterUsername);
        RegisterPanel.add(usernameCheck);
        RegisterPanel.add(RegisterPasswordLabel);
        RegisterPanel.add(RegisterPassword);
        RegisterPanel.add(passwordCheck);
        RegisterPanel.add(ConfirmPasswordLabel);
        RegisterPanel.add(ConfirmPassword);
        RegisterPanel.add(confirmCheck);
        RegisterPanel.add(imageLabel);
        RegisterPanel.add(iconLabel);
        RegisterPanel.add(combobox);
        RegisterPanel.add(imageCheck);
        RegisterPanel.add(ok);
        
        
    }
    public int getPlayID(){
        return PlayID;
    }
    public Player getPlayerInfo (int num){
        Player p = null;
        try{  
            ResultSet rs;
            rs = db.selectTable("select * from USERDATA where IDNUM = "+ num);
            if(rs.next()){
                p = new Player(rs.getInt(1), rs.getString(2), rs.getString(4), rs.getInt(5), 
                        rs.getString(6), rs.getInt(7), rs.getInt(8), rs.getInt(9), 
                        rs.getInt(10), rs.getDouble(11));
            }
            rs.close();
        }catch(SQLException ex){  
            System.err.println("SQL Exception: " + ex.getMessage());
        }
        return p;
    }
    private class focusListener implements FocusListener{
        ImageIcon correct = new ImageIcon("correct.png");
        ImageIcon incorrect = new ImageIcon("incorrect.png");
        public focusListener(){
            correct.setImage(correct.getImage().getScaledInstance(40, 40, 1));
            incorrect.setImage(incorrect.getImage().getScaledInstance(40, 40, 1));
        }
        public void focusGained(FocusEvent e){  

        }
        public void focusLost(FocusEvent e) {
            if(e.getSource() == RegisterUsername){
                if(!RegisterUsername.getText().equals("")){
                    if(RegisterUsername.getText().length() >= 6 && RegisterUsername.getText().length() <=20)
                        try{  
                            ResultSet rs;
                            rs = db.selectTable("select * from USERDATA where username = '"+RegisterUsername.getText()+"'");
                            if(rs.next()){
                                usernameCheck.setIcon(incorrect);
                                usernameCheck.setText("<html><body>The username already exist.<br><br></body></html>");
                                usernameCheck.setForeground(Color.red);
                                checkname = false;
                            }
                            else{
                                usernameCheck.setIcon(correct);
                                usernameCheck.setText("");
                                checkname = true;
                            }
                            rs.close();
                        }catch(SQLException ex){  
                            System.err.println("SQL Exception: " + ex.getMessage());
                        }
                    else{
                        usernameCheck.setIcon(incorrect);
                        usernameCheck.setText("<html><body><html><body>The length should be 6 to 20 characters<br><br></body></html><br><br></body></html>");
                        usernameCheck.setForeground(Color.red);
                        checkname = false;
                    }
                }
                else{
                    usernameCheck.setIcon(incorrect);
                    usernameCheck.setText("<html><body><html><body>The length should be 6 to 20 characters<br><br></body></html><br><br></body></html>");
                    usernameCheck.setForeground(Color.red);
                    checkname = false;
                }
            }
            
            if(e.getSource() == RegisterPassword){
                if(RegisterPassword.getPassword().length >= 8 && RegisterPassword.getPassword().length <=20){
                    passwordCheck.setIcon(correct);
                    passwordCheck.setText("");
                    checkpass = true;
                }
                else{
                    passwordCheck.setIcon(incorrect);
                    passwordCheck.setText("<html><body>The length should be 8 to 20 characters.<br><br></body></html>");
                    passwordCheck.setForeground(Color.red);
                    checkpass = false;
                }
            }
            
            if(e.getSource() == ConfirmPassword){
                if(RegisterPassword.getText().isEmpty()){
                    confirmCheck.setIcon(incorrect);
                    confirmCheck.setText("<html><body>Please input the correct password first.<br><br></body></html>");
                    confirmCheck.setForeground(Color.red);
                    checkconfirm = false;
                }
                else{
                    if(ConfirmPassword.getText().equals(RegisterPassword.getText())){
                        confirmCheck.setIcon(correct);
                        confirmCheck.setText("");
                        checkconfirm = true;
                    }
                    else{
                        confirmCheck.setIcon(incorrect);
                        confirmCheck.setText("<html><body>Both the input passwords must be consistent.<br><br></body></html>");
                        confirmCheck.setForeground(Color.red);
                        checkconfirm = false;
                    }
                }
            }
        }
    }
    
    public void actionPerformed(ActionEvent e){
        if(e.getSource() == login){
            try{
                ResultSet rs;
                rs = db.selectUserCheck("select * from USERDATA where username=? and password =? and islogin = false", username.getText(), password.getText());
                if(rs.next()){
                    db.updateTable("update USERDATA set islogin = true where username = '"+username.getText()+"'");
                    PlayID = rs.getInt(1);
                    mf.setEnabled(true);
                    this.dispose();
                }
                else
                    JOptionPane.showMessageDialog(this,"Username or password is incorrect or the account is logined", "Error", JOptionPane.NO_OPTION);
            }catch(SQLException ex){
                System.err.println("SQL Exception: " + ex.getMessage());
            }
        }
        if(e.getSource() == register){
            RegisterFrame();
            //this.setEnabled(false);
        }
        if(e.getSource() == exit){
            System.exit(0);
        }
        
    }
}
