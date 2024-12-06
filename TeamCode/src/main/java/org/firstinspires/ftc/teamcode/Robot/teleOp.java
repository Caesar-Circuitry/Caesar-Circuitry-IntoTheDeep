package org.firstinspires.ftc.teamcode.Robot;

import com.acmerobotics.dashboard.DashboardCore;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot.CustomMath.PDFL;
import org.firstinspires.ftc.teamcode.Robot.CustomMath.PID;

import java.util.List;
import java.util.Timer;

@TeleOp
@Config
public class teleOp extends LinearOpMode {
    private List<LynxModule> allHubs;
    private DcMotor FRM, BRM, FLM, BLM, armRotate, viper;
    private viperRotate rotate;
    private double lf_power, lb_power, rf_power, rb_power;
    public static double tunePos = 0, pos_in = 0;
    private static final double LIFT_TICKS_PER_IN = -125.5; // Example value, adjust based on your motor and gearing
    private DcMotor liftMotor;
    private Motor.Encoder liftEncoder;
    private Servo clawWrist, claw;
    private double liftTargetPos_ticks;
    private double liftLastPos_ticks = 0;
    private double liftPower = 0;
    private double prevLiftPower = 0;
    public static double kp = 0.01, ki = 0, kd = 0;
    private PIDController liftController; // Assume you have a PIDController class implemented
    private ElapsedTime time;

    private enum Pos {
        ZERO,
        NEUTRAL,
        SUB,
        INT_SAMPLE,
        INT_SPECIMEN,
        BASKET_ANGLE,
        HANG_ANGLE,
        BAR_ANGLE,
        SUB_RISE
    }

    private Pos targetPos = Pos.ZERO;
    private FtcDashboard dashboard = FtcDashboard.getInstance();
    private Telemetry dashboardTelemetry = dashboard.getTelemetry();
    /*Math*/

    private double vipermax = 16, viperOffset = 20.683, viperLength = 0, viperLengthAdjusted = viperLength + viperOffset, LastViperLengthAdjusted = viperLength + viperOffset, rotateHeightOffset = 14.5, rotateoffset = 40, rotateAngle = 10, rotateServoOffset = .196, rotateServoAngle = (90 - rotateAngle + rotateServoOffset) * (1 / 270);//inches
    private boolean rightbumper = true, leftbumper = true;
    private boolean armSubOverride = false;
    private boolean overrideFlag = false;
    private boolean armSubReturnOveride = false;
    /*MATH*/
    public static double neutralAngle = 25, SUBAngle = 13, intakeSample = 255, intakeSpecimen = 38, basketAngle = 125, HangAngle = 150, BarUpAngle = 90, HANGDOWNANGLE = 360,
            clawOpen = .9, clawClosed = .62, clawWristPickup = .05, clawWristBucket = 1, clawWristSpecimen = 0.4, clawWristSUB = .05, multiplier = 1,
            viperbasket = 17, viperZero = .1, viperBar = 7;
    private boolean firstTime = true, dirState = true; //dirState true up false down

