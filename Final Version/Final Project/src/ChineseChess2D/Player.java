/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChineseChess2D;

/**
 *
 * @author konatan
 */
public class Player {
    private int ID;
    private String name;
    private String imgID;
    private int score;
    private String rank;
    private int matchSum;
    private int win;
    private int lose;
    private int draw;
    private double winrate;
    private boolean isready;
    public Player(int ID, String name, String imgID, int score, String rank, int matchSum, int win, int lose, int draw, double winrate){
        this.ID = ID;
        this.name = name;
        this.imgID = imgID;
        this.score = score;
        this.rank = rank;
        this.matchSum = matchSum;
        this.win = win;
        this.lose = lose;
        this.draw = draw;
        this.winrate = winrate * 100;
        this.isready = false;
    }
    public Player(String name){
        this.name = name;
    }
    
    public String getName(){
        return name;
    }
    public String getImgID(){
        return imgID;
    }
    public int getScore(){
        return score;
    }
    public String getRank(){
        return rank;
    }
    public int getmatchSum(){
        return matchSum;
    }
    public int getWinNum(){
        return win;
    }
    public int getLoseNum(){
        return lose;
    }
    public int getDrawNum(){
        return draw;
    }
    public double getWinrate(){
        return winrate;
    }
    public boolean getReadyStauts(){
        return this.isready;
    }
    public void setReadyStauts(boolean b){
        this.isready = b;
    }
}
