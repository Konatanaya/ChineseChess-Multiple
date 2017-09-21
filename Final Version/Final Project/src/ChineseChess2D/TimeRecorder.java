/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChineseChess2D;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author konatan
 */
public class TimeRecorder extends JPanel{
    private JPanel RedTimePanel;
    private JLabel RedFullTimeLabel;
    private JLabel RedStepTimeLabel;
    private JLabel RedFullTime;
    private JLabel RedStepTime;
    private int RedHourF,RedMinuteF,RedSecondF;
    private int RedHourS,RedMinuteS,RedSecondS;
    private int BUHourS,BUMinuteS,BUSecondS;
    private JPanel BlackTimePanel;
    private JLabel BlackFullTimeLabel;
    private JLabel BlackStepTimeLabel;
    private JLabel BlackFullTime;
    private JLabel BlackStepTime;
    private int BlackHourF,BlackMinuteF,BlackSecondF;
    private int BlackHourS,BlackMinuteS,BlackSecondS;
    
    private boolean stauts = false;
    private timeThread red,black;
    public boolean over = false;
    private ChessBoard2D CB2D;
    private MainFrame mf;
    private boolean  checkOK = false;
    private Database db;
    public TimeRecorder(MainFrame mf){
        this.mf = mf;
        initRedPanel();
        initBlackPanel();
        this.setBounds(0, 250, 250, 300);
        this.setOpaque(false);
        //this.setBackground(Color.red);
        this.setLayout(null);
        this.add(BlackTimePanel);
        this.add(RedTimePanel);
        
    }

