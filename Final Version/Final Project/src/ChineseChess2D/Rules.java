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
public class Rules {
    public ChessBoard2D board = null; 
    public Rules(ChessBoard2D board){
        this.board = board;
        
    }
    public boolean PieceRule(Piece2D piece){
        int minY = Math.min(board.startY, board.endY);
        int maxY = Math.max(board.startY, board.endY);
        int minX = Math.min(board.startX, board.endX);
        int maxX = Math.max(board.startX, board.endX);
        int absX = Math.abs(board.endX-board.startX);
        int absY = Math.abs(board.endY-board.startY);
        int i = 0;
        switch(piece.getName()){
            case "車" :
                if(board.startX == board.endX){
                    for(i=minY+1;i<=maxY-1;i++){
                        if(board.point[board.startX][i].havePiece()){
                            return false;
                        }
                    }
                    if(i == maxY)
                        return true;
                }
                else if(board.startY == board.endY){
                    for(i=minX+1;i<=maxX-1;i++){
                        if(board.point[i][board.startY].havePiece()){
                            return false;
                        }
                    }
                    if(i == maxX)
                        return true;
                }
                else
                    return false;
            case "车" :
                if(board.startX == board.endX){
                    for(i=minY+1;i<=maxY-1;i++){
                        if(board.point[board.startX][i].havePiece()){
                            return false;
                        }
                    }
                    if(i == maxY)
                        return true;
                }
                else if(board.startY == board.endY){
                    for(i=minX+1;i<=maxX-1;i++){
                        if(board.point[i][board.startY].havePiece()){
                            return false;
                        }
                    }
                    if(i == maxX)
                        return true;
                }
                else
                    return false;
            case "马" :
                if(absX == 1 && absY == 2){
                    if(board.startY < board.endY){
                        if(board.point[board.startX][board.startY+1].havePiece())
                            return false;
                        else
                            return true;
                    }
                    if(board.startY > board.endY){
                        if(board.point[board.startX][board.startY-1].havePiece())
                            return false;
                        else
                            return true;
                    }
                }
                else if(absX == 2 && absY == 1){
                    if(board.startX < board.endX){
                        if(board.point[board.startX+1][board.startY].havePiece())
                            return false;
                        else
                            return true;
                    }
                    if(board.startX > board.endX){
                        if(board.point[board.startX-1][board.startY].havePiece())
                            return false;
                        else
                            return true;
                    }
                }
                else
                    return false;
            case "馬" :
                if(absX == 1 && absY == 2){
                    if(board.startY < board.endY){
                        if(board.point[board.startX][board.startY+1].havePiece())
                            return false;
                        else
                            return true;
                    }
                    if(board.startY > board.endY){
                        if(board.point[board.startX][board.startY-1].havePiece())
                            return false;
                        else
                            return true;
                    }
                }
                else if(absX == 2 && absY == 1){
                    if(board.startX < board.endX){
                        if(board.point[board.startX+1][board.startY].havePiece())
                            return false;
                        else
                            return true;
                    }
                    if(board.startX > board.endX){
                        if(board.point[board.startX-1][board.startY].havePiece())
                            return false;
                        else
                            return true;
                    }
                }
                else
                    return false;
            case "相":
                if(absX ==2 && absY == 2 && board.endY >= 6){
                    if(board.startX < board.endX){
                        if(board.startY < board.endY){
                            if(board.point[board.startX+1][board.startY+1].havePiece())
                                return false;
                            else
                                return true;
                        }
                        else{
                            if(board.point[board.startX+1][board.startY-1].havePiece())
                                return false;
                            else
                                return true;
                        }
                            
                    }
                    if(board.startX > board.endX){
                        if(board.startY < board.endY){
                            if(board.point[board.startX-1][board.startY+1].havePiece())
                                return false;
                            else
                                return true;
                        }
                        else{
                            if(board.point[board.startX-1][board.startY-1].havePiece())
                                return false;
                            else
                                return true;
                        }
                            
                    }
                }
                else
                    return false;
            case "象":
                if(absX ==2 && absY == 2 && board.endY <= 5){
                    if(board.startX < board.endX){
                        if(board.startY < board.endY){
                            if(board.point[board.startX+1][board.startY+1].havePiece())
                                return false;
                            else
                                return true;
                        }
                        else{
                            if(board.point[board.startX+1][board.startY-1].havePiece())
                                return false;
                            else
                                return true;
                        }
                            
                    }
                    if(board.startX > board.endX){
                        if(board.startY < board.endY){
                            if(board.point[board.startX-1][board.startY+1].havePiece())
                                return false;
                            else
                                return true;
                        }
                        else{
                            if(board.point[board.startX-1][board.startY-1].havePiece())
                                return false;
                            else
                                return true;
                        }
                            
                    }
                }
                else
                    return false;
            case "仕":
                if(absX == 1 && absY == 1){
                    if(board.endX>=4 && board.endX <= 6 && board.endY >= 8 && board.endY <=10){
                        return true;
                    }
                    else
                        return false;
                }
                else
                    return false;
            case "士":
                if(absX == 1 && absY == 1){
                    if(board.endX>=4 && board.endX <= 6 && board.endY >= 1 && board.endY <=3){
                        return true;
                    }
                    else
                        return false;
                }
                else
                        return false;
            case "兵":
                if(board.startX == board.endX){
                    if(board.startY-board.endY == 1)
                        return true;
                    else
                        return false;
                }
                else if(board.startY == board.endY && board.startY <= 5){
                    if(absX == 1)
                        return true;
                    else
                        return false;
                }
                else
                    return false;
            case "卒":
                if(board.startX == board.endX){
                    if(board.startY-board.endY == -1)
                        return true;
                    else
                        return false;
                }
                else if(board.startY == board.endY && board.startY >= 6){
                    if(absX == 1)
                        return true;
                    else
                        return false;
                } 
                else
                    return false;
            case "炮":
                int number = 0;
                if(board.startX == board.endX){
                    if(board.point[board.endX][board.endY].havePiece()){
                        for(i=minY+1;i<=maxY-1;i++){
                            if(board.point[board.startX][i].havePiece()){
                                number++;
                            }
                        }
                        if(number == 1)
                            return true;
                        else
                            return false;
                    }
                    else{
                         for(i=minY+1;i<=maxY-1;i++){
                            if(board.point[board.startX][i].havePiece()){
                                return false;
                            }
                        }
                        if(i == maxY)
                            return true;
                    }    
                }
                else if(board.startY == board.endY){
                    if(board.point[board.endX][board.endY].havePiece()){
                        for(i=minX+1;i<=maxX-1;i++){
                            if(board.point[i][board.startY].havePiece()){
                                number++;
                            }
                        }
                        if(number == 1)
                            return true;
                        else
                            return false;
                    }
                    else{
                        for(i=minX+1;i<=maxX-1;i++){
                            if(board.point[i][board.startY].havePiece()){
                                return false;
                            }
                        }
                        if(i == maxX)
                            return true;
                    }
                        
                }
                else
                    return false;
            case "帅":
                 if(board.startX == board.endX){
                    if(absY == 1 && board.endY <= 10 && board.endY >= 8)
                        return true;
                    else
                        return false;
                }
                else if(board.startY == board.endY){
                    if(absX == 1 && board.endX <= 6 && board.endX >= 4){
                        for(i=board.endY;i>=0;i--){
                            if(board.point[board.endX][i].havePiece()){
                                if(board.point[board.endX][i].getPiece().getName().equals("将"))
                                    return false;
                                else
                                    break;  
                            }
                        }
                        return true;
                    }
                    else
                        return false;
                }
                else
                    return false;
            case "将":
                if(board.startX == board.endX){
                    if(absY == 1 && board.endY <= 3 && board.endY >= 1)
                        return true;
                    else
                        return false;
                }
                else if(board.startY == board.endY){
                    if(absX == 1 && board.endX <= 6 && board.endX >= 4){
                        for(i=board.endY;i<=10;i++){
                            if(board.point[board.endX][i].havePiece()){
                                if(board.point[board.endX][i].getPiece().getName().equals("帅"))
                                    return false;
                                else
                                    break;
                            }    
                        }
                        return true;
                    }
                    else
                        return false;
                }
                else
                    return false;
                
            default:
                return false;
        }

    }
    public boolean PieceRule(Piece2D piece,String turn){
        int minY = Math.min(board.startY, board.endY);
        int maxY = Math.max(board.startY, board.endY);
        int minX = Math.min(board.startX, board.endX);
        int maxX = Math.max(board.startX, board.endX);
        int absX = Math.abs(board.endX-board.startX);
        int absY = Math.abs(board.endY-board.startY);
        int i = 0;
        if(turn.equals("red")){
            switch(piece.getName()){
                case "车" :
                    if(board.startX == board.endX){
                        for(i=minY+1;i<=maxY-1;i++){
                            if(board.point[board.startX][i].havePiece()){
                                return false;
                            }
                        }
                        if(i == maxY)
                            return true;
                    }
                    else if(board.startY == board.endY){
                        for(i=minX+1;i<=maxX-1;i++){
                            if(board.point[i][board.startY].havePiece()){
                                return false;
                            }
                        }
                        if(i == maxX)
                            return true;
                    }
                    else
                        return false;
                case "马" :
                    if(absX == 1 && absY == 2){
                        if(board.startY < board.endY){
                            if(board.point[board.startX][board.startY+1].havePiece())
                                return false;
                            else
                                return true;
                        }
                        if(board.startY > board.endY){
                            if(board.point[board.startX][board.startY-1].havePiece())
                                return false;
                            else
                                return true;
                        }
                    }
                    else if(absX == 2 && absY == 1){
                        if(board.startX < board.endX){
                            if(board.point[board.startX+1][board.startY].havePiece())
                                return false;
                            else
                                return true;
                        }
                        if(board.startX > board.endX){
                            if(board.point[board.startX-1][board.startY].havePiece())
                                return false;
                            else
                                return true;
                        }
                    }
                    else
                        return false;
                case "相":
                    if(absX ==2 && absY == 2 && board.endY >= 6){
                        if(board.startX < board.endX){
                            if(board.startY < board.endY){
                                if(board.point[board.startX+1][board.startY+1].havePiece())
                                    return false;
                                else
                                    return true;
                            }
                            else{
                                if(board.point[board.startX+1][board.startY-1].havePiece())
                                    return false;
                                else
                                    return true;
                            }

                        }
                        if(board.startX > board.endX){
                            if(board.startY < board.endY){
                                if(board.point[board.startX-1][board.startY+1].havePiece())
                                    return false;
                                else
                                    return true;
                            }
                            else{
                                if(board.point[board.startX-1][board.startY-1].havePiece())
                                    return false;
                                else
                                    return true;
                            }

                        }
                    }
                    else
                        return false;
                     
                case "仕":
                    if(absX == 1 && absY == 1){
                        if(board.endX>=4 && board.endX <= 6 && board.endY >= 8 && board.endY <=10){
                            return true;
                        }
                        else
                            return false;
                    }
                     else
                        return false;
                case "兵":
                    if(board.startX == board.endX){
                        if(board.startY-board.endY == 1)
                            return true;
                        else
                            return false;
                    }
                    else if(board.startY == board.endY && board.startY <= 5){
                        if(absX == 1)
                            return true;
                        else
                            return false;
                    }
                    else
                        return false;
                case "炮":
                    if(board.startX == board.endX){
                        int number = 0;
                        if(board.point[board.endX][board.endY].havePiece()){
                            for(i=minY+1;i<=maxY-1;i++){
                                if(board.point[board.startX][i].havePiece()){
                                    number++;
                                }
                            }
                            if(number == 1)
                                return true;
                            else
                                return false;
                        }
                        else{
                             for(i=minY+1;i<=maxY-1;i++){
                                if(board.point[board.startX][i].havePiece()){
                                    number++;
                                }
                            }
                            if(number == 1)
                                return true;
                            else
                                return false;
                        }    
                    }
                    else if(board.startY == board.endY){
                        if(board.point[board.endX][board.endY].havePiece()){
                            for(i=minX+1;i<=maxX-1;i++){
                                if(board.point[i][board.startY].havePiece()){
                                    return true;
                                }
                            }
                            if(i == maxX)
                                return false;
                        }
                        else{
                            for(i=minX+1;i<=maxX-1;i++){
                                if(board.point[i][board.startY].havePiece()){
                                    return false;
                                }
                            }
                            if(i == maxX)
                                return true;
                        }

                    }
                    else
                        return false;
                case "帅":
                     if(board.startX == board.endX){
                        if(absY == 1 && board.endY <= 10 && board.endY >= 8)
                            return true;
                        else
                            return false;
                    }
                    else if(board.startY == board.endY){
                        if(absX == 1 && board.endX <= 6 && board.endX >= 4){
                            for(i=board.endY;i>=0;i--){
                                if(board.point[board.endX][i].havePiece()){
                                    if(board.point[board.endX][i].getPiece().getName().equals("将"))
                                        return false;
                                    else
                                        break;  
                                }
                            }
                            return true;
                        }
                        else
                            return false;
                    }
                    else
                        return false;
                default:
                    return false;
            }

        }
        else{
            switch(piece.getName()){
                case "車" :
                    if(board.startX == board.endX){
                        for(i=minY+1;i<=maxY-1;i++){
                            if(board.point[board.startX][i].havePiece()){
                                return false;
                            }
                        }
                        if(i == maxY)
                            return true;
                    }
                    else if(board.startY == board.endY){
                        for(i=minX+1;i<=maxX-1;i++){
                            if(board.point[i][board.startY].havePiece()){
                                return false;
                            }
                        }
                        if(i == maxX)
                            return true;
                    }
                    else
                        return false;
                case "馬" :
                    if(absX == 1 && absY == 2){
                        if(board.startY < board.endY){
                            if(board.point[board.startX][board.startY+1].havePiece())
                                return false;
                            else
                                return true;
                        }
                        if(board.startY > board.endY){
                            if(board.point[board.startX][board.startY-1].havePiece())
                                return false;
                            else
                                return true;
                        }
                    }
                    else if(absX == 2 && absY == 1){
                        if(board.startX < board.endX){
                            if(board.point[board.startX+1][board.startY].havePiece())
                                return false;
                            else
                                return true;
                        }
                        if(board.startX > board.endX){
                            if(board.point[board.startX-1][board.startY].havePiece())
                                return false;
                            else
                                return true;
                        }
                    }
                    else
                        return false;
               
                case "象":
                    if(absX ==2 && absY == 2 && board.endY >= 6){
                        if(board.startX < board.endX){
                            if(board.startY < board.endY){
                                if(board.point[board.startX+1][board.startY+1].havePiece())
                                    return false;
                                else
                                    return true;
                            }
                            else{
                                if(board.point[board.startX+1][board.startY-1].havePiece())
                                    return false;
                                else
                                    return true;
                            }

                        }
                        if(board.startX > board.endX){
                            if(board.startY < board.endY){
                                if(board.point[board.startX-1][board.startY+1].havePiece())
                                    return false;
                                else
                                    return true;
                            }
                            else{
                                if(board.point[board.startX-1][board.startY-1].havePiece())
                                    return false;
                                else
                                    return true;
                            }
                        }
                    }
                    else
                        return false;
                case "士":
                    if(absX == 1 && absY == 1){
                        if(board.endX>=4 && board.endX <= 6 && board.endY >= 8 && board.endY <=10){
                            return true;
                        }
                        else
                            return false;
                    }
                    else
                        return false;
                case "卒":
                    if(board.startX == board.endX){
                        if(board.startY-board.endY == 1)
                            return true;
                        else
                            return false;
                    }
                    else if(board.startY == board.endY && board.startY <= 5){
                        if(absX == 1)
                            return true;
                        else
                            return false;
                    }
                    else
                        return false;
                case "炮":
                    if(board.startX == board.endX){
                        if(board.point[board.endX][board.endY].havePiece()){
                            for(i=minY+1;i<=maxY-1;i++){
                                if(board.point[board.startX][i].havePiece()){
                                    return true;
                                }
                            }
                            if(i == maxY)
                                return false;
                        }
                        else{
                             for(i=minY+1;i<=maxY-1;i++){
                                if(board.point[board.startX][i].havePiece()){
                                    return false;
                                }
                            }
                            if(i == maxY)
                                return true;
                        }    
                    }
                    else if(board.startY == board.endY){
                        if(board.point[board.endX][board.endY].havePiece()){
                            for(i=minX+1;i<=maxX-1;i++){
                                if(board.point[i][board.startY].havePiece()){
                                    return true;
                                }
                            }
                            if(i == maxX)
                                return false;
                        }
                        else{
                            for(i=minX+1;i<=maxX-1;i++){
                                if(board.point[i][board.startY].havePiece()){
                                    return false;
                                }
                            }
                            if(i == maxX)
                                return true;
                        }

                    }
                    else
                        return false;
                case "将":
                     if(board.startX == board.endX){
                        if(absY == 1 && board.endY <= 10 && board.endY >= 8)
                            return true;
                        else
                            return false;
                    }
                    else if(board.startY == board.endY){
                        if(absX == 1 && board.endX <= 6 && board.endX >= 4){
                            for(i=board.endY;i>=0;i--){
                                if(board.point[board.endX][i].havePiece()){
                                    if(board.point[board.endX][i].getPiece().getName().equals("帅"))
                                        return false;
                                    else
                                        break;  
                                }
                            }
                            return true;
                        }
                        else
                            return false;
                    }
                    else
                        return false;
                default:
                    return false;
            }

        }
    }
}
