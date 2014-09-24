package us.ihmc.darpaRoboticsChallenge.ros;

import java.util.ArrayList;

import org.ros.message.Time;
import org.ros.time.WallTimeProvider;

import us.ihmc.communication.packets.dataobjects.RobotConfigurationData;
import us.ihmc.darpaRoboticsChallenge.networkProcessor.time.PPSTimestampOffsetProvider;
import us.ihmc.utilities.net.ObjectCommunicator;
import us.ihmc.utilities.net.ObjectConsumer;
import us.ihmc.utilities.ros.RosJointStatePublisher;
import us.ihmc.utilities.ros.RosMainNode;

public class RosRobotJointStatePublisher implements ObjectConsumer<RobotConfigurationData>
{
   private final RosJointStatePublisher jointStatePublisher;
   private final WallTimeProvider wallTime;
   private final ArrayList<String> nameList = new ArrayList<String>();
   private final RosMainNode rosMainNode;
   private final PPSTimestampOffsetProvider ppsTimestampOffsetProvider;

   public RosRobotJointStatePublisher(ObjectCommunicator fieldComputer, final RosMainNode rosMainNode, PPSTimestampOffsetProvider ppsTimestampOffsetProvider,
         String rosNameSpace)
   {
      this.rosMainNode = rosMainNode;
      this.wallTime = new WallTimeProvider();
      this.jointStatePublisher = new RosJointStatePublisher(false);
      this.ppsTimestampOffsetProvider = ppsTimestampOffsetProvider;

      rosMainNode.attachPublisher("/" + rosNameSpace + "/joint_states", jointStatePublisher);
      fieldComputer.attachListener(RobotConfigurationData.class, this);
   }

   @Override
   public void consumeObject(RobotConfigurationData object)
   {
      if (rosMainNode.isStarted())
      {

         long timeStamp = ppsTimestampOffsetProvider.adjustRobotTimeStampToRosClock(object.getSimTime());
         Time t = Time.fromNano(timeStamp);//wallTime.getCurrentTime());

         updateNameList(object);

         jointStatePublisher.publish(nameList, object.getJointAngles(), null, null, t);
      }
   }

   private void updateNameList(RobotConfigurationData object)
   {
      String[] jointNames = object.getJointNames();
      for (int i = 0; i < jointNames.length; i++)
      {
         if (i >= nameList.size())
            nameList.add(jointNames[i]);
         else
            nameList.set(i, jointNames[i]);
      }
      
      // Shrink the list to the size of jointNames which shouldn't change
      for (int i = jointNames.length; i > nameList.size(); i--)
      {
         nameList.remove(i);
      }
   }
}
