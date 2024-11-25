package org.firstinspires.ftc.teamcode.opmode.auto;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
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
@Autonomous
public class TestAuto extends LinearOpMode {
    private Telemetry telemetryA;

    public static double RADIUS = 10;

    private Follower follower;

    private PathChain line;

    @Override
    public void runOpMode() throws InterruptedException {
        follower = new Follower(hardwareMap);
        follower.setPose(new Pose(24, 120, Math.toRadians(0)));
        line = follower.pathBuilder()
                .addPath(
                        new BezierCurve(
                                new Point(24, 120, Point.CARTESIAN),
                                new Point(49, 100, Point.CARTESIAN),
                                new Point(11, 22, Point.CARTESIAN),
                                new Point(24, 40, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180))
                .build();

        follower.followPath(line);

        telemetryA = new MultipleTelemetry(this.telemetry, FtcDashboard.getInstance().getTelemetry());
        telemetryA.addLine("I hope this works");
        telemetryA.update();
        waitForStart();
        follower.update();
        if (follower.atParametricEnd()) {
            follower.followPath(line);
        }

        follower.telemetryDebug(telemetryA);

    }

    /**
     * This initializes the Follower and creates the PathChain for the "circle". Additionally, this
     * initializes the FTC Dashboard telemetry.
     */
}