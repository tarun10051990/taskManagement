package com.taskmanagement.demo.service;

import com.taskmanagement.demo.dto.TaskDto;
import com.taskmanagement.demo.entity.Task;
import com.taskmanagement.demo.exception.TaskNotFoundException;
import com.taskmanagement.demo.repository.TaskRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class TaskService {

    @Autowired
    private TaskRespository taskRespository;

    public Task save(TaskDto taskDto){
        Task tk = new Task();
        log.info(taskDto.getTitle());
        tk.setTitle(taskDto.getTitle());
        tk.setDescription(taskDto.getDescription());
        tk.setStatus(taskDto.getStatus());
        return this.taskRespository.save(tk);
    }

    public String updateTask(int Id, TaskDto taskDto) {
        try {
            Task tk = this.taskRespository.findById(Id)
                    .orElseThrow(() -> new TaskNotFoundException("not able tp find the task id"));
            tk.setTitle(taskDto.getTitle());
            tk.setDescription(taskDto.getDescription());
            tk.setStatus(taskDto.getStatus());
            this.taskRespository.save(tk);
            return "successfully updated";
        } catch (Exception ex) {
            ex.printStackTrace();
            ex.getMessage();
        }
        return null;
    }

    public void deleteTask(int Id){
        Optional<Task> tk = this.taskRespository.findById(Id);
        if(tk.isPresent()){
            this.taskRespository.deleteById(Id);
        }else{
            throw new TaskNotFoundException("not able to find the task id");
        }
    }
}
