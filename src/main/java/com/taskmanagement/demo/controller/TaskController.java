package com.taskmanagement.demo.controller;

import com.taskmanagement.demo.dto.TaskDto;
import com.taskmanagement.demo.entity.Task;
import com.taskmanagement.demo.service.TaskService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping("/save")
    public ResponseEntity<Task> saveTask(@RequestBody TaskDto taskDto){
        Task task = this.taskService.save(taskDto);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PutMapping("/updateTask/{id}")
    public ResponseEntity<String> updateTask(@PathVariable Integer id, @RequestBody TaskDto taskDto){
        String updateTask = this.taskService.updateTask(id, taskDto);
        return new ResponseEntity<>(updateTask, HttpStatus.OK);
    }

    @DeleteMapping("/deleteTask/{Id}")
    public void deleteTask(@PathVariable Integer Id){
        this.taskService.deleteTask(Id);
    }
}
