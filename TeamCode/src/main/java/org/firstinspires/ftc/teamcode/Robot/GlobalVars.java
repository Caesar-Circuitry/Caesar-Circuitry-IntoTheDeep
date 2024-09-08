package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;

public class GlobalVars {
    private Pose2d RobotPosition;
    public GlobalVars(){
        this.RobotPosition = new Pose2d(0,0,0);
    }
    public GlobalVars(Pose2d robotPosition) {
        this.RobotPosition = robotPosition;
    }

    public Pose2d getRobotPosition() {
        return RobotPosition;
    }

    public void setRobotPosition(Pose2d robotPosition) {
        RobotPosition = robotPosition;
    }
}
