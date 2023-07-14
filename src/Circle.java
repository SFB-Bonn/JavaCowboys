import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

class Circle {
    private int number;
    private Map<String, Cowboy> order;
    private List<Map<String, Object>> roundMetrics;

    public Circle(int numCowboys) {
        this.number = numCowboys;
        this.order = createOrder();
        this.roundMetrics = new ArrayList<>();
    }

    private Map<String, Cowboy> createOrder() {
        Map<String, Cowboy> tmpCircle = new HashMap<>();
        String[] names = new String[number];
        for (int i = 0; i < number; i++) {
            String name = "cowboy_" + i;
            tmpCircle.put(name, new Cowboy(name));
            names[i] = name;
        }
        tmpCircle = tellNeighbors(tmpCircle, names);
        return tmpCircle;
    }

    private Map<String, Cowboy> tellNeighbors(Map<String, Cowboy> order, String[] names) {
        int length = names.length;
        for (int i = 0; i < length; i++) {
            String name = names[i];
            String left, right;
            if (i == 0) {
                left = names[length - 1];
                right = names[i + 1];
            } else if (i == length - 1) {
                left = names[i - 1];
                right = names[0];
            } else {
                left = names[i - 1];
                right = names[i + 1];
            }
            order.get(name).setLeftHand(left);
            order.get(name).setRightHand(right);
        }
        return order;
    }

    public void highNoon() {
        String[] names = order.keySet().toArray(new String[0]);
        Random rand = new Random();
        int firstNo = rand.nextInt(names.length);
        String nextShooter = names[firstNo];
        shootAnotherRound(nextShooter);
    }

    public String shootAnotherRound(String name) {
        Cowboy shooter = order.get(name);
        int shot = shooter.shoot();
        String opponent = shooter.shootWho();
        int opponentHealth = order.get(opponent).getHealth() - shot;
        String nextShooter;
        if (opponentHealth <= 0) {
            opponentHealth = 0;
            updateOpponents(shooter, opponent);
            order.remove(opponent);
            nextShooter = name;
        } else {
            order.get(opponent).setHealth(opponentHealth);
            nextShooter = order.get(opponent).getName();
        }

        Map<String, Object> metrics = new HashMap<>();
        metrics.put("shooter", shooter.getName());
        metrics.put("shot_strength", shot);
        metrics.put("opponent", opponent);
        metrics.put("opponent_health", opponentHealth);
        metrics.put("round", roundMetrics.size());
        roundMetrics.add(metrics);

        if (order.size() > 1) {
            shootAnotherRound(nextShooter);
        }

        return nextShooter;
    }

    private void updateOpponents(Cowboy shooter, String opponent) {
        String newOpponent;
        if (shooter.getLastShot().equals("left")) {
            newOpponent = order.get(opponent).getLeftHand();
            for (String thisCowboy : order.keySet()) {
                if (order.get(thisCowboy).getLeftHand().equals(opponent)) {
                    order.get(thisCowboy).setLeftHand(newOpponent);
                }
                if (order.get(thisCowboy).getRightHand().equals(opponent)) {
                    order.get(thisCowboy).setRightHand(shooter.getName());
                }
            }
        } else {
            newOpponent = order.get(opponent).getRightHand();
            for (String thisCowboy : order.keySet()) {
                if (order.get(thisCowboy).getRightHand().equals(opponent)) {
                    order.get(thisCowboy).setRightHand(newOpponent);
                }
                if (order.get(thisCowboy).getLeftHand().equals(opponent)) {
                    order.get(thisCowboy).setLeftHand(shooter.getName());
                }
            }
        }
    }

    public void report() {
        for (Map<String, Object> metrics : roundMetrics) {
            System.out.println(metrics);
        }
        String lastManStanding = new ArrayList<>(order.keySet()).get(0);
        System.out.println("\n" + lastManStanding + " won");
    }
}
