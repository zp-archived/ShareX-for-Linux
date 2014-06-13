package pro.zackpollard.projectx.commands;

import pro.zackpollard.projectx.ProjectX;

import java.awt.*;

public class FullScreenScreenshot extends Command {

    public FullScreenScreenshot(String name){

        super(name);
    }

    @Override
    public void execute() {

        Robot robot = ProjectX.getRobot();
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        robot.createScreenCapture(new Rectangle(0, 0,
                (int) toolkit.getScreenSize().getWidth(),
                (int) toolkit.getScreenSize().getHeight()));

        //TODO: Push this upstream to a server (Configurable).
        //TODO: Save this to disk (Configurable).
    }
}
