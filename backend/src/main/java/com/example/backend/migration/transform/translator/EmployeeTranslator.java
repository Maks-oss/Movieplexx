package com.example.backend.migration.transform.translator;

import com.example.backend.data.nosql.ActorDocument;
import com.example.backend.data.nosql.EmployeeDocument;
import com.example.backend.data.nosql.MovieDocument;
import com.example.backend.data.nosql.RoleDocument;
import com.example.backend.data.sql.Actor;
import com.example.backend.data.sql.Employee;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("employee_translator")
public class EmployeeTranslator implements ItemTranslator<Employee, EmployeeDocument> {
    @Override
    @Transactional
    public List<EmployeeDocument> transformData(List<Employee> input) {
        return input.stream().map(employee -> {
            var employeeDocument = createEmployeeDocument(employee);
            employeeDocument.setRoles(employee.getRoles().stream().map(role -> {
                var roleDocument = new RoleDocument();
                BeanUtils.copyProperties(role, roleDocument);
                return roleDocument;
            }).collect(Collectors.toSet()));
            return employeeDocument;
        }).collect(Collectors.toList());
    }

    private EmployeeDocument createEmployeeDocument(Employee employee) {
        var employeeDocument = new EmployeeDocument();
        BeanUtils.copyProperties(employee, employeeDocument);
        return employeeDocument;
    }
}
