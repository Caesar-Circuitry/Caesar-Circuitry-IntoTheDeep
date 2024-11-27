package org.firstinspires.ftc.teamcode.opmode.auto.paths;

import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierCurve;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathBuilder;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.BezierLine;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Point;

public class FourPlusZero {
    public static PathChain path() {
        PathBuilder builder = new PathBuilder();
        builder
                .addPath(
                        // Line 0, hang preload
                        new BezierLine(
                                new Point(10.000, 60.000, Point.CARTESIAN),
                                new Point(36.000, 68.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))

                .addPath(
                        // Line 1, start of pushing
                        new BezierCurve(
                                new Point(36.000, 68.000, Point.CARTESIAN),
                                new Point(28.000, 16.000, Point.CARTESIAN),
                                new Point(63.000, 46.000, Point.CARTESIAN),
                                new Point(64.000, 26.000, Point.CARTESIAN)
                        )


                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 2
                        new BezierLine(
                                new Point(64.000, 26.000, Point.CARTESIAN),
                                new Point(15.000, 26.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 3
                        new BezierCurve(
                                new Point(15.000, 26.000, Point.CARTESIAN),
                                new Point(63.000, 30.000, Point.CARTESIAN),
                                new Point(64.000, 16.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 4
                        new BezierLine(
                                new Point(64.000, 16.000, Point.CARTESIAN),
                                new Point(15.000, 16.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 5
                        new BezierCurve(
                                new Point(15.000, 16.000, Point.CARTESIAN),
                                new Point(63.000, 22.000, Point.CARTESIAN),
                                new Point(64.000, 8.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 6
                        new BezierLine(
                                new Point(64.000, 8.000, Point.CARTESIAN),
                                new Point(15.000, 8.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
                .addPath(
                        // Line 7
                        new BezierCurve(
                                new Point(15.000, 8.000, Point.CARTESIAN),
                                new Point(24.000, 18.000, Point.CARTESIAN),
                                new Point(14.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180))
                .addPath(
                        // Line 8, end of pushing
                        new BezierLine(
                                new Point(14.000, 24.000, Point.CARTESIAN),
                                new Point(10.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))

                .addPath(
                        // Line 9
                        new BezierCurve(
                                new Point(10.000, 24.000, Point.CARTESIAN),
                                new Point(30.000, 24.000, Point.CARTESIAN),
                                new Point(33.000, 68.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0))
                .addPath(
                        // Line 10
                        new BezierLine(
                                new Point(33.000, 68.000, Point.CARTESIAN),
                                new Point(36.000, 68.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))

                .addPath(
                        // Line 11
                        new BezierCurve(
                                new Point(36.000, 68.000, Point.CARTESIAN),
                                new Point(18.000, 44.000, Point.CARTESIAN),
                                new Point(14.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180))
                .addPath(
                        // Line 12
                        new BezierLine(
                                new Point(14.000, 24.000, Point.CARTESIAN),
                                new Point(10.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))

                .addPath(
                        // Line 13
                        new BezierCurve(
                                new Point(10.000, 24.000, Point.CARTESIAN),
                                new Point(30.000, 24.000, Point.CARTESIAN),
                                new Point(33.000, 68.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0))
                .addPath(
                        // Line 14
                        new BezierLine(
                                new Point(33.000, 68.000, Point.CARTESIAN),
                                new Point(36.000, 68.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))

                .addPath(
                        // Line 15
                        new BezierCurve(
                                new Point(36.000, 68.000, Point.CARTESIAN),
                                new Point(18.000, 44.000, Point.CARTESIAN),
                                new Point(14.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(0), Math.toRadians(180))
                .addPath(
                        // Line 16
                        new BezierLine(
                                new Point(14.000, 24.000, Point.CARTESIAN),
                                new Point(10.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(180))

                .addPath(
                        // Line 17
                        new BezierCurve(
                                new Point(10.000, 24.000, Point.CARTESIAN),
                                new Point(30.000, 24.000, Point.CARTESIAN),
                                new Point(33.000, 68.000, Point.CARTESIAN)
                        )
                )
                .setLinearHeadingInterpolation(Math.toRadians(180), Math.toRadians(0))
                .addPath(
                        // Line 18
                        new BezierLine(
                                new Point(33.000, 68.000, Point.CARTESIAN),
                                new Point(36.000, 68.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))

                .addPath(
                        // Line 19
                        new BezierCurve(
                                new Point(36.000, 68.000, Point.CARTESIAN),
                                new Point(25.000, 68.000, Point.CARTESIAN),
                                new Point(12.000, 24.000, Point.CARTESIAN)
                        )
                )
                .setConstantHeadingInterpolation(Math.toRadians(0))
        ;
        return builder.build();
    }
}