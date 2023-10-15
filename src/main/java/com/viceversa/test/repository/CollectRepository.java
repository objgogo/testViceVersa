package com.viceversa.test.repository;

import com.viceversa.test.entity.Collect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectRepository extends JpaRepository<Collect,Long> {
}
