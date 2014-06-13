package pro.zackpollard.projectx;

import pro.zackpollard.projectx.commands.FullScreenScreenshot;
import pro.zackpollard.projectx.managers.CommandManager;
import pro.zackpollard.projectx.utils.Logger;

import java.awt.*;

public class ProjectX {

    private static Robot robot;
    private final CommandManager commandManager = new CommandManager(this);
    private final Logger logger = new Logger();

    public void start(String[] args) {

        registerCommands();
        initialiseVars();
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
     */
    private void registerCommands() {

        this.getCommandManager().registerCommand(new FullScreenScreenshot("fss"));
    }

    public static Robot getRobot() {

        return ProjectX.robot;
    }

    public Logger getLogger() {

        return this.logger;
    }

    public CommandManager getCommandManager() {

        return this.commandManager;
    }
}
