package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MinesweeperUI extends JFrame {
    private String mode;
    private Board board;
    private JButton[][] buttons;
    public MinesweeperUI() {
        setTitle("Minesweeper");
        setSize(400, 400);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(controlPanel(), BorderLayout.NORTH);
        board = new Board();
        mode = board.normal();
        JPanel panel = new JPanel(new GridLayout(board.getRow(), board.getCol()));
        buttons = new JButton[board.getRow()][board.getCol()];
        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getCol(); j++) {
                JButton button = createButton(i, j);
                buttons[i][j] = button;
                panel.add(button);
            }
        }
        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    private JPanel controlPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JButton restartButton = new JButton("Restart");
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                board.restartGame();
                resetButtons();
            }
        });
        panel.add(restartButton);
        JButton changeModeButton = new JButton("Change Mode");
//        changeModeButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if(mode == "normal"){
//                    board.changeGameMode("ultimate");
//                }else{
//                    board.changeGameMode("normal");
//                }
//            }
//        });
//        panel.add(changeModeButton);
        JButton rulesButton = new JButton("View Rules");
        rulesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String rules = board.viewRules();
                JOptionPane.showMessageDialog(null, rules, "Minesweeper Rules", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(rulesButton);
        JButton flagsButton = new JButton("View Flags");
        flagsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                byte flags = board.viewFlags();
                JOptionPane.showMessageDialog(null, "Flags: " + flags, "Flags Count", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        panel.add(flagsButton);
        return panel;
    }
    private JButton createButton(final int row, final int col) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(40, 40));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                if (SwingUtilities.isLeftMouseButton(evt)) {
                    board.leftClickTile((byte) row, (byte) col);
                    updateButtonState(row, col);
                    updateUI();
                    checkGameState();
                } else if (SwingUtilities.isRightMouseButton(evt)) {
                    if(board.viewFlags() != 0){
                        board.rightClickTile((byte) row, (byte) col);
                    }
                    updateButtonState(row, col);
                }
            }
        });
        return button;
    }
    private void updateButtonState(int row, int col) {
        Tile tile = board.tiles[row][col];
        JButton button = buttons[row][col];
        if (tile.isFlagged()) {
            button.setText("F");
        } else if (tile.isFlipped()) {
            if (tile.isBomb()) {
                checkGameState();
            } else {
                button.setText(Integer.toString(tile.getNumber()));
            }
        } else {
            button.setText("");
        }
    }
    private void updateUI(){
        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getCol(); j++) {
                updateButtonState(i,j);
            }
        }
    }
    private void checkGameState() {
        if (board.isGameLost()) {
            JOptionPane.showMessageDialog(this, "You Lost!");
            board.restartGame();
            resetButtons();
        } else if (board.isGameWon()) {
            JOptionPane.showMessageDialog(this, "You Won!");
            board.restartGame();
            resetButtons();
        }
    }
    private void resetButtons() {
        for (int i = 0; i < board.getRow(); i++) {
            for (int j = 0; j < board.getCol(); j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MinesweeperUI::new);
    }
}
