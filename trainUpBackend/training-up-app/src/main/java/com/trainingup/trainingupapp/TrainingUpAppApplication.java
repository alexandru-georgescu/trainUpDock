package com.trainingup.trainingupapp;

import com.trainingup.trainingupapp.threads.SmtpThread;
import com.trainingup.trainingupapp.threads.TokenThread;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class TrainingUpAppApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(TrainingUpAppApplication.class, args);
		SmtpThread thread = new SmtpThread();
		context.getAutowireCapableBeanFactory().autowireBean(thread);
		TokenThread tokenThread = new TokenThread();
		context.getAutowireCapableBeanFactory().autowireBean(tokenThread);

		thread.start();
		tokenThread.start();
	}

}