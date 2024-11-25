package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;

/**
 * This is the Circle autonomous OpMode. It runs the robot in a PathChain that's actually not quite
 * a circle, but some Bezier curves that have control points set essentially in a square. However,
 * it turns enough to tune your centripetal force correction and some of your heading. Some lag in
 * heading is to be expected.
 *
 * @author Anyi Lin - 10158 Scott's Bots
 * @author Aaron Yang - 10158 Scott's Bots
 * @author Harrison Womack - 10158 Scott's Bots
 * @version 1.0, 3/12/2024
 */
@Config
@Autonomous (name = "Specimen", group = "Autos")
public class SpecimenAuto extends OpMode {
    private Telemetry telemetryA;

    private Follower follower;

    private PathChain preload, push, specimen2To, specimen2Away, specimen3To, specimen3Away, specimen4To, specimen4Away;

    @Override
    public void init() {
        follower = new Follower(hardwareMap);

        preload = follower.pathBuilder()
                .addPath(
                        // Line 1
                        new BezierLine(
                                new Point(10.000, 60.000, Point.CARTESIAN),
                                new Point(36.000, 68.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        push = follower.pathBuilder()
                .addPath(
                        // Line 2
                        new BezierCurve(
                                new Point(36.000, 68.000, Point.CARTESIAN),
                                new Point(28.000, 16.000, Point.CARTESIAN),
                                new Point(63.000, 46.000, Point.CARTESIAN),
                                new Point(64.000, 26.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 3
                        new BezierLine(
                                new Point(64.000, 26.000, Point.CARTESIAN),
                                new Point(15.000, 26.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 4
                        new BezierCurve(
                                new Point(15.000, 26.000, Point.CARTESIAN),
                                new Point(63.000, 30.000, Point.CARTESIAN),
                                new Point(64.000, 16.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 5
                        new BezierLine(
                                new Point(64.000, 16.000, Point.CARTESIAN),
                                new Point(15.000, 16.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 6
                        new BezierCurve(
                                new Point(15.000, 16.000, Point.CARTESIAN),
                                new Point(63.000, 22.000, Point.CARTESIAN),
                                new Point(64.000, 8.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 7
                        new BezierLine(
                                new Point(64.000, 8.000, Point.CARTESIAN),
                                new Point(15.000, 8.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 8
                        new BezierCurve(
                                new Point(15.000, 8.000, Point.CARTESIAN),
                                new Point(24.000, 18.000, Point.CARTESIAN),
                                new Point(14.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180))
                .addPath(
                        // Line 9
                        new BezierLine(
                                new Point(14.000, 24.000, Point.CARTESIAN),
                                new Point(10.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();

        specimen2To = follower.pathBuilder()
                .addPath(
                        // Line 10
                        new BezierCurve(
                                new Point(10.000, 24.000, Point.CARTESIAN),
                                new Point(30.000, 24.000, Point.CARTESIAN),
                                new Point(33.000, 68.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0))
                .addPath(
                        // Line 11
                        new BezierLine(
                                new Point(33.000, 68.000, Point.CARTESIAN),
                                new Point(36.000, 68.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
        specimen3To = follower.pathBuilder()
                .addPath(
                        // Line 14
                        new BezierCurve(
                                new Point(10.000, 24.000, Point.CARTESIAN),
                                new Point(30.000, 24.000, Point.CARTESIAN),
                                new Point(33.000, 68.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0))
                .addPath(
                        // Line 15
                        new BezierLine(
                                new Point(33.000, 68.000, Point.CARTESIAN),
                                new Point(36.000, 68.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();
        specimen4To = follower.pathBuilder()
                .addPath(
                        // Line 18
                        new BezierCurve(
                                new Point(10.000, 24.000, Point.CARTESIAN),
                                new Point(30.000, 24.000, Point.CARTESIAN),
                                new Point(33.000, 68.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0))
                .addPath(
                        // Line 19
                        new BezierLine(
                                new Point(33.000, 68.000, Point.CARTESIAN),
                                new Point(36.000, 68.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        specimen2Away = follower.pathBuilder()
                .addPath(
                        // Line 12
                        new BezierCurve(
                                new Point(36.000, 68.000, Point.CARTESIAN),
                                new Point(18.000, 44.000, Point.CARTESIAN),
                                new Point(14.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180))
                .addPath(
                        // Line 13
                        new BezierLine(
                                new Point(14.000, 24.000, Point.CARTESIAN),
                                new Point(10.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
        specimen3Away = follower.pathBuilder()
                .addPath(
                        // Line 16
                        new BezierCurve(
                                new Point(36.000, 68.000, Point.CARTESIAN),
                                new Point(18.000, 44.000, Point.CARTESIAN),
                                new Point(14.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180))
                .addPath(
                        // Line 17
                        new BezierLine(
                                new Point(14.000, 24.000, Point.CARTESIAN),
                                new Point(10.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))
                .build();
        specimen4Away = follower.pathBuilder()
                .addPath(
                        // Line 20
                        new BezierCurve(
                                new Point(36.000, 68.000, Point.CARTESIAN),
                                new Point(25.000, 68.000, Point.CARTESIAN),
                                new Point(12.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .build();

        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryA.addLine("The robot is ready");
        telemetryA.update();
    }

    /**
     * This runs the OpMode, updating the Follower as well as printing out the debug statements to
     * the Telemetry, as well as the FTC Dashboard.
     */
    @Override
    public void loop() {
        follower.update();

        follower.telemetryDebug(telemetryA);
    }

    public void start() {
        if (follower.atParametricEnd()) {
            follower.followPath(preload);
            follower.followPath(push);
            follower.followPath(specimen2To);
            follower.followPath(specimen2Away);
            follower.followPath(specimen3To);
            follower.followPath(specimen3Away);
            follower.followPath(specimen4To);
            follower.followPath(specimen4Away);
        }
    }
}
