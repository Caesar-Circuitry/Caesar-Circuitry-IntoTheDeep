package org.firstinspires.ftc.teamcode.pedroPathing.localization.localizers;


import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Quaternion;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Localizer;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.MathFunctions;
import org.firstinspires.ftc.teamcode.pedroPathing.pathGeneration.Vector;
import org.firstinspires.ftc.vision.apriltag.AprilTagLibrary;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

/**
 * This is the April Tag Localizer class. This class extends the Localizer superclass and is a
 * localizer that uses a camera to read april tags to always know where your robot is on the field.
 * This class also uses a secondary localizer to gauge heading and for when your camera can't see
 * any april tags.
 *
 * The diagram below, which is taken from Road Runner, shows a typical set up.
 *
 * The view is from the bottom of the robot looking upwards.
 *
 * left on robot is y pos
 *
 * front on robot is x pos
 *
 *    /--------------\
 *    |     ____     |
 *    |     ----     |
 *    | ||        || |
 *    | ||        || |   left (y pos)
 *    |              |
 *    |              |
 *    \--------------/
 *      front (x pos)
 *
 * @author Anyi Lin - 10158 Scott's Bots
 * @version 1.0, 7/20/2024
 */

/**
 * Copyright (c) 2024, Austin Bacher @hypocritical1
 * All rights reserved.
 *
 * The original code used to find the position of the robot with April Tags was created by
 * j5155 and Michael from team 14343, with slight modifications made by Austin Bacher.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public class AprilTagLocalizer extends Localizer {
    private HardwareMap hardwareMap;
    private Pose startPose;
    private double previousHeading;
    private double totalHeading;
    private boolean XAxisInverted = false; // Set to -1 to invert
    private boolean YAxisInverted = false; // Set to -1 to invert
    private long tagDetectTime;
    private AprilTagProcessor aprilTag;
    private Pose cameraOffset;
    private Localizer backupLocalizer;
    private Limelight3A limelight;
    private LLResult result;
    private Pose pose;

    /**
     * This is the April Tag Localizer class. It uses April Tags
     * to relocalize to always have a very accurate position,
     * regardless of where the robot started from.
     *
     * @param map the HardwareMap
     * @param setStartPose the Pose to start from
     */
    public AprilTagLocalizer(HardwareMap map, Pose setStartPose) {
        hardwareMap = map;

        // todo: Change this only if needed.
        XAxisInverted = false;
        YAxisInverted = false;

        // todo: Update this to your backup localizer.
        backupLocalizer = new ThreeWheelIMULocalizer(hardwareMap);

        // todo: Change this to match your webcam's name in your hardware map.
        limelight = hardwareMap.get(Limelight3A.class,"limelight");
        limelight.setPollRateHz(100); // This sets how often we ask Limelight for data (100 times per second)
        limelight.pipelineSwitch(0);

        // TODO: update this
        cameraOffset = new Pose(0,0);

        setStartPose(setStartPose);
        totalHeading = 0;
        previousHeading = startPose.getHeading();

    }
//    public Pose getVectorBasedOnTags() {
//        ArrayList<AprilTagDetection> detections = aprilTag.getDetections();
//        if (detections.isEmpty()) {
//            return null;
//        } else {
//            tagDetectTime = aprilTag.getDetections().get(0).frameAcquisitionNanoTime;
//
//            Vector2d posVector2d = aprilTag.getDetections().stream() // get the tag detections as a Java stream
//                    // convert them to Vector2d positions using getFCPosition
//                    .map(detection -> getFCPosition(detection, backupLocalizer.getPose().getHeading(), cameraOffset))
//                    // add them together
//                    .reduce(new Vector2d(0, 0), Vector2d::plus)
//                    // divide by the number of tags to get the average position
//                    .div(aprilTag.getDetections().size());
//
//            return new Pose(posVector2d.x, posVector2d.y);
//        }
//    }


    /**
     * This returns the current pose estimate.
     *
     * @return returns the current pose estimate as a Pose
     */
    @Override
    public Pose getPose() {
        result = limelight.getLatestResult();
        limelight.updateRobotOrientation(backupLocalizer.getPose().getHeading());
        if (result != null && result.isValid()) {
            Pose3D botpose_mt2 = result.getBotpose_MT2();
            if (botpose_mt2 != null) {
                pose = new Pose(botpose_mt2.getPosition().x,botpose_mt2.getPosition().y);
            }
        }

        if (pose != null) {
            double x = pose.getX();
            double y = pose.getY();

            if (XAxisInverted) x = x * -1;
            if (YAxisInverted) y = y * -1;

            // update secondary localizer
            backupLocalizer.setPose(new Pose(x, y));

            return MathFunctions.addPoses(startPose, new Pose(x, y, backupLocalizer.getTotalHeading()));
        } else {
            // in case the robot couldn't detect any tags
            return backupLocalizer.getPose();
        }
    }

    // Thanks to j5155 and Michael from team 14343 for creating the vast majority of FCPosition(); and getVectorBasedOnTags();

