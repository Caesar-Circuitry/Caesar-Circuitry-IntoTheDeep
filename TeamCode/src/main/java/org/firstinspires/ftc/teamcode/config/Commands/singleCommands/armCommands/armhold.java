package org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;

public class armhold extends CommandBase {
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
