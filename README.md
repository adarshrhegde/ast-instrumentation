**Instrumentation of Apache Commons Lang**

Applications and their repositories:
1. Instrumenting Application(ast-instrumentation) - Adarsh_Hegde_hw2
2. Instrumented Application (Apache Commons Lang) - Instrumentated_App_Hw2

Read access to both repositories is provided to graders

The application ast-instrumentation is an instrumenting app that takes the source code of another app as input and creates an AST using eclipse JDT.
The AST is then modified to add instrumentation code. 

How to run:
Once the instrumenting application is setup in the IDE open Main.java.

The main class consists of the following statement. Make changes to source_dir(source code directory) and backup_dir(Backup directory) in the statement.
InstrumentationParser instrumentationParser = new InstrumentationParser(source_dir, backup_dir);


Reference : https://github.com/apache/commons-lang

Apache Commons Lang is a library containing Java utility classes that provide helper utilities for the java.lang API.

**Implementation of Junit test cases details:**

1. Test cases have been written for StringUtils.java as part of StringUtilsTest.java. Since the project already contained test cases I have added new methods in StringUtils.java and added corresponding test cases into StringUtilsTest.java.
											
	isPalindrome(CharSequence) ----> testIsPalindrome()
	
	getCountVowelsInString(CharSequence) -----> testGetCountVowelsInString()
	
	hasWhitespace(CharSequence)	----> testHasWhitespace()
	
	
**Implementation of build scripts:**

1. Maven : Was already present in the code.

2. Gradle : The build.gradle and settings.gradle files in the root directory are used
for running gradle tasks on the project.

   **Performing Tests:**
   
        gradle clean
        
        gradle test
	
   **Compiling classes:**
   
        gradle assemble
    
   **Creating build:**
    
        gradle build
      

3. SBT : The build.sbt file in the root directory is used to perform SBT build on the project.

    **Performing Tests:**
       
            sbt clean
            
            sbt test
    	
    **Compiling classes:**
       
            sbt compile
        
    **Creating build:**
        
            sbt package
        
4. **Running the project using jar file:**

    Once the jar file is ready after using the above build tools we will run the utility.
    Since this is a utility containing helper methods we will call them from a driver program. 
    For this we will create a new Java Project and create the following drive program in it. 
    Also we will include the Apache Commons Lang jar in the new Java project.
    
        import org.apache.commons.lang3.StringUtils;
        public class ApacheCommonsDriver {
        
            public static void main(String[] args) throws InterruptedException {
        
                for(int i =0; i< 20;i++) {
                    System.out.println("This is iteration "+i);
                    Thread.sleep(1000);
                }
        
                System.out.println(StringUtils.isPalindrome("stats"));
                System.out.println(StringUtils.hasWhitespace("OOLE  "));
                System.out.println(StringUtils.getCountVowelsInString("Adarsh"));
            }
        }


    If we see the above class I have added code to delay the termination of the program. 
    This is done to be able to monitor the app properly which we will see in the following section.
    In the above class ApacheCommonsDriver.java I have called the utility methods that I had created in the project.
    The above class can be executed directly.
    
5. Monitoring Tools:

    a. Jconsole: Jconsole is used to provide performance and resource consumption statistics of applications.
       While the above java class is executing obtain the java process id from Task Manager. Now, execute the following command to launch Jconsole statistics.
       
            jconsole <process_id>
    
       Snapshot : img2/jconsole.png . The image shows the CPU usage, memory usage, 
       number of threads created and number of classes loaded by the Java process.
    
    b. Java Mission Control: This is used for monitoring, profiling and troubleshooting Java applications.
       Use the following command in the jdk bin directory to run Java Mission Control.
       
               jmc
               
       Now run the driver program. The process id will be detected in the JMC. Right click on it and select Start JMX console.
       This will bring up the monitoring information including heap memory usage and CPU usage.
    
        Snapshot : img2/jmc.png
        
    c. jcmd: Sends commands to the JVM inorder to diagnose JVM and the application. Run the following command during execution of the app.
    
            jcmd <process_id> Thread.print
            
        The above statement prints all the active threads.
        Snapshot : img2/jcmd.png
    
    d. jmap: Is used to print JVM memory related statistics. Run the following command during execution of the app.
        
            jmap -J-d64 -heap <process_id>
            
        The above command prints heap related statistics like capacity, available space, used space, etc.
        Snapshot : img2/jmap.png
    
    e. jstack: The jstack utility attaches to the process of the Java app and prints the stack traces associated with the threads in the JVM.
    
            jstack -J-d64 -m <process_id>
            
        The above statement prints deadlock information if any and the stack traces associated with any thread along with the thread information.
        Snapshot : img2/jstack.png
        
    f. jps: Prints the list of all the JVMs on the system.
    
            jps
            
        Snapshot: img2/jps.png
        
    g. Java Visual VM: The java visual vm is used to monitor java application performance.
    
            jvisualvm
            
        On running the above command we get the interface for monitoring. Once we run the app it can be seen on the interface under the Local applications.
        The monitoring information for the app shows memory usage statistics, garbage collection statistics, and memory and CPU usage information.
        Snapshot: img2\visualvm.png
        
    h. jstat: This utility is used to diagnose performance issues related to heap sizing and garbage collection.
    
            jstat -gcutil <process_id> 450 5
            
        The above command shows garbage collection statistics within time interval.
        Snapshot: img2/jstat.png
        
    i. jstatd daemon: This tool is a Remote Method Invocation (RMI) server application that monitors the creation and termination of instrumented 
         Java Virtual Machines and provides an interface to allow remote monitoring tools to attach to VMs running on the local host. 
         
            jstatd -J-Djava.security.policy=all.policy
            
        While running the program for my local app I got an error saying "Remote object cannot be created". This is because the tool is used to specifically monitor remote applications.
        Snapshot: img2/jstatd.png    
        
    j. visualgc utility: This utility provides a graphical view of the garbage collection system. As with jstat, it uses the built-in instrumentation of Java HotSpot VM. 
        It shows the same garbage collection information as visualvm.
    
            
            
         
     
    
    


