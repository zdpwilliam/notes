package com.william.base;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.collect.Lists;

/**
 * 
 * @author william
 *
 */
public class ObjectsTest {

	public static void main(String[] args) {
		testEquals();
		testHashCode();
		testToString();
		testCompareTo();
	}

	public static void testEquals() {
		System.out.println(Objects.equal("a", "a")); // returns true
		System.out.println(Objects.equal(null, "a")); // returns false
		System.out.println(Objects.equal("a", null)); // returns false
		System.out.println(Objects.equal(null, null)); // returns true
	}

	public static void testHashCode() {
		System.out.println(Objects.hashCode("a", "b", 2, 35));
	}

	@SuppressWarnings("deprecation")
	public static void testToString() {
		Person person = new Person();
		System.out.println(Objects.toStringHelper(person).add("firstName", "xiaoxin"));
		System.out.println(Objects.toStringHelper(Person.class).add("firstName", "xiaoxin"));
		System.out.println(Objects.toStringHelper("").add("firstName", "xiaoxin"));
	}

	public static void testCompareTo() {
		String name = "william";
		Person person1 = new Person(name, name, 1);
		Person person2 = new Person(name, name, 2);
		
		List<Person> persons = Lists.newArrayList();
		persons.add(person2);
		persons.add(person1);
		
		System.out.println(Arrays.toString(persons.toArray()));
		Collections.sort(persons);
		System.out.println(Arrays.toString(persons.toArray()));
	}

	static class Person implements Comparable<Person> {
		private String lastName;
		private String firstName;
		private int zipCode;

		public Person() {
		}
		
		public Person(String lastName, String firstName, int zipCode) {
			super();
			this.lastName = lastName;
			this.firstName = firstName;
			this.zipCode = zipCode;
		}

		public int compareTo(Person other) {
			/*int cmp = lastName.compareTo(other.lastName);
			if (cmp != 0) {
				return cmp;
			}
			cmp = firstName.compareTo(other.firstName);
			if (cmp != 0) {
				return cmp;
			}
			return Integer.compare(zipCode, other.zipCode);*/
			return ComparisonChain.start()
		            .compare(this.lastName, other.lastName)
		            .compare(this.firstName, other.firstName)
		            .compare(this.zipCode, other.zipCode)
		            .result();
		}

		@Override
		public String toString() {
			return "Person {lastName=" + lastName + ", firstName=" + firstName + ", zipCode=" + zipCode + "}";
		}
	}
}
