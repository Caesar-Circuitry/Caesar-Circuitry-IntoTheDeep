package org.firstinspires.ftc.teamcode.Robot;


import com.outoftheboxrobotics.photoncore.Photon;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp
public class testTeleop extends LinearOpMode {
    private Robot robot;
    private double voltage;
    private enum rotateState{
        HOME,
        MIDDLE,
        Bucket,
    }
    private rotateState targetRotateState = rotateState.HOME, RotateState = rotateState.HOME;
    private ElapsedTime time = new ElapsedTime();
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        waitForStart();
        while (opModeIsActive()){
            time.reset();
            //robot.driveTrain.setDrivePowers(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x);
            //voltage = robot.getVoltage();
            //telemetry.addData("voltage", voltage);
            if(gamepad1.b){
                targetRotateState = rotateState.HOME;
            } else if (gamepad1.a) {
                targetRotateState = rotateState.MIDDLE;
            } else if (gamepad1.x) {
                targetRotateState = rotateState.Bucket;
            }
            
            switch (RotateState){
                case HOME:
                    if (targetRotateState != rotateState.HOME){
                        robot.rotateMiddle();

                        if(robot.rotate.getAngle() == robot.viperRotateMiddle){
                            RotateState = rotateState.MIDDLE;
                        }
                    }
                    break;
                case MIDDLE:
                    if (targetRotateState != rotateState.MIDDLE){

                        if (targetRotateState == rotateState.HOME){

                            robot.rotateHome();

                            if(robot.rotate.getAngle() == robot.viperRotateHome){
                                RotateState = rotateState.HOME;
                            }

                        } else if (targetRotateState == rotateState.Bucket) {

                            robot.rotateBucket();

                            if(robot.rotate.getAngle() == robot.viperRotateBucket){
                                RotateState = rotateState.Bucket;
                            }
                        }
                    }
                    break;
                case Bucket:
                    if (targetRotateState != rotateState.Bucket){
                        robot.rotateMiddle();

                        if(robot.rotate.getAngle() == robot.viperRotateMiddle){
                            RotateState = rotateState.MIDDLE;
                        }
                    }
                    break;
            }
            robot.periodic();
            telemetry.addData("RefreshRate", 1/time.time());
            telemetry.update();
        }
    }
}
