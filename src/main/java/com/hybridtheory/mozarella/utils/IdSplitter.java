package com.hybridtheory.mozarella.utils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IdSplitter {
	public static List<Integer> getIds(String ids){
		return Arrays.asList(ids.split(",")).stream()
											.map(id -> convertIfPossible(id))
											.filter(id -> id!=null)
											.collect(Collectors.toList());
	}
	
	private static Integer convertIfPossible(String toConvert){
		try{
			return Integer.valueOf(toConvert);
		} catch (NumberFormatException e){
			return null;
		}
	}
}