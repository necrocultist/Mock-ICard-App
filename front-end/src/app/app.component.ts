import {Component} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {Employee} from '../models/employee';
import {EmployeeService} from "../services/employee.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgForOf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'siemens-bootcamp-fe';

  public employees: Employee[] = [];

  constructor(private employeeService: EmployeeService) {}

  ngOnInit() {
    this.getEmployees();
  }

  public getEmployees(): void {
    this.employeeService.getAllEmployees().subscribe(
      (data: Employee[]) => {
        this.employees = data;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }
}
