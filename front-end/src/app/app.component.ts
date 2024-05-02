import {Component, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {Employee} from '../models/employee';
import {NgForOf, NgIf} from "@angular/common";
import {FormsModule, NgForm} from "@angular/forms";
import {Company} from "../models/enums/company.enum";
import {Building} from "../models/enums/building.enum";
import {EmployeeCrudService} from "./employee/employee-crud.service";
import {EmployeeFilter} from './employee/employee-filter';
import {EmployeeService} from "./services/employee.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgxPaginationModule} from "ngx-pagination";
import {NotificationModule} from "./notification/notification.module";
import {EmployeeEventsCrudService} from "./employee-events/employee-events-crud.service";
import {MatDialog} from "@angular/material/dialog";
import {EmployeeEventsPopupComponent} from "./employee-events-popup/employee-events-popup.component";

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgForOf, FormsModule, NgIf, NgxPaginationModule, NotificationModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent implements OnInit {
  title = 'siemens-bootcamp-fe';

  protected employees: Employee[] = [];
  protected sortedEmployees: Employee[] = [];
  private originalEmployees: Employee[] = [];

  protected editedEmployee: Employee = {} as Employee;
  protected deletedEmployee: Employee = {} as Employee;

  protected companies = Object.values(Company).filter(value => typeof value === 'string');
  protected buildings = Object.values(Building).filter(value => typeof value === 'string');

  protected itemPerPage: number = 10;
  protected currentPage: number = 1;

  protected notificationMessage: string = '';
  protected showNotification: boolean = false;
  protected canNotify: boolean = false;

  constructor(private employeeService: EmployeeService,
              private employeeCrudService: EmployeeCrudService,
              private employeeFilterService: EmployeeFilter,
              private employeeEventsCrudService: EmployeeEventsCrudService,
              private dialog: MatDialog
  ) {
  }

  ngOnInit() {
    this.getEmployees();
    this.subscribeToEmployeeModalsEvents();
    this.canNotify = true;
  }

  private subscribeToEmployeeModalsEvents(): void {
    this.employeeCrudService.employeeChanged.subscribe(() => this.getEmployees());
    this.employeeCrudService.employeeChanged.subscribe((message: string) => this.showSuccessNotification(message));
    this.employeeEventsCrudService.employeeEventAdded.subscribe((message: string) => this.showSuccessNotification(message));
  }

  public getEmployees(): void {
    this.employeeService.getAllEmployees().subscribe(
      (data: Employee[]) => {
        this.employees = data;
      },
      (error: HttpErrorResponse) => {
        console.error(error.message);
      }
    );
  }

  public onAddEmployee(addForm: NgForm): void {
    this.employeeCrudService.addEmployee(addForm);
  }

  public onUpdateEmployee(employee: Employee): void {
    this.employeeCrudService.updateEmployee(employee);
    this.showSuccessNotification('Employee updated successfully');
  }

  public onDeleteEmployee(employee: Employee): void {
    this.employeeCrudService.deleteEmployee(employee);
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

  public onChangeItemsPerPage() {
    this.currentPage = 1;
  }

  public onGetEmployeeEvents(employee: Employee): void {
    const dialogRef = this.dialog.open(EmployeeEventsPopupComponent, {
      width: '1000px',
      data: employee
    });
  }

  public onAddEmployeeEvent(eventForm: NgForm): void {
    this.employeeEventsCrudService.addEvent(this.editedEmployee, eventForm);
  }

  private showSuccessNotification(message: string): void {
    this.notificationMessage = message;
    this.showNotification = true;
    setTimeout(() => {
      this.showNotification = false;
    }, 3000);
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

    if (mode === 'event' && employee) {
      this.editedEmployee = employee;
      button.setAttribute('data-target', '#addEventModal');
    }

    if (mode === 'seeEvents' && employee) {
      this.editedEmployee = employee;
        button.setAttribute('data-target', '#employeeEventsPopupModal');
    }

    container?.appendChild(button);
    button.click();
  }

  public returnToOriginal(): void {
    window.location.reload();
  }
}
