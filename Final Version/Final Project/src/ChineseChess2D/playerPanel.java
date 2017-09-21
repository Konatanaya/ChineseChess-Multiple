/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChineseChess2D;

import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author konatan
 */
public class playerPanel extends JPanel{
    private JPanel info;
    private JPanel stauts;
    private JLabel image;
    private JLabel name;
    private JLabel score;
    private JLabel level;
    private JLabel matchsum;
    private JLabel win;
    private JLabel lose;
    private JLabel draw;
    private JLabel winrate;
    private JLabel turnImg;
    private JLabel stautsImg;
    
    private ImageIcon Ico;
    private ImageIcon stautsIco;
    private ImageIcon turnIco;
    private Database db;
    private Player player;
    public String turn;
    
    public playerPanel(String turn){
        this.turn = turn;
        this.setSize(250,250);
        this.setVisible(true);
        this.setOpaque(false);
        //this.setBackground(Color.red);
        this.setLayout(null);
        init();
        initStandardInfo();
    }

    public playerPanel(Player p,String turn){
        this.player = p;
        this.turn = turn;
        this.setSize(250,250);
        this.setVisible(true);
        this.setOpaque(false);
        //this.setBackground(Color.red);
        this.setLayout(null);
        init();
        initInfo(player);
    }
    public void init(){
        info = new JPanel();
        info.setBounds(10, 145, 230, 95);
        info.setLayout(null);
        name = new JLabel("",JLabel.LEFT);
        name.setBounds(10,7,210,15);
        name.setFont(new Font("Arial",0,15));
        score = new JLabel("",JLabel.LEFT);
        score.setBounds(10, 29, 100, 15);
        score.setFont(new Font("Arial",0,15));
        //score.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
        level = new JLabel("",JLabel.LEFT);
        level.setBounds(120,29,100,15);
        level.setFont(new Font("Arial",0,15));
        matchsum = new JLabel("",JLabel.LEFT);
        matchsum.setBounds(10,72,100,15);
        matchsum.setFont(new Font("Arial",0,15));
        win = new JLabel("",JLabel.LEFT);
        win.setBounds(10,51,60,15);
        win.setFont(new Font("Arial",0,15));
        //win.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
        lose = new JLabel("",JLabel.LEFT);
        lose.setBounds(85,51,60,15);
        lose.setFont(new Font("Arial",0,15));
        draw = new JLabel("",JLabel.LEFT);
        draw.setBounds(150,51,60,15);
        draw.setFont(new Font("Arial",0,15));
        winrate = new JLabel("",JLabel.LEFT);
        winrate.setBounds(110,72,110,15);
        winrate.setFont(new Font("Arial",0,15));
        info.add(name);
        info.add(score);
        info.add(level);
        info.add(matchsum);
        info.add(win);
        info.add(lose);
        info.add(draw);
        info.add(winrate);
        
        
        stauts = new JPanel();
        stauts.setBounds(145, 10, 95, 125);
        stauts.setLayout(null);
        stauts.setOpaque(false);
        //stauts.setBackground(Color.gray);
        turnImg = new JLabel();
        turnImg.setBounds(5, 5, 85, 85);
        //turnImg.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
        stautsImg = new JLabel();
        //stautsImg.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
        stautsImg.setBounds(35, 95, 25, 25);
        stautsImg.setVisible(false);
        stauts.add(turnImg);
        stauts.add(stautsImg);
        
        image = new JLabel();
        image.setBounds(10, 10, 125, 125);
        image.setBackground(Color.yellow);
        image.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        this.add(info);
        this.add(image);
        this.add(stauts);
    }
    public void initInfo(Player p){
        Ico = new ImageIcon("image/"+p.getImgID()+".jpg");
        Ico.setImage(Ico.getImage().getScaledInstance(125, 125, 1));
        image.setIcon(Ico);
        
        if(turn.equals("red"))
            turnIco = new ImageIcon("image/red.gif");
        else
            turnIco = new ImageIcon("image/black.gif");
        turnIco.setImage(turnIco.getImage().getScaledInstance(85, 85, 1));
        turnImg.setIcon(turnIco);
        
        stautsIco = new ImageIcon("image/handup.png");
        stautsIco.setImage(stautsIco.getImage().getScaledInstance(25, 25, 1));
        stautsImg.setIcon(stautsIco);
        
        name.setText("Username: "+player.getName());
        score.setText("Score: "+player.getScore());
        level.setText("Rank: "+player.getRank());
        matchsum.setText("Matchsum: "+player.getmatchSum());
        win.setText("Win: "+player.getWinNum());
        draw.setText("Draw: "+player.getDrawNum());
        lose.setText("Lose: "+player.getLoseNum());
        winrate.setText("Winrate: "+player.getWinrate()+"%");
    }
    public void initStandardInfo(){
        Ico = new ImageIcon("image/standard.jpg");
        Ico.setImage(Ico.getImage().getScaledInstance(125, 125, 1));
        image.setIcon(Ico);
        
        if(turn.equals("red"))
            turnIco = new ImageIcon("image/red.gif");
        else
            turnIco = new ImageIcon("image/black.gif");
        turnIco.setImage(turnIco.getImage().getScaledInstance(85, 85, 1));
        turnImg.setIcon(turnIco);
        
        name.setText("Username: PublicGuy");

    }
    public void handupForReady(){
        if(stautsImg.isVisible())
            stautsImg.setVisible(false);
        else
            stautsImg.setVisible(true);
    }
    public void setTurn(String turn){
        this.turn = turn;
    }
    public void setBottomLocation(){
        this.setLocation(0, 550);
    }
    public void setTopLocation(){
        this.setLocation(0, 0);
    }
}
