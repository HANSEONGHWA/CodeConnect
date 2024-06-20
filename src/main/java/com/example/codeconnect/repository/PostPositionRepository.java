package com.example.codeconnect.repository;

import com.example.codeconnect.entity.PostPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostPositionRepository extends JpaRepository<PostPosition, Long> {
}
