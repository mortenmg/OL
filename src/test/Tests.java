package test;


import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import system.*;

/**
 * @author Morten
 *
 */
public class Tests {


	/**
	 * @throws java.lang.Exception
	 */
	
	public static final int h = Toolkit.getDefaultToolkit().getScreenSize().height;
	public static final int w = Toolkit.getDefaultToolkit().getScreenSize().width;
	
	@SuppressWarnings("static-access")
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		init init = new init();
		init.main(null);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testScannerInputField() throws AWTException, InterruptedException {
		Robot rob = new Robot();
		longWait();
		rob.keyPress(KeyEvent.VK_1);
		shortWait();
		rob.keyPress(KeyEvent.VK_0);
		shortWait();
		rob.keyPress(KeyEvent.VK_0);
		shortWait();
		rob.keyPress(KeyEvent.VK_1);
		shortWait();
		assertEquals(WindowUserLogin.textFieldTemp.getText(),"1001");
		rob.keyPress(KeyEvent.VK_ENTER);
		longWait();
		rob.keyPress(KeyEvent.VK_4);
		shortWait();
		rob.keyPress(KeyEvent.VK_0);
		shortWait();
		rob.keyPress(KeyEvent.VK_4);
		shortWait();
		rob.keyPress(KeyEvent.VK_ENTER);
		
	}
	
	@Test
	public void testEditProduct() throws AWTException, InterruptedException{
		Robot rob = new Robot();
		longWait();
		rob.mouseMove(w/2, h/2);
		shortWait();
		click();
		longWait();
		WindowAdminLogon.getPasswordField().setText("OLProgram");
		longWait();
		rob.keyPress(KeyEvent.VK_TAB);
		shortWait();
		rob.keyPress(KeyEvent.VK_TAB);
		shortWait();
		rob.keyPress(KeyEvent.VK_ENTER);
		longWait();	
		
	}

	public void click() throws AWTException{
		Robot rob = new Robot();
		rob.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		rob.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	private void shortWait() throws InterruptedException {
		Thread.sleep(10);
	}

	private void longWait() throws InterruptedException {
		Thread.sleep(800);
	}
}
