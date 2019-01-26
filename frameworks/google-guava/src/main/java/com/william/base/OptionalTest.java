package com.william.base;

import java.util.Set;

import com.google.common.base.Optional;

/**
 * 优雅的使用null:
 * 
 *		大多数情况下，开发人员使用 null 表明的是某种缺失情形：可能是已经有一个默认值，或没有值，或找不到值。
 * 	例如，Map.get 返回 null 就表示找不到给定键对应的值。Guava 用 Optional表示可能为 null 的 T 类型引用。
 * 	一个 Optional 实例可能包含非 null 的引用（我们称之为引用存在），也可能什么也不包括（称之为引用缺失）。
 * 	它从不说包含的是 null 值，而是用存在或缺失来表示。但 Optional 从不会包含 null 值引用。
 * 	
 * 	创建 Optional 实例（以下都是静态方法）：
 * 		Optional.of(T)	创建指定引用的 Optional 实例，若引用为 null 则快速失败
 * 		Optional.absent()	创建引用缺失的 Optional 实例
 * 		Optional.fromNullable(T)	创建指定引用的 Optional 实例，若引用为 null 则表示缺失
 *
 * 	用 Optional 实例查询引用（以下都是非静态方法）：
 * 		boolean isPresent()	如果 Optional 包含非 null 的引用（引用存在），返回true
 * 		T get()	返回 Optional 所包含的引用，若引用缺失，则抛出 java.lang.IllegalStateException
 * 		T or(T)	返回 Optional 所包含的引用，若引用缺失，返回指定的值
 * 		T orNull()	返回 Optional 所包含的引用，若引用缺失，返回 null
 * 		Set<T> asSet()	返回 Optional 所包含引用的单例不可变集，如果引用存在，返回一个只有单一元素的集合，如果引用缺失，返回一个空集合。
 * 
 * @author william
 *
 */
public class OptionalTest {

	public static void main(String[] args) {
		testOptionalStatic();
		
		testOptional();
	}

	public static void testOptional() {
		/**
		 * 2.实例方法：
		 *
		 * 1> boolean
		 * isPresent()：如果Optional包含的T实例不为null，则返回true；若T实例为null，返回false
		 * 
		 * 2> T get()：返回Optional包含的T实例，该T实例必须不为空；否则，对包含null的Optional实例调用get()
		 * 会抛出一个IllegalStateException异
		 * 
		 * 3> T or(T)：若Optional实例中包含了传入的T的相同实例，返回Optional包含的该T实例，
		 * 否则返回输入的T实例作为默认值
		 * 
		 * 4> T orNull()：返回Optional实例中包含的非空T实例，如果Optional中包含的是空值，
		 * 返回null，逆操作是fromNullable()
		 * 
		 * 5> Set
		 * <T> asSet()：返回一个不可修改的Set，该Set中包含Optional实例中包含的所有非空存在的T实例，且在该Set中，
		 * 每个T实例都是单态，如果Optional中没有非空存在的T实例，返回的将是一个空的不可修改的Set。
		 */
		Optional<Long> value = Optional.fromNullable(null);
		if (value.isPresent() == true) {
			System.out.println("获得返回值: " + value.get());
		} else {
			System.out.println("获得返回值: " + value.or(-12L));
		}
		System.out.println("获得返回值 orNull: " + value.orNull());

		Optional<Long> valueNoNull = Optional.fromNullable(15L);
		if (valueNoNull.isPresent() == true) {
			Set<Long> set = valueNoNull.asSet();
			System.out.println("获得返回值 set 的 size : " + set.size());
			System.out.println("获得返回值: " + valueNoNull.get());
		} else {
			System.out.println("获得返回值: " + valueNoNull.or(-12L));
		}
		System.out.println("获得返回值 orNull: " + valueNoNull.orNull());
	}

	public static void testOptionalStatic() {
		/**
		 * 1.创建 Optional 实例，常用静态方法：
		 * 
		 * Optional.of(T)：获得一个Optional对象，其内部包含了一个非null的T数据类型实例，若T=null，则立刻报错。
		 * Optional.absent()：获得一个Optional对象，其内部包含了空值
		 * Optional.fromNullable(T)：将一个T的实例转换为Optional对象，T的实例可以不为空，也可以为空
		 * Optional.fromNullable(null)：和Optional.absent()等价。
		 */
		Integer integer = 5;
		Optional<Integer> optionalFirst = Optional.of(integer);
		optionalFirst.isPresent(); 	//如果 Optional 包含非 null 的引用（引用存在），返回true
		optionalFirst.get(); 		//返回 Optional 所包含的引用，若引用缺失，则抛出 java.lang.IllegalStateException
		System.out.println(optionalFirst.isPresent() + " ---- " + optionalFirst.get());

		Optional<Integer> optionalSecond = Optional.of(6);
		Optional<Integer> absentOpt = Optional.absent();
		Optional<Integer> NullableOpt = Optional.fromNullable(null);
		Optional<Integer> NoNullableOpt = Optional.fromNullable(10);

		if (optionalSecond.isPresent()) {
			System.out.println("possible isPresent: " + optionalSecond.isPresent());
			System.out.println("possible value: " + optionalSecond.get());
		}
		
		if (!absentOpt.isPresent()) {
			System.out.println("absentOpt isPresent: " + absentOpt.isPresent());
		}
		
		if (!NullableOpt.isPresent()) {
			System.out.println("fromNullableOpt isPresent: " + NullableOpt.isPresent());
		}
		
		if (NoNullableOpt.isPresent()) {
			System.out.println("NoNullableOpt isPresent: " + NoNullableOpt.isPresent());
		}
	}
}
