package org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.viperSubsystem;

public class viperSub extends CommandBase {
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
