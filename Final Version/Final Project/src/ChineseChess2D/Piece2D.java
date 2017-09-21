/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChineseChess2D;

import java.awt.*;
import javax.swing.JLabel;

/**
 *
 * @author konatan
 */
public class Piece2D extends JLabel{
    private String s, colorType;
    private Color color;
    private int length;
    private ChessBoard2D board;
    private Image img;
    private String ID;
    
    public Piece2D(String s, String ID, String colorType, Color color, int length, Image img, ChessBoard2D board){
        this.s = s;
        this.ID = ID;
        this.colorType = colorType;
        this.color = color;
        this.length = length;
        this.img = img;
        this.board = board;
        this.setSize(length, length);
        this.addMouseListener(board);
    }
    
    public String getName(){
        return s;
    }
    
    public String getID(){
        return ID;
    }
    
    public String getColorType(){
        return colorType;
    }
    
    public void paint(Graphics g) { 
        g.drawImage(img, 2, 2, length-2, length-2, null);  
        g.setColor(color);  
        g.setFont(new Font("楷体", Font.BOLD, 40));  
        g.drawString(s, 12, length - 18);  
    }
}
