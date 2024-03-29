package day01.java8;

import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import day01.java8.Employee.Status;
import org.junit.Test;


public class TestStreamAPI3 {
	List<Employee>employees =Arrays.asList(new Employee(18,"张三",9999.99,Status.Free),
			new Employee(38,"李四",3333.33,Status.Busy),
			new Employee(38,"王五",5555.55,Status.Vocation),
			new Employee(8,"赵六",7777.77, Status.Free),
			new Employee(8,"田七",9777.77,Status.Busy),
			new Employee(8,"田七",9777.77,Status.Busy)
	);
	/*
	 * 收集
	 * collect--将流转换为其他形式。接收一个Collector接口的实现，用于给Stream中元素做汇总的方法
	 */
	@Test
	public void test10(){
		String str=employees.stream().map(Employee::getName)
				.collect(Collectors.joining());
		System.out.println(str);
		String str2=employees.stream().map(Employee::getName)
				.collect(Collectors.joining(",","(",")"));
		System.out.println(str2);
	}
	@Test
	public void test9(){
		DoubleSummaryStatistics collect = employees.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
		System.out.println(collect);
		System.out.println(collect.getAverage());
		System.out.println(collect.getCount());
		System.out.println(collect.getMax());
		System.out.println(collect.getSum());
	}
	//分组
	@Test
	public void test8(){
		Map<Boolean, List<Employee>> map = employees.stream().collect(Collectors.partitioningBy((e)->e.getAge()>=35));
		System.out.println(map);
	}
	@Test
	public void test7(){
		Map<Status,Map<String,List<Employee>>> map=employees.stream().collect(Collectors.groupingBy(Employee::getStatus,Collectors.groupingBy((x)->{
			if(x.getAge()<=30)
				return "青年";
			else if(x.getAge()>30&&x.getAge()<=50)
				return "中年";
			else
				return "老年";
		})));
		System.out.println(map);
	}
	@Test
	public void test6(){
		Map<Status,List<Employee>> map=employees.stream().collect(Collectors.groupingBy(Employee::getStatus));
		System.out.println(map);
		System.out.println("========================");
		for(Map.Entry<Status, List<Employee>>entry:map.entrySet()){
			System.out.println(entry.getKey());
		}
	}
	//--------------------------------------------------------------------
	@Test
	public void test5(){
		//总数
		Long count=employees.stream().collect(Collectors.counting());
		System.out.println(count);
		System.out.println("-------------------");
		//平均值
		Double avg=employees.stream().collect(Collectors.averagingDouble(Employee::getSalary));
		System.out.println("avg="+avg);
		System.out.println("---------------------");
		//总和
		double sum=employees.stream().collect(Collectors.summingDouble(Employee::getSalary));
		System.out.println("sum="+sum);
		System.out.println("--------------------");
		//最大值
		Optional<Employee>op1=employees.stream().collect(Collectors.maxBy((x,y)->Double.compare(x.getSalary(), y.getSalary())));
		System.out.println(op1.get().getSalary());
		//最小值
		Optional<Employee>op2=employees.stream().collect(Collectors.minBy((x,y)->Double.compare(x.getSalary(), y.getSalary())));
		System.out.println(op2.get().getSalary());
	}
	@Test
	public void test4(){
		List<String> list=employees.stream().map(Employee::getName)
				.collect(Collectors.toList());
		list.forEach(System.out::println);
		System.out.println("--------------");
		Set<String> set=employees.stream().map(Employee::getName)
				.collect(Collectors.toSet());
		set.forEach(System.out::println);
		System.out.println("----------------");
		HashSet<String> hashset=employees.stream().map(Employee::getName)
				.collect(Collectors.toCollection(HashSet::new));
		hashset.forEach(System.out::println);
	}
	/*
	 * 归约
	 * reduce(T identity,BinaryOperator)/reduce(BinaryOperator)--可以将流中元素反复结合起来，得到一个值。
	 */
	@Test
	public void test3(){
		List<Integer> list=Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		Integer sum=list.stream().reduce(0, (x,y)->x+y);
		System.out.println(sum);
		System.out.println("----------------");
		Optional<Double>op1=employees.stream().map(Employee::getSalary).reduce(Double::sum);
		System.out.println(op1.get());
	}
	/*
	 * 查找与匹配
	 * allMatch--检查是否匹配所有元素
	 * anyMatch--检查是否至少匹配一个元素
	 * noneMatch--检查是否没有匹配所有元素
	 * findFirst--返回第一个元素
	 * findAny--返回当前流中的任意元素
	 * count--返回流中元素的总个数
	 * max--返回流中最大值
	 * min--返回流中最小值
	 *
	 */
	@Test
	public void test1(){
		Boolean b1=employees.stream().allMatch((e)->e.getStatus().equals(Status.Busy));
		System.out.println("b1="+b1);
		System.out.println("-----------------");
		Boolean b2=employees.stream().anyMatch((e)->e.getStatus().equals(Status.Busy));
		System.out.println("b2="+b2);
		System.out.println("-----------------");
		Boolean b3=employees.stream().noneMatch((e)->e.getStatus().equals(Status.Busy));
		System.out.println("b3="+b3);
		System.out.println("-----------------");
		Optional<Employee> op1=employees.stream().findFirst();
		System.out.println(op1.get());
		Optional<Employee> op2=employees.parallelStream().findAny();
		System.out.println(op2.get());
		long count=employees.stream().count();
		System.out.println("返回流中元素的总个数="+count);
		System.out.println("--------------------");
		Optional<Double>op3=employees.stream().map(Employee::getSalary).max(Double::compare);
		System.out.println("max="+op3.get());
		Optional<Double>op4=employees.stream().map(Employee::getSalary).min(Double::compare);
		System.out.println("min="+op4.get());
	}
}