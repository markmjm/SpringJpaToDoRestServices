package com.mjm.todomicroservice;

import com.mjm.todomicroservice.doas.ToDoDao;
import com.mjm.todomicroservice.doas.UserDao;
import com.mjm.todomicroservice.entities.ToDo;
import com.mjm.todomicroservice.entities.User;
import com.mjm.todomicroservice.utilities.EncryptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Date;

@SpringBootApplication
public class TodomicroserviceApplication implements CommandLineRunner{

	@Autowired
	private UserDao userDao;

	@Autowired
	private ToDoDao toDoDao;

	@Autowired
	private EncryptionUtils encryptionUtils;

	private static Logger LOGGER = LoggerFactory.getLogger(TodomicroserviceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TodomicroserviceApplication.class, args);
	}


	@Override
	public void run(String... strings) throws Exception {
		//
		LOGGER.info("Start adding data to tables");
		userDao.save(new User("JimmyJam@gmail.com", "Jimmy Jam", encryptionUtils.encrypt("JimmyJamPW")));
		userDao.save(new User("JohnDoe@gmail.com", "John Doe", encryptionUtils.encrypt("JohnDoePW")));
		userDao.save(new User("BillBall@gmail.com", "Bill Ball", encryptionUtils.encrypt("BillBallPW")));

		toDoDao.save(new ToDo(null, "learn MicroService", new Date(), "high", "JimmyJam@gmail.com"));
		toDoDao.save(new ToDo(null, "learn Springboot", new Date(), "high", "JimmyJam@gmail.com"));
		toDoDao.save(new ToDo(null, "learn Photography", new Date(), "low", "JimmyJam@gmail.com"));
		toDoDao.save(new ToDo(null, "learn cooking", new Date(), "low", "JimmyJam@gmail.com"));

		toDoDao.save(new ToDo(null, "Jogg", new Date(), "high", "JohnDoe@gmail.com"));
		toDoDao.save(new ToDo(null, "Study", new Date(), "high", "JohnDoe@gmail.com"));
		toDoDao.save(new ToDo(null, "Do Laundry", new Date(), "low", "JohnDoe@gmail.com"));
		toDoDao.save(new ToDo(null, "Go To Bank", new Date(), "medium", "JohnDoe@gmail.com"));


		LOGGER.info("End adding data to tables");



	}
}
