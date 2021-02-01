package com.pk.minesweeper.game.levels;

public enum Level {

    EASY(7, 10),
    MEDIUM(8, 15),
    HARD(9, 20);

    private final int columns;
    private final int minesToBoardRatio;
    private static Level currentLevel;

    Level(int columns, int minesToBoardRatio) {
        this.columns = columns;
        this.minesToBoardRatio = minesToBoardRatio;
    }

    public static Level getCurrentLevel() {
        return currentLevel;
    }

    public static void setCurrentLevel(Level level) {
        currentLevel = level;
    }

    public int getColumns() {
        return columns;
    }

    public int getMinesToBoardRatio() {
        return minesToBoardRatio;
    }
}
