public class Main {
    public static void main(String[] args) {
        Args progArgs = Args.get();
        progArgs.parse(args);
        Circle thisCircle = new Circle(progArgs.getNumber());
        thisCircle.highNoon();
        thisCircle.report();
    }
}
