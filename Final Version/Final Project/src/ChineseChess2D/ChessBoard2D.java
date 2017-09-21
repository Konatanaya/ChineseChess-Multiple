/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChineseChess2D;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author konatan
 */
public class ChessBoard2D extends JPanel implements MouseListener{
    public JFrame message = new JFrame();
    public int length = 70;
    public Point2D point[][];
    public int Xnumber = 9;
    public int Ynumber = 10;
    public Image RedpieceImg, BlackpieceImg, boardImg;
    public Piece2D rj1,rm1,rx1,rs1,rs,rs2,rx2,rm2,rj2,rp1,rp2,rb1,rb2,rb3,rb4,rb5;
    public Piece2D bj1,bm1,bx1,bs1,bj,bs2,bx2,bm2,bj2,bp1,bp2,bz1,bz2,bz3,bz4,bz5;
    public List<Piece2D> listPiece = new ArrayList<Piece2D>();
    public Piece2D selectPiece;
    public flicker k = null;
    public boolean flickerCheck;
    public int startX,startY,endX,endY;
    public int clickNum;
    public String turn = null;
    public Rules rule;
    public Piece2D enteredPiece;
    private Database db;
    public int step = 0;
    private TimeRecorder TR;
    private MainFrame mf;
    private boolean isReady = false;
    public Music stepmusic;
    private JDialog jd;
    public static String reply;
    public ChessBoard2D(TimeRecorder TR, MainFrame mf){
        this.mf = mf;
        this.TR = TR;
        TR.get2Dchess(this);
        rule = new Rules(this);
        db = new Database("jdbc:derby://localhost:1527/ChineseChess");
        this.setBounds(250, 10, 800, 800);
        this.setLayout(null);
        RedpieceImg = Toolkit.getDefaultToolkit().getImage("redpiece.png");
        BlackpieceImg = Toolkit.getDefaultToolkit().getImage("blackpiece.png");
        boardImg = Toolkit.getDefaultToolkit().getImage("board.png");
        point = new Point2D[Xnumber+1][Ynumber+1];
        for(int i=1;i<=Xnumber;i++){
            for(int j=1;j<=Ynumber;j++){
                point[i][j] = new Point2D(i*length+50,j*length+15);
            }
        }
        this.addMouseListener(this);
        initPieces();
        initOK();
        isReady = true;
        if(mf.onlineMode)
            for(Piece2D p:listPiece){
                if(!p.getColorType().equals(mf.player1.turn))
                    p.setEnabled(false);
            }
    }
    
