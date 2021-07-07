package demo;
import org.testng.annotations.Test;
import library.Golden_template;
public class TestNG_multithread_Test {
	@Test
	public void test1() {
		Golden_template.openbrowser("chrome");
		System.out.println("test1 "+Thread.currentThread().getId());
	    
	}

	@Test
	public void test2() {
		Golden_template.openbrowser("chrome");
		System.out.println("test2 "+Thread.currentThread().getId());
	}
}
