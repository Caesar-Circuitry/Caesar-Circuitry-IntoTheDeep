package org.firstinspires.ftc.teamcode.config.Commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.FollowPathCommand;

import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;

import java.util.function.BooleanSupplier;

public class commands {
    // Builtins
    public static Command sleep(long ms) { return new WaitCommand(ms); }
    public static Command sleepUntil(BooleanSupplier condition) { return new WaitUntilCommand(condition); }

    // Pedro Pathing
    public static FollowPathCommand followPath(Follower follower, PathChain path) { return new FollowPathCommand(follower, path); }
    public static FollowPathCommand followPath(Follower follower, Path path) { return new FollowPathCommand(follower, path); }

}