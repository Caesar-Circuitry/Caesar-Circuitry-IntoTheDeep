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
@Autonomous(name = "3Clip_Auto", group = "Autonomous")
public class threeSpeci extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(12, -58, Math.toRadians(90));
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        Action redClipToBar, redClipNearBar, redClipAwayBar, longWait1, releaseTIme, shortWait, backAwayWait, backAway, grabTime, shortWait1;

        Action redClipToBar1, redClipNearBar1, redClipAwayBar1, longWait2, releaseTIme1, shortWait2, backAwayWait1, backAway1, grabTime1, shortWait3;

        armActions actions = new armActions(hardwareMap);

        grabTime = drive.actionBuilder((drive.pose))
                .waitSeconds(.25)
                .build();
        backAwayWait = drive.actionBuilder((drive.pose))
                .waitSeconds(.5)
                .build();
        longWait1 = drive.actionBuilder(drive.pose)
                .waitSeconds(.75)
                .build();
        releaseTIme = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();
        shortWait = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();
        shortWait1 = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();

        backAway = drive.actionBuilder((new Pose2d(6,-34,Math.toRadians(90))))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(38,-43, Math.toRadians(270)), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(38,-48), Math.toRadians(270))
                .build();
        redClipNearBar = drive.actionBuilder(drive.pose)
                .splineToLinearHeading(new Pose2d(6,-38, Math.toRadians(90)), Math.toRadians(90))
                .build();
        redClipToBar = drive.actionBuilder(new Pose2d(6,-38,Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(6,-25),Math.toRadians(90))
                .build();
        redClipAwayBar = drive.actionBuilder(new Pose2d(6,-25,Math.toRadians(90)))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(6,-34), Math.toRadians(90))
                .build();


        grabTime1 = drive.actionBuilder((drive.pose))
                .waitSeconds(.25)
                .build();
        backAwayWait1 = drive.actionBuilder((drive.pose))
                .waitSeconds(.5)
                .build();
        longWait2 = drive.actionBuilder(drive.pose)
                .waitSeconds(.75)
                .build();
        releaseTIme1 = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();
        shortWait2 = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();
        shortWait3 = drive.actionBuilder(drive.pose)
                .waitSeconds(.5)
                .build();

        backAway1 = drive.actionBuilder((new Pose2d(6,-34,Math.toRadians(90))))
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(38,-43, Math.toRadians(270)), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(38,-48), Math.toRadians(270))
                .build();
        redClipNearBar1 = drive.actionBuilder(drive.pose) // up date to backaway ending position
                .splineToLinearHeading(new Pose2d(6,-38, Math.toRadians(90)), Math.toRadians(90))
                .build();
        redClipToBar1 = drive.actionBuilder(new Pose2d(6,-38,Math.toRadians(90)))
                .splineToConstantHeading(new Vector2d(6,-25),Math.toRadians(90))
                .build();
        redClipAwayBar1 = drive.actionBuilder(new Pose2d(6,-25,Math.toRadians(90)))
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(6,-34), Math.toRadians(90))
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
                                grabTime,
                                actions.traverse(),
                                shortWait,
                                actions.hold(),
                                redClipNearBar,
                                actions.barUp(),
                                longWait1,
                                redClipToBar,
                                backAwayWait,
                                actions.barDown(),
                                releaseTIme,
                                actions.clawOpen(),
                                redClipAwayBar,
                                actions.intake(),
                                shortWait1,
                                actions.hold(),
                                backAway,
                                actions.clawClose(),
                                //add small back away
                                redClipNearBar1,
                                actions.barUp(),
                                longWait2,
                                redClipToBar1,
                                backAwayWait1,
                                actions.barDown(),
                                releaseTIme1,
                                actions.clawOpen()
                        ),
                        actions.periodic()
                )
        );

    }
}
