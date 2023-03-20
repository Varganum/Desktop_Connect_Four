package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ConnectFour extends JFrame {

    private static final int ROWS_NUMBER = 6;
    private static final int COLUMNS_NUMBER = 7;

    private boolean isTurnX = true;
    public ConnectFour() {
        super("Connect Four");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new GridLayout(ROWS_NUMBER, COLUMNS_NUMBER));
        //setTitle("Connect Four");

        Cell[][] gameCells = new Cell[ROWS_NUMBER][COLUMNS_NUMBER];
        char columnChar;
        for (int i = ROWS_NUMBER; i > 0; i--) {
            for (int j = 0; j < COLUMNS_NUMBER; j++) {
                gameCells[i - 1][j] = new Cell(this);
                columnChar = (char) ('A' + j);
                //gameCells[i - 1][j].setText(Character.toString(columnChar) + i);
                gameCells[i - 1][j].setText(" ");
                gameCells[i - 1][j].setName("Button" + columnChar + i);
                add(gameCells[i - 1][j]);
            }
        }
        setVisible(true);

    }

    public boolean getIsTurnX() {
        return this.isTurnX;
    }

    public void setIsTurnX(boolean turn) {
        this.isTurnX = turn;
    }

}

class Cell extends JButton implements ActionListener {

    ConnectFour game;

    public Cell(ConnectFour game) {
        super();
        setFocusPainted(false);
        this.addActionListener(this);
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (game.getIsTurnX()) {
            this.setText("X");
            game.setIsTurnX(false);
        } else {
            this.setText("O");
            game.setIsTurnX(true);
        }
    }
}