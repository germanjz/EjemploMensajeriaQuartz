package mx.com.ejemplo.quartz.main;

import org.quartz.SchedulerException;

import mx.com.ejemplo.quartz.firejob.LanzarJob;

public class DisparadorMensajeria {
	
	public static void main(String[] args) throws SchedulerException, InterruptedException {
		LanzarJob trabajo = new LanzarJob();
		trabajo.fireJob();
	}
}
