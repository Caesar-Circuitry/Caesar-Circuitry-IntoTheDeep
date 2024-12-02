package org.firstinspires.ftc.teamcode.config.Commands.singleCommands.armCommands;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.config.RobotConstants;
import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;

public class armUpdate extends CommandBase {
    armSubsystem subsystem;
    public armUpdate(armSubsystem subsystem){
        this.subsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize(){
    }
    @Override
    public void execute(){
        subsystem.run();
    }
    @Override
    public boolean isFinished(){
        return false;
    }
    @Override
    public void end(boolean interrupted){
        subsystem.hold();
    }
}
