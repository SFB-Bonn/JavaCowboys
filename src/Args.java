import org.apache.commons.cli.*;

public class Args {
    private static Args instance = null;

    private int number;

    private Args() {
        // Private constructor to enforce singleton pattern
    }

    public static Args get() {
        if (instance == null) {
            instance = new Args();
        }
        return instance;
    }

    public int getNumber() {
        return number;
    }

    public void parse(String[] args) {
        Options options = new Options();
        options.addOption("n", "number", true, "Number of cowboys");

        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("n")) {
                number = Integer.parseInt(cmd.getOptionValue("n"));
            } else {
                number = 5; // Default value
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
