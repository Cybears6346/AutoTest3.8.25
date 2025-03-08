// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.Subroutines;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;

public class Autonomous {
    // Fresh beginnings
    double l2PositionBottomed = 6.875; // Calculation 31.875 - 26
    double l3PositionBottomed = 21.625;
    double l4PositionBottomed = 46;



    double startingToReefCenter1 = 88 - 30 - 2.9 + 0.9; // - 2.9 for front bumper + 0.9 for 2 inches behind starting line
    double startingToReefCenter = 650 + startingToReefCenter1;

    // The below applies to all starting positions
    // public void Initial(SendableChooser<String> Auto_Selector, String[] AutoNames){
    //     Auto_Selector.setDefaultOption(AutoNames[0], AutoNames[0]);
    //     Auto_Selector.addOption(AutoNames[1], AutoNames[1]);
    //     Auto_Selector.addOption(AutoNames[2], AutoNames[2]);
    //    //Auto_Selector.addOption(AutoNames[3], AutoNames[3]);
    //     // Auto_Selector.addOption(AutoNames[4], AutoNames[4]);
    //     // Auto_Selector.addOption(AutoNames[5], AutoNames[5]);
    //     // Auto_Selector.addOption(AutoNames[6], AutoNames[6]);
    //     // Auto_Selector.addOption(AutoNames[7], AutoNames[7]);
    //     SmartDashboard.putData("Auto choices", Auto_Selector);
    // }

    public void doNothingAuto(Driving DriveSub)
    {
        System.out.println("Doing nothing, applies to all starting positions");
    }

    //The below applies to [Team] center starting position
    public void leaveStartingPositionAuto(Driving DriveSub)
    {
        if (DriveSub.getEncoderToInches() < startingToReefCenter) // Assumes 2 inches of movement is necessary to leave starting position/area
        {
            DriveSub.AutoDrive(-1, 0);
            System.out.println("Linear displacement: " + DriveSub.getEncoderToInches());
        }
    }

    //This Auton works but isn't based on any encoder values or something similar
    public void scoreL1Auto(Driving DriveSub, Shooter ShooterSub)
    {
        while (DriveSub.getEncoderToInches() < startingToReefCenter) // Assumes 2 inches of movement is necessary to leave starting position/area
        {
            DriveSub.AutoDrive(-0.5, 0);
            DriveSub.returnDrive().feed();
            System.out.println("Linear displacement: " + DriveSub.getEncoderToInches());
            DriveSub.returnDrive().feed();
        }
        while (ShooterSub.getEncoderToInches() < 0.5)
        {
            ShooterSub.SetSpeed(-0.5);
        }
    }

    // public void scoreL2Auto(Driving DriveSub, Elevator ElevatorSub)
    // {
    //     // while (DriveSub.getEncoderToInches() < startingToReefCenter)
    //     // {
    //     //     DriveSub.AutoDrive(1, 0);
    //     // }
    //     while (-ElevatorSub.getEncoderToInches() > l2PositionBottomed) // 'l2PositionBottomed' begated because negative speed leads to negative encoder readings
    //     {
    //         ElevatorSub.SetSpeed(-0.01);
            
    //         System.out.println(-ElevatorSub.getEncoderToInches());
    //     }
    //     ElevatorSub.SetSpeed(0);
    // }
    
    // public void scoreL2Auto(Driving DriveSub, Elevator ElevatorSub) {
    //     double targetHeight = 6.875; // inches
    //     double gearDiameter = 1.45; // inches
    //     double gearRatio = 27; // 1:27 gear ratio

    //     // Convert inches to encoder rotations
    //     double wheelCircumference = gearDiameter * Math.PI;
    //     double targetRotations = (targetHeight / wheelCircumference) * gearRatio;

    //     //double initialPosition = ElevatorSub.getEncoderToInches2();
    //   //  double ;;; = initialPosition + targetRotations;

    //     // while (ElevatorSub.getEncoderToInches2() < targetPosition) {
    //     //     ElevatorSub.SetSpeed(-0.01); // Negative speed moves up
    //     //     System.out.println("Encoder: " + ElevatorSub.getEncoderToInches2());
    //     // }

    //     ElevatorSub.SetSpeed(0); // Stop motor when target reached
    // }

    // public void scoreL2Auto2(Driving DriveSub, Elevator ElevatorSub, XboxController ArmController)
    // {
    //     while (ElevatorSub.getEncoderRevCount(ArmController) < 4)
    //     { 
    //         ElevatorSub.SetSpeed(-0.5);
    //         System.out.println(ElevatorSub.getEncoderRevCount(ArmController));
            
    //     }
    //     ElevatorSub.SetSpeed(0);
    // }

    // public void removeAlgae(Driving DriveSub, AlgaeArm AlgaeArmSub, AlgaeWheel AlgaeWheelSub, Elevator ElevatorSub)
    // {
    //     while (DriveSub.getEncoderToInches() < startingToReefCenter)
    //     {
    //         DriveSub.AutoDrive(1, 0); // For linear travel
    //     }
    //     while (ElevatorSub.getEncoderToInches() < l2PositionBottomed)
    //     {
    //         ElevatorSub.SetSpeed(1);
    //     }
    //     // while ( )
    //     // {
            
    //     // }
    // }


}
