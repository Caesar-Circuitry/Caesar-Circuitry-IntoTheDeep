package org.firstinspires.ftc.teamcode.paths;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
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
@Autonomous(name = "red_Clip", group = "Autonomous")
public class autoRed extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(28, -64, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Action redClip, redClipToBar, redClipNearBar, redClipAwayBar, longWait1, longWait2, shortWait, shortestWait, backAwayWait, backAway, secondClipToWall, secondClipToChamber, traverse2OverrideWait, grabTime;
        Action redClipToBar1, backaway1, longWait3, longWait4, shortWait1, shortestWait1, backAway1, traverse2OverrideWait1, grabTime1;

        armActions actions = new armActions(hardwareMap);

        grabTime = drive.actionBuilder((drive.pose))
                .waitSeconds(.5)
                .build();
        backAwayWait = drive.actionBuilder((drive.pose))
                .waitSeconds(.5)
                .build();
        traverse2OverrideWait = drive.actionBuilder((drive.pose))
                .waitSeconds(.25)
                .build();
        longWait1 = drive.actionBuilder(drive.pose)
                .waitSeconds(1)
                .build();

        longWait2 = drive.actionBuilder(drive.pose)
                .waitSeconds(1)
                .build();

        shortWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();
        shortestWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.1)
                .build();
        grabTime1 = drive.actionBuilder((drive.pose))
                .waitSeconds(.5)
                .build();
        traverse2OverrideWait1 = drive.actionBuilder((drive.pose))
                .waitSeconds(.25)
                .build();
        longWait3 = drive.actionBuilder(drive.pose)
                .waitSeconds(1)
                .build();

        longWait4 = drive.actionBuilder(drive.pose)
                .waitSeconds(1)
                .build();

        shortWait1 = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();
        shortestWait1 = drive.actionBuilder(drive.pose)
                .waitSeconds(.1)
                .build();

        backAway = drive.actionBuilder((new Pose2d(6,-31,Math.toRadians(90))))
                .strafeTo(new Vector2d(6,-48))
                .build();
        backAway1 = drive.actionBuilder((new Pose2d(1,-31,Math.toRadians(90))))
                .strafeTo(new Vector2d(6,-48))
                .build();
        redClip = drive.actionBuilder(drive.pose)
                .splineToConstantHeading(new Vector2d(6,-40),Math.toRadians(90))
                .waitSeconds(1)
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(36,-35),Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(36,-12),Math.toRadians(90))
                .strafeTo(new Vector2d(49,-8))
                .strafeTo(new Vector2d(49,-55))
                //go for second alliance sample
                .strafeTo(new Vector2d(50,-8))
                .strafeTo(new Vector2d(54,-8))
                .strafeTo(new Vector2d(49,-55))
                .waitSeconds(1)
                .build();
        redClipNearBar = drive.actionBuilder(drive.pose)
                .splineToConstantHeading(new Vector2d(6,-48),Math.toRadians(90))
                .build();
        redClipToBar = drive.actionBuilder(new Pose2d(6,-48,Math.toRadians(90)))
                .strafeTo(new Vector2d(6,-31.25))
                .build();
        redClipToBar1 = drive.actionBuilder(new Pose2d(6,-48,Math.toRadians(90)))
                .strafeTo(new Vector2d(1,-31.5))
                .build();
        redClipAwayBar = drive.actionBuilder(new Pose2d(6,-40,Math.toRadians(90)))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(38,-35),Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(38,-14),Math.toRadians(90))
                .waitSeconds(.01)
                //first sample
                .splineToConstantHeading(new Vector2d(49, -10), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(49,-52), Math.toRadians(-90))
                .waitSeconds(.01)
                //second sample
                .splineToConstantHeading(new Vector2d(55, -10), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(58,-10), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(58, -55), Math.toRadians(-90))
//                //third sample
//                .waitSeconds(.01)
//                .splineToConstantHeading(new Vector2d(66, -10), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(70,-10), Math.toRadians(-90))
//                .splineToConstantHeading(new Vector2d(70, -50), Math.toRadians(-90))

                //Don't use
//                .strafeTo(new Vector2d(53,-6))
//                .strafeTo(new Vector2d(53,-55))
//                //go for second alliance sample
//                .strafeTo(new Vector2d(55,-6))
//                .strafeTo(new Vector2d(67,-6))
//                .strafeTo(new Vector2d(67,-55))
//                //go for third sample
//                .strafeTo(new Vector2d(68,-6))
//                .strafeTo(new Vector2d(72,-6))
//                .strafeTo(new Vector2d(72,-55))
                .build();

        secondClipToWall = drive.actionBuilder(new Pose2d(6,-40,Math.toRadians(90)))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(52,-55,Math.toRadians(0)),Math.toRadians(-90))
                .waitSeconds(1)
                .strafeTo(new Vector2d(60,-55))
                .build();
        secondClipToChamber = drive.actionBuilder(new Pose2d(60,-55,Math.toRadians(0)))
                .setReversed(true)
                .splineToSplineHeading(new Pose2d(6,-48,Math.toRadians(90)),Math.toRadians(0))
                .build();

        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.addLine("I am ready");
            telemetry.update();
        }

        waitForStart();
        if (isStopRequested()) return;


        Actions.runBlocking(
                new ParallelAction(
                        new SequentialAction(
                                actions.clawClose(),
                                actions.traverse(),
                                shortWait,
                                actions.hold(),
                                redClipNearBar,
                                actions.barUp(),
                                longWait1,
                                redClipToBar,
                                longWait2,
                                actions.barDown(),
                                shortestWait,
                                actions.clawOpen(),
                                backAway,
                                actions.intake(),
                                traverse2OverrideWait,
                                actions.hold(),
                                //place second specimen
                                actions.clawOpen(),
                                secondClipToWall,
                                grabTime,
                                actions.clawClose(),
                                grabTime1,
                                secondClipToChamber,
                                actions.barUp(),
                                longWait3,
                                redClipToBar1,
                                longWait4,
                                actions.barDown(),
                                shortestWait1,
                                actions.clawOpen(),
                                backAway1,
                                actions.traverse(),
                                traverse2OverrideWait1,
                                actions.hold(),
                                //grab samples
                                backAwayWait,
                                redClipAwayBar
                        ),
                        actions.periodic()
                )
        );

    }
}