    private void initRedPanel(){
        RedTimePanel = new JPanel();
        RedTimePanel.setLayout(null);
        RedTimePanel.setBackground(Color.gray);
        RedHourF = RedMinuteF = RedSecondF = 99;
        RedHourS = RedMinuteS = RedSecondS = 99;
        
        RedFullTimeLabel = new JLabel("Full Time",JLabel.CENTER);
        RedFullTimeLabel.setBounds(10, 20, 100, 50);
        RedFullTimeLabel.setFont(new Font("Arial",0,20));
        RedFullTimeLabel.setForeground(Color.white);
        RedFullTimeLabel.setBorder(BorderFactory.createLineBorder(Color.white));
        
        RedFullTime = new JLabel(RedHourF+" : "+RedMinuteF+" : "+RedSecondF,JLabel.CENTER);
        RedFullTime.setBounds(110, 20, 130, 50);
        RedFullTime.setFont(new Font("Arial",0,20));
        RedFullTime.setForeground(Color.white);
        RedFullTime.setBorder(BorderFactory.createLineBorder(Color.white));
        
        RedStepTimeLabel = new JLabel("Step Time",JLabel.CENTER);
        RedStepTimeLabel.setBounds(10, 70, 100, 50);
        RedStepTimeLabel.setFont(new Font("Arial",0,20));
        RedStepTimeLabel.setForeground(Color.white);
        RedStepTimeLabel.setBorder(BorderFactory.createLineBorder(Color.white));

        RedStepTime = new JLabel(RedHourS+" : "+RedMinuteS+" : "+RedSecondS,JLabel.CENTER);
        RedStepTime.setBounds(110, 70, 130, 50);
        RedStepTime.setFont(new Font("Arial",0,20));
        RedStepTime.setForeground(Color.white);
        RedStepTime.setBorder(BorderFactory.createLineBorder(Color.white));
        
        RedTimePanel.add(RedFullTimeLabel);
        RedTimePanel.add(RedFullTime);
        RedTimePanel.add(RedStepTimeLabel);
        RedTimePanel.add(RedStepTime);
    }
    private void initBlackPanel(){ 
        BlackTimePanel = new JPanel();
        BlackTimePanel.setLayout(null);
        BlackTimePanel.setBackground(Color.gray);
        BlackHourF = BlackMinuteF = BlackSecondF = 99;
        BlackHourS = BlackMinuteS = BlackSecondS = 99;
        
        BlackFullTimeLabel = new JLabel("Full Time",JLabel.CENTER);
        BlackFullTimeLabel.setBounds(10, 20, 100, 50);
        BlackFullTimeLabel.setFont(new Font("Arial",0,20));
        BlackFullTimeLabel.setForeground(Color.white);
        BlackFullTimeLabel.setBorder(BorderFactory.createLineBorder(Color.white));
        
        BlackFullTime = new JLabel(BlackHourF+" : "+BlackMinuteF+" : "+BlackSecondF,JLabel.CENTER);
        BlackFullTime.setBounds(110, 20, 130, 50);
        BlackFullTime.setFont(new Font("Arial",0,20));
        BlackFullTime.setForeground(Color.white);
        BlackFullTime.setBorder(BorderFactory.createLineBorder(Color.white));
        
        BlackStepTimeLabel = new JLabel("Step Time",JLabel.CENTER);
        BlackStepTimeLabel.setBounds(10, 70, 100, 50);
        BlackStepTimeLabel.setFont(new Font("Arial",0,20));
        BlackStepTimeLabel.setForeground(Color.white);
        BlackStepTimeLabel.setBorder(BorderFactory.createLineBorder(Color.white));
       
        BlackStepTime = new JLabel(BlackHourS+" : "+BlackMinuteS+" : "+BlackSecondS,JLabel.CENTER);
        BlackStepTime.setBounds(110, 70, 130, 50);
        BlackStepTime.setFont(new Font("Arial",0,20));
        BlackStepTime.setForeground(Color.white);
        BlackStepTime.setBorder(BorderFactory.createLineBorder(Color.white));
        
        BlackTimePanel.add(BlackFullTimeLabel);
        BlackTimePanel.add(BlackFullTime);
        BlackTimePanel.add(BlackStepTimeLabel);
        BlackTimePanel.add(BlackStepTime);
    }
    public void setTime(){
        JFrame setTime = new JFrame();
        JLabel FullTime = new JLabel("Full Time",JLabel.CENTER);
        FullTime.setForeground(Color.WHITE);
        FullTime.setBounds(10,10,60,25);
        JLabel StepTime = new JLabel("Step Time",JLabel.CENTER);
        StepTime.setBounds(10,45,60,25);
        StepTime.setForeground(Color.WHITE);
        JLabel FH = new JLabel("(H)",JLabel.CENTER);
        FH.setForeground(Color.WHITE);
        FH.setBounds(99, 10, 25, 25);
        JLabel FM = new JLabel("(M)",JLabel.CENTER);
        FM.setForeground(Color.WHITE);
        FM.setBounds(152, 10, 25, 25);
        JLabel FS = new JLabel("(S)",JLabel.CENTER);
        FS.setForeground(Color.WHITE);
        FS.setBounds(205, 10, 25, 25);
        JLabel SH = new JLabel("(H)",JLabel.CENTER);
        SH.setForeground(Color.WHITE);
        SH.setBounds(99, 45, 25, 25);
        JLabel SM = new JLabel("(M)",JLabel.CENTER);
        SM.setForeground(Color.WHITE);
        SM.setBounds(152, 45, 25, 25);
        JLabel SS = new JLabel("(S)",JLabel.CENTER);
        SS.setForeground(Color.WHITE);
        SS.setBounds(205, 45, 25, 25);
        JTextField fh = new JTextField(2);
        fh.setBounds(74, 10, 25, 25);
        fh.setOpaque(false);
        fh.setForeground(Color.white);
        JTextField fm = new JTextField(2);
        fm.setBounds(127, 10, 25, 25);
        fm.setOpaque(false);
        fm.setForeground(Color.white);
        JTextField fs = new JTextField(2);
        fs.setBounds(180, 10, 25, 25);
        fs.setOpaque(false);
        fs.setForeground(Color.white);
        JTextField sh = new JTextField(2);
        sh.setBounds(74, 45, 25, 25);
        sh.setOpaque(false);
        sh.setForeground(Color.white);
        JTextField sm = new JTextField(2);
        sm.setBounds(127, 45, 25, 25);
        sm.setOpaque(false);
        sm.setForeground(Color.white);
        JTextField ss = new JTextField(2);
        ss.setBounds(180, 45, 25, 25);
        ss.setOpaque(false);
        ss.setForeground(Color.white);
        JButton ok = new JButton();
        ImageIcon icon = new ImageIcon("image/OK.png");
        ok.setBounds(2, 80, 236, 30);
        ok.setIcon(icon);
        ok.setOpaque(false);  
        ok.setContentAreaFilled(false);  
        ok.setMargin(new Insets(0, 0, 0, 0));  
        ok.setFocusPainted(false);  
        ok.setBorderPainted(false);  
        ok.setBorder(null);  
        ok.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
 
        ImageIcon bg = new ImageIcon("image/timeRecBack.jpg");
        JLabel bglabel = new JLabel(bg);
        bglabel.setBounds(0,0, bg.getIconWidth(),bg.getIconHeight());
        setTime.getLayeredPane().add(bglabel,new Integer(Integer.MIN_VALUE));
        ((JPanel)setTime.getContentPane()).setOpaque(false);
        setTime.setLayout(null);
        setTime.setSize(240, 120);
        setTime.setLocationRelativeTo(mf);
        setTime.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTime.setUndecorated(true);
        setTime.setVisible(true);
        mf.setEnabled(false);
        
        fh.setText("0");
        fm.setText("15");
        fs.setText("0");
        sh.setText("0");
        sm.setText("0");
        ss.setText("30");
        
        ok.addActionListener((ActionEvent e) -> {
            if((checkIntegerNum(fh.getText()) && checkIntegerNum(fm.getText()) && checkIntegerNum(fs.getText())
                    && checkIntegerNum(sh.getText()) && checkIntegerNum(sm.getText()) && checkIntegerNum(ss.getText()))
                    && !(fh.getText().equals("0") && fm.getText().equals("0") && fs.getText().equals("0")
                    && sh.getText().equals("0") && sm.getText().equals("0") && ss.getText().equals("0"))
                    && (!fh.getText().isEmpty() && !fm.getText().isEmpty() && !fs.getText().isEmpty()
                    && !sh.getText().isEmpty() && !sm.getText().isEmpty() && !ss.getText().isEmpty())
                    && (Integer.parseInt(sm.getText())<=60 && Integer.parseInt(ss.getText())<=60
                    && Integer.parseInt(fm.getText())<=60 && Integer.parseInt(fs.getText())<=60)){
                int hourF = Integer.parseInt(fh.getText()),
                        minuteF = Integer.parseInt(fm.getText()),
                        secondF = Integer.parseInt(fs.getText()),
                        hourS = Integer.parseInt(sh.getText()),
                        minuteS = Integer.parseInt(sm.getText()), 
                        secondS = Integer.parseInt(ss.getText());
                SetTime(hourF, minuteF, secondF, hourS, minuteS, secondS);
                Start();
                mf.setEnabled(true);
                checkOK = true;
                setTime.dispose();
                if(mf.onlineMode){
                    Object sendObject = (Object)("#o#settime");
                    mf.sendObject(sendObject);
                    sendObject = (Object)(hourF+","+minuteF+","+secondF+","+hourS+","+minuteS+","+secondS+",");
                    mf.sendObject(sendObject);
                }
            }
            else{
                JOptionPane.showMessageDialog(setTime,"Please input correct number!", "Error", JOptionPane.NO_OPTION);
                checkOK = false;
            }
        });
        setTime.add(FullTime);
        setTime.add(StepTime);
        setTime.add(fh);
        setTime.add(FH);
        setTime.add(fm);
        setTime.add(FM);
        setTime.add(fs);
        setTime.add(FS);
        
        setTime.add(sh);
        setTime.add(SH);
        setTime.add(sm);
        setTime.add(SM);
        setTime.add(ss);
        setTime.add(SS);
        setTime.add(ok);
    }
    
