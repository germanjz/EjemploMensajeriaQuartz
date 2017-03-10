package mx.com.ejemplo.quartz.firejob;

import java.util.concurrent.CountDownLatch;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import mx.com.ejemplo.quartz.firejob.latch.ILatch;
import mx.com.ejemplo.quartz.job.MyJob;

public class LanzarJob implements ILatch {
	private int conteo = 3;
	private CountDownLatch latch = new CountDownLatch(conteo + 1);

	public void fireJob() throws SchedulerException, InterruptedException {
		SchedulerFactory scFact = new StdSchedulerFactory();

		Scheduler scheduler = scFact.getScheduler();
		scheduler.start();

		JobBuilder jobBuilder = JobBuilder.newJob(MyJob.class);

		JobDataMap dataMap = new JobDataMap();
		dataMap.put("latch", this);

		JobDetail jobDetail = jobBuilder.usingJobData("ejemplo", 
				"mx.com.ejemplo.quartz.firejob.LanzarJob")
				.usingJobData(dataMap)
				.withIdentity("myJob", "grupo1")
				.build();
		
		Trigger trigger = TriggerBuilder.newTrigger()
				.withIdentity("myTrigger", "grupo1")
				.startNow()
				.withSchedule(SimpleScheduleBuilder
						.simpleSchedule()
						.withRepeatCount(conteo)
						.withIntervalInSeconds(2))
				.build();

		scheduler.scheduleJob(jobDetail, trigger);
		latch.await();
		System.out.println("Todos los triggers ejecutados, apagando scheduler");
		scheduler.shutdown();
	}
	
	@Override
	public void countDown() {
		latch.countDown();
	}
}
