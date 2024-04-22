import {Injectable, Output} from '@angular/core';
import {Employee} from "../../models/employee";

@Injectable({
  providedIn: 'root'
})
export class EmployeeFilter {

  private sortDirection: 'asc' | 'desc' = 'asc';

  constructor() {
  }

  public sortEmployees(employees: Employee[], key: keyof Employee): Employee[] {
    const sortedEmployees = this.performSort(employees, key);
    this.toggleSortDirection();
    return sortedEmployees;
  }

  private performSort(employees: Employee[], key: keyof Employee): Employee[] {
    return employees.sort((a: Employee, b: Employee) => {
      const valueA = String(a[key]).toLowerCase();
      const valueB = String(b[key]).toLowerCase();
      const comparison = valueA.localeCompare(valueB);
      return this.sortDirection === 'asc' ? comparison : -comparison;
    });
  }

  private toggleSortDirection(): void {
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
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
