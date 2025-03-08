package frc.robot.Subroutines;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import com.revrobotics.spark.config.SparkFlexConfig;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;

import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

// import edu.wpi.first.wpilibj.TimedRobot;
// import edu.wpi.first.wpilibj.XboxController;
// import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



public class SparkConfigure {

    public static void Drive_Initialize(SparkMax m_LeaderLeft, SparkMax m_LeaderRight, SparkMax m_FollowerLeft, SparkMax m_FollowerRight)
    {
    SparkMaxConfig globalLeaderConfig = new SparkMaxConfig();
    SparkMaxConfig rightLeaderConfig = new SparkMaxConfig();
    SparkMaxConfig leftFollowerConfig = new SparkMaxConfig();
    SparkMaxConfig rightFollowerConfig = new SparkMaxConfig();
        globalLeaderConfig
            .smartCurrentLimit(50)
            .idleMode(IdleMode.kBrake);

        // Apply the global config and invert since it is on the opposite side
        rightLeaderConfig
            .apply(globalLeaderConfig)
            .inverted(true);

        // Apply the global config and set the leader SPARK for follower mode
        leftFollowerConfig
            .apply(globalLeaderConfig)
            .follow(m_LeaderLeft)
            .inverted(true);

        // Apply the global config and set the leader SPARK for follower mode
        rightFollowerConfig
            .apply(globalLeaderConfig)
            .follow(m_LeaderRight)
            .inverted(true);

        m_LeaderLeft.configure(globalLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_FollowerLeft.configure(leftFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_LeaderRight.configure(rightLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_FollowerRight.configure(rightFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
    public static void winchInit(SparkMax m_winchLeft,SparkMax m_winchRight) {
        SparkMaxConfig globalWinchLeaderConfig = new SparkMaxConfig();
        SparkMaxConfig rightFollowerWinchConfig = new SparkMaxConfig();
            globalWinchLeaderConfig
                .smartCurrentLimit(50)
                .idleMode(IdleMode.kBrake);

            rightFollowerWinchConfig
                .apply(globalWinchLeaderConfig)
                .follow(m_winchLeft);

        m_winchLeft.configure(globalWinchLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_winchRight.configure(rightFollowerWinchConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    public static void pickupInit(SparkMax m_pickupLeft, SparkMax m_pickupRight)
    {
        SparkMaxConfig globalPickupLeaderConfig = new SparkMaxConfig();
        SparkMaxConfig rightFollowerPickupConfig = new SparkMaxConfig();
            globalPickupLeaderConfig
                .smartCurrentLimit(50)
                .idleMode(IdleMode.kBrake);

            rightFollowerPickupConfig
                .apply(globalPickupLeaderConfig)
                .follow(m_pickupLeft);

        m_pickupLeft.configure(globalPickupLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_pickupRight.configure(rightFollowerPickupConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
    public static void ElevatorInit(SparkMax m_elevatorLeft, SparkMax m_elevatorRight)//SparkFlex m_elevatorRight
    {
        SparkFlexConfig globalElevatorLeaderConfig = new SparkFlexConfig();
        SparkFlexConfig rightFollowerElevatorConfig = new SparkFlexConfig();

        // SparkFlexConfig rightFollowerShooterConfig = new SparkFlexConfig();
        globalElevatorLeaderConfig
                .smartCurrentLimit(50)
                .idleMode(IdleMode.kBrake);

        rightFollowerElevatorConfig
                .smartCurrentLimit(50)
                .idleMode(IdleMode.kBrake)
                .inverted(true);

                m_elevatorLeft.configure(globalElevatorLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
                m_elevatorRight.configure(rightFollowerElevatorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

    
    // public static void ElevatorInit(SparkFlex m_elevatorLeft)
    // {
    //     SparkFlexConfig globalElevatorLeaderConfig = new SparkFlexConfig();
    //     // SparkFlexConfig rightFollowerElevatorConfig = new SparkFlexConfig();
    //         globalElevatorLeaderConfig
    //             .smartCurrentLimit(50)
    //             .idleMode(IdleMode.kBrake)
    //             .inverted(true);

                

    //     m_elevatorLeft.configure(globalElevatorLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
    // }

    public static void AlgaeWheelInit(SparkMax m_wheel, SparkMax apple)
    {
        SparkFlexConfig globalAlgaeWheelLeaderConfig = new SparkFlexConfig();
        globalAlgaeWheelLeaderConfig
                .smartCurrentLimit(50)
                .idleMode(IdleMode.kBrake)
                .inverted(true);

        SparkFlexConfig globalAlgaeArmLeaderConfig = new SparkFlexConfig();
        globalAlgaeWheelLeaderConfig
                        .smartCurrentLimit(50)
                        .idleMode(IdleMode.kBrake)
                        .inverted(true);  
              
        
            m_wheel.configure(globalAlgaeWheelLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
            apple.configure(globalAlgaeArmLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    
    }

    public static void AlgaeWheelInit(SparkMax m_wheel)
    {
        SparkFlexConfig globalAlgaeWheelLeaderConfig = new SparkFlexConfig();
        globalAlgaeWheelLeaderConfig
                .smartCurrentLimit(50)
                .idleMode(IdleMode.kBrake)
                .inverted(false);
              
        
            m_wheel.configure(globalAlgaeWheelLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
            
    
    }

    public static void AlgaeArmInit(SparkFlex m_wheel)
    {
        SparkFlexConfig globalAlgaeArmLeaderConfig = new SparkFlexConfig();
        globalAlgaeArmLeaderConfig
                .smartCurrentLimit(50)
                .idleMode(IdleMode.kBrake)
                .inverted(true);

              
        
            m_wheel.configure(globalAlgaeArmLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
            
    
    }

    public static void ShooterInit(SparkMax m_shooterLeft, SparkMax m_shooterRight)
    {
        SparkMaxConfig globalShooterLeaderConfig = new SparkMaxConfig();
        SparkMaxConfig rightFollowerShooterConfig = new SparkMaxConfig();
        globalShooterLeaderConfig
                .smartCurrentLimit(50)
                .idleMode(IdleMode.kBrake);

        rightFollowerShooterConfig
                .apply(globalShooterLeaderConfig)
                .inverted(true);

                m_shooterLeft.configure(globalShooterLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
                m_shooterRight.configure(rightFollowerShooterConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }

}
