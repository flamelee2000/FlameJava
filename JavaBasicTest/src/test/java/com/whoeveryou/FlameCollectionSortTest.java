package com.whoeveryou;

import java.util.ArrayList;  
import java.util.Collections;  
import java.util.Comparator;  
import java.util.GregorianCalendar;  
import java.util.Iterator;  
import java.util.List;  

import org.junit.Test;
  
public class FlameCollectionSortTest {  
	
    @SuppressWarnings("unchecked")
	@Test
	public void test() {  
        List<Book> list = new ArrayList<Book>();
        Book b1 = new Book(10000, "红楼梦", 150.86, new GregorianCalendar(2009,  
                01, 25), "曹雪芹、高鄂");  
        Book b2 = new Book(10001, "三国演义", 99.68, new GregorianCalendar(2008, 7,  
                8), "罗贯中 ");  
        Book b3 = new Book(10002, "水浒传", 100.8, new GregorianCalendar(2009, 6,  
                28), "施耐庵 ");  
        Book b4 = new Book(10003, "西游记", 120.8, new GregorianCalendar(2011, 6,  
                8), "吴承恩");  
        Book b5 = new Book(10004, "天龙八部", 10.4, new GregorianCalendar(2011, 9,  
                23), "搜狐");  
        list.add(b1);  
        list.add(b2);  
        list.add(b3);  
        list.add(b4);  
        list.add(b5);
        
        System.out.println("数组序列中的元素:");  
        myprint(list);  
        Collections.sort(list, new PriceComparator());
        System.out.println("按书的价格排序:");  
        myprint(list);  
        Collections.sort(list, new CalendarComparator());
        System.out.println("按书的出版时间排序:");  
        myprint(list);  
    }  
  
    public static void myprint(List<Book> list) {  
    	
        @SuppressWarnings("rawtypes")
		Iterator it = list.iterator();
        
        while (it.hasNext()) {
            System.out.println("\t" + it.next());
        }  
    }  
  
    @SuppressWarnings("rawtypes")
	static class PriceComparator implements Comparator {  
        public int compare(Object object1, Object object2) {
            Book p1 = (Book) object1;
            Book p2 = (Book) object2;  
            return new Double(p1.price).compareTo(new Double(p2.price));  
        }  
    }  
  
    @SuppressWarnings("rawtypes")
	static class CalendarComparator implements Comparator {
        public int compare(Object object1, Object object2) {
            Book p1 = (Book) object1;
            Book p2 = (Book) object2;
            return p2.calendar.compareTo(p1.calendar);  
        }
    }
}
