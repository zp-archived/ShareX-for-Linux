package pro.zackpollard.projectx;

import java.awt.*;

public class ProjectX {

    private static Robot robot;

    public static void main(String[] args) {

        registerCommands();
        initialiseVars();
    }

    private static void registerCommands() {


    }

    private static void initialiseVars() {

        try {
            ProjectX.robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public static Robot getRobot() {

        return ProjectX.robot;
    }
}
