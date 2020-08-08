import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**Class that represents game appearance and contains Board field that represents game logic. */
public class GameGui {
    /**Field that contains logic of the game*/
    private Board board;
    /**Field that we give to main and show to player*/
    private GraphicsContext gc;

    /** we chose default size of board to be 500, step represents width and height of one field.*/
    private int size = 500, step;

    public GameGui(int n){
        board = new Board(n); //making game logic with size nxn
        step = size/n; //we get step by dividing size with number of field
        size = step*n; //in case field is too big we need to size it down to match n fields size of step
        Canvas cnv = new Canvas(size, size);
        gc = cnv.getGraphicsContext2D();

        make_start_board(n);
        board.putRandom();
        draw_board();
    }

/**Default constructor makes board with 10x10 fields*/
    public GameGui(){
        this(10);
    }

    /**Makes new empty board and put random field in it.*/
    public void restart(){
        board = new Board(board.getN());
        make_start_board(board.getN());
        board.putRandom();
        draw_board();
    }

    /**Draws empty board with grid*/
    private void make_start_board(int n){
        gc.clearRect(0, 0, size, size);
        gc.setFill(Color.LIGHTSALMON);
        gc.fillRect(0, 0, 500, 500);
        gc.setFill(Color.BLACK);
        for (int i = 0; i <= size; i+=step)
            gc.strokeLine(0, i, size, i);
        for (int i = 0; i <= size; i+=step)
            gc.strokeLine(i, 0, i, size);
    }

    /**Draws one square on the board, x and y are coordinates of top left corner of the field, field will be size step X step, in field goes number txt*/
    private void draw_single_square(int x, int y, int txt){
        gc.setFill(Color.HOTPINK);
        gc.fillRect(x, y, step, step);
        gc.setFill(Color.BLACK);
        if (txt > 10_000) //if number is big we need to set smaller font so it fits in field
            gc.setFont(Font.font(14));
        else if (txt>1000)
            gc.setFont(Font.font(16));
        else
            gc.setFont(Font.font(20));

        gc.fillText(txt+"", x+5, y+step-5); //for text we give coordinates of bottom left corner, that's why y goes down by step (in canvas top left corner has coordinates (0, 0)), +/- 5 is so text is not stick to the edge of the field
    }

    /**Drawing board based on matrix in board field*/
    private void draw_board(){
        int n = board.getN();
        make_start_board(n);
        int[][] mat = board.getBoard();
        for (int i=0; i<n; i++)
            for (int j=0; j<n; j++)
                if (mat[i][j]>0)
                    draw_single_square(j*step, i*step, mat[i][j]);
    }

    public Canvas getCanvas() {
        return gc.getCanvas();
    }

    /**Method that handle actions that occure on the scene*/
    public void action(KeyEvent key){
        if (key.getCode() == KeyCode.LEFT)
            board.move_left();
        else if (key.getCode() == KeyCode.RIGHT)
            board.move_right();
        else if(key.getCode() == KeyCode.UP)
            board.move_up();
        else if(key.getCode() == KeyCode.DOWN)
            board.move_down();
        else
            return;

        if(!board.putRandom()){ //if there's no more empty fields game is over
            Window gameOver = new Window("Well done, your highest value is " + board.getMaxNumOnBoard() + ".\nYou can try some other move after " +
                    "you exit this, but your board is currently full of numbers.\nBetter luck next time ;)");
            gameOver.show();
        }
        draw_board();
    }
}
