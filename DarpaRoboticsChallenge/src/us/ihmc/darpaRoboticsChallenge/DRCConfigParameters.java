package us.ihmc.darpaRoboticsChallenge;

import us.ihmc.darpaRoboticsChallenge.drcRobot.DRCRobotParameters;
import us.ihmc.darpaRoboticsChallenge.sensors.DRCLIDARDataStore.DRCLidarDataStoreType;
import us.ihmc.graphics3DAdapter.camera.VideoSettings;
import us.ihmc.graphics3DAdapter.camera.VideoSettings.Quality;
import us.ihmc.graphics3DAdapter.camera.VideoSettingsH264LowLatency;

public class DRCConfigParameters
{
   public static final boolean USE_GAZEBO_PHYSICS = false;
   public static final String[] JOINTS_TO_IGNORE_FOR_GAZEBO = {"hokuyo_joint"};
   public static final boolean SHOW_SCS_GUI_FOR_GAZEBO = true;

   public static final boolean SHOW_BANDWIDTH_DIALOG = false;

   public static final double ESTIMATE_DT = 0.001;


   // Set whether or not to use GFE Robot Model
   public static final boolean USE_GFE_ROBOT_MODEL = true;

   // Convenience field
   public static final boolean USE_R2_ROBOT_MODEL = !USE_GFE_ROBOT_MODEL;


   // Networking
   public static final String GAZEBO_HOST = "localhost";    // "10.4.42.2";    // "localhost";
   public static final String ROS_MASTER_URI = "http://" + GAZEBO_HOST + ":11311";
   public static final String SCS_MACHINE_IP_ADDRESS = "localhost";    // "10.100.0.37";
   public static final int CONTROLLER_TO_UI_TCP_PORT = 4893;
   public static final int CONTROLLER_TO_UI_UDP_PORT = CONTROLLER_TO_UI_TCP_PORT + 1;

   public static final int NETWORK_PROCESSOR_TO_CONTROLLER_TCP_PORT = 4895;
   public static final int NETWORK_PROCESSOR_TO_CONTROLLER_UDP_PORT = NETWORK_PROCESSOR_TO_CONTROLLER_TCP_PORT + 1;

   public static final int NETWORK_PROCESSOR_TO_UI_TCP_PORT = 4897;
   public static final int NETWORK_PROCESSOR_TO_UI_UDP_PORT = NETWORK_PROCESSOR_TO_UI_TCP_PORT + 1;

   public static final String OPERATOR_INTERFACE_IP_ADDRESS = "localhost";    // "10.4.8.1";

   public static final long ROBOT_JOINT_SERVER_UPDATE_MILLIS = 100;

   // Video Settings
   public static final boolean STREAM_VIDEO = true;
   public static final VideoSettings VIDEOSETTINGS = new VideoSettingsH264LowLatency(800, 600, Quality.MEDIUM);

   // public static final VideoSettings VIDEOSETTINGS = new VideoSettingsH264LowLatency(200, 150, Quality.LOW);

   // State Estimator
   public static final boolean USE_STATE_ESTIMATOR = true;
   public static final boolean INTRODUCE_FILTERED_GAUSSIAN_POSITIONING_ERROR = false;
   public static final double NOISE_FILTER_ALPHA = 1e-1;
   public static final double POSITION_NOISE_STD = 0.01;
   public static final double QUATERNION_NOISE_STD = 0.01;


   // LIDAR:
   public static final double LIDAR_SPINDLE_VELOCITY = 3.25;
   
   public static final boolean STREAM_POLAR_LIDAR = true;
   static final int LIDAR_UPDATE_RATE_OVERRIDE = 3; 
   static final int LIDAR_SWEEPS_PER_SCAN = 6;    // 1
   static final int LIDAR_POINTS_PER_SWEEP = 70;    // 640
   static final boolean OVERRIDE_DRC_LIDAR_CONFIG = true;
   public static final float LIDAR_MIN_DISTANCE = 0.2f;
   public static final float LIDAR_MAX_DISTANCE = 10.0f;
   public static final float LIDAR_SWEEP_MAX_YAW = 0.8f;
   public static final float LIDAR_SWEEP_MIN_YAW = -0.8f;
   public static final float LIDAR_SCAN_MAX_ROLL = 0.4f;    // rolling the lidar to simulated a faster update rate
   public static final float LDIAR_SCAN_MIN_ROLL = -0.3f;
   public static final float LIDAR_ANGLE_INCREMENT = (float) Math.toRadians(0.25);
   public static final float LIDAR_TIME_INCREMENT = 0.0f;
   public static final float LIDAR_SCAN_TIME = 0.0f;
   public static final double LIDAR_NOISE_LEVEL_OVERRIDE = 0.005;    // DRCGazebo will simulate with: 0.005
   public static final boolean DEBUG_GAZEBO_LIDAR = false;

   // LIDAR Processor
   public static final DRCLidarDataStoreType DRCLIDAR_DATA_STORE_TYPE = DRCLidarDataStoreType.GROUND_ONLY_QUAD_TREE_AND_OCTREE;
   public static final double GRID_RESOLUTION = 0.005;    // in meters
   public static final double OCTREE_RESOLUTION = 0.05;
   public static final float QUADTREE_HEIGHT_THRESHOLD = 0.005f;
   public static final double LIDAR_BLINDNESS_CYLINDAR_SQUARED_RADIUS = 0.1;
   public static final boolean HIDE_THINGS_ABOVE_HEAD_FROM_LIDAR = true;

   // Footstep Generator
   public static final double BOUNDING_BOX_FOR_FOOTSTEP_HEIGHT_FINDING_SIDE_LENGTH =
      (1 + 0.3) * 2
      * Math.sqrt(DRCRobotParameters.DRC_ROBOT_FOOT_FORWARD * DRCRobotParameters.DRC_ROBOT_FOOT_FORWARD
                  + 0.25 * DRCRobotParameters.DRC_ROBOT_FOOT_WIDTH * DRCRobotParameters.DRC_ROBOT_FOOT_WIDTH);




}
