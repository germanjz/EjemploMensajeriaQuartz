package mx.com.ejemplo.quartz.job;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import mx.com.ejemplo.quartz.firejob.latch.ILatch;
import mx.com.ejemplo.quartz.ws.client.LlamadoMensajeria;

public class MyJob implements Job {
	private static int conteo;
	
	@Override
	public void execute(JobExecutionContext jobContext) throws JobExecutionException {
		System.out.println("--------------------------------------------------------------------");
		System.out.println("MyJob Inicia en: " + jobContext.getFireTime());
		JobDetail jobDetail = jobContext.getJobDetail();
		System.out.println("Mi nombre es: " + jobDetail.getJobDataMap().getString("ejemplo"));				
		
		LlamadoMensajeria llamadoMensajeria = new LlamadoMensajeria();
		llamadoMensajeria.llamadoServicioWeb();
		
		System.out.println("MyJob Termina en: " + jobContext.getJobRunTime() + ", clave: " + jobDetail.getKey());
		
		if (jobContext.getNextFireTime() != null)
			System.out.println("MyJob proxima ejecucion: " + jobContext.getNextFireTime());
		else {
			System.out.println("Todos los trabajos han sido ejecutados ");
		}
		
		System.out.println("--------------------------------------------------------------------");
		
		ILatch latch = (ILatch) jobDetail.getJobDataMap().get("latch");
		latch.countDown();
		conteo ++;
		
		System.out.println("Job count " + conteo);
		if (conteo == 2) {
			throw new RuntimeException("Some RuntimeException!");
		}
		if (conteo == 4) {
			throw new JobExecutionException("Some JobExecutionException!");
		}
	}
}