    public boolean initOK(){
        return isReady;
    }
    public void initPieces(){
//        turn = "red";
        selectPiece = null;
        clickNum = 0;
        flickerCheck = false;
        this.removeAll();
        this.repaint();
        for(int i=1;i<=9;i++){
            for(int j=1;j<=10;j++){
                point[i][j].setHvaePiece(false);
            }
        }
        rj1 = new Piece2D("车","rj1","red",Color.red,length-4,RedpieceImg,this);
        rm1 = new Piece2D("马","rm1","red",Color.red,length-4,RedpieceImg,this);
        rx1 = new Piece2D("相","rx1","red",Color.red,length-4,RedpieceImg,this);
        rs1 = new Piece2D("仕","rs1","red",Color.red,length-4,RedpieceImg,this);
        rs = new Piece2D("帅","rs","red",Color.red,length-4,RedpieceImg,this);
        rs2 = new Piece2D("仕","rs2","red",Color.red,length-4,RedpieceImg,this);
        rx2 = new Piece2D("相","rx2","red",Color.red,length-4,RedpieceImg,this);
        rm2 = new Piece2D("马","rm2","red",Color.red,length-4,RedpieceImg,this);
        rj2 = new Piece2D("车","rj2","red",Color.red,length-4,RedpieceImg,this);
        rp1 = new Piece2D("炮","rp1","red",Color.red,length-4,RedpieceImg,this);
        rp2 = new Piece2D("炮","rp2","red",Color.red,length-4,RedpieceImg,this);
        rb1 = new Piece2D("兵","rb1","red",Color.red,length-4,RedpieceImg,this);
        rb2 = new Piece2D("兵","rb2","red",Color.red,length-4,RedpieceImg,this);
        rb3 = new Piece2D("兵","rb3","red",Color.red,length-4,RedpieceImg,this);
        rb4 = new Piece2D("兵","rb4","red",Color.red,length-4,RedpieceImg,this);
        rb5 = new Piece2D("兵","rb5","red",Color.red,length-4,RedpieceImg,this);
        listPiece.add(rj1); listPiece.add(rm1); listPiece.add(rx1);
        listPiece.add(rs1); listPiece.add(rs);  listPiece.add(rs2);
        listPiece.add(rx2); listPiece.add(rm2); listPiece.add(rj2);
        listPiece.add(rp1); listPiece.add(rp2); listPiece.add(rb1);
        listPiece.add(rb2); listPiece.add(rb3); listPiece.add(rb4);
        listPiece.add(rb5);

        bj1 = new Piece2D("車","bj1","black",Color.black,length-4,BlackpieceImg,this);
        bm1 = new Piece2D("馬","bm1","black",Color.black,length-4,BlackpieceImg,this);
        bx1 = new Piece2D("象","bx1","black",Color.black,length-4,BlackpieceImg,this);
        bs1 = new Piece2D("士","bs1","black",Color.black,length-4,BlackpieceImg,this);
        bj = new Piece2D("将","bj","black",Color.black,length-4,BlackpieceImg,this);
        bs2 = new Piece2D("士","bs2","black",Color.black,length-4,BlackpieceImg,this);
        bx2 = new Piece2D("象","bx2","black",Color.black,length-4,BlackpieceImg,this);
        bm2 = new Piece2D("馬","bm2","black",Color.black,length-4,BlackpieceImg,this);
        bj2 = new Piece2D("車","bj2","black",Color.black,length-4,BlackpieceImg,this);
        bp1 = new Piece2D("炮","bp1","black",Color.black,length-4,BlackpieceImg,this);
        bp2 = new Piece2D("炮","bp2","black",Color.black,length-4,BlackpieceImg,this);
        bz1 = new Piece2D("卒","bz1","black",Color.black,length-4,BlackpieceImg,this);
        bz2 = new Piece2D("卒","bz2","black",Color.black,length-4,BlackpieceImg,this);
        bz3 = new Piece2D("卒","bz3","black",Color.black,length-4,BlackpieceImg,this);
        bz4 = new Piece2D("卒","bz4","black",Color.black,length-4,BlackpieceImg,this);
        bz5 = new Piece2D("卒","bz5","black",Color.black,length-4,BlackpieceImg,this);
        listPiece.add(bj1); listPiece.add(bm1); listPiece.add(bx1);
        listPiece.add(bs1); listPiece.add(bj);  listPiece.add(bs2);
        listPiece.add(bx2); listPiece.add(bm2); listPiece.add(bj2);
        listPiece.add(bp1); listPiece.add(bp2); listPiece.add(bz1);
        listPiece.add(bz2); listPiece.add(bz3); listPiece.add(bz4);
        listPiece.add(bz5);
        
    }
    
