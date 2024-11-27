package org.firstinspires.ftc.teamcode.config.Commands.singleCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.clawWristSubsystem;

public class clawWristCommands {
    public class clawWristZero extends CommandBase {

        private final clawWristSubsystem subsystem;

        public clawWristZero(clawWristSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize() {
            subsystem.setTargetAngle(RobotConstants.armPos.ZERO);
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    }
    public class clawWristSub extends CommandBase {

        private final clawWristSubsystem subsystem;

        public clawWristSub(clawWristSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize() {
            subsystem.setTargetAngle(RobotConstants.armPos.SUB);
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    }
    public class clawWristNeutral extends CommandBase {

        private final clawWristSubsystem subsystem;

        public clawWristNeutral(clawWristSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize() {
            subsystem.setTargetAngle(RobotConstants.armPos.NEUTRAL);
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    }
    public class clawWristWall extends CommandBase {

        private final clawWristSubsystem subsystem;

        public clawWristWall(clawWristSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize() {
            subsystem.setTargetAngle(RobotConstants.armPos.WALL);
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    }
    public class clawWristHighChamberPlace extends CommandBase {

        private final clawWristSubsystem subsystem;

        public clawWristHighChamberPlace(clawWristSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize() {
            subsystem.setTargetAngle(RobotConstants.armPos.HIGH_CHAMBER_PLACE);
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    }
    public class clawWristHighChamberRelease extends CommandBase {

        private final clawWristSubsystem subsystem;

        public clawWristHighChamberRelease(clawWristSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize() {
            subsystem.setTargetAngle(RobotConstants.armPos.HIGH_CHAMBER_RELEASE);
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    }
    public class clawWristHighBasket extends CommandBase {

        private final clawWristSubsystem subsystem;

        public clawWristHighBasket(clawWristSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize() {
            subsystem.setTargetAngle(RobotConstants.armPos.HIGH_BASKET);
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    }
}
