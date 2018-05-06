package me.mattgd.wildfire;

public enum BlockState {

    EMPTY(0),
    TREE(1),
    BURNING(2);

    private int value;

    BlockState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
