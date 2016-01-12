package com.whoeveryou;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * 
 * @author ray
 * 
 */

@SuppressWarnings("rawtypes")
public class Book implements Comparable {
	public int id;
	public String name;
	public double price;
	private String author;
	public GregorianCalendar calendar;

	public Book() {
		this(0, "X", 0.0, new GregorianCalendar(), "");
	}

	public Book(int id, String name, double price, GregorianCalendar calender,
			String author) {
		this.id = id;
		this.name = name;
		this.price = price;
		this.calendar = calender;
		this.author = author;
	}

	public String toString() {
		String showStr = id + "\t" + name;
		DecimalFormat formatPrice = new DecimalFormat("0.00");
		showStr += "\t" + formatPrice.format(price);
		showStr += "\t" + author;
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyyƒÍMM‘¬dd»’");
		showStr += "\t" + formatDate.format(calendar.getTime());
		return showStr;
	}

	public int compareTo(Object obj) {
		Book b = (Book) obj;
		return this.id - b.id;
	}


}
