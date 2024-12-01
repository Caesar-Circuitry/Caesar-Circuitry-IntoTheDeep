package org.firstinspires.ftc.teamcode.config.subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.config.Math.rotatingArmController;
import org.firstinspires.ftc.teamcode.config.RobotConstants;

public class viperSubsystem extends SubsystemBase {
    private DcMotor liftMotor;
    private Motor.Encoder liftEncoder;
    private final double LIFT_TICKS_PER_IN = -125.5;
    private double liftTargetPos_ticks;
    private double liftLastPos_ticks = 0;
    private double liftPower = 0;
    private double prevLiftPower = 0;
    private double kp = 0.01,ki = 0,kd = 0;
    private PIDController liftController;
    private double tunePos = 0, pos_in = 0;
    private double speed_0to1 = 1;
    private double holdingPow = 0, middlePoint = 110, viperTolerence = 1;
    private RobotConstants.armPos targetPos = RobotConstants.armPos.ZERO;
    public viperSubsystem(HardwareMap hardwareMap){
        liftMotor = hardwareMap.get(DcMotor.class, "viper");
        liftEncoder = new Motor(hardwareMap, "viper", Motor.GoBILDA.RPM_312).encoder;
        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        liftController = new PIDController(kp, ki, kd); // Example PID constants, adjust as needed
        liftEncoder.reset();
    }

    public void run(){
        liftTargetPos_ticks = pos_in * LIFT_TICKS_PER_IN;
        liftPower = liftController.calculate(liftLastPos_ticks, liftTargetPos_ticks) * speed_0to1;
        try {
            liftLastPos_ticks = liftEncoder.getPosition();
        }catch (Exception e){
            liftLastPos_ticks = 0;
        }

        if (liftPower != prevLiftPower) {
            liftMotor.setPower(liftPower);
        }

        prevLiftPower = liftPower;
    }
    public void setTargetPos(RobotConstants.armPos pos){
        this.targetPos = pos;
        switch (targetPos){
            case ZERO:
                this.pos_in = RobotConstants.viperZero;
                break;
            case SUB:
                this.pos_in = RobotConstants.viperSub;
                break;
            case NEUTRAL:
                this.pos_in = RobotConstants.viperZero;
                break;
            case WALL:
                this.pos_in = RobotConstants.viperZero;
                break;
            case HIGH_CHAMBER_PLACE:
                this.pos_in = RobotConstants.viperBar;
                break;
            case HIGH_CHAMBER_RELEASE:
                this.pos_in = RobotConstants.viperZero;
                break;
            case HIGH_BASKET:
                this.pos_in = RobotConstants.viperbasket;
                break;
        }
    }
    public boolean reachedAngle(){
        switch (targetPos){
            case ZERO:
                if(this.liftLastPos_ticks/LIFT_TICKS_PER_IN < RobotConstants.viperZero + viperTolerence &&this.liftLastPos_ticks/LIFT_TICKS_PER_IN > RobotConstants.viperZero - viperTolerence){
                    return true;
                }
                return false;
            case SUB:
                if(this.liftLastPos_ticks/LIFT_TICKS_PER_IN < RobotConstants.viperSub + viperTolerence &&this.liftLastPos_ticks/LIFT_TICKS_PER_IN > RobotConstants.viperSub - viperTolerence){
                    return true;
                }
                return false;
            case NEUTRAL:
                if(this.liftLastPos_ticks/LIFT_TICKS_PER_IN < RobotConstants.viperZero + viperTolerence &&this.liftLastPos_ticks/LIFT_TICKS_PER_IN > RobotConstants.viperZero - viperTolerence){
                    return true;
                }
                return false;
            case WALL:
                if(this.liftLastPos_ticks/LIFT_TICKS_PER_IN < RobotConstants.viperZero + viperTolerence &&this.liftLastPos_ticks/LIFT_TICKS_PER_IN > RobotConstants.viperZero - viperTolerence){
                    return true;
                }
                return false;
            case HIGH_CHAMBER_PLACE:
                if(this.liftLastPos_ticks/LIFT_TICKS_PER_IN < RobotConstants.viperBar + viperTolerence &&this.liftLastPos_ticks/LIFT_TICKS_PER_IN > RobotConstants.viperBar - viperTolerence){
                    return true;
                }
                return false;
            case HIGH_CHAMBER_RELEASE:
                if(this.liftLastPos_ticks/LIFT_TICKS_PER_IN < RobotConstants.viperZero + viperTolerence &&this.liftLastPos_ticks/LIFT_TICKS_PER_IN > RobotConstants.viperZero - viperTolerence){
                    return true;
                }
                return false;
            case HIGH_BASKET:
                if(this.liftLastPos_ticks/LIFT_TICKS_PER_IN < RobotConstants.viperbasket + viperTolerence &&this.liftLastPos_ticks/LIFT_TICKS_PER_IN > RobotConstants.viperbasket - viperTolerence){
                    return true;
                }
                return false;
        }
        return false;
    }
    public void hold(){
        double pos = liftLastPos_ticks / LIFT_TICKS_PER_IN;
            liftPower = holdingPow;
        if (liftPower != prevLiftPower) {
            liftMotor.setPower(liftPower);
            prevLiftPower = liftPower;
        }
    }
}