//    public Vector2d getFCPosition(AprilTagDetection detection, double botheading, Pose cameraOffset) {
//        // get coordinates of the robot in RC coordinates
//        // ensure offsets are RC
//        double x = detection.ftcPose.x - cameraOffset.getX();
//        double y = detection.ftcPose.y - cameraOffset.getY();
//
//        // invert heading to correct properly
//        botheading = -botheading;
//
//        // rotate RC coordinates to be field-centric
//        double x2 = x*Math.cos(botheading)+y*Math.sin(botheading);
//        double y2 = x*-Math.sin(botheading)+y*Math.cos(botheading);
//        // add FC coordinates to apriltag position
//        // tags is just the CS apriltag library
//        VectorF tagpose = getCenterStageTagLibrary().lookupTag(detection.id).fieldPosition;
//
//
//        // todo: this will need to be changed for next season (use tag heading to automate??)
//        if (!detection.metadata.name.contains("Audience")) { // is it a backdrop tag?
//            return new Vector2d(
//                    tagpose.get(0) + y2,
//                    tagpose.get(1) - x2);
//
//        } else {
//            return new Vector2d(
//                    tagpose.get(0) - y2,
//                    tagpose.get(1) + x2);
//
//        }
//    }

    /**
     * This returns the current velocity estimate.
     * The April Tag localizer class uses the backup localizer because
     * finding the velocity with april tags will not be very accurate
     *
     * @return returns the current velocity estimate as a Pose
     */
    @Override
    public Pose getVelocity() {
        return backupLocalizer.getVelocity();
    }

    /**
     * This returns the current velocity estimate.
     *
     * @return returns the current velocity estimate as a Vector
     */
    @Override
    public Vector getVelocityVector() {
        return getVelocity().getVector();
    }

    /**
     * This sets the start pose. Changing the start pose should move the robot as if all its
     * previous movements were displacing it from its new start pose.
     *
     * @param setStart the new start pose
     */
    @Override
    public void setStartPose(Pose setStart) {
        startPose = setStart;
    }

    /**
     * This sets the current pose estimate. Changing this should just change the robot's current
     * pose estimate, not anything to do with the start pose.
     *
     * @param setPose the new current pose estimate
     */

    // todo: do this!
    @Override
    public void setPose(Pose setPose) {
        this.pose = setPose;
    }

    /**
     * This updates the total heading of the robot to the backup localizer total heading
     */
    @Override
    public void update() {
        backupLocalizer.update();
        limelight.updateRobotOrientation(backupLocalizer.getPose().getHeading());
        totalHeading = backupLocalizer.getTotalHeading();
    }

    /**
     * This returns how far the robot has turned in radians, in a number not clamped between 0 and
     * 2 * pi radians. This is used for some tuning things and nothing actually within the following.
     *
     * @return returns how far the robot has turned in total, in radians.
     */
    public double getTotalHeading() {
        return totalHeading;
    }

    /**
     * Since the camera is tuned with a separate program, there are no multipliers.
     * Because of this, we just return the backup localizer's multipliers instead.
     */
    @Override
    public double getForwardMultiplier() {
        return backupLocalizer.getForwardMultiplier();
    }

    @Override
    public double getLateralMultiplier() {
        return backupLocalizer.getLateralMultiplier();
    }

    @Override
    public double getTurningMultiplier() {
        return backupLocalizer.getTurningMultiplier();
    }

    @Override
    public void resetIMU() {
        backupLocalizer.resetIMU();
    }

    public void start(){
        limelight.start(); // This tells Limelight to start looking!
    }
}