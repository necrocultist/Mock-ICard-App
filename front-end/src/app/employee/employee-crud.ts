import { EventEmitter, Injectable, Output} from '@angular/core';
import { NgForm } from "@angular/forms";
import { Employee } from "../../models/employee";
import { HttpErrorResponse } from "@angular/common/http";
import { EmployeeService } from "../../services/employee.service";

@Injectable({
  providedIn: 'root'
})
export class EmployeeCrud {
  @Output() employeeChanged = new EventEmitter<string>();

  public employees: Employee[] = [];

  constructor(private employeeService: EmployeeService) {}

  public getEmployees(): Employee[] {
    this.employeeService.getAllEmployees().subscribe(
      (data: Employee[]) => {
        this.employees = data;
        return this.employees;
      },
      (error: HttpErrorResponse) => {
        console.error(error.message);
        return this.employees;
      }
    );
    return this.employees;
  }

  public addEmployee(addForm: NgForm): void {
    document.getElementById('add-employee-form')?.click();
    this.employeeService.createEmployee(addForm.value).subscribe(
      (response: Employee) => {
        this.employeeChanged.emit("Employee added successfully!");
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        console.error(error.message);
        addForm.reset();
      }
    );
  }

  public updateEmployee(employee: Employee): void {
    this.employeeService.updateEmployee(employee.id, employee).subscribe(
      (response: Employee) => {
        this.employeeChanged.emit("Employee updated successfully!");
      },
      (error: HttpErrorResponse) => {
        console.error(error.message);
      }
    );
  }

  public deleteEmployee(employee: Employee): void {
    this.employeeService.deleteEmployee(employee.id).subscribe(
      () => {
        this.employeeChanged.emit("Employee deleted successfully!");
      },
      (error: HttpErrorResponse) => {
        console.error(error.message);
      }
    );
  }
}
