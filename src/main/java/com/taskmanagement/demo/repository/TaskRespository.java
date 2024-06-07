package com.taskmanagement.demo.repository;

import com.taskmanagement.demo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRespository extends JpaRepository<Task,Integer> {
}
