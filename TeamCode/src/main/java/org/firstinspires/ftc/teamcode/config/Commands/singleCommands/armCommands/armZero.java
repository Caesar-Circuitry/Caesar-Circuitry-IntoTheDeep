package org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;

public class armZero extends CommandBase {
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
