package com.royalchess.gui;

import com.royalchess.engine.board.Board;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class MoveHistory extends JPanel {

    private final JScrollPane scrollPane;
    private JTextArea moveHistoryTextArea;
    private String moveHistoryContent;
    private static final Dimension HISTORY_PANEL_DIMENSION = new Dimension(150,40);

    MoveHistory() {

        moveHistoryContent = " Game start!\n";
        moveHistoryTextArea = new JTextArea(moveHistoryContent);
        moveHistoryTextArea.setBackground(Color.darkGray);
        moveHistoryTextArea.setFont(new Font("font",0,18));
        moveHistoryTextArea.setForeground(Color.decode("#eeeeee"));
        scrollPane = new JScrollPane(moveHistoryTextArea,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Move History"));
        Border border = BorderFactory.createMatteBorder(30,0,0,0,Color.decode("#eeeeee"));
        scrollPane.setBorder(BorderFactory.createTitledBorder(border,"Move History",0,0,new Font("font",0,20)));
        scrollPane.setViewportView(moveHistoryTextArea);
        scrollPane.setPreferredSize(new Dimension(300, 575));
        this.add(scrollPane,BorderLayout.SOUTH);
        this.setVisible(true);
        setBackground(Color.decode("#1b1a1c"));
    }

    public void printMove(ChessBoard.MoveLog movelog, Board board) {
        StringBuilder sb = new StringBuilder();
        if (movelog.size() % 2 != 0) {
            sb.append("White move:");
        }
        else {
            sb.append("Black move:");
        }
        sb.append(movelog.getMove(movelog.size()-1).toString());
        sb.append(calculateCheckAndCheckMateHash(board));
        sb.append("\n");
        moveHistoryContent += " " + sb.toString();
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                moveHistoryTextArea.setText(moveHistoryContent);
            }
        });


    }

    public void clearHistory() {
        moveHistoryContent = " Game start!\n";
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                moveHistoryTextArea.setText(moveHistoryContent);
            }
        });
    }



    private String calculateCheckAndCheckMateHash(final Board board) {
        if (board.getCurrentPlayer().isInCheckMate()) {
            return "#";
        }
        else if(board.getCurrentPlayer().isInCheck()) {
            return "+";
        }
        return "";
    }


}