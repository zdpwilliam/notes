package com.william.base;

import com.google.common.base.Optional;
import static com.google.common.base.Preconditions.*;

/**
 * 前置条件：让方法调用的前置条件判断更简单。
 * 
 * 		Guava 在 Preconditions 类中提供了若干前置条件判断的实用方法，我们强烈建议在 Eclipse 中静态导入这些方法。
 * 每个方法都有三个变种：
 * 		1> 没有额外参数：抛出的异常中没有错误消息；
 * 		2> 有一个 Object 对象作为额外参数：抛出的异常使用 Object.toString() 作为错误消息；
 * 		3> 有一个 String 对象作为额外参数，并且有一组任意数量的附加 Object 对象：这个变种处理异常消息的方式有点类似 printf，
 *		        但考虑 GWT 的兼容性和效率，只支持%s 指示符。例如：
 * 
 * @author william
 *
 */
public class PreconditionsTest {
	
	public static void main(String[] args) {
		testPreconditions();
	}
	
	private static void testPreconditions() {
		Integer count = 0;
		checkArgument(count == 0, "checkArgument must be positive: %s", count);
		
		Optional<Integer> countOptional = Optional.fromNullable(5);
		
		//检查 value 是否为 null，该方法直接返回 value，因此可以内嵌使用 checkNotNull。
		countOptional = checkNotNull(countOptional, "checkNotNull must be positive: %s", countOptional.get());
		
		//用来检查对象的某些状态
		checkState(count == 0, "checkState must be positive: %s", count);
		
		//检查 index 作为索引值对某个列表、字符串或数组是否有效。index>=0 && index<size 
		checkElementIndex(5, 10);
		
		//检查[start, end]表示的位置范围对某个列表、字符串或数组是否有效
		checkPositionIndexes(1, 5, 4);
	}
}
