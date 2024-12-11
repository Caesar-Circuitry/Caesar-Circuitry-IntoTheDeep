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
        Action mainAction;

        mainAction = drive.actionBuilder(drive.pose)
                .stopAndAdd(
                        actions.barUp()
                )
                .splineToConstantHeading(new Vector2d(6,-36), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(6,-25), Math.toRadians(90))
                .waitSeconds(.25)
                .stopAndAdd(
                        actions.barDown()
                )
                .stopAndAdd(
                        actions.clawOpen()
                ).setReversed(true)

                .splineToConstantHeading(new Vector2d(6,-40), Math.toRadians(90))
                .stopAndAdd(
                        actions.intake()
                )
                .waitSeconds(.40)
                .stopAndAdd(
                        actions.hold()
                )
                .stopAndAdd(
                        actions.clawOpen()
                )
                .waitSeconds(.01)

                .setReversed(true)
                .splineToLinearHeading(new Pose2d(35,-34,Math.toRadians(0)),Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(45,-0), Math.toRadians(0))
                .strafeToConstantHeading(new Vector2d(45,-48))
                // comment out this code if you dont want 4 speci
//                    .splineToConstantHeading(new Vector2d(55,-0), Math.toRadians(0))
//                    .strafeToConstantHeading(new Vector2d(55,-48))
                // this the end of that code
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(38,-42, Math.toRadians(-90)),Math.toRadians(-90))
                .strafeToConstantHeading(new Vector2d(38,-48))
                .stopAndAdd(
                        actions.clawClose()
                )
                .waitSeconds(.01)
                .splineToConstantHeading(new Vector2d(38,-42),Math.toRadians(270))
                .afterTime(1, actions.barUp())
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(4,-32, Math.toRadians(90)), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(4,-25),Math.toRadians(90))
                .waitSeconds(.25)
                .stopAndAdd(
                        actions.barDown()
                )
                .stopAndAdd(
                        actions.clawOpen()
                )
                .setReversed(true)
                .splineToConstantHeading(new Vector2d(6,-40), Math.toRadians(-90))
                .stopAndAdd(
                        actions.intake()
                )
                .stopAndAdd(
                        actions.intake()
                )
                .waitSeconds(.40)
                .stopAndAdd(
                        actions.hold()
                )
                .stopAndAdd(
                        actions.clawOpen()
                )
                .waitSeconds(.01)
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(38,-43, Math.toRadians(270)), Math.toRadians(-90))
                .splineToConstantHeading(new Vector2d(38,-48), Math.toRadians(270))
                .stopAndAdd(
                        actions.clawClose()
                )
                .waitSeconds(.01)
                .splineToConstantHeading(new Vector2d(38,-42),Math.toRadians(270))
                .afterTime(1, actions.barUp())
                .setReversed(true)
                .splineToLinearHeading(new Pose2d(2,-32, Math.toRadians(90)), Math.toRadians(90))
                .splineToConstantHeading(new Vector2d(2,-25),Math.toRadians(90))
                .waitSeconds(.25)
                .stopAndAdd(
                        actions.barDown()
                )
                .stopAndAdd(
                        actions.clawOpen()
                )

//                    .setReversed(true)
//                    .splineToConstantHeading(new Vector2d(6,-40), Math.toRadians(-90))
//                    .stopAndAdd(
//                        actions.intake()
//                )
//                    .waitSeconds(.001)
//                    .setReversed(true)
//                    .splineToLinearHeading(new Pose2d(38,-43, Math.toRadians(270)), Math.toRadians(-90))
//                    .splineToConstantHeading(new Vector2d(38,-48), Math.toRadians(270))
//                    .stopAndAdd(
//                        actions.clawClose()
//                )
//                    .waitSeconds(.001)
//                    .splineToConstantHeading(new Vector2d(38,-42),Math.toRadians(270))
//                    .afterTime(1, actions.barUp())
//                .setReversed(true)
//                .splineToLinearHeading(new Pose2d(0,-32, Math.toRadians(90)), Math.toRadians(90))
//                .splineToConstantHeading(new Vector2d(0,-25),Math.toRadians(90))
//                .waitSeconds(.25)
//                .stopAndAdd(
//                        actions.barDown()
//                )
//                .stopAndAdd(
//                        actions.clawOpen()
//                )

                .setReversed(true)
                .splineToConstantHeading(new Vector2d(6,-48), Math.toRadians(-90))
                .stopAndAdd(
                        actions.intake()
                )
                .strafeToConstantHeading(new Vector2d(38,-48))

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
                                mainAction
                        ),
                        actions.periodic()
                )
        );

    }
}
