package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

public class ActionWithUpdate implements Action {
    private final Action action;
    private final Runnable func;

    // Constructor
    public ActionWithUpdate(Action action, Runnable func) {
        this.action = action;
        this.func = func;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket telemetryPacket) {
        func.run();  // Run the Runnable
        return action.run(telemetryPacket);  // Run the Action's run method
    }
}