    public void RedBottomBlackTop(){
        point[1][10].setPiece(rj1, this);
        point[2][10].setPiece(rm1, this);
        point[3][10].setPiece(rx1, this);
        point[4][10].setPiece(rs1, this);
        point[5][10].setPiece(rs, this);
        point[6][10].setPiece(rs2, this);
        point[7][10].setPiece(rx2, this);
        point[8][10].setPiece(rm2, this);
        point[9][10].setPiece(rj2, this); 
        point[2][8].setPiece(rp1, this);
        point[8][8].setPiece(rp2, this);
        point[1][7].setPiece(rb1, this);
        point[3][7].setPiece(rb2, this);
        point[5][7].setPiece(rb3, this);
        point[7][7].setPiece(rb4, this);
        point[9][7].setPiece(rb5, this);
         
        point[1][1].setPiece(bj1, this);
        point[2][1].setPiece(bm1, this);
        point[3][1].setPiece(bx1, this);
        point[4][1].setPiece(bs1, this);
        point[5][1].setPiece(bj, this);
        point[6][1].setPiece(bs2, this);;
        point[7][1].setPiece(bx2, this);
        point[8][1].setPiece(bm2, this);
        point[9][1].setPiece(bj2, this);
        point[2][3].setPiece(bp1, this);
        point[8][3].setPiece(bp2, this);
        point[1][4].setPiece(bz1, this);
        point[3][4].setPiece(bz2, this);
        point[5][4].setPiece(bz3, this);
        point[7][4].setPiece(bz4, this);
        point[9][4].setPiece(bz5, this);
    }
    public void RedTopBlackBottom(){
        point[1][1].setPiece(rj1, this);
        point[2][1].setPiece(rm1, this);
        point[3][1].setPiece(rx1, this);
        point[4][1].setPiece(rs1, this);
        point[5][1].setPiece(rs, this);
        point[6][1].setPiece(rs2, this);
        point[7][1].setPiece(rx2, this);
        point[8][1].setPiece(rm2, this);
        point[9][1].setPiece(rj2, this); 
        point[2][3].setPiece(rp1, this);
        point[8][3].setPiece(rp2, this);
        point[1][4].setPiece(rb1, this);
        point[3][4].setPiece(rb2, this);
        point[5][4].setPiece(rb3, this);
        point[7][4].setPiece(rb4, this);
        point[9][4].setPiece(rb5, this);
         
        point[1][10].setPiece(bj1, this);
        point[2][10].setPiece(bm1, this);
        point[3][10].setPiece(bx1, this);
        point[4][10].setPiece(bs1, this);
        point[5][10].setPiece(bj, this);
        point[6][10].setPiece(bs2, this);;
        point[7][10].setPiece(bx2, this);
        point[8][10].setPiece(bm2, this);
        point[9][10].setPiece(bj2, this);
        point[2][8].setPiece(bp1, this);
        point[8][8].setPiece(bp2, this);
        point[1][7].setPiece(bz1, this);
        point[3][7].setPiece(bz2, this);
        point[5][7].setPiece(bz3, this);
        point[7][7].setPiece(bz4, this);
        point[9][7].setPiece(bz5, this);
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g.drawImage(boardImg, 0, 0, this);
        g2d.setStroke(new BasicStroke(3.0f));
        for(int i=1;i<=Ynumber;i++){
            if(i == 1 || i == Ynumber){
                
                g2d.drawLine(point[1][i].getX(), point[1][i].getY(), point[Xnumber][i].getX(), point[Xnumber][i].getY());
            }
            else{
                
                g2d.drawLine(point[1][i].getX(), point[1][i].getY(), point[Xnumber][i].getX(), point[Xnumber][i].getY());
            }
        }
        for(int i=1;i<=Xnumber;i++){
            g2d.drawLine(point[i][1].getX(), point[i][1].getY(), point[i][Ynumber-5].getX(), point[i][Ynumber-5].getY());
            g2d.drawLine(point[i][6].getX(), point[i][6].getY(), point[i][Ynumber].getX(), point[i][Ynumber].getY());
        }
        g2d.drawLine(point[1][5].getX(), point[1][5].getY(), point[1][6].getX(), point[1][6].getY());
        g2d.drawLine(point[Xnumber][5].getX(), point[Xnumber][5].getY(), point[Xnumber][6].getX(), point[Xnumber][6].getY());
        g2d.drawLine(point[4][1].getX(), point[4][1].getY(), point[6][3].getX(), point[6][3].getY());
        g2d.drawLine(point[6][1].getX(), point[6][1].getY(), point[4][3].getX(), point[4][3].getY());
        g2d.drawLine(point[4][Ynumber].getX(), point[4][Ynumber].getY(), point[6][Ynumber-2].getX(), point[6][Ynumber-2].getY());
        g2d.drawLine(point[4][Ynumber-2].getX(), point[4][Ynumber-2].getY(), point[6][Ynumber].getX(), point[6][Ynumber].getY());
        for(int i=1; i<=Xnumber;i++){
            g2d.drawString(""+i, i*length+50, length/2+15);
            g2d.drawString(""+(char)(i+64), length/2+30, i*length+15);
        }
        g2d.drawString(""+(char)(74), length/2+30, 10*length+15);

        Graphics2D g2d2 = (Graphics2D)g2d.create();
        g2d2.setFont(new Font("楷体", Font.BOLD, 60));
        g2d2.translate(185, 430);
        g2d2.rotate(-0.5*Math.PI);
        g2d2.drawString("楚", -5, 55);
        g2d2.setFont(new Font("楷体", Font.BOLD, 60));
        g2d2.translate(0, 75);
        g2d2.drawString("河", -5, 55);
        
        g2d2.setFont(new Font("楷体", Font.BOLD, 60));
        g2d2.translate(0, 350);
        g2d2.rotate(Math.PI);
        g2d2.drawString("漢", -55, 55);
        g2d2.setFont(new Font("楷体", Font.BOLD, 60));
        g2d2.translate(0, 75);
        g2d2.drawString("界", -55, 55);
        
    }
    
