package org.firstinspires.ftc.teamcode.Robot;



import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.RobotConstants.OpModeType;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
@Disabled
/*
* Incomplete Robot class
*
* meant to unify systems making more cohesive
*
* allows for easy switching between opModes while keeping data
*
* allows for quick centric switching in teleOp
*
*/
public class Robot {
    public OpModeType opModeType;
    private Follower follower;
    private Pose pose;
    private boolean fieldCentric;
    
    /**
     * @Param opModeType Is it auto or tele-op
    **/
    public Robot(OpModeType opMode, HardwareMap hardwareMap){
        this.opModeType = opMode;
        this.pose = new Pose(0,0,0);
        follower = new Follower(hardwareMap);
        if(opModeType == RobotConstants.OpModeType.TELEOP){
           initTele();
        }
        else{
            initAuto();
        }
    }

    public Robot(OpModeType opMode, HardwareMap hardwareMap, Pose startingPose){
        this.opModeType = opMode;
        this.pose = startingPose;
        follower = new Follower(hardwareMap);
        if(opModeType == RobotConstants.OpModeType.TELEOP){
            initTele();
        }
        else{
            initAuto();
        }
    }

    public void initTele(){

    }

    public void initAuto(){

    }

    public void enableFieldCentric(){
        fieldCentric = true;
    }public void disableFieldCentric(){
        fieldCentric = false;
    }

}
