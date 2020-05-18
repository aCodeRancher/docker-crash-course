package com.in28minutes.springboot.web.controller;

import com.in28minutes.springboot.web.model.Todo;
import com.in28minutes.springboot.web.security.SecurityConfiguration;
import com.in28minutes.springboot.web.service.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

//load the TodoController
@WebMvcTest(TodoController.class)
//load the TodoRepository
@AutoConfigureDataJpa
@AutoConfigureMockMvc
//load the MyTestConfig with a defined user
@ContextConfiguration(classes=MyTestConfig.class)
@Import(SecurityConfiguration.class)
class TodoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    TodoRepository todoRepository;

    @Test
    @WithUserDetails(value="in28minutes", userDetailsServiceBeanName="userDetailsService")
    @Transactional
    public void addTodo() throws Exception{
        assertTrue(todoRepository.count()==3);
        Todo todo = new Todo(10004, "in28minutes","Learn piano", new Date(), false);
        mockMvc.perform(post("/add-todo").flashAttr("todo", todo))
                .andExpect(redirectedUrl("/list-todos"));
        assertTrue(todoRepository.count()==4);
      }

    @Test
    @WithMockUser(roles = "CLIENT")
    public void addTodo_notAuthorized() throws Exception {
        Todo todo = new Todo(10005, "in28minutes", "Learn basketball", new Date(), false);
        mockMvc.perform(post("/add-todo").flashAttr("todo", todo))
                       .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value="in28minutes", userDetailsServiceBeanName="userDetailsService")
    @Transactional
    public void showAddTodoPage() throws Exception{
          MvcResult result =  mockMvc.perform(get("/add-todo"))
                .andExpect(view().name("todo")).andReturn();
         Todo todo = (Todo)result.getModelAndView().getModel().get("todo");
         assertTrue(todo.getDesc().equals("Default Desc"));
     }

    @Test
    @WithUserDetails(value="in28minutes", userDetailsServiceBeanName="userDetailsService")
    @Transactional
    public void updateTodo() throws Exception{
        Todo todo = todoRepository.findById(10001).get();
        assertTrue(todoRepository.findById(10001).get().getDesc().equals("Learn Spring Boot"));
        todo.setDesc("Learn Spring Boot version 2");
         mockMvc.perform(post("/update-todo").flashAttr("todo", todo))
                .andExpect(redirectedUrl("/list-todos"));
        assertTrue(todoRepository.findById(10001).get().getDesc().equals("Learn Spring Boot version 2"));
    }

    @Test
    @WithUserDetails(value="in28minutes", userDetailsServiceBeanName="userDetailsService")
    @Transactional
    public void showUpdateTodoPage() throws Exception{
        MvcResult result = mockMvc.perform(get("/update-todo?id=10001"))
                 .andExpect(view().name("todo")).andReturn();
        Todo todo = (Todo) result.getModelAndView().getModelMap().get("todo");
        Todo todo_fromRepository = todoRepository.findById(10001).get();
        assertTrue(todo==todo_fromRepository);
    }

    @Test
    @WithUserDetails(value="in28minutes", userDetailsServiceBeanName="userDetailsService")
    public void showTodos() throws Exception{
        MvcResult result = mockMvc.perform(get("/list-todos/"))
                .andExpect(view().name("list-todos")).andReturn();
        Map<String, Object> modelAttributes = result.getModelAndView().getModel();
        List<Todo> todos = (List)modelAttributes.get("todos");
        assertTrue(todos.size()==3);
    }

    @Test
    @WithUserDetails(value="in28minutes", userDetailsServiceBeanName="userDetailsService")
    @Transactional
    public void deleteTodo() throws Exception{
        assertTrue(todoRepository.count()==3);
        MvcResult result = mockMvc.perform(get("/delete-todo?id=10001"))
                .andExpect(redirectedUrl("/list-todos")).andReturn();
        assertTrue(todoRepository.count()==2);
    }
}