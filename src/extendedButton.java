import javafx.scene.control.Button;

public class extendedButton extends Button {
    private int row ;
    private int column;

    public extendedButton(int i_row, int i_column) {
        row = i_row;
        column = i_column;
    }

    public int getRow () {return row;}
    public int getColumn () {return column;}
}
