// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import javax.lang.model.util.ElementScanner14;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Subroutines.Autonomous;
import frc.robot.Subroutines.Camera;
import frc.robot.Subroutines.Driving;
import frc.robot.Subroutines.Shooter;
import frc.robot.Subroutines.Elevator;
import frc.robot.Subroutines.AlgaeArm;
import frc.robot.Subroutines.AlgaeWheel;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends TimedRobot {
  public static double kDefaultSpeed = 1.0;

  XboxController DriverController = new XboxController(0); //Assigns a new Xbox controller to port 0
  XboxController ArmController = new XboxController(1); //Assigns a new Xbox controller to port 0

  Camera CamSub = new Camera(); //Allows us to call the Camera Subroutines in the Camera.Java class
  Driving DriveSub = new Driving(); //Allows us to call the Driving Subroutines in the Driving.Java class
  Shooter ShooterSub = new Shooter(); //Allows us to call the Shooter Subroutines in the Shooter.Java class
  Autonomous AutoSub = new Autonomous(); //Allows us to call the Autonomous Subroutines in the Autonomous.Java class
  Timer TimerSub = new Timer(); //Allows for timers
  Elevator ElevatorSub = new Elevator(); // Allows us to call the Elevator subroutine
  AlgaeArm AlgaeArmSub = new AlgaeArm();
  AlgaeWheel AlgaeWheelSub = new AlgaeWheel();


  boolean AutoFinished = false;
  private static final String A_Do_Nothing = "Do Nothing Auto";
  private static final String A_LeaveStartingPosition = "Leave Starting Position Auto";
  private static final String A_scoreL1 = "Score L1 Auto";
  String A_Selected = A_Do_Nothing;

  String[] AutoNames = {A_Do_Nothing, A_LeaveStartingPosition, A_scoreL1, ""};
  SendableChooser<String> Auto_Selector = new SendableChooser<>();

  public void Initial(SendableChooser<String> Auto_Selector, String[] AutoNames){
    Auto_Selector.setDefaultOption(AutoNames[0], AutoNames[0]);
    Auto_Selector.addOption(AutoNames[1], AutoNames[1]);
    Auto_Selector.addOption(AutoNames[2], AutoNames[2]);
    // Auto_Selector.addOption(AutoNames[3], AutoNames[3]);
    // Auto_Selector.addOption(AutoNames[4], AutoNames[4]);
    // Auto_Selector.addOption(AutoNames[5], AutoNames[5]);
    // Auto_Selector.addOption(AutoNames[6], AutoNames[6]);
    // Auto_Selector.addOption(AutoNames[7], AutoNames[7]);
    SmartDashboard.putData("Auto choices", Auto_Selector);
  }



  @Override
  public void robotInit() { //This code runs when the robot powers on
    CamSub.Initial();//starts the camera streams from the roboRio
    // AutoSub.Initial(Auto_Selector, AutoNames);
    DriveSub.Initial(); //inverts the rear right motor and other setup for the driving controls
    // ArmSub.Initial(); //inverts the left arm motor and other setup for the arm controls
    ShooterSub.Initial();
    // PickupSub.Initial();
    // WinchSub.Initial();
    ElevatorSub.Initial();

    Auto_Selector.setDefaultOption("Default Auto", A_scoreL1);
    Auto_Selector.addOption("Leave Starting Position", A_LeaveStartingPosition);
    Auto_Selector.addOption("Score L1", A_scoreL1);
    SmartDashboard.putData("Auto Selector", Auto_Selector);

  //TERRY TEST/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ElevatorSub.setZeroPosition();
  //TERRY TEST/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    AlgaeArmSub.Initial();

    AlgaeWheelSub.Initial();
    SmartDashboard.putString("Auto Command", "RobotInit");
    SmartDashboard.putNumber("Drive Speed %", kDefaultSpeed);
    
  }
  @Override
  public void robotPeriodic(){
    SmartDashboard.putNumber("Wheel Encoder",DriveSub.getEncoder());
  }

  double startTime;

  @Override
  public void autonomousInit(){
    // //This code starts the autonomous program. 
    // //At the beginning of every autonomous, the winch and arm will move to zero on their limit switches.
    // AutonomousSub.Initial(Auto_Selector, AutoNames);  
    TimerSub.reset();
    TimerSub.start();

    // A_Selected = Auto_Selector.getSelected();
    System.out.println("Auto Selected: " + A_Selected);

    

    // SmartDashboard.putNumber("Timer", TimerSub.get());
    // SmartDashboard.putNumber("Timer", TimerSub.get());
    // // stopAll();
    // // getSelected1();
    // // RunAuto();
    // AutoFinished = true;

  }

  double startingToReefCenter = 88 - 30 - 2.9 + 0.9;

  @Override
  public void autonomousPeriodic(){
     // TimerSub.getFPGATimestamp();

      double time = Timer.getFPGATimestamp();
      
    SmartDashboard.putNumber("Timer", TimerSub.get());
    while (DriveSub.getEncoderToInches() < startingToReefCenter && time < 15) // Assumes 2 inches of movement is necessary to leave starting position/area
    {
        DriveSub.AutoDrive(-0.5, 0);
        DriveSub.returnDrive().feed();
        System.out.println("Linear displacement: " + DriveSub.getEncoderToInches());
        // DriveSub.returnDrive().feed();
    }
    while (ShooterSub.getEncoderToInches() < 0.5 && time < 15)
    {
        ShooterSub.SetSpeed(-0.5);
    }    
    // RunAuto();
    // double time = Timer.getFPGATimestamp();
    // if (time - startTime > 0 && time - startTime < 1)
    // {
    //   DriveSub.ManualDrive(1,0, true);
    // }
    stopAll();
  }
 
  @Override
  public void teleopInit(){
    // AutoFinished = true;
    SmartDashboard.putString("Auto Command", "AutoReset");
    stopAll();
  
  }

  // public void macro(){ // D Pad up L2
  //   boolean reached = false;
  //       while (ElevatorSub.getEncoderRevCount(ArmController) < 0.92) {
  //           ElevatorSub.SetSpeed(-0.1); // Negative speed moves up
  //           if (reached)
  //           {
  //             break;
  //           }
  //     }
  // }

  // public void macro1() // D
  // {
  //   boolean reached = false;
  //   while (ElevatorSub.getEncoderRevCount(ArmController) < 1.82)
  //   {
  //     ElevatorSub.SetSpeed(-1); // Negative speed moves up
  //     if (reached)
  //     {
  //       break;
  //     }
  //   }
  // }

  @Override
  public void teleopPeriodic() {
    //Manual Drive Code: Controlled with Left Y and Right X thumb sticks on the Driver Controller
    DriveSub.ManualDrive(DriverController.getLeftY() * 0.8, DriverController.getRightX() * 1,true);
    SmartDashboard.putNumber("Elevator encoder", ElevatorSub.getAbsoluteAngle());
    SmartDashboard.putNumber("Elevator Encoder DutyCycle",ElevatorSub.getElevatorEncoderValue());
    SmartDashboard.putNumber("Elevator Encoder Revolution Count", ElevatorSub.getEncoderRevCount(ArmController));
    
    // if (ArmController.getXButtonPressed())
    // {
    //   macro();
    // }
    // if (ArmController.getPOV() == 0)
    // {
    //   macro();
    // }
    
    
    //Winch Code: Controlled with the Right Y thumb stick on the Arm Controller
    // if(Math.abs(ArmController.getRightY())>0.25)
    // {
    //   WinchSub.SetSpeed(-ArmController.getRightY(), ArmController.getYButton());
    // } else
    // {
    //   WinchSub.SetSpeed(0.00, ArmController.getYButton());
    // }
    

    //Manual Arm Code: Controlled by Left Y on the Arm Controller
    //Minimum speed must always be 0.05 to prevent arm from dropping
    // if(Math.abs(ArmController.getLeftY())>0.05){ArmSub.SetSpeed(ArmController.getLeftY());}
    // else{ArmSub.SetSpeed(0.05);}
    
    //Manual Pickup Code: Controlled by Left and Right Triggers on the Arm Controller
    // PickupSub.SetSpeedTeleOp(ArmController.getLeftTriggerAxis() - ArmController.getRightTriggerAxis());

    //Manual Shooter Code: Controlled by B Button on the Arm Controller
    // ShooterSub.SetSpeed(1, ArmController.getBButton());
    
    // //Preset Shooter Code: Controlled by A Button on the Arm Controller
    // if(ArmController.getAButtonPressed()){
    //   AutoSub.Preset_Shoot(ShooterSub, PickupSub);
    // }
    
    //Assign Shoot & Amp arm positions: Controlled by the Start and Back button on the Arm Controller
    // ArmSub.setShootPosition(ArmController.getStartButtonPressed());
    // ArmSub.setAmpPosition(ArmController.getBackButtonPressed());

    // Elevator Code, controls level positioning
    // if (ArmController.getLeftTriggerAxis() > 0.25)
    // {
    //   ElevatorSub.StageUp(1);
    // } else if (ArmController.getRightTriggerAxis() > 0.25)
    // {
    //   ElevatorSub.StageDown(1);
    // }
    // if (ArmController.getLeftY() > 0.25)
    // {
    //   ElevatorSub.SetSpeed(kDefaultSpeed);
    // } else if(ArmController.getLeftY() < -0.25)
    // {
    //   ElevatorSub.SetSpeed(-kDefaultSpeed);
    // }

    ShooterSub.SetSpeed(0);
    if (ArmController.getLeftTriggerAxis() > 0.25)
    {
      ShooterSub.SetSpeed(ArmController.getLeftTriggerAxis());
    } else if(ArmController.getRightTriggerAxis() > 0.25)
    {
      ShooterSub.SetSpeed(-ArmController.getRightTriggerAxis());
    }

 
      ElevatorSub.SetSpeed(ArmController.getRightY());
      AlgaeArmSub.SetSpeed(-ArmController.getLeftY());
      



    /*
    //Test Individual Motor Code: Controlled by Y +button on the Driver Controller
    if (DriverController.getYButton() ==  true){
      TestMotor(0); //Change TestMotor(CAN_ID) to desired motor Range: 1 to 13
    }
    */




    // // Algae Arm Control
    

 
    
  
    // if (ArmController.getRightY() > 0.35)
    // {
    //   AlgaeArmSub.SetSpeed(0.15);
    // } else if (ArmController.getRightY() < -0.35)
    // {
    //   AlgaeArmSub.SetSpeed(-0.15);
    // }
  

    //Algae Wheels Control
    AlgaeWheelSub.SetSpeed(0);
      if (ArmController.getAButton())
      {
        AlgaeWheelSub.SetSpeed(1);
      } else if (ArmController.getBButton())
    {
      AlgaeWheelSub.SetSpeed(-1);
    }
//Macro Testing
  //   if(ArmController.getXButton()){
  //   ElevatorSub.macro();
  // }
    //ElevatorSub.SetSpeed(0);

    
    // if (ArmController.getAButtonPressed())
    // {
    //   AlgaeWheelSub.SetSpeed(1);
    // } else if (ArmController.getBButtonPressed())
    // {
    //   AlgaeWheelSub.SetSpeed(-1);
    // }

    // trash code
    // if (ArmController.getLeftBumper())
    // {
    //   while (ArmController.getLeftBumper())
    //   {
    //     AlgaeWheelSub.SetSpeed(1);
    //   }
    // } else if (ArmController.getRightBumper())
    // {
    //   while (ArmController.getRightBumper())
    //   {
    //     AlgaeWheelSub.SetSpeed(-1);
    //   }
    // }

    // TRiggers
    
    // ShooterSub.SetSpeed(0);
    // if (ArmController.getLeftTriggerAxis() > 0.25)
    // {
    //   ShooterSub.SetSpeed(ArmController.getLeftTriggerAxis());
    // } else if (ArmController.getRightTriggerAxis() > 0.25)
    // {
    //   ShooterSub.SetSpeed(-ArmController.getRightTriggerAxis());
    // }
    
  

  }

double targetHeight = 6.875;
    // public void testingMacro(){
    //     while (ElevatorSub.getPositionInInches() < targetHeight) {
    //         System.out.println(ElevatorSub.getPositionInInches());
    //         ElevatorSub.SetSpeed(-0.01); // Negative speed moves up
    //         System.out.println("Encoder: " + ElevatorSub.getEncoderToInches2());
    //     }
    // }
    
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // public void RunAuto(){
  //   if (AutoFinished == false){
  //       switch (A_Selected) {
  //         //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  //         case A_Do_Nothing:
  //           AutoSub.doNothingAuto(DriveSub);
          
  //           break;

  //         case A_LeaveStartingPosition:
  //           AutoSub.leaveStartingPositionAuto(DriveSub);
  //           break;

  //         case A_scoreL1:
  //           AutoSub.scoreL1Auto(DriveSub, ShooterSub, time);
  //         SmartDashboard.putString("Auto Command", "Auto Finished");;

  //         default:
  //         break;
  //       }
  //   }
  // }

  // public void getSelected1(){
  //   A_Selected = Auto_Selector.getSelected();
  //   // SmartDashboard.putString("Auto Selected", A_Selected);
  // }

  public void stopAll(){
    DriveSub.ManualDrive(0, 0, true);
    ElevatorSub.SetSpeed(0);
    // PickupSub.SetSpeed(0);
    ShooterSub.SetSpeed(0);

  }


}