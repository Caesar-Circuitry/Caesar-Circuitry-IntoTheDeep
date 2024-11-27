package org.firstinspires.ftc.teamcode.config.Commands.singleCommands.viperCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.subsystems.viperSubsystem;


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

