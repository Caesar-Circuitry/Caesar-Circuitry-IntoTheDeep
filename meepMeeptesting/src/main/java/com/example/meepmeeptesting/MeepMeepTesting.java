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
                    .setConstraints(70, 70, Math.toRadians(180), Math.toRadians(180), 15)
                    .build();

            myFirstBot.runAction(myFirstBot.getDrive().actionBuilder(new Pose2d(4, -34, Math.toRadians(90)))
                    .setReversed(true)
                    .splineToLinearHeading(new Pose2d(35,-34,Math.toRadians(0)),Math.toRadians(90))
                    .splineToConstantHeading(new Vector2d(45,-12), Math.toRadians(0))
                    // .splineToLinearHeading(new Pose2d(45,-12, Math.toRadians(-90)), Math.toRadians(0))
                    .setReversed(false)
                    .strafeToConstantHeading(new Vector2d(45,-43))
                    .waitSeconds(.05) // the points above and below are the most probable to need fixing
                            .setReversed(true)
                            .splineToLinearHeading(new Pose2d(38,-30, Math.toRadians(-90)),Math.toRadians(-90))
//                    .strafeToConstantHeading(new Vector2d(38,-30))
                    .strafeToConstantHeading(new Vector2d(38,-45))
                    .build());

            meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_JUICE_LIGHT)
                    .setDarkMode(true)
                    .setBackgroundAlpha(0.95f)
                    // Add both of our declared bot entities
                    .addEntity(myFirstBot)
                    .start();
        }
    }
