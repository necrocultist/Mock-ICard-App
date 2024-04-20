import {Injectable} from '@angular/core';
import {Employee} from "../../models/employee";

@Injectable({
  providedIn: 'root'
})
export class EmployeeFilter {

  constructor() {
  }

  public sortEmployees(employees: Employee[], key: keyof Employee): Employee[] {
    return employees.sort((a: Employee, b: Employee) => {
      return a[key] > b[key] ? 1 : -1;
    });
  }

  public searchEmployees(employees: Employee[], searchTerm: string): Employee[] {
    if (!searchTerm) {
      return employees;
    }

    searchTerm = searchTerm.trim().toLowerCase();
    return employees.filter(employee =>
      employee.name.toLowerCase().includes(searchTerm) ||
      employee.surname.toLowerCase().includes(searchTerm) ||
      employee.company.toLowerCase().includes(searchTerm) ||
      employee.building.toLowerCase().includes(searchTerm)
    );
  }
}
