package mx.com.ejemplo.quartz.main;

import org.quartz.SchedulerException;

import mx.com.ejemplo.quartz.firejob.ConfigurarJob;

public class DisparadorMensajeria {
	
	public static void main(String[] args) throws SchedulerException, InterruptedException {
		ConfigurarJob trabajo = new ConfigurarJob();
		trabajo.fireJob();
	}
}
