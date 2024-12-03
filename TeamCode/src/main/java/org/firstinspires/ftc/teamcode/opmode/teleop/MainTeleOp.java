package org.firstinspires.ftc.teamcode.opmode.teleop;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.button.GamepadButton;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.config.Commands.CommandGroups.armGroup.armHighChamberPlaceGroup;
import org.firstinspires.ftc.teamcode.config.Commands.CommandGroups.armGroup.armHighChamberReleaseGroup;
import org.firstinspires.ftc.teamcode.config.Commands.CommandGroups.armGroup.armNeutralGroup;
import org.firstinspires.ftc.teamcode.config.Commands.CommandGroups.armGroup.armSubGroup;
import org.firstinspires.ftc.teamcode.config.Commands.CommandGroups.armGroup.armWallGroup;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.clawCommands.clawGrab;
import org.firstinspires.ftc.teamcode.config.Commands.singleCommands.clawCommands.clawRelease;
import org.firstinspires.ftc.teamcode.config.subsystems.armSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.clawSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.clawWristSubsystem;
import org.firstinspires.ftc.teamcode.config.subsystems.viperSubsystem;
import org.firstinspires.ftc.teamcode.pedroPathing.follower.Follower;
import org.firstinspires.ftc.teamcode.pedroPathing.localization.Pose;

@TeleOp(name = "Robot Centric TeleOp", group = "TeleOp")
@Config
public class MainTeleOp extends LinearOpMode {
    private Follower follower;
    private final Pose startPose = new Pose(0,0,0);

    GamepadEx driverOp;
    GamepadEx toolOp;

    private double driveMultiplier = 1;

    private clawWristSubsystem clawWrist;
    private armSubsystem arm;
    private viperSubsystem viper;
    private clawSubsystem claw;

    TriggerReader leftTriggerReader;
    TriggerReader rightTriggerReader;

    private boolean subIntakeFlag = false;
    private boolean specimenPlaceFlag = false;

    @Override
    public void runOpMode() throws InterruptedException {
        driverOp = new GamepadEx(gamepad1);
        toolOp = new GamepadEx(gamepad2);

        follower = new Follower(hardwareMap);
        follower.setStartingPose(startPose);

        leftTriggerReader = new TriggerReader(driverOp, GamepadKeys.Trigger.LEFT_TRIGGER);
        rightTriggerReader = new TriggerReader(driverOp, GamepadKeys.Trigger.RIGHT_TRIGGER);


        clawWrist = new clawWristSubsystem(hardwareMap);
        arm = new armSubsystem(hardwareMap);
        viper = new viperSubsystem(hardwareMap);
        claw = new clawSubsystem(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {
            drive();

            driverOp.readButtons();
            toolOp.readButtons();

            similarCommands();
            driver2Commands();
            driver1Commands();

            CommandScheduler.getInstance().run();

            telemetry.addData("armPos", arm.getPos());
            telemetry.update();
        }
    }

    public void drive() {
        follower.setTeleOpMovementVectors(-gamepad1.left_stick_y * driveMultiplier, -gamepad1.left_stick_x * driveMultiplier, (-gamepad1.right_stick_x * driveMultiplier)/2, true);
        follower.update();

        telemetry.addData("X: ", follower.getPose().getX());
        telemetry.addData("Y: ", follower.getPose().getY());
        telemetry.addData("Heading in Degrees: ", Math.toDegrees(follower.getPose().getHeading()));
    }

    public void similarCommands() {
        new GamepadButton(driverOp, GamepadKeys.Button.B)
                .or(new GamepadButton(toolOp, GamepadKeys.Button.B))
                .whenActive(() -> {
                    new armNeutralGroup(clawWrist, arm, viper).schedule(); // Schedule the arm neutral command
                    specimenPlaceFlag = false;
                    subIntakeFlag = false;
                });
    }

    public void driver2Commands() {

        new GamepadButton(toolOp, GamepadKeys.Button.DPAD_UP)
                .whenActive(() -> {
                    new armWallGroup(clawWrist,arm,viper).schedule(); // Schedule the specimen wall intake command
                    new clawRelease(claw).schedule();   // Schedule the claw open command
                });

        // basket control
        new GamepadButton(toolOp, GamepadKeys.Button.A)
                .whenActive(() -> {
                    new armWallGroup(clawWrist,arm,viper).schedule(); // Schedule the high basket drop command
                });

        // specimen control
        new GamepadButton(toolOp, GamepadKeys.Button.X)
                .whenActive(() -> {
                    new armHighChamberPlaceGroup(clawWrist,arm,viper).schedule(); // Schedule the high specimen place command
                    specimenPlaceFlag = true;
                });

        new GamepadButton(toolOp, GamepadKeys.Button.Y)
                .whenActive(() -> {
                    new armHighChamberReleaseGroup(clawWrist,arm,viper,claw).schedule(); // Schedule the specimen release command
                });

        if (specimenPlaceFlag) {
            new GamepadButton(toolOp, GamepadKeys.Button.RIGHT_BUMPER)
                    .whenActive(() -> {
                        // Make viper inch up
                    });
            new GamepadButton(toolOp, GamepadKeys.Button.LEFT_BUMPER)
                    .whenActive(() -> {
                        // Make viper inch down
                    });
        }

        // sub control
        new GamepadButton(toolOp, GamepadKeys.Button.DPAD_DOWN)
                .whenActive(() -> {
                    new armSubGroup(clawWrist,arm,viper).schedule(); // Schedule the intake inside submersible command
                    new clawRelease(claw).schedule(); // Schedule the claw open command
                });

        if (subIntakeFlag) {
            new GamepadButton(toolOp, GamepadKeys.Button.RIGHT_BUMPER)
                    .whenActive(() -> {
                        // Make arm go down
                    });
            new GamepadButton(toolOp, GamepadKeys.Button.LEFT_BUMPER)
                    .whenActive(() -> {
                        // Make arm go up
                    });
        }

        // claw controls
        leftTriggerReader.readValue();
        rightTriggerReader.readValue();

        if (leftTriggerReader.wasJustPressed()) {
            new clawRelease(claw).schedule(); // Schedule the claw open command
        }

        if (rightTriggerReader.wasJustPressed()) {
            new clawGrab(claw).schedule(); // Schedule the claw close command
        }

    }

    public void driver1Commands() {
        new GamepadButton(driverOp, GamepadKeys.Button.Y)
                .whenActive(() -> { //need to change command to the actual one when made
                    new armNeutralGroup(clawWrist,arm,viper).schedule(); // Schedule the arm hang command
                    new clawRelease(claw).schedule(); // Schedule the claw open command
                });

        new GamepadButton(driverOp, GamepadKeys.Button.X)
                .whenActive(() -> {
                    new armNeutralGroup(clawWrist,arm,viper).schedule(); // Schedule the arm hang down command
                    new clawRelease(claw).schedule(); // Schedule the claw open command
                });

        if (gamepad1.left_bumper) {
                driveMultiplier = .5;
        } else {
                driveMultiplier = 1;
        }

        new GamepadButton(driverOp, GamepadKeys.Button.START)
                .whenActive(() -> {
                    follower.setPose(new Pose(follower.getPose().getX(), follower.getPose().getY(),0));
                });
    }
}
