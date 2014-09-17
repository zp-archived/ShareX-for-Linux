package pro.zackpollard.projectx.commands;

import lombok.Getter;
import pro.zackpollard.projectx.ProjectX;

public abstract class Command {

    private final String name;
    private final String[] aliases;

	@Getter
	private ProjectX projectX;

    /**
     * Construct a new command with no permissions or aliases.
     *
     * @param name the name of this command
     */
    public Command(ProjectX projectX, String name) {

        this(projectX, name, null);
    }

    /**
     * Construct a new command.
     *
     * @param name    primary name of this command
     * @param aliases aliases which map back to this command
     */
    public Command(ProjectX projectX, String name, String... aliases) {

        this.name = name;
        this.aliases = aliases;
	    this.projectX = projectX;
    }

    /**
     * Used to get the command name set for this Command.
     *
     * @return The command name that was set for this Command.
     */

	public String getName() {

		return this.name;
	}

    /**
     * Used to get the aliases of this Command.
     *
     * @return an Array of type String of the aliases this Command has.
     */
    public String[] getAliases() {

        return aliases;
    }

    /**
     * Used to get the actual name of the class. Used mostly for internal logging.
     *
     * @return The full name of the class.
     */
    public String getActualName() {

        return this.getClass().getName();
    }

    /**
     * Code that should be executed when the command is run should be entered here.
     */
    public abstract void execute();
}