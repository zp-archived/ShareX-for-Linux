package pro.zackpollard.projectx.commands;

public abstract class Command {

    private final String name;
    private final String[] aliases;

    /**
     * Construct a new command with no permissions or aliases.
     *
     * @param name the name of this command
     */
    public Command(String name)
    {
        this( name, null );
    }

    /**
     * Construct a new command.
     *
     * @param name primary name of this command
     * @param permission the permission node required to execute this command,
     * null or empty string allows it to be executed by everyone
     * @param aliases aliases which map back to this command
     */
    public Command(String name, String... aliases)
    {
        this.name = name;
        this.aliases = aliases;
    }

    public abstract void execute();
}