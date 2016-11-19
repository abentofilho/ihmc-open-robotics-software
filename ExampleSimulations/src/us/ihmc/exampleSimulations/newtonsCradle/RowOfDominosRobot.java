package us.ihmc.exampleSimulations.newtonsCradle;

import java.util.Random;

import javax.vecmath.Vector3d;

import us.ihmc.graphics3DDescription.Graphics3DObject;
import us.ihmc.graphics3DDescription.appearance.AppearanceDefinition;
import us.ihmc.graphics3DDescription.appearance.YoAppearance;
import us.ihmc.robotics.dataStructures.variable.DoubleYoVariable;
import us.ihmc.robotics.geometry.RigidBodyTransform;
import us.ihmc.robotics.math.frames.YoFrameVector;
import us.ihmc.robotics.robotDescription.CollisionMeshDescription;
import us.ihmc.simulationconstructionset.FloatingJoint;
import us.ihmc.simulationconstructionset.FunctionToIntegrate;
import us.ihmc.simulationconstructionset.Link;
import us.ihmc.simulationconstructionset.Robot;

public class RowOfDominosRobot extends Robot
{
   public RowOfDominosRobot()
   {
      super("RowOfDominosRobot");

      Random random = new Random(1999L);

      final YoFrameVector yoLinearMomentum = new YoFrameVector("linearMomentum", null, this.getRobotsYoVariableRegistry());
      final DoubleYoVariable potentialEnergy = new DoubleYoVariable("potentialEnergy", this.getRobotsYoVariableRegistry());
      final DoubleYoVariable kineticEnergy = new DoubleYoVariable("kineticEnergy", this.getRobotsYoVariableRegistry());
      final DoubleYoVariable totalEnergy = new DoubleYoVariable("totalEnergy", this.getRobotsYoVariableRegistry());

      int numberOfDominos = 30;

      double dominoWidth = 0.024;
      double dominoDepth = 0.0075;
      double dominoHeight = 0.048;

      double dominoMass = 0.2;
      RigidBodyTransform nextDominoTransform = new RigidBodyTransform();
      nextDominoTransform.setTranslation(new Vector3d(0.0, 0.0, dominoHeight/2.0 + 0.001));

      Vector3d dominoTranslation = new Vector3d(dominoHeight * 0.6, 0.0, 0.0);
      RigidBodyTransform tempTransform = new RigidBodyTransform();
      RigidBodyTransform tempTransform2 = new RigidBodyTransform();

      for (int i = 0; i < numberOfDominos; i++)
      {
         Vector3d offset = new Vector3d(0.0, 0.0, 0.0);
         FloatingJoint floatingJoint = new FloatingJoint("domino" + i, "domino" + i, offset, this);

         Link link = new Link("domino" + i);
         link.setMassAndRadiiOfGyration(dominoMass, dominoDepth/2.0, dominoWidth/2.0, dominoHeight/2.0);
         link.setComOffset(0.0, 0.0, 0.0);

         Graphics3DObject linkGraphics = new Graphics3DObject();
         linkGraphics.translate(0.0, 0.0, -dominoHeight/2.0);
         AppearanceDefinition randomColor = YoAppearance.randomColor(random);
         linkGraphics.addCube(dominoDepth, dominoWidth, dominoHeight, randomColor);
         link.setLinkGraphics(linkGraphics);

         CollisionMeshDescription collisionMeshDescription = new CollisionMeshDescription();
         collisionMeshDescription.addCubeReferencedAtCenter(dominoDepth, dominoWidth, dominoHeight);
         link.setCollisionMesh(collisionMeshDescription);

         floatingJoint.setLink(link);
         this.addRootJoint(floatingJoint);

         floatingJoint.setRotationAndTranslation(nextDominoTransform);

         tempTransform.setIdentity();
         tempTransform.setRotationYawAndZeroTranslation(0.15);
         tempTransform.setTranslation(dominoTranslation);

         tempTransform2.setIdentity();
         tempTransform2.multiply(tempTransform, nextDominoTransform);
         nextDominoTransform.set(tempTransform2);

         if (i==0)
         {
            floatingJoint.setAngularVelocityInBody(new Vector3d(0.0, 10.0, 0.0));
         }
      }

      FunctionToIntegrate functionToIntegrate = new FunctionToIntegrate()
      {
         @Override
         public double[] computeDerivativeVector()
         {
            Vector3d linearMomentum = new Vector3d();
            computeLinearMomentum(linearMomentum);
            kineticEnergy.set(computeTranslationalKineticEnergy());
            potentialEnergy.set(computeGravitationalPotentialEnergy());
            totalEnergy.set(kineticEnergy.getDoubleValue());
            totalEnergy.add(potentialEnergy);
            yoLinearMomentum.set(linearMomentum);

            return null;
         }

         @Override
         public int getVectorSize()
         {
            return 0;
         }

         @Override
         public DoubleYoVariable[] getOutputVariables()
         {
            return null;
         }
      };

      this.addFunctionToIntegrate(functionToIntegrate);
   }

}
