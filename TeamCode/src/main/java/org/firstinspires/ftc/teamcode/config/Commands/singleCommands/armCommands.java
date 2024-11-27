package org.firstinspires.ftc.teamcode.config.Commands.singleCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;

public class armCommands {
    public class armhold extends CommandBase{
        armSubsystem subsystem;
        public armhold(armSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }
        @Override
        public void initialize() {
            subsystem.hold();
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    }
    public class armZero extends CommandBase{
        armSubsystem subsystem;
        public armZero(armSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize(){
            subsystem.setTargetAngle(RobotConstants.armPos.ZERO);
        }
        @Override
        public void execute(){
            subsystem.run();
        }
        @Override
        public boolean isFinished(){
            return subsystem.reachedAngle();
        }
        @Override
        public void end(boolean interrupted){
            subsystem.hold();
        }
    }
    public class armSub extends CommandBase{
        armSubsystem subsystem;
        public armSub(armSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize(){
            subsystem.setTargetAngle(RobotConstants.armPos.SUB);
        }
        @Override
        public void execute(){
            subsystem.run();
        }
        @Override
        public boolean isFinished(){
            return subsystem.reachedAngle();
        }
        @Override
        public void end(boolean interrupted){
            subsystem.hold();
        }
    }
    public class armNeutral extends CommandBase{
        armSubsystem subsystem;
        public armNeutral(armSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize(){
            subsystem.setTargetAngle(RobotConstants.armPos.NEUTRAL);
        }
        @Override
        public void execute(){
            subsystem.run();
        }
        @Override
        public boolean isFinished(){
            return subsystem.reachedAngle();
        }
        @Override
        public void end(boolean interrupted){
            subsystem.hold();
        }
    }
    public class armWall extends CommandBase{
        armSubsystem subsystem;
        public armWall(armSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize(){
            subsystem.setTargetAngle(RobotConstants.armPos.WALL);
        }
        @Override
        public void execute(){
            subsystem.run();
        }
        @Override
        public boolean isFinished(){
            return subsystem.reachedAngle();
        }
        @Override
        public void end(boolean interrupted){
            subsystem.hold();
        }
    }
    public class armHighChamberPlace extends CommandBase{
        armSubsystem subsystem;
        public armHighChamberPlace(armSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize(){
            subsystem.setTargetAngle(RobotConstants.armPos.HIGH_CHAMBER_PLACE);
        }
        @Override
        public void execute(){
            subsystem.run();
        }
        @Override
        public boolean isFinished(){
            return subsystem.reachedAngle();
        }
        @Override
        public void end(boolean interrupted){
            subsystem.hold();
        }
    }
    public class armHighChamberRelease extends CommandBase{
        armSubsystem subsystem;
        public armHighChamberRelease(armSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize(){
            subsystem.setTargetAngle(RobotConstants.armPos.HIGH_CHAMBER_RELEASE);
        }
        @Override
        public void execute(){
            subsystem.run();
        }
        @Override
        public boolean isFinished(){
            return subsystem.reachedAngle();
        }
        @Override
        public void end(boolean interrupted){
            subsystem.hold();
        }
    }
    public class armHighBasket extends CommandBase{
        armSubsystem subsystem;
        public armHighBasket(armSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize(){
            subsystem.setTargetAngle(RobotConstants.armPos.HIGH_BASKET);
        }
        @Override
        public void execute(){
            subsystem.run();
        }
        @Override
        public boolean isFinished(){
            return subsystem.reachedAngle();
        }
        @Override
        public void end(boolean interrupted){
            subsystem.hold();
        }
    }
}
