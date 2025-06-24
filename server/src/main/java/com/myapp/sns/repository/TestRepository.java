package com.myapp.sns.repository;

// public class TestRepository {
  
// }

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.myapp.sns.entity.TestEntity;

@Repository
public interface TestRepository extends JpaRepository<TestEntity, Long> {}
