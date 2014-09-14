package pro.zackpollard.projectx;

import pro.zackpollard.projectx.commands.Command;
import pro.zackpollard.projectx.commands.FullScreenScreenshot;
import pro.zackpollard.projectx.config.Config;
import pro.zackpollard.projectx.managers.CommandManager;
import pro.zackpollard.projectx.utils.Logger;

import java.awt.*;
import java.io.File;

public class ProjectX {

    private static Robot robot;
    private final CommandManager commandManager = new CommandManager(this);
    private final Logger logger = new Logger();
    private final Config config = new Config();

    public static Robot getRobot() {

        return ProjectX.robot;
    }

    public void start(String[] args) {

        this.config.saveDefaultConfig();
        this.config.loadConfiguration(new File("config.json"), this.logger);
        registerCommands();
        initialiseVars();

        if (args.length == 1) {

            Command command = commandManager.getCommandWithName(args[0]);
            command.execute();
        }
    }

    /**
     * Initialise all variables in this bracket.
     */
    private void initialiseVars() {

        try {
            ProjectX.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * Initialise all commands in this bracket.
     * //TODO: Change this to a config based system so registration here is not needed to be done manually, and some can be disabled.
     */
    private void registerCommands() {

        this.getCommandManager().registerCommand(new FullScreenScreenshot("fss"));
    }

    public Logger getLogger() {

        return this.logger;
    }

    public CommandManager getCommandManager() {

        return this.commandManager;
    }
}
