package com.example.codeconnect.repository;

import com.example.codeconnect.entity.PostTechStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostTechStackRepository extends JpaRepository<PostTechStack, Long> {
}
