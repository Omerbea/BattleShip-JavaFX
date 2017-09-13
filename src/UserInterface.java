import javax.swing.*;
import java.util.*;

public class UserInterface {

    private int boardSize = -1;

    public void setBoardSize(int i_boardSize) {
        boardSize = i_boardSize;
    }
        /* PARAMS
     * optins: list of all the lines that should to present
     * style: where and how to menu should to present. for right now we support only 'middel' style
     * RETURN
     * true if success , false otherwise.
     * */

    public boolean printMenu(List<String> options, String style) {

        int optionNumber = 1;

        System.out.println("Menu :");
        for (String option : options) {
            System.out.printf("%d. %s \n\r", optionNumber, option);
            optionNumber++;
        }
        System.out.println();
        System.out.println("Please Enter your choice : ");
        return true;
    }

    public boolean printMassage (String msg){

        System.out.println(msg);
        return  true;
    }

    public ArrayList<Integer> ValidateUserMove(String move) throws Exception {

        String []splittedMove = move.split("[ ]+");
        ArrayList<Integer> result = new ArrayList<>();
        int []cordinate = new int[2];
        int index = 0 ;

        int row = -1 ;
        int column = -1;

        if(move.isEmpty()) {
            throw new Exception("Empty input is not valid.");
        }
        // check if first element is ""
        if(splittedMove[0].equals("")) {
            index = 1;
        }

        try {
            row = Integer.parseInt(splittedMove[index]);
            cordinate[0] = row ;
        } catch (Exception e) {
            throw new Exception("Row must be A number !");
        }

        try {
            column = (splittedMove[index + 1].charAt(0) - 'A');

            cordinate[1] = column ;
            if(column < 0 || column > 25) {
                throw new Exception("Column must be A Capital letter ! inner");
            }
        } catch (Exception e) {
           throw new Exception("Column must be A Capital letter !");
        }

        for (int j = 0; j < cordinate.length; j++)
        {
            result.add(cordinate[j]);
        }
        return result;
    }

    public  ArrayList <Integer> waitForCoordinates (){
        boolean isCoordinatesAreGood = true;
        Scanner scanner = new Scanner(System.in);
        ArrayList<Integer> coordinates = new ArrayList<Integer>();


        while(isCoordinatesAreGood) {
            try {
                coordinates = ValidateUserMove(scanner.nextLine());
                isCoordinatesAreGood = false ;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again !");
                scanner = new Scanner(System.in);
            }
        }

        return coordinates;
    }

    /* PARAMS
     * optins: list of all the lines that should to present
     * style: where and how to menu should to present. for right now we support only 'middel' style
     * startFrom : the defult the first options start from 0. if you want something else, like to start form 1 set startFrom to 1 shoul
     * RETURN
     * true if success , false otherwise.
     * */
    public boolean printMenu(List<String> options, String Style, int startFrom) {

        return false;
    }

    //listing to keybaord and return the input
    public int getMenuOption() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public void printBaordsAndMenu(String name, char[][] boardOne, char[][] boardTwo, int score, List<String> menu) {


        int row = 0;
        int column = 0;
        boardSize = boardOne[0].length;



        System.out.println(name + " Board :");
        System.out.println();
        printTopRaw();
        for (row = 0; row < boardSize; row++) {

            if(row >= 9) {
                System.out.printf(Integer.toString(row + 1));
            } else {
                System.out.printf(Integer.toString(row + 1) + ' ');
            }


            for (column = 0; column < boardSize; column++) {
                System.out.printf(" %c", boardOne[row][column]);
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");

        System.out.println("Rival Board :");
        printTopRaw();
        for (row = 0; row < boardSize; row++) {


            if(row >= 9) {
                System.out.printf(Integer.toString(row + 1));
            } else {
                System.out.printf(Integer.toString(row + 1) + ' ');
            }

            for (column = 0; column < boardSize; column++) {
                System.out.printf(" %c", boardTwo[row][column]);
            }
            System.out.printf("\n");
        }
        System.out.printf("\n");

        System.out.printf("Current Score : %d \n" , score);

        printMenu(menu , "");

    }


    private void printTopRaw() {

        String row = new String("   ");
        int rowEnd = boardSize + 65;
        char letter = 0;

        for(int i = 65 ; i < rowEnd ; i++ ) {
            letter = (char)i ;
            row = row.concat(Character.toString(letter) + " ");
        }

        System.out.println(row);
    }

}