    public boolean checkIntegerNum(String s){
        for(int i = 0; i<s.length();i++){
            if(!Character.isDigit(s.charAt(i)))
                return false;
        }
        return true;
    }
    public void SetRedBlack(){
        RedTimePanel.setBounds(0, 0, 250, 140);
        BlackTimePanel.setBounds(0, 160, 250, 140);     
    }
    public void SetBlackRed(){
        BlackTimePanel.setBounds(0, 0, 250, 140);
        RedTimePanel.setBounds(0, 160, 250, 140);
    }
    public void Start(){
        String c = "";
        stauts = true;
        red = new timeThread("Red",RedHourF,RedMinuteF,RedSecondF,RedHourS,RedMinuteS,RedSecondS);
        black = new timeThread("Black",BlackHourF,BlackMinuteF,BlackSecondF,BlackHourS,BlackMinuteS,BlackSecondS);
        red.start(); 
        black.start();
        black.setSuspend(true);   
    }
    public void stop(){
        red.stop();
        black.stop();
        stauts = false;
    }
    public boolean getStauts(){
        return stauts;
    }
    public void initTime(){
        RedHourF = RedMinuteF = RedSecondF = 99;
        RedHourS = RedMinuteS = RedSecondS = 99;
        RedFullTime.setText(RedHourF+" : "+RedMinuteF+" : "+RedSecondF);
        RedStepTime.setText(RedHourS+" : "+RedMinuteS+" : "+RedSecondS);
        
        BlackHourF = BlackMinuteF = BlackSecondF = 99;
        BlackHourS = BlackMinuteS = BlackSecondS = 99;
        BlackFullTime.setText(BlackHourF+" : "+BlackMinuteF+" : "+BlackSecondF);
        BlackStepTime.setText(BlackHourS+" : "+BlackMinuteS+" : "+BlackSecondS);
    }
    public void Suspend(String type){
        if(type.equals("Red")){
            red.setSuspend(true);
            black.setSuspend(false);                           
        }
        else{
            black.setSuspend(true);
            red.setSuspend(false);
        }
    }
    