    @Override
    public void runOpMode() throws InterruptedException {
        time = new ElapsedTime();
        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.MANUAL);
        }
        liftMotor = hardwareMap.get(DcMotor.class, "viper");
        liftEncoder = new Motor(hardwareMap, "viper", Motor.GoBILDA.RPM_312).encoder;
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftController = new PIDController(kp, ki, kd); // Example PID constants, adjust as needed
        liftEncoder.reset();
        viper = hardwareMap.get(DcMotor.class, "viper");
        FRM = hardwareMap.get(DcMotor.class, "FRM");
        BRM = hardwareMap.get(DcMotor.class, "BRM");
        FLM = hardwareMap.get(DcMotor.class, "FLM");
        BLM = hardwareMap.get(DcMotor.class, "BLM");
        FRM.setDirection(DcMotorSimple.Direction.REVERSE);
        BRM.setDirection(DcMotorSimple.Direction.REVERSE);
        FRM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BRM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FLM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BLM.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armRotate = hardwareMap.get(DcMotor.class, "armRotate");
        armRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rotate = new viperRotate(armRotate);

        clawWrist = hardwareMap.get(Servo.class, "clawWrist"); //port 1 control hub//
        claw = hardwareMap.get(Servo.class, "claw"); //port 0 control hub//
        time.reset();
        waitForStart();

        while (opModeIsActive()) {
            drive();
            if (gamepad2.a) {
                targetPos = Pos.BASKET_ANGLE;
                firstTime = true;
            } else if (gamepad2.x) {
                viperBar = 6;
                targetPos = Pos.BAR_ANGLE;
                firstTime = true;
            } else if (gamepad2.y) {
                viperBar = 0;
                claw.setPosition(clawOpen);
            } else if (gamepad2.b || gamepad1.b) {
                if (overrideFlag){
                    targetPos=Pos.SUB_RISE;
                    overrideFlag = false;
                }
                else{
                    targetPos = Pos.NEUTRAL;
                    firstTime = true;
                }
            } else if (gamepad2.right_bumper) {
                if (viperLength < vipermax && rightbumper) {
                    rightbumper = false;
                    viperLength += 1;
                }
            } else if (gamepad2.left_bumper) {
                if (viperLength > 0 & leftbumper) {
                    leftbumper = false;
                    viperLength -= 1;
                }
            } else if (gamepad2.dpad_up) {
                targetPos = Pos.INT_SPECIMEN;
                firstTime = true;
            } else if (gamepad2.dpad_down) {
                targetPos = Pos.SUB;
                firstTime = true;
                overrideFlag = true;
            } else if (gamepad2.left_trigger > 0) {
                claw.setPosition(clawOpen);
            } else if (gamepad2.right_trigger > 0) {
                if(!armSubOverride) {
                    claw.setPosition(clawClosed);
                }else {
                    armSubOverride = false;
                    armSubReturnOveride = true;
                    targetPos = Pos.NEUTRAL;
                }
            }

            if (!gamepad2.right_bumper) {
                rightbumper = true;

            }
            if (!gamepad2.left_bumper) {
                leftbumper = true;
            }

            if (gamepad1.dpad_down) {
                multiplier = .2;
            } else if (!gamepad1.dpad_down) {
                multiplier = 1;
            } else if (gamepad1.right_bumper && gamepad1.left_bumper) {
                targetPos = Pos.HANG_ANGLE;
                HangAngle = 150;
            } else if (targetPos == Pos.HANG_ANGLE && gamepad1.b) {
                targetPos = Pos.HANG_ANGLE;
                HangAngle = HANGDOWNANGLE;
            }
            dashboardTelemetry.addData("targetPos", targetPos);
//            dashboardTelemetry.addData("targetViperPos", pos_in);
//            dashboardTelemetry.addData("rotateAngle", rotate.getAngle());
//            dashboardTelemetry.addData("actualViperPos", liftLastPos_ticks / LIFT_TICKS_PER_IN);
            dashboardTelemetry.addData("rotateAngle", rotateAngle);
            dashboardTelemetry.addData("servoAngle", rotateServoAngle);
            dashboardTelemetry.addData("viperLength", viperLength);
            dashboardTelemetry.addData("viperLengthTot", viperLengthAdjusted);
            dashboardTelemetry.update();
            states();
            update();
            liftRunToPosition(1);
            for (LynxModule hub : allHubs) {
                hub.clearBulkCache();
            }
        }

    }

    private void drive() {
        double x = -gamepad1.left_stick_x * multiplier;
        double y = gamepad1.left_stick_y * multiplier;
        double turn = -gamepad1.right_stick_x / 1.2;

        double theta = Math.atan2(y, x);
        double power = Math.hypot(x, y);

        double sin = Math.sin(theta - Math.PI / 4);
        double cos = Math.cos(theta - Math.PI / 4);
        double max = Math.max(Math.abs(sin), Math.abs(cos));

        lf_power = power * cos / max + turn;
        lb_power = power * sin / max + turn;
        rf_power = power * sin / max - turn;
        rb_power = power * cos / max - turn;

        if ((power + Math.abs(turn)) > 1) {
            lf_power /= power + turn;
            lb_power /= power + turn;
            rf_power /= power + turn;
            rb_power /= power + turn;
        }

    }

    public void liftRunToPosition(double speed_0to1) {
        liftTargetPos_ticks = pos_in * LIFT_TICKS_PER_IN;
        liftPower = liftController.calculate(liftLastPos_ticks, liftTargetPos_ticks) * speed_0to1;

        liftLastPos_ticks = liftEncoder.getPosition();

        if (liftPower != prevLiftPower) {
            liftMotor.setPower(liftPower);
        }

        prevLiftPower = liftPower;
    }

    private void update() {
        FLM.setPower(lf_power);
        BLM.setPower(lb_power);
        FRM.setPower(rf_power);
        BRM.setPower(rb_power);


        rotate.periodic();
    }

