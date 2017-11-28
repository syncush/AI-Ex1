public enum PLACE {RIGHT(0), RIGHTDOWN(1), DOWN(2), DOWNLEFT(3), LEFT(4), LEFTUP(5), UP(6), UPRIGHT(7);
    private final int id;
    PLACE(int id) { this.id = id; }
    public int getValue() { return id; }};