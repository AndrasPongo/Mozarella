package com.hybridtheory.mozarella.wordteacher.teacher;

import java.util.List;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;

public interface ItemPrioritizer {
	public Double assignPriority(List<Result> results);
}
