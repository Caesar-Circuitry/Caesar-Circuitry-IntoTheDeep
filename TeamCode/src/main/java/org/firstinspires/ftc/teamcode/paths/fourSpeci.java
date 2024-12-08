package org.firstinspires.ftc.teamcode.paths;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;
import org.firstinspires.ftc.teamcode.paths.actions.armActions;

@Config
@Autonomous(name = "4Clip_Auto", group = "Autonomous")
public class fourSpeci extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(12, -58, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        armActions actions = new armActions(hardwareMap);

        // preload
        Action preloadInFront, preloadOnBar;
        Action preloadRaiseArmWait, preloadArmToBarWait, preloadEnsureClipOnBarWait, preloadClipReleaseWait;

        preloadInFront = drive.actionBuilder(drive.pose)
                .splineToLinearHeading(new Pose2d(6,-38, Math.toRadians(90)), Math.toRadians(90))
                .build();
        preloadOnBar = drive.actionBuilder(new Pose2d(6,-38,Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(6,-25),Math.toRadians(90))
                .build();

        preloadRaiseArmWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();
        preloadArmToBarWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.75)
                .build();
        preloadEnsureClipOnBarWait = drive.actionBuilder((drive.pose))
                .waitSeconds(.5)
                .build();
        preloadClipReleaseWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();


        // 2nd specimen
        Action speci2BackAwayFromBar, speci2Grab, speci2BackAwayFromWall, speci2InFront, speci2OnBar;
        Action speci2ArmToIntakeWait, speci2ArmToBarWait, speci2EnsureClipOnBarWait, speci2ClipReleaseWait;

        speci2BackAwayFromBar = drive.actionBuilder(new Pose2d(6,-25,Math.toRadians(90)))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(6,-34), Math.toRadians(90))
                .build();
        speci2Grab = drive.actionBuilder((new Pose2d(6,-34,Math.toRadians(90))))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(38,-43, Math.toRadians(270)), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(38,-48), Math.toRadians(270))
                .build();
        speci2BackAwayFromWall = drive.actionBuilder(new Pose2d(38,-48,Math.toRadians(270)))
                .splineToConstantHeading(new Vector2d(38,-43),Math.toRadians(270))
                .build();
        speci2InFront = drive.actionBuilder(new Pose2d(38, -43, Math.toRadians(270)))
                .splineToLinearHeading(new Pose2d(4,-38, Math.toRadians(90)), Math.toRadians(90))
                .build();
        speci2OnBar = drive.actionBuilder(new Pose2d(4,-38,Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(4,-25),Math.toRadians(90))
                .build();

        speci2ArmToIntakeWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();
        speci2ArmToBarWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.75)
                .build();
        speci2EnsureClipOnBarWait = drive.actionBuilder((drive.pose))
                .waitSeconds(.5)
                .build();
        speci2ClipReleaseWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();


        // push samples
        Action pushBackAwayFromBar, pushSamples;
        Action pushArmToIntakeWait;

        pushBackAwayFromBar = drive.actionBuilder(new Pose2d(4,-25,Math.toRadians(90)))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(4,-34), Math.toRadians(90))
                .build();
        pushSamples = drive.actionBuilder((new Pose2d(4,-34,Math.toRadians(90))))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(35,-34), Math.toRadians(90))
                .waitSeconds(.05)
                // first sample
                .splineToConstantHeading(new Vector2d(35,-12), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(45,-12), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(45,-35), Math.toRadians(-90))
                .waitSeconds(.05)
                //second sample
                .setReversed(false)
                .splineToConstantHeading(new Vector2d(45,-12), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(55,-12), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(55,-35), Math.toRadians(-90))
                .build();

        pushArmToIntakeWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();


        // 3rd specimen
        Action speci3Grab, speci3BackAwayFromWall, speci3InFront, speci3OnBar;
        Action speci3ArmToBarWait, speci3EnsureClipOnBarWait, speci3ClipReleaseWait;

        speci3Grab = drive.actionBuilder((new Pose2d(55,-35,Math.toRadians(-90))))
                .splineToLinearHeading(new Pose2d(38,-43, Math.toRadians(-90)), Math.toRadians(-90))
                .strafeToConstantHeading(new Vector2d(38,-48))
                .build();
        speci3BackAwayFromWall = drive.actionBuilder(new Pose2d(38,-48,Math.toRadians(270)))
                .splineToConstantHeading(new Vector2d(38,-43),Math.toRadians(270))
                .build();
        speci3InFront = drive.actionBuilder(new Pose2d(38, -43, Math.toRadians(270)))
                .splineToLinearHeading(new Pose2d(2,-38, Math.toRadians(90)), Math.toRadians(90))
                .build();
        speci3OnBar = drive.actionBuilder(new Pose2d(2,-38,Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(2,-25),Math.toRadians(90))
                .build();

        speci3ArmToBarWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.75)
                .build();
        speci3EnsureClipOnBarWait = drive.actionBuilder((drive.pose))
                .waitSeconds(.5)
                .build();
        speci3ClipReleaseWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();

        // 4th specimen
        Action speci4BackAwayFromBar, speci4Grab, speci4BackAwayFromWall, speci4InFront, speci4OnBar;
        Action speci4ArmToIntakeWait, speci4ArmToBarWait, speci4EnsureClipOnBarWait, speci4ClipReleaseWait;

        speci4BackAwayFromBar = drive.actionBuilder(new Pose2d(2,-25,Math.toRadians(90)))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(2,-34), Math.toRadians(90))
                .build();
        speci4Grab = drive.actionBuilder((new Pose2d(2,-34,Math.toRadians(90))))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(38,-43, Math.toRadians(270)), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(38,-48), Math.toRadians(270))
                .build();
        speci4BackAwayFromWall = drive.actionBuilder(new Pose2d(38,-48,Math.toRadians(270)))
                .splineToConstantHeading(new Vector2d(38,-43),Math.toRadians(270))
                .build();
        speci4InFront = drive.actionBuilder(new Pose2d(38, -43, Math.toRadians(270)))
                .splineToLinearHeading(new Pose2d(0,-38, Math.toRadians(90)), Math.toRadians(90))
                .build();
        speci4OnBar = drive.actionBuilder(new Pose2d(0,-38,Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(0,-25),Math.toRadians(90))
                .build();

        speci4ArmToIntakeWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();
        speci4ArmToBarWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.75)
                .build();
        speci4EnsureClipOnBarWait = drive.actionBuilder((drive.pose))
                .waitSeconds(.5)
                .build();
        speci4ClipReleaseWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();



        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.addLine("I am ready");
            telemetry.update();
        }

        Actions.runBlocking(
                new SequentialAction(
                        actions.clawClose()
                )
        );

        waitForStart();
        if (isStopRequested()) return;


        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                actions.traverse(),
                                preloadRaiseArmWait,
                                actions.hold(),
                                preloadInFront,
                                actions.barUp(),
                                preloadArmToBarWait,
                                preloadOnBar,
                                preloadEnsureClipOnBarWait,
                                actions.barDown(),
                                preloadClipReleaseWait,
                                actions.clawOpen(),

                                // go grab 2nd specimen and place

                                speci2BackAwayFromBar,
                                actions.intake(),
                                speci2ArmToIntakeWait,
                                actions.hold(),
                                speci2Grab,
                                actions.clawClose(),
                                speci2BackAwayFromWall,
                                speci2InFront,
                                actions.barUp(),
                                speci2ArmToBarWait,
                                speci2OnBar,
                                speci2EnsureClipOnBarWait,
                                actions.barDown(),
                                speci2ClipReleaseWait,
                                actions.clawOpen(),

                                // go push 2 samples from spike marks

                                pushBackAwayFromBar,
                                actions.intake(),
                                pushArmToIntakeWait,
                                actions.hold(),
                                pushSamples,

                                // go grab 3rd specimen and place

                                speci3Grab,
                                actions.clawClose(),
                                speci3BackAwayFromWall,
                                speci3InFront,
                                actions.barUp(),
                                speci3ArmToBarWait,
                                speci3OnBar,
                                speci3EnsureClipOnBarWait,
                                actions.barDown(),
                                speci3ClipReleaseWait,
                                actions.clawOpen(),

                                // go grab 4th specimen, place, and park

                                // go grab 2nd specimen and place

                                speci4BackAwayFromBar,
                                actions.intake(),
                                speci4ArmToIntakeWait,
                                actions.hold(),
                                speci4Grab,
                                actions.clawClose(),
                                speci4BackAwayFromWall,
                                speci4InFront,
                                actions.barUp(),
                                speci4ArmToBarWait,
                                speci4OnBar,
                                speci4EnsureClipOnBarWait,
                                actions.barDown(),
                                speci4ClipReleaseWait,
                                actions.clawOpen()
                                // need to add park
                        ),
                        actions.periodic()
                )
        );

    }
}
