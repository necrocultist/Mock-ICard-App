import { Component } from '@angular/core';
import { NgForm } from "@angular/forms";
import { HttpErrorResponse } from "@angular/common/http";
import {EmployeeService} from "../../../services/employee.service";
import {Employee} from "../../../models/employee";

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.css']
})
export class AddEmployeeComponent {

  constructor(private employeeService: EmployeeService) { }

  public addEmployee(addForm: NgForm): void {
    this.employeeService.createEmployee(addForm.value).subscribe(
      (response: Employee) => {
        // Handle success
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        console.error(error.message);
        addForm.reset();
      }
    );
  }
}
