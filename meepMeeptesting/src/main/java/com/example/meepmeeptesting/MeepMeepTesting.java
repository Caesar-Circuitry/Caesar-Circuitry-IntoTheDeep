package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedLight;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
        public static void main(String[] args) {
            MeepMeep meepMeep = new MeepMeep(800);

            // Declare our first bot
            RoadRunnerBotEntity myFirstBot = new DefaultBotBuilder(meepMeep)
                    // We set this bot to be blue
                    .setColorScheme(new ColorSchemeBlueDark())
                    .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                    .build();

            myFirstBot.runAction(myFirstBot.getDrive().actionBuilder(new Pose2d(28, -64, Math.toRadians(90)))
                    .splineToConstantHeading(new Vector2d(6,-48),Math.toRadians(90))
                    .strafeTo(new Vector2d(6,-31.25))
                    .strafeTo(new Vector2d(6,-48))
                    .setReversed(true)
                    .splineToLinearHeading(new Pose2d(52,-55,Math.toRadians(0)),Math.toRadians(-90))
                    .waitSeconds(1)
                    .strafeTo(new Vector2d(60,-55))
                    .setReversed(true)
                    .splineToSplineHeading(new Pose2d(6,-48,Math.toRadians(90)),Math.toRadians(0))
                    .strafeTo(new Vector2d(1,-31.5))
                    .strafeTo(new Vector2d(6,-48))
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
                    .build());

            meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
                    .setDarkMode(true)
                    .setBackgroundAlpha(0.95f)
                    // Add both of our declared bot entities
                    .addEntity(myFirstBot)
                    .start();
        }
    }
