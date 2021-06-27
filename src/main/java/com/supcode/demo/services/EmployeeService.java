package com.supcode.demo.services;

import java.util.List;

import javax.websocket.server.PathParam;

import com.supcode.demo.bo.Project;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeService {
    @GetMapping(value="/employees/{id}/projects")
    public List<Project> getProjectsOfEmployee(@PathVariable String id){
        return null;
    }
}
