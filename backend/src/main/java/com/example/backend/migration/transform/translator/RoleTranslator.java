package com.example.backend.migration.transform.translator;

import com.example.backend.data.nosql.EmployeeDocument;
import com.example.backend.data.nosql.RoleDocument;
import com.example.backend.data.sql.Employee;
import com.example.backend.data.sql.Role;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Component("role_translator")
public class RoleTranslator implements ItemTranslator<Role, RoleDocument> {
    @Override
    @Transactional
    public List<RoleDocument> transformData(List<Role> input) {
        return input.stream().map(role -> {
            var roleDocument = createRoleDocument(role);
            roleDocument.setEmployees(role.getEmployees().stream().map(employee -> {
                var employeeDocument = new EmployeeDocument();
                BeanUtils.copyProperties(employee, employeeDocument);
                return employeeDocument;
            }).collect(Collectors.toSet()));
            return roleDocument;
        }).collect(Collectors.toList());
    }

    private RoleDocument createRoleDocument(Role role) {
        var roleDocument = new RoleDocument();
        BeanUtils.copyProperties(role, roleDocument);
        return roleDocument;
    }
}
