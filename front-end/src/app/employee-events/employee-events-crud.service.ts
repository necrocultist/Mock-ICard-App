import { EventEmitter, Injectable, Output} from '@angular/core';
import { NgForm } from "@angular/forms";
import { Employee } from "../../models/employee";
import { HttpErrorResponse } from "@angular/common/http";
import { EmployeeService } from "../services/employee.service";
import {EmployeeEventService} from "../services/employee-event.service";
import {EmployeeEvent} from "../../models/employee-event";

@Injectable({
  providedIn: 'root'
})
export class EmployeeEventsCrudService {
  @Output() employeeEventAdded = new EventEmitter<string>();
  constructor(private employeeEventService: EmployeeEventService) {}

  public addEvent(employee: Employee, addForm: NgForm): void {
    document.getElementById('add-event-form')?.click();
    this.employeeEventService.createEmployeeEvent(employee.id, addForm.value).subscribe(
      (response: EmployeeEvent) => {
        this.employeeEventAdded.emit("Event added successfully to " + employee.name + " " + employee.surname + "!");
        addForm.reset();
      },
      (error: HttpErrorResponse) => {
        console.error(error.message);
        addForm.reset();
      }
    );
  }
}
