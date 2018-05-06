package com.example.marketplace.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.marketplace.exception.ResourceNotFoundException;
import com.example.marketplace.model.Project;
import com.example.marketplace.repository.BidRepositoryCustom;
import com.example.marketplace.repository.ProjectRepository;

@RestController
@RequestMapping("/api")
public class ProjectController {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ProjectController.class);	
	
    @Autowired
    ProjectRepository projectRepository;
    
    @Autowired
    BidRepositoryCustom bidRepositoryCustom;

    // Get All Projects
    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
    
    // Create a new Project
    @PostMapping("/projects")
    public Project createProject(@Valid @RequestBody Project project) {
    		Project savedProject = projectRepository.save(project);
    		
    		// Add task to scheduler to set the lowest bid for the project when deadline is reached    
    		LOGGER.info("Begin scheduling task for project ID: " + savedProject.getId());
    		Runnable task  = () -> bidRepositoryCustom.setWinningBidAutomatically(savedProject.getId());
    		ScheduledExecutorService executor = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    		long now = System.currentTimeMillis();
    		long interval = project.getDeadline().getTime() - now;
    		LOGGER.info("Begin executing task for project ID: " + savedProject.getId());
    		executor.schedule(task, interval, TimeUnit.MILLISECONDS);
    		
    		return savedProject;
    }
    
    // Get a Single Project
    @GetMapping("/projects/{id}")
    public ResponseEntity<Map<Float, Project>> getProjectById(@PathVariable(value = "id") Long projectId) {
    	
    		Map<Float, Project> map = new HashMap<Float, Project>();
    		
    		float lowestBidAmount = bidRepositoryCustom.getLowestBidAmountByProjectId(projectId);
    		Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));
    		
        map.put(lowestBidAmount, project);

        return new ResponseEntity<Map<Float, Project>>(map, HttpStatus.OK);
    
    }
    
    // Update a Project
    @PutMapping("/projects/{id}")
    public Project updateProject(@PathVariable(value = "id") Long projectId,
                                            @Valid @RequestBody Project projectDetails) {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));

        project.setTitle(projectDetails.getTitle());
        project.setDescription(projectDetails.getDescription());

        Project updatedProject = projectRepository.save(project);
        
        return updatedProject;
    }
    
    // Delete a Project
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<?> deleteProject(@PathVariable(value = "id") Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project", "id", projectId));

        projectRepository.delete(project);

        return ResponseEntity.ok().build();
    }

}