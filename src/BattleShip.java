import java.util.ArrayList;

final public class BattleShip extends GameTool {



    private int score ;
    private String direction ;
    private int sizeForLive ;
    public BattleShip(String category ,String i_id, int i_size, char i_sign, int i_score, String i_shipDirection){
        super(category, i_id , i_sign ,i_score , i_size, "battleShip");
        score = i_score ;
        if (category.equals("L_SHAPE")) {
            this.sizeForLive = (i_size * 2) - 1;
        }
        else if(category.equals("REGULAR")) {
            this.sizeForLive = i_size;
        }
        else{
            System.out.print("ERROR: ship category no L_shpe or regular");
        }
        direction = i_shipDirection;
    }


    /*return true if the ship distroyed*/
    @Override
    public boolean updateHitMe(Position position) {

        if (size == 0 ){
            System.out.print("ERROR: BattleShip.updateHitMe() -  size = 0 ");
            //TODO: THROW EXCEPTION
        }
        this.sizeForLive--;
        positionsHited.add(position);
        if (this.sizeForLive == 0){
            super.setAlive(false);
            return true;
        }
        return false;
    }



    @Override
    public int getScore() {
        return super.score;
    }

    @Override
    public String getShipDirection() {
        return direction;
    }
}
