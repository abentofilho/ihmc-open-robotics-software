package us.ihmc.atlas;

import us.ihmc.darpaRoboticsChallenge.DRCBumpyAndShallowRampsWalkingTest;
import us.ihmc.darpaRoboticsChallenge.drcRobot.DRCRobotModel;

public class AtlasBumpyAndShallowRampsWalkingTest extends DRCBumpyAndShallowRampsWalkingTest
{

   @Override
   public DRCRobotModel getRobotModel()
   {
      return new AtlasRobotModel(AtlasRobotVersion.DRC_NO_HANDS, false, false);
   }

}
