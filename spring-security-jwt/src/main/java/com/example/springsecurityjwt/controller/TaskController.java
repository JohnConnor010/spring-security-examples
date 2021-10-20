package com.example.springsecurityjwt.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/task")
public class TaskController {
    @GetMapping
    public String listTasks(){
        return "任务列表";
    }

    @PostMapping
    public String newTask(){
        return "创建了一个新任务";
    }

    @PutMapping(value = "/{taskId}")
    public String updateTask(@PathVariable("taskId") final Integer taskId){
        return "更新了 id: " + taskId + " 的任务";
    }
}
