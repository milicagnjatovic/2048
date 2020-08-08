import jdk.jfr.Description;

import java.util.Random;

/**Class thar represents board of the game.
 * Moving methods first collide all the fields that can be collided in one go, and then move them to side they should be.
 * For moving it's important from which side loops start, eg if we have |2 | 2 | 2 |, if we move left we get | 4 | 2 | 0 |, and
 * if we move right we get | 0 | 2 | 4 |. Same goes for moving up and down. That's the reason why we can't make one function for vertical
 * collisions and one for horizontal collisions. Fields that are collided while moving can't be collided with others in same go, eg
 * | 2 | 2 | 2 | 2 | become | 4 | 4 | 0 | 0 | (if we're moving left), to get | 8 | 0 | 0 | 0| from this we need to press the key again.*/
public class Board {
    /**matrix that represents game board*/
    private int[][] board; //if we use Integer instead of int we have to replace == with equals, if not 128 == 128 will be false
    /**num of numbers in one row (column) on board*/
    private int n;
    /**number of empty places on board, when it reaches 0 game is over*/
    private int free_places;
    private int maxNumOnBoard;

    /**Constructor that makes matrix size n, and initialize all fields*/
    public Board(int n){
        this.n = n;
        this.free_places = n*n;
        board = new int[n][n];
        maxNumOnBoard = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                board[i][j] = 0;
    }

    public int getN() {
        return n;
    }

    public int getMaxNumOnBoard() {
        return maxNumOnBoard;
    }

    public int[][] getBoard() {
        return board;
    }

    @Deprecated
    private void print_board (){
        for (int[] line : board) {
            for (Integer i : line) {
                System.out.print(i +"  ");
            }
            System.out.println();
        }
        System.out.println();
    }

    /**Method that collides fields that should collide (starting from the left) and move all the fields to the left.*/
    public void move_left(){
        //loops start from top left corner
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n-1; j++) {
                if (board[i][j] == board[i][j+1] && board[i][j] != 0) { //if two fields are same they should become one field double of size of the one; fields that are zero are not interesting
                    board[i][j] *=2; //field that's closer to the side where we started off gets the value and further one gets 0, because further one will be considered in the next iteration (whats now n+1 will be n in next iteration)
                    board[i][j+1]=0;
                    free_places += 1; //if two fields collide one field gets free
                    if (board[i][j]>maxNumOnBoard) //for keeping track of highest number on the board
                        maxNumOnBoard = board[i][j];
                }
                else if (board[i][j+1]==0){ //if field that's further from where we started off is 0 then we set it to filed that is closer, this way if we have | 2 | 0 | we'll get | 0 | 2 |, so 2 is considered in next iteration, cause if we have | 2 | 0 | ... | 0 | 2 | we should get | 4 | 0 | ... | 0 |
                    board[i][j+1] = board[i][j];
                    board[i][j]=0;
                }
            }
        }

        //after we collided all the fields that should collide we may got some empty spaces that shouldn't exist, so we need to move all the numbers to the left
        for (int i = 0; i < n; i++) {
            int left = 0; //first empty space on the left
            for (int j = 0; j < n; j++) {
                if (board[i][j]>0){ //when we find number we should move it to first empty field on the left
                    board[i][left++] = board[i][j];
                    if(j!=left-1) board[i][j]=0; //if we're actually moving value we need to remove it from previous place (put zero there), but if it's on good position before moving we mustn't put 0 there
                }
            }
        }
//        print_board();
    }


    /**Method that collides fields that should collide (starting from the right) and move all the fields to the right.*/
    public void move_right(){
//     loop start from top right corner
//        system of method is same as for move_left, only difference is that we're going from different side in every loop
        for (int i = 0; i < n; i++) {
            for (int j = n-1; j > 0; j--) {
                if (board[i][j]==board[i][j-1] && board[i][j] != 0){
                    board[i][j] *=2;
                    board[i][j-1]=0;
                    free_places += 1;
                    if (board[i][j]>maxNumOnBoard)
                        maxNumOnBoard = board[i][j];
                }
                else if (board[i][j-1]==0){
                    board[i][j-1] = board[i][j];
                    board[i][j]=0;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            int right = n-1; //first emmty space is one on the right side
            for (int j = n-1; j >= 0; j--) {
                if (board[i][j]>0){
                    board[i][right--] = board[i][j];
                    if (right+1 != j) board[i][j]=0;
                }
            }
        }
//        print_board();
    }

    /**Method that collides fields that should collide (starting from the top) and move all the fields to the top.*/
    public void move_up(){
        //we start from top left corner and move down, system similar to the rest ov move_* functions
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j]==board[i+1][j] && board[i][j] != 0){ //we're moving in column which means second index is fix and we change first one
                    board[i][j] *=2;
                    board[i+1][j]=0;
                    free_places += 1;
                    if (board[i][j]>maxNumOnBoard)
                        maxNumOnBoard = board[i][j];
                }
                else if (board[i+1][j]==0){
                    board[i+1][j] = board[i][j];
                    board[i][j]=0;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            int up = 0;
            for (int j = 0; j < n; j++)
                if (board[j][i]>0){
                    board[up++][i] = board[j][i];
                    if (up-1!=j)board[j][i] = 0;
                }
        }
//        print_board();
    }

    /**Method that collides fields that should collide (starting from the bottom) and move all the fields to the bottom.*/
    public void move_down(){
//        we start from bottom left corner and move up
        for (int i = 0; i < n; i++) {
            for (int j = n-1; j > 0 ; j--) {
                if (board[j][i]==board[j-1][i]  && board[j][i] != 0){
                    board[j][i] *=2;
                    board[j-1][i]=0;
                    free_places += 1;
                    if (board[i][j]>maxNumOnBoard)
                        maxNumOnBoard = board[i][j];
                }
                else if (board[j-1][i]==0){
                    board[j-1][i] = board[j][i];
                    board[j][i]=0;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            int down = n-1;
            for (int j = n-1; j >= 0; j--)
                if (board[j][i]>0){
                    board[down--][i] = board[j][i];
                    if (down+1!=j)board[j][i] = 0;
                }
        }

//        print_board();
    }

    private static Random random = new Random();


/** Considered using set for that contains all the free places and the randomly chose one to put new number, but in this
 * case we need to clear whole set with every movement up, down, left or right, and then go trough board and add free places.
 * Complexity of this depends of number of fields on board, but generally can be O(n^2) for both time and space.
 * Randomly choosing coordinates for new field: it can happen many times that it chose already taken fields, chances of that
 * happening are higher when there's many already taken fields.  Ideally would be to use just random choosing when there's
 * less than 50% fields taken, and using set when there's less
 * than 50% fields free. Function return false if there's no more empty fields suggesting that game is over, otherwise returns true and waits for the next move.
 **/
    public boolean putRandom(){
        if (free_places == 0) //if there's no empty spaces game is over and we return false
            return false;
        free_places -= 1; //we'll place number on one of empty field, so there's one less field empty

        //we randomly choose coordinates of the field
        int i = random.nextInt(n);
        int j = random.nextInt(n);

        //while we're choosing already taken field we keep looking for empty one
        while (board[i][j] != 0){
            i = random.nextInt(n);
            j = random.nextInt(n);
        }

        //we want to put 2 on the field more times than we put 4
        if (random.nextDouble()>0.75)
            board[i][j] = 4;
        else
            board[i][j] = 2;

//        System.out.println("*" + free_places + "*");
        return true;
    }
}
