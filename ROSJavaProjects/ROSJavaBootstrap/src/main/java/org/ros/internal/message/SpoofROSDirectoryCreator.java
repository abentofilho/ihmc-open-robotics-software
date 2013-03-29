package org.ros.internal.message;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.ros.EnvironmentVariables;

import com.google.common.collect.Lists;

public class SpoofROSDirectoryCreator
{

   private static final int DEPTH = 3;
   private static final boolean DEBUG = true;

   public SpoofROSDirectoryCreator()
   {
   }

   public void generate(File outputDirectory, Collection<String> packages, Collection<File> packagePath) throws IOException
   {
      String outputDirectoryStart = outputDirectory.getAbsolutePath();
      Collection<File> returnedFiles = new ArrayList<File>();
      for (File file : packagePath)
      {
         returnedFiles.clear();
         if (file.isDirectory())
            returnedFiles.addAll(recursivelyFindFiles(DEPTH, file));
         String packagePathDirectoryregex = "^" + file.getAbsolutePath();
         for (File returnedFile : returnedFiles)
         {
            String newFileName = returnedFile.toString().replaceFirst(packagePathDirectoryregex, outputDirectoryStart);
            FileUtils.copyFile(returnedFile, new File(newFileName));
         }
      }
   }

   public static Collection<File> recursivelyFindFiles(int depth, File startDirectory)
   {
      if (DEBUG)
         System.out.println("SpoofROSDirectoryCreator: depth" + depth + " Directory " + startDirectory.getAbsolutePath());
      Collection<File> returnedFiles = new ArrayList<File>();
      for (File file : startDirectory.listFiles())
      {
         if (file.isDirectory() && depth > 0)
         {
            returnedFiles.addAll(recursivelyFindFiles(depth - 1, file));
         }
         if (file.getPath().endsWith(".msg"))
         {
            returnedFiles.add(file);
         }
         if (file.getPath().endsWith(".srv"))
         {
            returnedFiles.add(file);
         }
         if (file.getPath().endsWith("manifest.xml"))
         {
            returnedFiles.add(file);
         }
         if (file.getPath().endsWith("stack.xml"))
         {
            returnedFiles.add(file);
         }
      }
      return returnedFiles;
   }

   public static void main(String[] args) throws IOException
   {
      List<String> arguments = Lists.newArrayList(args);
      if (arguments.size()<1)
         throw new RuntimeException("Failure to supply appropriate output directory argument");
      File outputDirectory = new File(arguments.remove(0));
      String rosPackagePath = System.getenv(EnvironmentVariables.ROS_PACKAGE_PATH);
      Collection<File> packagePath = Lists.newArrayList();
      for (String path : rosPackagePath.split(File.pathSeparator))
      {
         File packageDirectory = new File(path);
         if (packageDirectory.exists())
         {
            packagePath.add(packageDirectory);
         }
      }
      SpoofROSDirectoryCreator spoofROSDirectoryCreator = new SpoofROSDirectoryCreator();
      spoofROSDirectoryCreator.generate(outputDirectory, arguments, packagePath);
   }

}
