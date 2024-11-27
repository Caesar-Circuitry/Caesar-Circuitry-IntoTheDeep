package org.firstinspires.ftc.teamcode.config.Commands.singleCommands.clawCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.subsystems.clawSubsystem;

public class clawGrab extends CommandBase {

        private final clawSubsystem subsystem;

        public clawGrab(clawSubsystem subsystem){
            this.subsystem = subsystem;
            addRequirements(subsystem);
        }

        @Override
        public void initialize() {
            subsystem.grab();
        }

        @Override
        public boolean isFinished() {
            return true;
        }
    }