//    boolean firstTime = true;
//    dirState = //Can be up or down

    private void states() {
        switch (targetPos) {
            case NEUTRAL:
                if (armSubReturnOveride){
                    dirCode(neutralAngle, viperZero, clawWristPickup);
                }
                else {
                    if (firstTime) {
                        firstTime = false;
                        time.reset();
                        if (liftLastPos_ticks / LIFT_TICKS_PER_IN <= viperZero) {
                            dirState = true;
                        } else {
                            dirState = false;
                        }
                    }

                    dirCode(neutralAngle, viperZero, clawWristPickup);
                }
                break;
            case SUB:
                time.reset();
                if (liftLastPos_ticks / LIFT_TICKS_PER_IN <= LastViperLengthAdjusted) {
                    dirState = true;
                } else {
                    dirState = false;
                }
                armMath();
                dirCode(neutralAngle-2, 6, rotateServoAngle +.02);
                break;
            case HANG_ANGLE:
                if (liftLastPos_ticks / LIFT_TICKS_PER_IN <= LastViperLengthAdjusted) {
                    dirState = true;
                } else {
                    dirState = false;
                }
                dirCode(HangAngle, viperZero, clawWristSpecimen);
                break;
            case INT_SAMPLE:
                if (firstTime) {
                    firstTime = false;
                    time.reset();
                    if (liftLastPos_ticks / LIFT_TICKS_PER_IN <= viperZero) {
                        dirState = true;
                    } else {
                        dirState = false;
                    }
                }
                dirCode(intakeSample, viperZero, clawWristSUB);
                break;
            case BAR_ANGLE:
                if (firstTime) {
                    firstTime = false;
                    time.reset();
                    if (liftLastPos_ticks / LIFT_TICKS_PER_IN <= viperBar) {
                        dirState = true;
                    } else {
                        dirState = false;
                    }
                }
                dirCode(BarUpAngle, viperBar, clawWristPickup);
                break;
            case BASKET_ANGLE:
                if (firstTime) {
                    firstTime = false;
                    time.reset();
                    if (liftLastPos_ticks / LIFT_TICKS_PER_IN <= viperbasket) {
                        dirState = true;
                    } else {
                        dirState = false;
                    }
                }
                dirCode(basketAngle, viperbasket, clawWristBucket);
                break;
            case INT_SPECIMEN:
                if (firstTime) {
                    firstTime = false;
                    time.reset();
                    if (liftLastPos_ticks / LIFT_TICKS_PER_IN <= viperZero) {
                        dirState = true;
                    } else {
                        dirState = false;
                    }
                }
                dirCode(intakeSpecimen, viperZero, clawWristSpecimen);
                break;
            case SUB_RISE: //CODE BUTTON "B" FOR THIS FUNCTION
                armSubOverride = true;
                dirCode(rotateAngle+20, viperZero, rotateServoAngle + .02);
                break;
        }
    }

    private void dirCode(double rotPos, double viperPos, double wristPos) {
        if (!armSubOverride || armSubReturnOveride) {
            if (!dirState) {//down
                clawWrist.setPosition(wristPos);
//                if (time.time() > .8) {
                    pos_in = viperPos;
                    if (liftLastPos_ticks / LIFT_TICKS_PER_IN <= viperPos + .3 && liftLastPos_ticks / LIFT_TICKS_PER_IN >= viperPos - .3) {
                        rotate.setTargetAngle(rotPos);
                    }
//                }
            } else if (dirState) {//up
                rotate.setTargetAngle(rotPos);
                if (rotate.getAngle() <= rotPos + 1 && rotate.getAngle() >= rotPos - 1) {
                    pos_in = viperPos;
                    if (liftLastPos_ticks / LIFT_TICKS_PER_IN <= viperPos + .3 && liftLastPos_ticks / LIFT_TICKS_PER_IN >= viperPos - .3) {
                        clawWrist.setPosition(wristPos);
                    }
                }
            }
        }
        else if(armSubOverride){
            rotate.setTargetAngle(rotPos);
            if (rotate.getAngle() <= rotPos + 1 && rotate.getAngle() >= rotPos - 1) {
                pos_in = viperPos;
                clawWrist.setPosition(wristPos);
            }
        } else if (armSubReturnOveride) {
            rotate.switchCutPower();
            claw.setPosition(clawClosed);
            rotate.switchCutPower();
            rotate.setTargetAngle(rotPos);
            if (rotate.getAngle() <= rotPos + 1 && rotate.getAngle() >= rotPos - 1) {
                pos_in = viperPos;
                if (liftLastPos_ticks / LIFT_TICKS_PER_IN <= viperPos + .3 && liftLastPos_ticks / LIFT_TICKS_PER_IN >= viperPos - .3) {
                    clawWrist.setPosition(wristPos);
                    armSubReturnOveride = false;
                }
            }
        }
    }
        private void armMath () {
            //viperLengthAdjusted always has to have viperLength plus viperoffset
            LastViperLengthAdjusted = viperLengthAdjusted;
            viperLengthAdjusted = viperLength + viperOffset;
            rotateAngle = ((Math.acos(rotateHeightOffset / viperLengthAdjusted) - Math.acos(rotateHeightOffset / viperOffset)) * 42) + 10;
            rotateServoAngle = ((90 - rotateAngle) / 270) - rotateServoOffset;//inches
        }
    }
