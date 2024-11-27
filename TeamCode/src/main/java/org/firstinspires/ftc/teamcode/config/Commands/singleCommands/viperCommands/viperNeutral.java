package org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.viperSubsystem;

public class viperNeutral extends CommandBase {
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
