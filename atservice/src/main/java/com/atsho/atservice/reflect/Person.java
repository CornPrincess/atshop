package com.atsho.atservice.reflect;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class Person implements Serializable,TestInterface{
	private Long id;
	public String name;

	public Person() {
		this.id = 100L;
		this.name = "afsdfasd";
	}

	public Person(Long id, String name) {
//		super();
		this.id = id;
		this.name = name;
	}
	
	
	public Person(Long id) {
		super();
		this.id = id;
	}
	@SuppressWarnings("unused")
	private Person(String name) {
		super();
		this.name = name+"=======";
	}

	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}
	private String getSomeThing() {
		return "sdsadasdsasd";
	}
	
	private void testPrivate(){
		System.out.println("this is a private method");
	}
}
