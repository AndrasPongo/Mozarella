package com.hybridtheory.mozarella.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.ResultsContainer;

public interface ResultContainerRepository extends JpaRepository<ResultsContainer, Integer> {

}