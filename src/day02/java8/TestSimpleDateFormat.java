package day02.java8;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestSimpleDateFormat {
  public static void main(String[] args)throws Exception {
	
	Callable<Date> task=new Callable<Date>() {
		@Override
		public Date call() throws Exception {
			return (Date) DateFormatThreadLocal.convert("20171218");
		}
	};
	ExecutorService pool=Executors.newFixedThreadPool(10);
	List<Future<Date>> results=new ArrayList<>();	
    for(int i=0;i<10;i++){
		results.add(pool.submit(task));
	}
    for (Future<Date> future : results) {
		System.out.println(future.get());
	}
    pool.shutdown();
}
}
