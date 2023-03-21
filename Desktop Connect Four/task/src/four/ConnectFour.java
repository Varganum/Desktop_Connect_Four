package four;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class ConnectFour extends JFrame {

    public static final int RAWS_NUMBER = 6;
    public static final int COLUMNS_NUMBER = 7;

    private boolean isTurnX = true;
    private final ColumnChecker checker;

    private final char[][] cellChars;
    private final Cell[][] gameCells;
    boolean isGameOver;

    public ConnectFour() {
        super("Connect Four");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel fieldPanel = new JPanel(new GridLayout(RAWS_NUMBER, COLUMNS_NUMBER));

        JPanel resetPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        resetPanel.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

        JButton resetButton = new JButton("Reset");
        resetButton.setName("ButtonReset");
        resetPanel.add(resetButton);

        this.gameCells = new Cell[RAWS_NUMBER][COLUMNS_NUMBER];
        this.cellChars = new char[RAWS_NUMBER][COLUMNS_NUMBER];
        isGameOver = false;

        checker = new ColumnChecker(gameCells);
        char columnChar;

        for (int i = RAWS_NUMBER; i > 0; i--) {
            for (int j = 0; j < COLUMNS_NUMBER; j++) {
                gameCells[i - 1][j] = new Cell(this, j, i - 1);
                columnChar = (char) ('A' + j);
                gameCells[i - 1][j].setText(" ");
                cellChars[i - 1][j] = ' ';
                gameCells[i - 1][j].setName("Button" + columnChar + i);
                fieldPanel.add(gameCells[i - 1][j]);
            }
        }

        resetButton.addActionListener(e -> {
            this.setIsTurnX(true);
            isGameOver = false;
            for (int i = RAWS_NUMBER; i > 0; i--) {
                for (int j = 0; j < COLUMNS_NUMBER; j++) {
                    if (!gameCells[i - 1][j].getText().isBlank()) {
                        gameCells[i - 1][j].resetCell();
                    }
                }
            }
        });

        add(fieldPanel, BorderLayout.CENTER);
        add(resetPanel, BorderLayout.SOUTH);
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

    public void checkWinner(Cell cell) {
        int currentCellRaw = cell.getRaw();
        int currentCellColumn = cell.getColumn();
        isGameOver = checkRaw(currentCellRaw) || checkColumn(currentCellColumn)
                    || checkRightDiagonal(currentCellRaw, currentCellColumn)
                    || checkLeftDiagonal(currentCellRaw, currentCellColumn);
    }

    private boolean checkLeftDiagonal(int currentCellRaw, int currentCellColumn) {
        StringBuilder leftDiagonal = new StringBuilder();
        int currentColumn = currentCellColumn + currentCellRaw + 1;
        for (int i = 0; i < RAWS_NUMBER; i ++) {
            currentColumn--;
            if (currentColumn >= 0 & currentColumn < COLUMNS_NUMBER) {
                leftDiagonal.append(cellChars[i][currentColumn]);
            }
        }
        String leftDiagonalString = new String(leftDiagonal);

        boolean result = leftDiagonalString.length() > 3 &
                (leftDiagonalString.matches(".*XXXX.*") || leftDiagonalString.matches(".*OOOO.*"));
        if (result) {
            String winCells;
            for (int k = 0; k < 3; k++) {
                winCells = leftDiagonalString.substring(k, k + 4);
                if (winCells.equals("XXXX") || winCells.equals("OOOO")) {
                    if (currentCellRaw >= COLUMNS_NUMBER - currentCellColumn - 1) {
                        for (int l = currentCellRaw - (COLUMNS_NUMBER - currentCellColumn - 1) + k;
                             l < currentCellRaw - (COLUMNS_NUMBER - currentCellColumn - 1) + k + 4; l++) {
                            gameCells[l][currentCellColumn + currentCellRaw - l].setBackground(Color.CYAN);
                        }
                    } else {
                        for (int l = k; l < k + 4; l++) {
                            gameCells[l][currentCellColumn + currentCellRaw - l].setBackground(Color.CYAN);
                        }
                    }
                    break;
                }
            }
        }
        return result;
    }

    private boolean checkRightDiagonal(int currentCellRaw, int currentCellColumn) {
        StringBuilder rightDiagonal = new StringBuilder();
        int currentColumn = currentCellColumn - currentCellRaw - 1;
        for (int i = 0; i < RAWS_NUMBER; i ++) {
            currentColumn++;
            if (currentColumn >= 0 & currentColumn < COLUMNS_NUMBER) {
                rightDiagonal.append(cellChars[i][currentColumn]);
            }
        }
        String rightDiagonalString = new String(rightDiagonal);

        boolean result = rightDiagonalString.length() > 3 &
                (rightDiagonalString.matches(".*XXXX.*") || rightDiagonalString.matches(".*OOOO.*"));
        if (result) {
            String winCells;
            for (int k = 0; k < 3; k++) {
                winCells = rightDiagonalString.substring(k, k + 4);
                if (winCells.equals("XXXX") || winCells.equals("OOOO")) {
                    if (currentCellRaw >= currentCellColumn) {
                        for (int l = currentCellRaw - currentCellColumn + k; l < currentCellRaw - currentCellColumn + k + 4; l++) {
                            gameCells[l][l + currentCellColumn - currentCellRaw].setBackground(Color.CYAN);
                        }
                    } else {
                        for (int l = k; l < k + 4; l++) {
                            gameCells[l][l + currentCellColumn - currentCellRaw].setBackground(Color.CYAN);
                        }
                    }
                    break;
                }
            }
        }
        return result;
    }

    private boolean checkColumn(int currentCellColumn) {
        StringBuilder column = new StringBuilder();
        for (int i = 0; i < RAWS_NUMBER; i ++) {
            column.append(cellChars[i][currentCellColumn]);
        }
        String columnString = new String(column);
        boolean result = columnString.matches(".*XXXX.*") || columnString.matches(".*OOOO.*");
        if (result) {
            String winCells;
            for (int k = 0; k < 3; k++) {
                winCells = columnString.substring(k, k + 4);
                if (winCells.equals("XXXX") || winCells.equals("OOOO")) {
                    for (int l = k; l < k + 4; l++) {
                        gameCells[l][currentCellColumn].setBackground(Color.CYAN);
                    }
                }
            }
        }
        return result;
    }

    private boolean checkRaw(int currentCellRaw) {
        String rawString = new String(cellChars[currentCellRaw]);
        boolean result = rawString.matches(".*XXXX.*") || rawString.matches(".*OOOO.*");
        if (result) {
            String winCells;
            for (int k = 0; k < 4; k++) {
                winCells = rawString.substring(k, k + 4);
                if (winCells.equals("XXXX") || winCells.equals("OOOO")) {
                    for (int l = k; l < k + 4; l++) {
                        gameCells[currentCellRaw][l].setBackground(Color.CYAN);
                    }
                }
            }
        }
        return result;
    }

    public void changeCellsChar(int raw, int column, char newChar) {
        this.cellChars[raw][column] = newChar;
    }

    public boolean getIsGameOver() {
        return this.isGameOver;
    }
}

class Cell extends JButton implements ActionListener {

    private final ConnectFour game;
    private final int column;
    private final int raw;

    private static final Color BASELINE_COLOR = Color.YELLOW;


    public Cell(ConnectFour game, int column, int raw) {
        super();
        setFocusPainted(false);
        this.addActionListener(this);
        this.game = game;
        this.column = column;
        this.raw = raw;
        this.setBackground(Cell.BASELINE_COLOR);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Cell cellToFill = game.getChecker().findCellToFill(this.getColumn());
        if (Objects.nonNull(cellToFill) & !game.getIsGameOver()) {
            if (game.getIsTurnX()) {
                cellToFill.setText("X");
                game.changeCellsChar(cellToFill.getRaw(), cellToFill.getColumn(), 'X');
                game.setIsTurnX(false);
            } else {
                cellToFill.setText("O");
                game.changeCellsChar(cellToFill.getRaw(), cellToFill.getColumn(), 'O');
                game.setIsTurnX(true);
            }
            game.checkWinner(cellToFill);
        }

    }

    public int getColumn() {
        return column;
    }

    public int getRaw() {
        return raw;
    }

    public void resetCell() {
        this.setText(" ");
        this.setBackground(Cell.BASELINE_COLOR);
        game.changeCellsChar(this.getRaw(), this.getColumn(), ' ');
    }
}

class ColumnChecker {
    private final Cell[][] field;

    ColumnChecker(Cell[][] field) {
        this.field = field;
    }

    public Cell findCellToFill (int column) {
        Cell result = null;
        for (int i = 1; i <= ConnectFour.RAWS_NUMBER; i++ ) {
            if (field[i - 1][column].getText().isBlank()) {
                result = field[i - 1][column];
                break;
            }
        }
        return result;
    }

}