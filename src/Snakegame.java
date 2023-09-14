import javax.swing.*;

public class Snakegame extends JFrame {
    Board board;
    Snakegame(){
        board = new Board();
        add(board);
        pack();
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args) {
        Snakegame snakegame = new Snakegame();

    }
}