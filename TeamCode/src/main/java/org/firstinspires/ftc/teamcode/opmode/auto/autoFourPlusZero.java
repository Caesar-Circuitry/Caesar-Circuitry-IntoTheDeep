package org.firstinspires.ftc.teamcode.opmode.auto;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.clawSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.clawWristSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.viperSubsystem;
import org.firstinspires.ftc.teamcode.config.paths.Paths;
import org.firstinspires.ftc.teamcode.config.Commands.commands;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;

@Autonomous(name = "4+0", group = "Hope", preselectTeleOp = "Field Centric TeleOp")
public class autoFourPlusZero extends CommandOpMode {
    public PathChain chain;
    public Follower follower;

    @Override
    public void initialize() {
        this.follower = new Follower(hardwareMap);
        this.follower.setStartingPose(new Pose(10, 60, 0));
        this.chain = Paths.fourPlusZero;
        clawWristSubsystem clawWrist = new clawWristSubsystem(hardwareMap);
        armSubsystem arm = new armSubsystem(hardwareMap);
        viperSubsystem viper = new viperSubsystem(hardwareMap);
        clawSubsystem claw = new clawSubsystem(hardwareMap);
        schedule(
                new SequentialCommandGroup(
                        commands.sleepUntil(this::opModeIsActive),
                        //hang preload
                        commands.followPath(follower, chain.getPath(0)).alongWith(
                                commands.sleep(100).andThen(
                                commands.armHighChamberPlaceGroup(clawWrist, arm, viper)
                                )
                        ),
                        commands.sleep(500).andThen(
                                commands.armHighChamberReleaseGroup(clawWrist, arm, viper, claw)
                        ),


                        // push 3 samples from spike marks
                        commands.followPath(follower, chain.getPath(1))
                                .alongWith(commands.sleep(500)
                                        .andThen(commands.armWallGroup(clawWrist,arm,viper))
                                        .andThen(commands.clawRelease(claw))
                                ),

                        commands.followPath(follower, chain.getPath(2)))
                                .andThen(commands.followPath(follower, chain.getPath(3)))
                                .andThen(commands.followPath(follower, chain.getPath(4)))
                                .andThen(commands.followPath(follower, chain.getPath(5)))
                                .andThen(commands.followPath(follower, chain.getPath(6)))
                                .andThen(commands.followPath(follower, chain.getPath(7)))
                                .andThen(commands.followPath(follower, chain.getPath(8))),

                        // go grab 2nd specimen
                        commands.followPath(follower, chain.getPath(9))
                                .andThen(commands.followPath(follower, chain.getPath(10))),
                        commands.sleep(1000).andThen(commands.clawGrab(claw)),

                        // go hang 2nd specimen
                        commands.followPath(follower, chain.getPath(11))
                                .alongWith(commands.sleep(200)
                                        .andThen(commands.armHighChamberPlaceGroup(clawWrist,arm,viper)))
                                .andThen(
                                        commands.followPath(follower, chain.getPath(12))
                                ),
                        commands.sleep(500).andThen(
                                commands.armHighChamberReleaseGroup(clawWrist, arm, viper, claw))
                                .andThen(commands.clawRelease(claw)),

                        // go grab 3rd specimen
                        commands.followPath(follower, chain.getPath(13))
                                .alongWith(commands.sleep(200).andThen(commands.armWallGroup(clawWrist,arm,viper)))
                                .andThen(commands.followPath(follower, chain.getPath(14))),
                        commands.sleep(1000).andThen(commands.clawGrab(claw)),

                        // go hang 3rd specimen
                        commands.followPath(follower, chain.getPath(15))
                                .alongWith(commands.sleep(200)
                                        .andThen(commands.armHighChamberPlaceGroup(clawWrist,arm,viper)))
                                .andThen(
                                        commands.followPath(follower, chain.getPath(16))
                                ),
                        commands.sleep(500).andThen(
                                        commands.armHighChamberReleaseGroup(clawWrist, arm, viper, claw))
                                .andThen(commands.clawRelease(claw)),

                        // go grab 4th specimen
                        commands.followPath(follower, chain.getPath(17))
                                .alongWith(commands.sleep(200).andThen(commands.armWallGroup(clawWrist,arm,viper)))
                                .andThen(commands.followPath(follower, chain.getPath(18))),
                        commands.sleep(1000).andThen(commands.clawGrab(claw)),

                        // go hang 4th specimen
                        commands.followPath(follower, chain.getPath(11))
                                .alongWith(commands.sleep(200)
                                        .andThen(commands.armHighChamberPlaceGroup(clawWrist,arm,viper)))
                                .andThen(
                                        commands.followPath(follower, chain.getPath(12))
                                ),
                        commands.sleep(500).andThen(
                                        commands.armHighChamberReleaseGroup(clawWrist, arm, viper, claw))
                                .andThen(commands.clawRelease(claw))
                );
    }
}