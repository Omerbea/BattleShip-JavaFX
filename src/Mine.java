public class Mine extends GameTool {
    public Mine (String i_species){
        super(i_species, "Mine" , 'M', 0, 1, "Mine");
    }

     public boolean updateHitMe(Position position){
        super.positionsHited.add(position);
        return  true;
     }

    @Override
    public String getShipDirection() {
        return "Mine";

    }

    public  Mine (String i_species , String i_type , char i_sign , int i_row , int i_column){
        super( i_species, i_type , i_sign, 0 , 1, "Mine");
    }
}
