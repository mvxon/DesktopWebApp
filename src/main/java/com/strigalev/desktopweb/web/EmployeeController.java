package com.strigalev.desktopweb.web;

import com.strigalev.desktopweb.list.ListUsr;
import com.strigalev.desktopweb.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    private final ListUsr<Employee> listUsr = new ListUsr<>();


    // create employee rest api
    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        employee.setId(listUsr.size() + 1);
        listUsr.add(employee);
        return employee;
    }

    // get employee by id rest api
    @GetMapping("/{index}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Integer index) {
        Employee employee = listUsr.get(index);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/count")
    public ResponseEntity<?> getEmployeesCount() {
        return ResponseEntity.ok(listUsr.size());
    }

    // update employee rest api
    @PutMapping("/{index}")
    public void updateEmployee(@PathVariable Integer index,
                               @RequestBody Employee employeeDetails
    ) {
        Employee employee = listUsr.get(index);
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        listUsr.update(employee, index);
    }

}
