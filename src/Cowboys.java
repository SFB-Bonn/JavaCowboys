import java.util.Random;

class Cowboy {
    private String name;
    private int health;
    private String leftHand;
    private String rightHand;
    private String lastShot;

    public Cowboy(String newName) {
        this.name = newName;
        this.health = 10;
        this.leftHand = "";
        this.rightHand = "";
        this.lastShot = "";
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public void setLeftHand(String name) {
        this.leftHand = name;
    }

    public void setRightHand(String name) {
        this.rightHand = name;
    }

    public int shoot() {
        Random rand = new Random();
        return rand.nextInt(5) + 1;
    }

    public String shootWho() {
        if (health % 2 == 0) {
            lastShot = "right";
            return rightHand;
        } else {
            lastShot = "left";
            return leftHand;
        }
    }
}
