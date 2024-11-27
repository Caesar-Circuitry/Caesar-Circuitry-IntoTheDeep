package org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.viperSubsystem;

public class viperHighChamberPlace extends CommandBase {
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
