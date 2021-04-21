package kz.iitu.demo.controller;

import kz.iitu.demo.model.Todolist;
import kz.iitu.demo.repo.TodolistRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/students/")
public class TodolistController {
    @Autowired
    private TodolistRepo todolistRepo;

    @GetMapping("showForm")
    public String showStudentForm(Todolist todolist) {
        return "page2";
    }
    @GetMapping("list")
    public String students(Model model) {
        model.addAttribute("students", this.todolistRepo.findAll());
        return "index";
    }
    @PostMapping("add")
    public String addStudent(@Valid Todolist todolist, BindingResult result, Model model) {
        if(result.hasErrors()) {
            return "page2"; // Page 2
        }
        this.todolistRepo.save(todolist);
        return "redirect:list";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Todolist todolist = this.todolistRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student id : " + id));

        model.addAttribute("todolist", todolist);
        return "page1";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable("id") long id, @Valid Todolist todolist, BindingResult result, Model model) {
        if(result.hasErrors()) {
            todolist.setId(id);
            return "page1";
        }

        todolistRepo.save(todolist);
        model.addAttribute("students", this.todolistRepo.findAll());
        return "index";
    }

    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable ("id") long id, Model model) {

        Todolist todolist = this.todolistRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid student id : " + id));

        this.todolistRepo.delete(todolist);
        model.addAttribute("students", this.todolistRepo.findAll());
        return "index";

    }
}