    public boolean checkWin(String name){
        if(name.equals("帅")){            
            return true;
        }
        else if(name.equals("将")){
            return true;
        }
        else{
            return false;
        }
    }
    
    public char transCoding(int i){
        char c;
        c = (char)(i+64);
        return c;
    } 
    public void mousePressed(MouseEvent e) {  
        
    }
    public void mouseClicked(MouseEvent e) {
        boolean check = true;
        Object o = new Object();
        int x,y;
        clickNum++;
        if(!mf.onlineMode){
            if(selectPiece == null && e.getSource() == this || turn == null){
                clickNum = 0;
            }
            if(clickNum == 2){
                flickerCheck = false;
                k.stop();
                if(e.getSource() instanceof Piece2D){
                    if(turn.equals(((Piece2D)e.getSource()).getColorType())){//更换棋子
                        clickNum = 1;
                    }
                    else{
                        for(int i=1;i<=Xnumber;i++){
                            for(int j=1;j<=Ynumber;j++){
                                x = point[i][j].getX();
                                y = point[i][j].getY();
                                if(((Piece2D)e.getSource()).getBounds().contains(x,y)){
                                    endX = i;
                                    endY = j;                           
                                    break;
                                }
                            }
                        }
                        if(rule.PieceRule(selectPiece)){//吃子
                            stepmusic = new Music("eat.wav",0);
                            Piece2D eat = (Piece2D)e.getSource();
                            point[endX][endY].removePiece(eat, this);
                            point[endX][endY].setPiece(selectPiece, this);
                            point[startX][startY].setHvaePiece(false);
                            step++;
                            db.insertTable("insert into CHESSMANUAL values("+step+",'"+selectPiece.getID()+"','"
                                    +selectPiece.getName()+"','"+turn+"',"+startX+","+startY+","
                                    +endX+","+endY+",'"+eat.getID()+"','"+eat.getName()+"','"+eat.getColorType()+"','"+mf.p1.getName()+"')");
                            mf.appendText("System: "+selectPiece.getColorType()+" "+selectPiece.getName()+" moves from "
                                    +transCoding(startY)+startX+" to "+transCoding(endY)+endX+".\n");
                            mf.appendText("System: "+eat.getColorType()+" "+eat.getName()+" is eaten.\n");
                            if(checkWin(eat.getName())){
                                if(eat.getName().equals("帅")){            
                                    JOptionPane.showMessageDialog(mf,"The victory belongs to Black!", "Victory", JOptionPane.NO_OPTION);
                                }
                                else if(eat.getName().equals("将")){
                                    JOptionPane.showMessageDialog(mf,"The victory belongs to Red!", "Victory", JOptionPane.NO_OPTION);
                                }
                                turn = null;
                                TR.stop();
                            }
                            else{
                                if(turn.equals("red")){
                                    turn = "black";
                                    TR.Suspend("Red");
                                }
                                else{
                                    turn = "red";
                                    TR.Suspend("Black");
                                }
                                clickNum = 0;
                            }
                        }
                        else
                            clickNum = 0;
                    }     
                }
                else if(e.getSource() == this){//移动
                    stepmusic = new Music("go.wav",0);
                    for(int i=1;i<=Xnumber;i++){
                        for(int j=1;j<=Ynumber;j++){
                            x = point[i][j].getX();
                            y = point[i][j].getY();
                            if(e.getX()-x<35 && e.getX()-x>-35 && e.getY()-y<35 && e.getY()-y>-35){
                                endX = i;
                                endY = j;                       
                                break;
                            }
                        }
                    }
                    if(rule.PieceRule(selectPiece)){
                        point[startX][startY].setHvaePiece(false);
                        point[endX][endY].setPiece(selectPiece, this);
                        step++;
                        db.insertTable("insert into CHESSMANUAL values("+step+",'"+selectPiece.getID()+"','"
                                +selectPiece.getName()+"','"+turn+"',"+startX+","+startY+","
                                +endX+","+endY+","+null+","+null+","+null+",'"+mf.p1.getName()+"')");
                        mf.appendText("System: "+selectPiece.getColorType()+" "+selectPiece.getName()+" moves from "
                                    +transCoding(startY)+startX+" to "+transCoding(endY)+endX+".\n");
                        if(turn.equals("red")){
                            turn = "black";                      
                            TR.Suspend("Red");
                        }
                        else{
                            turn = "red";
                            TR.Suspend("Black");
                        }
                        
                        clickNum = 0;

                    }
                    else{//test
                        clickNum = 0;
                        selectPiece = null;
                    }
                }
                this.setCursor(Cursor.getDefaultCursor());
            }
            if(clickNum == 1 && e.getSource() instanceof Piece2D && turn != null){//选中棋子
                selectPiece = (Piece2D)e.getSource();
                stepmusic = new Music("select.wav",0);
                if(turn.equals(selectPiece.getColorType())){
                    for(int i=1;i<=Xnumber;i++){
                        for(int j=1;j<=Ynumber;j++){
                            x = point[i][j].getX();
                            y = point[i][j].getY();
                            if(selectPiece.getBounds().contains(x,y)){
                                startX = i;
                                startY = j;                         
                                break;
                            }
                        }
                    }
                    flickerCheck = true;
                    k = new flicker(selectPiece, this);
                    k.start();
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                }
                else
                    clickNum = 0;
            }
        }
        else{
            if(mf.player1.turn.equals(turn)){//onlinemode
                if((selectPiece == null && e.getSource() == this) || turn == null ){
                    clickNum = 0;
                }
                if(clickNum == 2){
                    flickerCheck = false;
                    if(k.isAlive())
                        k.stop();
                    if(e.getSource() instanceof Piece2D){
                        if(turn.equals(((Piece2D)e.getSource()).getColorType())){//更换棋子
                            clickNum = 1;
                        }
                        else{
                            for(int i=1;i<=Xnumber;i++){
                                for(int j=1;j<=Ynumber;j++){
                                    x = point[i][j].getX();
                                    y = point[i][j].getY();
                                    if(((Piece2D)e.getSource()).getBounds().contains(x,y)){
                                        endX = i;
                                        endY = j;                           
                                        break;
                                    }
                                }
                            }
                            if(rule.PieceRule(selectPiece, mf.player1.turn)){//吃子
                                stepmusic = new Music("eat.wav",0);
                                Piece2D eat = (Piece2D)e.getSource();
                                point[endX][endY].removePiece(eat, this);
                                point[endX][endY].setPiece(selectPiece, this);
                                point[startX][startY].setHvaePiece(false);
                                step++;
                                db.insertTable("insert into CHESSMANUAL values("+step+",'"+selectPiece.getID()+"','"
                                        +selectPiece.getName()+"','"+turn+"',"+startX+","+startY+","
                                        +endX+","+endY+",'"+eat.getID()+"','"+eat.getName()+"','"+eat.getColorType()+"','"+mf.p1.getName()+"')");
                                String s1 = "System: "+selectPiece.getColorType()+" "+selectPiece.getName()+" moves from " +transCoding(startY)+startX+" to "+transCoding(endY)+endX+".\n";
                                mf.appendText(s1);
                                
                                o = (Object)("#m#"+s1);
                                mf.sendObject(o);
                                s1 = "System: "+eat.getColorType()+" "+eat.getName()+" is eaten.\n";
                                mf.appendText(s1);
                                o = (Object)("#m#"+s1);
                                mf.sendObject(o);
                                if(checkWin(eat.getName())){
                                    db.updateTable("update userdata set win = "+(mf.p1.getWinNum()+1)+",score = "+(mf.p1.getScore()+100)+", matchesnum = "
                                            +(mf.p1.getmatchSum()+1)+",winrate = "+(float)(mf.p1.getWinNum()+1)/(mf.p1.getmatchSum()+1)+"where username = '"+mf.p1.getName()+"'");
                                    o = (Object)("#o#changeTurn");
                                    mf.sendObject(o);
                                    o = (Object)("#o#win");
                                    mf.sendObject(o);
                                    if(eat.getName().equals("帅")){            
                                        JOptionPane.showMessageDialog(mf,"The victory belongs to Black!", "Victory", JOptionPane.NO_OPTION);
                                    }
                                    else if(eat.getName().equals("将")){
                                        JOptionPane.showMessageDialog(mf,"The victory belongs to Red!", "Victory", JOptionPane.NO_OPTION);
                                    }
                                    turn = null;
                                    TR.stop();
                                }
                                else{
                                    if(turn.equals("red")){
                                        turn = "black";
                                        TR.Suspend("Red");
                                    }
                                    else{
                                        turn = "red";
                                        TR.Suspend("Black");
                                    }
                                    o = new Object();
                                    o = (Object)("#o#changeTurn");
                                    mf.sendObject(o);
                                    clickNum = 0;
                                }
                            }
                            else
                                clickNum = 0;
                        }     
                    }
                    else if(e.getSource() == this){//移动
                        stepmusic = new Music("go.wav",0);
                        for(int i=1;i<=Xnumber;i++){
                            for(int j=1;j<=Ynumber;j++){
                                x = point[i][j].getX();
                                y = point[i][j].getY();
                                if(e.getX()-x<35 && e.getX()-x>-35 && e.getY()-y<35 && e.getY()-y>-35){
                                    endX = i;
                                    endY = j;                       
                                    break;
                                }
                            }
                        }
                        if(rule.PieceRule(selectPiece, mf.player1.turn)){
                            point[startX][startY].setHvaePiece(false);
                            point[endX][endY].setPiece(selectPiece, this);
                            step++;
                            db.insertTable("insert into CHESSMANUAL values("+step+",'"+selectPiece.getID()+"','"
                                    +selectPiece.getName()+"','"+turn+"',"+startX+","+startY+","
                                    +endX+","+endY+","+null+","+null+","+null+",'"+mf.p1.getName()+"')");
                            String s1 = "System: "+selectPiece.getColorType()+" "+selectPiece.getName()+" moves from "
                                        +transCoding(startY)+startX+" to "+transCoding(endY)+endX+".\n";
                            o = (Object)("#m#"+s1);
                            mf.sendObject(o);
                            mf.appendText(s1);
                            
                            if(turn.equals("red")){
                                turn = "black";                      
                                TR.Suspend("Red");
                            }
                            else{
                                turn = "red";
                                TR.Suspend("Black");
                            }
                            o = (Object)("#o#changeTurn");
                            mf.sendObject(o);
                            clickNum = 0;
                        }
                        else{//test
                            clickNum = 0;
                            selectPiece = null;
                        }
                    }
                    this.setCursor(Cursor.getDefaultCursor());
                    
                }
                if(clickNum == 1 && e.getSource() instanceof Piece2D && turn != null){//选中棋子
                    selectPiece = (Piece2D)e.getSource();
                    if(selectPiece.getColorType().equals(mf.player1.turn)){
                        stepmusic = new Music("select.wav",0);
                        if(turn.equals(selectPiece.getColorType())){
                            for(int i=1;i<=Xnumber;i++){
                                for(int j=1;j<=Ynumber;j++){
                                    x = point[i][j].getX();
                                    y = point[i][j].getY();
                                    if(selectPiece.getBounds().contains(x,y)){
                                        startX = i;
                                        startY = j;                         
                                        break;
                                    }
                                }
                            }
                            flickerCheck = true;
                            k = new flicker(selectPiece, this);
                            k.start();
                            o = (Object)("#o#flicker");
                            mf.sendObject(o);
                            o = (Object)(startX+","+startY+".");
                            mf.sendObject(o);
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                        }
                        else
                            clickNum = 0;
                    }
                }
            }
        }
    }
    public void mouseReleased(MouseEvent e) {

    }
    public void mouseEntered(MouseEvent e) {
        int x,y;
        if(e.getSource() instanceof Piece2D){
            enteredPiece = (Piece2D)e.getSource();
            if(clickNum == 0 && enteredPiece.getColorType().equals(turn)){
                enteredPiece.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }
            else if(clickNum == 1)
                enteredPiece.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }      
    }  
  
    public void mouseExited(MouseEvent e) {
        if(e.getSource() == enteredPiece)
            enteredPiece.setCursor(Cursor.getDefaultCursor());
    }  
    
    public class flicker extends Thread{
        private Piece2D p;
        private ChessBoard2D board;
        public flicker(Piece2D piece, ChessBoard2D board){
            this.p = piece;
            this.board = board;
        }
        public void run(){
            try{
                while(true){
                    if(flickerCheck){
                        p.setVisible(false);
                        Thread.sleep(350);
                        p.setVisible(true);
                        Thread.sleep(350);
                    }
                    else{
                        p.setVisible(true);
                        break;
                    }
                }
            } catch(Exception e){
                e.getStackTrace();
            }finally{
                p.setVisible(true);
            }
        }
    }
    
    
    
}
