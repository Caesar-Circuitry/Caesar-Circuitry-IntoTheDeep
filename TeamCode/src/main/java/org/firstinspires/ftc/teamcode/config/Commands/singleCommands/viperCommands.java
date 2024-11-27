package org.firstinspires.ftc.teamcode.config.Commands.singleCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.viperSubsystem;

public class viperCommands {
    public class viperhold extends CommandBase {
        viperSubsystem subsystem;
        public viperhold(viperSubsystem subsystem){
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
    public class viperZero extends CommandBase{
        viperSubsystem subsystem;
        public viperZero(viperSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize(){
            subsystem.setTargetPos(RobotConstants.armPos.ZERO);
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
    public class viperSub extends CommandBase{
        viperSubsystem subsystem;
        public viperSub(viperSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize(){
            subsystem.setTargetPos(RobotConstants.armPos.SUB);
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
    public class viperNeutral extends CommandBase{
        viperSubsystem subsystem;
        public viperNeutral(viperSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize(){
            subsystem.setTargetPos(RobotConstants.armPos.NEUTRAL);
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
    public class viperWall extends CommandBase{
        viperSubsystem subsystem;
        public viperWall(viperSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize(){
            subsystem.setTargetPos(RobotConstants.armPos.WALL);
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
    public class viperHighChamberPlace extends CommandBase{
        viperSubsystem subsystem;
        public viperHighChamberPlace(viperSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize(){
            subsystem.setTargetPos(RobotConstants.armPos.HIGH_CHAMBER_PLACE);
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
    public class viperHighChamberRelease extends CommandBase{
        viperSubsystem subsystem;
        public viperHighChamberRelease(viperSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize(){
            subsystem.setTargetPos(RobotConstants.armPos.HIGH_CHAMBER_RELEASE);
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
    public class viperHighBasket extends CommandBase{
        viperSubsystem subsystem;
        public viperHighBasket(viperSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize(){
            subsystem.setTargetPos(RobotConstants.armPos.HIGH_BASKET);
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
