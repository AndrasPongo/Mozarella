package com.hybridtheory.mozarella.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.LearnItem;
import com.hybridtheory.mozarella.wordteacher.learnmaterials.Result;

public interface ResultRepository extends JpaRepository<Result,Integer> {

}
