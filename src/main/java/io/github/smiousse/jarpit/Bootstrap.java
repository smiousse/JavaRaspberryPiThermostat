package io.github.smiousse.jarpit;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import io.github.smiousse.jarpit.services.MasterControllerJob;
import io.github.smiousse.jarpit.utils.ApplicationPropertyManager;

/**
 * @author smiousse
 *
 */
public class Bootstrap {

    public static String APP_HOME = "../";
    private static Scheduler scheduler;

    public static void main(String[] args) {

        intEnvProperties();

        startQuartz();

    }

    private static void startQuartz() {
        try {
            // Grab the Scheduler instance from the Factory
            scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            JobDetail job = JobBuilder.newJob(MasterControllerJob.class).withIdentity("job1", "group1").build();

            // Trigger the job to run now, and then repeat every 40 seconds
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(30).repeatForever()).build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);

            Runtime.getRuntime().addShutdownHook(new Thread() {

                @Override
                public void run() {
                    super.run();
                    try {
                        scheduler.shutdown(true);
                    }
                    catch (SchedulerException se) {
                        se.printStackTrace();
                    }
                }
            });

        }
        catch (SchedulerException se) {
            se.printStackTrace();
        }
    }

    /**
     * 
     */
    private static void intEnvProperties() {

        try {
            if (System.getProperty(ApplicationPropertyManager.ENV_HOME_LOCATION) == null
                    || System.getProperty(ApplicationPropertyManager.ENV_HOME_LOCATION).isEmpty()) {
                System.setProperty(ApplicationPropertyManager.ENV_HOME_LOCATION, Bootstrap.class.getResource("../../../../").getFile());
            }
            System.setProperty(ApplicationPropertyManager.ENV_PROPERTY_FILE_LOCATION,
                    System.getProperty(ApplicationPropertyManager.ENV_HOME_LOCATION) + "application.properties");

            System.out.println("ENV_HOME_LOCATION = " + System.getProperty(ApplicationPropertyManager.ENV_HOME_LOCATION));
            System.out.println("ENV_PROPERTY_FILE_LOCATION = " + System.getProperty(ApplicationPropertyManager.ENV_PROPERTY_FILE_LOCATION));

            ApplicationPropertyManager.loadApplicationProperties();
        }
        catch (Exception e) {
            // TSLT: handle exception
        }
    }

}
