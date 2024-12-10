package org.firstinspires.ftc.teamcode.paths;

import com.acmerobotics.dashboard.config.Config;
import org.firstinspires.ftc.teamcode.paths.actions.armActions;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.RoadRunner.MecanumDrive;

@Config
@Autonomous(name = "3Clip_Auto", group = "Autonomous")
public class threeSpeci extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(12, -58, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);

        armActions actions = new armActions(hardwareMap);

        // preload
        Action preloadInFront, preloadOnBar;
        Action preloadRaiseArmWait, preloadArmToBarWait, preloadEnsureClipOnBarWait, preloadClipReleaseWait;

        preloadInFront = drive.actionBuilder(drive.pose)
                .stopAndAdd(
                        actions.barUp()
                )
                .splineToLinearHeading(new Pose2d(6,-25, Math.toRadians(90)), Math.toRadians(90))
                .waitSeconds(.25)
                .stopAndAdd(
                        actions.barDown()
                )
                .build();
        preloadOnBar = drive.actionBuilder(new Pose2d(6,-38,Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(6,-25),Math.toRadians(90))
                .build();

        preloadRaiseArmWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.25)
                .build();
        preloadArmToBarWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();
        preloadEnsureClipOnBarWait = drive.actionBuilder((drive.pose))
                .waitSeconds(.25)
                .build();
        preloadClipReleaseWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.25)
                .build();


        // 2nd specimen
        Action speci2BackAwayFromBar, speci2Grab, speci2BackAwayFromWall, speci2InFront, speci2OnBar;
        Action speci2ArmToIntakeWait, speci2ArmToBarWait, speci2EnsureClipOnBarWait, speci2ClipReleaseWait;

        speci2BackAwayFromBar = drive.actionBuilder(new Pose2d(4,-23,Math.toRadians(90)))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(4,-34), Math.toRadians(90))
                .build();
        speci2Grab = drive.actionBuilder((new Pose2d(4,-34,Math.toRadians(90))))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(38,-43, Math.toRadians(270)), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(38,-48), Math.toRadians(270))
                .build();
        speci2BackAwayFromWall = drive.actionBuilder(new Pose2d(38,-48,Math.toRadians(270)))
                .splineToConstantHeading(new Vector2d(38,-40),Math.toRadians(270))
                .build();
        speci2InFront = drive.actionBuilder(new Pose2d(38, -40, Math.toRadians(270)))
                .splineToLinearHeading(new Pose2d(2,-38, Math.toRadians(90)), Math.toRadians(90))
                .build();
        speci2OnBar = drive.actionBuilder(new Pose2d(2,-38,Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(2,-23),Math.toRadians(90))
                .build();

        speci2ArmToIntakeWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();
        speci2ArmToBarWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();
        speci2EnsureClipOnBarWait = drive.actionBuilder((drive.pose))
                .waitSeconds(.25)
                .build();
        speci2ClipReleaseWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.25)
                .build();


        // 3rd specimen
        Action speci3BackAwayFromBar, speci3Grab, speci3BackAwayFromWall, speci3InFront, speci3OnBar;
        Action speci3ArmToIntakeWait, speci3ArmToBarWait, speci3EnsureClipOnBarWait, speci3ClipReleaseWait;

        speci3BackAwayFromBar = drive.actionBuilder(new Pose2d(6,-25,Math.toRadians(90)))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(6,-34), Math.toRadians(90))
                .build();
        speci3Grab = drive.actionBuilder((new Pose2d(6,-34,Math.toRadians(90))))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(35,-34,Math.toRadians(0)),Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(45,-0), Math.toRadians(0))
                .setReversed(false)
                .strafeToConstantHeading(new Vector2d(45,-48)) // the points above and below are the most probable to need fixing
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(38,-40, Math.toRadians(-90)),Math.toRadians(-90))
                .strafeToConstantHeading(new Vector2d(38,-48))
                .build();
        speci3BackAwayFromWall = drive.actionBuilder(new Pose2d(38,-48,Math.toRadians(270)))
                .splineToConstantHeading(new Vector2d(38,-40),Math.toRadians(270))
                .build();
        speci3InFront = drive.actionBuilder(new Pose2d(38, -40, Math.toRadians(270)))
                .splineToLinearHeading(new Pose2d(4,-38, Math.toRadians(90)), Math.toRadians(90))
                .build();
        speci3OnBar = drive.actionBuilder(new Pose2d(4,-38,Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(4,-23),Math.toRadians(90))
                .build();

        speci3ArmToIntakeWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();
        speci3ArmToBarWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();
        speci3EnsureClipOnBarWait = drive.actionBuilder((drive.pose))
                .waitSeconds(.25)
                .build();
        speci3ClipReleaseWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.25)
                .build();



        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.addLine("I am ready");
            telemetry.update();
            Actions.runBlocking(
                    new SequentialAction(
                            actions.clawClose()
                    )
            );
        }

        waitForStart();
        if (isStopRequested()) return;


        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                actions.traverse(),
                                preloadInFront
//                                actions.barUp(),
//                                preloadArmToBarWait,
//                                preloadOnBar,
//                                preloadEnsureClipOnBarWait,
//                                actions.barDown(),
//                                preloadClipReleaseWait,
//                                actions.clawOpen(),
//
//                                // go grab 2nd specimen and place
//
////                                speci2BackAwayFromBar,
////                                actions.intake(),
////                                speci2ArmToIntakeWait,
////                                actions.hold(),
////                                speci2Grab,
////                                actions.clawClose(),
////                                speci2BackAwayFromWall,
////                                speci2InFront,
////                                actions.barUp(),
////                                speci2ArmToBarWait,
////                                speci2OnBar,
////                                speci2EnsureClipOnBarWait,
////                                actions.barDown(),
////                                speci2ClipReleaseWait,
////                                actions.clawOpen(),
//
//                                // go grab 3rd specimen, place, and park
//
//                                speci3BackAwayFromBar,
//                                actions.intake(),
//                                speci3ArmToIntakeWait,
//                                actions.hold(),
//                                speci3Grab,
//                                actions.clawClose(),
//                                speci3BackAwayFromWall,
//                                speci3InFront,
//                                actions.barUp(),
//                                speci3ArmToBarWait,
//                                speci3OnBar,
//                                speci3EnsureClipOnBarWait,
//                                actions.barDown(),
//                                speci3ClipReleaseWait,
//                                actions.clawOpen(),
//                                // need to add park
//
//                                speci2BackAwayFromBar,
//                                actions.intake(),
//                                speci2ArmToIntakeWait,
//                                actions.hold(),
//                                speci2Grab,
//                                actions.clawClose(),
//                                speci2BackAwayFromWall,
//                                speci2InFront,
//                                actions.barUp(),
//                                speci2ArmToBarWait,
//                                speci2OnBar,
//                                speci2EnsureClipOnBarWait,
//                                actions.barDown(),
//                                speci2ClipReleaseWait,
//                                actions.clawOpen()
                        ),
                        actions.periodic()
                )
        );

    }
}
