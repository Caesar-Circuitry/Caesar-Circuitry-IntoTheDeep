package org.firstinspires.ftc.teamcode.Robot.CustomMath;

import com.acmerobotics.roadrunner.Pose2d;
/*Experimental ment for Pose2d*/
public class KalmanFilter {
    private Pose2d estimate; // Current estimate of the pose
    private Pose2d processNoise; // Process noise covariance
    private Pose2d measurementNoise; // Measurement noise covariance
    private double alpha; // Weight for the sensor measurement

    public KalmanFilter(Pose2d initialEstimate, Pose2d processNoise, Pose2d measurementNoise, double alpha) {
        this.estimate = initialEstimate;
        this.processNoise = processNoise;
        this.measurementNoise = measurementNoise;
        this.alpha = alpha;
    }

    public Pose2d predict(Pose2d odometry) {
        // Predict the new state based on odometry
        estimate = estimate.plus(odometry.log()).plus(processNoise.log());
        return estimate;
    }

    public Pose2d update(Pose2d sensorMeasurement) {
        // Update the estimate with the sensor measurement
        double newX = alpha * sensorMeasurement.position.x + (1 - alpha) * estimate.position.x;
        double newY = alpha * sensorMeasurement.position.y + (1 - alpha) * estimate.position.y;
        double newHeading = alpha * sensorMeasurement.heading.toDouble() + (1 - alpha) * estimate.heading.toDouble();

        // Create the new estimate
        estimate = new Pose2d(newX, newY, newHeading);
        return estimate;
    }

    public Pose2d getEstimate() {
        return estimate;
    }
}