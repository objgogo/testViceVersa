package com.viceversa.test.repository;

import com.viceversa.test.entity.Gallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long>, JpaSpecificationExecutor<Gallery> {
}
