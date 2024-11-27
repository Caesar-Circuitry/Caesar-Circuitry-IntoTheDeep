package org.firstinspires.ftc.teamcode.config.Commands;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.WaitCommand;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;

import org.firstinspires.ftc.teamcode.config.Commands.CommandGroups.armHighChamberReleaseGroup;
import org.firstinspires.ftc.teamcode.config.Commands.CommandGroups.armWallGroup;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.FollowPathCommand;
import org.firstinspires.ftc.teamcode.config.Commands.CommandGroups.armHighChamberPlaceGroup;

import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.clawCommands.clawGrab;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.clawCommands.clawRelease;
import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.clawWristSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.clawSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.viperSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Path;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;

import java.util.function.BooleanSupplier;

public class commands {
    // Builtins
    public static Command sleep(long ms) {
        return new WaitCommand(ms);
    }
    public static Command sleepUntil(BooleanSupplier condition) {
        return new WaitUntilCommand(condition);
    }

    // Pedro Pathing
    public static FollowPathCommand followPath(Follower follower, PathChain path) {
        return new FollowPathCommand(follower, path);
    }
    public static FollowPathCommand followPath(Follower follower, Path path) {
        return new FollowPathCommand(follower, path);
    }

    // ArmClawFunctions
    public static Command armHighChamberPlaceGroup(clawWristSubsystem clawWrist, armSubsystem arm, viperSubsystem viper) {
        return new armHighChamberPlaceGroup(clawWrist, arm, viper);
    }
    public static Command armHighChamberReleaseGroup(clawWristSubsystem clawWrist, armSubsystem arm, viperSubsystem viper, clawSubsystem claw) {
        return new armHighChamberReleaseGroup(clawWrist, arm, viper, claw);
    }
    public static Command armWallGroup(clawWristSubsystem clawWrist, armSubsystem arm, viperSubsystem viper) {
        return new armWallGroup(clawWrist, arm, viper);
    }

    public static Command clawGrab(clawSubsystem claw) {
        return new clawGrab(claw);
    }
    public static Command clawRelease(clawSubsystem claw) {
        return new clawRelease(claw);
    }

}