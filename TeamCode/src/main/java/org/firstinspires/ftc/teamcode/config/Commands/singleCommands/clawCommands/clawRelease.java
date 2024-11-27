package org.firstinspires.ftc.teamcode.config.Commands.singleCommands.clawCommands;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.subsystems.clawSubsystem;


public class clawRelease extends CommandBase{

        private final clawSubsystem subsystem;

        public clawRelease(clawSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize() {
            subsystem.release();
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    }