    public void SetTime(int hourF,int minuteF,int secondF,int hourS,int minuteS,int secondS){
        BlackHourF = RedHourF = hourF;
        BlackMinuteF = RedMinuteF = minuteF;
        BlackSecondF = RedSecondF = secondF;
        BUHourS =BlackHourS = RedHourS = hourS;
        BUMinuteS = BlackMinuteS = RedMinuteS = minuteS;
        BUSecondS = BlackSecondS = RedSecondS = secondS;
        RedFullTime.setText(checkNum(RedHourF)+" : "+checkNum(RedMinuteF)+" : "+checkNum(RedSecondF));
        RedStepTime.setText(checkNum(RedHourS)+" : "+checkNum(RedMinuteS)+" : "+checkNum(RedSecondS));
        BlackFullTime.setText(checkNum(BlackHourF)+" : "+checkNum(BlackMinuteF)+" : "+checkNum(BlackSecondF));
        BlackStepTime.setText(checkNum(BlackHourS)+" : "+checkNum(BlackMinuteS)+" : "+checkNum(BlackSecondS));
        //this.repaint();
    }
    public String checkNum(int i){
        String s;
        if(0<=i && i<=9)
            s = "0"+i+"";
        else
            s = i+"";
        return s;
    }
    public void get2Dchess(ChessBoard2D CB2D){
        this.CB2D = CB2D;
    }
    
    public class timeThread extends Thread{
        private int hourF,minuteF,secondF,hourS,minuteS,secondS;
        private final String c = "";
        private boolean runout = false, suspend = false;
        private String type;
        private boolean restart = false;
        public timeThread(String type,int hourF,int minuteF,int secondF,int hourS,int minuteS,int secondS){
            this.type = type;
            this.hourF = hourF;
            this.minuteF = minuteF;
            this.secondF = secondF;
            this.hourS = hourS;
            this.minuteS = minuteS;
            this.secondS = secondS;
        }
        
        public void setSuspend(boolean s){
            if(!s){
                synchronized (c) {  
                    c.notifyAll();
                    restart = true;
                }
                
            }
            this.hourS = BUHourS;
            this.minuteS = BUMinuteS;
            this.secondS = BUSecondS;
            RedStepTime.setText(checkNum(this.hourS)+" : "+checkNum(this.minuteS)+" : "+checkNum(this.secondS));
            BlackStepTime.setText(checkNum(this.hourS)+" : "+checkNum(this.minuteS)+" : "+checkNum(this.secondS));
            this.suspend = s;
            
            
        }
        
        public void run(){
            try {
                while(true){
                    Thread.sleep(1000);
                    synchronized (c) {
                        if (suspend) {
                            c.wait();
                        }                       
                    }
                    if(restart){
                        Thread.sleep(1000);
                        restart = false;
                    }
                    if(this.secondF == 0 && this.minuteF == 0 && this.hourF == 0)
                            runout = true;
                    if(this.secondS == 0 && this.minuteS == 0 && this.hourS == 0 && runout){
                        over = true;
                        String turn = CB2D.turn;
                        
                        if(mf.onlineMode){
                            if(mf.player1.turn.equals(turn)){
                                Object o = (Object)("#o#giveupTime");
                                mf.sendObject(o); 
                                CB2D.selectPiece = null;
                                if(CB2D.flickerCheck){
                                    CB2D.flickerCheck = false;
                                    CB2D.k.stop();
                                }
                                CB2D.clickNum = -1;
                                CB2D.turn = null;
                                mf.TR.stop();
                                JOptionPane.showMessageDialog(mf,"You give up. You lose!", "Victory",JOptionPane.NO_OPTION);
                            }
                        }
                        else
                            mf.giveUp(CB2D.turn);
                    }
                    if(!runout){
                        this.secondF--;
                        if(this.secondF == -1){
                            this.secondF = 59;
                            this.minuteF--;
                            if(this.minuteF == -1){
                                this.minuteF = 59;
                                this.hourF--;   
                            }
                        } 
                        if(type.equals("Red"))
                            RedFullTime.setText(checkNum(this.hourF)+" : "+checkNum(this.minuteF)+" : "+checkNum(this.secondF));
                        else
                            BlackFullTime.setText(checkNum(this.hourF)+" : "+checkNum(this.minuteF)+" : "+checkNum(this.secondF));
                        
                    }
                    else if(runout && !over){
                        this.secondS--;
                        if(this.secondS == -1){
                            this.secondS = 59;
                            this.minuteS--;
                            if(this.minuteS == -1){
                                this.minuteS = 59;
                                this.hourF--;   
                            }
                        }
                        if(type.equals("Red"))
                            RedStepTime.setText(checkNum(this.hourS)+" : "+checkNum(this.minuteS)+" : "+checkNum(this.secondS));
                        else
                            BlackStepTime.setText(checkNum(this.hourS)+" : "+checkNum(this.minuteS)+" : "+checkNum(this.secondS));
                        
                    }
                }
            }catch (InterruptedException ex){
                Logger.getLogger(TimeRecorder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
