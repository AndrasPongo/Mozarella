package com.hybridtheory.mozarella.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hybridtheory.mozarella.wordteacher.learnmaterials.SomeEntity;

public interface SomeEntityRepository extends JpaRepository<SomeEntity, Integer> {

}
