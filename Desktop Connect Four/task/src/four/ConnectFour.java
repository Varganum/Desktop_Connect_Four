package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ConnectFour extends JFrame {

    public static final int ROWS_NUMBER = 6;
    public static final int COLUMNS_NUMBER = 7;

    private boolean isTurnX = true;
    private ColumnChecker checker;
    public ConnectFour() {
        super("Connect Four");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new GridLayout(ROWS_NUMBER, COLUMNS_NUMBER));
        //setTitle("Connect Four");

        Cell[][] gameCells = new Cell[ROWS_NUMBER][COLUMNS_NUMBER];
        checker = new ColumnChecker(gameCells);
        char columnChar;

        for (int i = ROWS_NUMBER; i > 0; i--) {
            for (int j = 0; j < COLUMNS_NUMBER; j++) {
                gameCells[i - 1][j] = new Cell(this, j, i - 1);
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

    public ColumnChecker getChecker() {
        return checker;
    }
}

class Cell extends JButton implements ActionListener {

    private final ConnectFour game;
    private final int column;
    private final int raw;


    public Cell(ConnectFour game, int column, int raw) {
        super();
        setFocusPainted(false);
        this.addActionListener(this);
        this.game = game;
        this.column = column;
        this.raw = raw;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Cell cellToFill = game.getChecker().findCellToFill(this.getColumn());
        if (Objects.nonNull(cellToFill)) {
            if (game.getIsTurnX()) {
                cellToFill.setText("X");
                game.setIsTurnX(false);
            } else {
                cellToFill.setText("O");
                game.setIsTurnX(true);
            }
        }
    }

    public int getColumn() {
        return column;
    }

    public int getRaw() {
        return raw;
    }
}

class ColumnChecker {
    private Cell[][] field;

    ColumnChecker(Cell[][] field) {
        this.field = field;
    }

    public Cell findCellToFill (int column) {
        Cell result = null;
        for (int i = 1; i <= ConnectFour.ROWS_NUMBER; i++ ) {
            if (field[i - 1][column].getText().isBlank()) {
                result = field[i - 1][column];
                break;
            }
        }
        return result;
    }

}