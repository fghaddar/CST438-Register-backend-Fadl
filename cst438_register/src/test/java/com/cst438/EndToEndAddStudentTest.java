package com.cst438;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;

@SpringBootTest
public class EndToEndAddStudentTest {
	
	public static final String EDGE_DRIVER_FILE_LOCATION = "C:\\Users\\fadlg\\Dropbox\\My PC (LAPTOP-T13A7Q9Q)\\Downloads\\edgedriver_win32\\msedgedriver.exe";
	public static final String URL = "https://cst438register--frontend.herokuapp.com/";
	public static final String TEST_USER_EMAIL = "new_student@csumb.edu";
	public static final String TEST_USER_NAME = "New Student";
	public static final int SLEEP_DURATION = 1000; // 1 second.

	@Autowired
	StudentRepository studentRepository;
	
	
	// If the student exists in the database, delete it. This makes the test repeatable.
	@Test
	public void addStudentTest() throws Exception {
		Student x = null;
		do {
			x = studentRepository.findByEmail(TEST_USER_EMAIL);
			if (x != null)
				studentRepository.delete(x);
		} while (x != null);
		
		// set the driver location and start driver
		System.setProperty("webdriver.edge.driver", EDGE_DRIVER_FILE_LOCATION);
		WebDriver driver = new EdgeDriver();
		// Puts an Implicit wait for 10 seconds before throwing exception
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		try {
			driver.get(URL);
			Thread.sleep(SLEEP_DURATION);
			
			// Locate and click "Add Student" button
			driver.findElement(By.xpath("//button[@id='addStudent']")).click();
			Thread.sleep(SLEEP_DURATION);
			
			// Enter the name and email, then click add
			driver.findElement(By.xpath("//input[@name='name']")).sendKeys(TEST_USER_NAME);
			driver.findElement(By.xpath("//input[@name='email']")).sendKeys(TEST_USER_EMAIL);
			driver.findElement(By.xpath("//button[span='Add']")).click();
			Thread.sleep(SLEEP_DURATION);
			
			// verify that new student is in the database.
			Student student = studentRepository.findByEmail(TEST_USER_EMAIL);
			assertNotNull(student, "Added student does not show in the database.");
		} 
		catch (Exception ex) {
			throw ex;
		}
		finally {

			// clean up database.
			Student e = studentRepository.findByEmail(TEST_USER_EMAIL);
			if (e != null)
				studentRepository.delete(e);
			driver.quit();
		}

	}
}
