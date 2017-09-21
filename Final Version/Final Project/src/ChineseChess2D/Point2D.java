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
public class Point2D {
    private int x;
    private int y;
    private boolean havePiece;
    private Piece2D piece;
    public Point2D(int X, int Y){
        this.x = X;
        this.y = Y;
        this.havePiece = false;
    }
    public void setX(int X){
        this.x = X;
    }
    public void setY(int Y){
        this.y = Y;
    }
    public void setHvaePiece(boolean check){
        havePiece = check;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public boolean havePiece(){
        return havePiece;
    }
    public Piece2D getPiece(){
        return piece;
    }
    public void setPiece(Piece2D piece, ChessBoard2D board){
        this.piece = piece;
        piece.setBounds(x - board.length / 2, y - board.length / 2, board.length, board.length);
        board.add(piece);
        this.havePiece = true;
        board.validate(); 
    }
    public void removePiece(Piece2D piece, ChessBoard2D board){
        this.piece = null;
        board.remove(piece);
        this.havePiece = false;
        board.validate(); 
    }
}
