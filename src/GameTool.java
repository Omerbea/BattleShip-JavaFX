import sun.text.normalizer.UCharacterProperty;

import java.util.ArrayList;

public abstract class GameTool {

    private String category;
    private String typeId;
    private String type;
    private char sign;
    int score;
    private int row;
    private int column;
    int size;
    private boolean isAlive = true;
    ArrayList<Position>  positionsHited = new ArrayList<>();

    public GameTool( String i_category , String i_typeId, char i_sign, int i_score, int i_size , String i_type) {
        category = i_category;
        typeId = i_typeId;
        this.type = i_type;
        sign = i_sign;
        score = i_score;
        size = i_size;
    }

    public String getCategory() {
        return category;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public char getSign() {
        return sign;
    }


    public boolean getIsAlive(){
        return this.isAlive;
    }

    public String getType() {
        return type;
    }

    public String getTypeId() {
        return typeId;
    }

    public int getScore() {
        return score;
    }

    abstract public boolean updateHitMe(Position position);

    public char getMySing (){
        return  sign;
    }

    public boolean isHitMyThere (Position i_position){
        boolean flag = false;
        for (Position position : this.positionsHited){
            if (i_position.row == position.row && i_position.column == position.column){
                flag = true;
            }
        }
        return  flag;
    }

    public void setCoordinates(int i_row , int i_column) {
        row = i_row ;
        column = i_column;
    }

    public int getRow() {
        return row ;
    }

    public int getColumn() {
        return column;
    }

    public int getSize() {
        return size;
    }

    abstract public String getShipDirection();

    public void setSize(int i_size) {
        size = i_size ;
    }



}
