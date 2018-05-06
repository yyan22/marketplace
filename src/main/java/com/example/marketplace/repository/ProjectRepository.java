package com.example.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.marketplace.model.Project;

public interface ProjectRepository extends JpaRepository<Project, Long>{

}
