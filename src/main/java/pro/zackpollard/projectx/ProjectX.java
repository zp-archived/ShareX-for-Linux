package pro.zackpollard.projectx;

import pro.zackpollard.projectx.commands.Command;
import pro.zackpollard.projectx.commands.FullScreenScreenshot;
import pro.zackpollard.projectx.commands.SelectionScreenshot;
import pro.zackpollard.projectx.managers.CommandManager;
import pro.zackpollard.projectx.managers.ImageUploadManager;
import pro.zackpollard.projectx.uploaders.image.CustomImageUploader;
import pro.zackpollard.projectx.utils.Logger;

import java.awt.*;

public class ProjectX {

    private static Robot robot;
    private final CommandManager commandManager = new CommandManager(this);
    private final Logger logger = new Logger();
    private ImageUploadManager imageUploadManager = new ImageUploadManager(this);

    public static Robot getRobot() {

        return ProjectX.robot;
    }

    public void start(String[] args) {

        registerCommands();
        registerUploaders();
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

        this.getCommandManager().registerCommand(new FullScreenScreenshot(this, "fss"));
        this.getCommandManager().registerCommand(new SelectionScreenshot(this, "sss"));
    }

    private void registerUploaders() {

        this.getImageUploadManager().registerUploader(new CustomImageUploader(this, "Custom"));

        //TODO: Remove this once the GUI or there is ability to change this through CLI.
        this.getImageUploadManager().setUploader(this.getImageUploadManager().getImageUploaders().get("Custom"));
    }

    public Logger getLogger() {

        return this.logger;
    }

    public CommandManager getCommandManager() {

        return this.commandManager;
    }

    public ImageUploadManager getImageUploadManager() {

        return imageUploadManager;
    }
}