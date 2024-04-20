import {Component, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {Employee} from '../models/employee';
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule, NgForm} from "@angular/forms";
import {Company} from "../enums/company.enum";
import {Building} from "../enums/building.enum";
import {EmployeeCrud} from "./employee/employee-crud";
import {EmployeeFilter} from './employee/employee-filter';
import {EmployeeService} from "../services/employee.service";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgForOf, FormsModule, NgIf],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  title = 'siemens-bootcamp-fe';

  public employees: Employee[] = [];
  public sortedEmployees: Employee[] = [];

  public companies = Object.values(Company).filter(value => typeof value === 'string');
  public buildings = Object.values(Building).filter(value => typeof value === 'string');
  public editedEmployee: Employee = {} as Employee;
  public deletedEmployee: Employee = {} as Employee;

  public canNotify: boolean = true;
  private originalEmployees: Employee[] = [];

  constructor( private employeeService: EmployeeService,
    private employeeModals: EmployeeCrud,
    private employeeFilterService: EmployeeFilter
  ) {
  }
  ngOnInit() {
    this.getEmployees();
    this.subscribeToEmployeeModalsEvents();
    console.log(this.buildings);
  }

  private subscribeToEmployeeModalsEvents(): void {
    this.employeeModals.employeeChanged.subscribe(() => this.getEmployees());
  }

  public getEmployees(): void {
    this.employeeService.getAllEmployees().subscribe(
      (data: Employee[]) => {
        console.log(data);
        this.employees = data;
        this.canNotify = false;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
        this.canNotify = false;
      }
    );
  }

  public onAddEmployee(addForm: NgForm): void {
    this.employeeModals.addEmployee(addForm);
  }

  public onUpdateEmployee(employee: Employee): void {
    this.employeeModals.updateEmployee(employee);
  }

  public onDeleteEmployee(employee: Employee): void {
    this.employeeModals.deleteEmployee(employee);
    this.getEmployees();
  }

  public onSortEmployees(key: keyof Employee): void {
    this.sortedEmployees = this.employeeFilterService.sortEmployees(this.employees, key);
  }

  public onSearchEmployees(key: string): void {
    if (this.originalEmployees.length === 0) {
      this.getEmployees();
      this.originalEmployees = [...this.employees]; // Store a copy of the original employees
    } else {
      this.employees = [...this.originalEmployees]; // Restore from original
    }

    this.employees = this.employeeFilterService.searchEmployees(this.originalEmployees, key);

    this.canNotify = this.employees.length === 0 && !!key.trim();
  }

  public onOpenModal(employee: Employee | null, mode: string): void {
    const container = document.getElementById('main-container');
    const button = document.createElement('button');
    button.type = 'button';
    button.style.display = 'none';
    button.setAttribute('data-toggle', 'modal');

    if (mode === 'add') {
      button.setAttribute('data-target', '#addEmployeeModal');
    }

    if (mode === 'edit' && employee) {
      this.editedEmployee = employee;
      button.setAttribute('data-target', '#updateEmployeeModal');
    }

    if (mode === 'delete' && employee) {
      this.deletedEmployee = employee;
      button.setAttribute('data-target', '#deleteEmployeeModal');
    }

    container?.appendChild(button);
    button.click();
  }
}
