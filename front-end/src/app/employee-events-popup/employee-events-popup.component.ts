import {Component, Inject, Input} from '@angular/core';
import {Employee} from "../../models/employee";
import {EmployeeEvent} from "../../models/employee-event";
import {EmployeeEventService} from "../services/employee-event.service";
import {HttpErrorResponse} from "@angular/common/http";
import {NgForOf, NgIf} from "@angular/common";
import {NgxPaginationModule} from "ngx-pagination";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-employee-events-popup',
  standalone: true,
  imports: [
    NgForOf,
    NgxPaginationModule,
    ReactiveFormsModule,
    FormsModule,
    MatButton,
    NgIf
  ],
  templateUrl: './employee-events-popup.component.html',
  styleUrl: './employee-events-popup.component.css'
})
export class EmployeeEventsPopupComponent {
  private employee: Employee = {} as Employee;
  protected employeeEvents: EmployeeEvent[] = [];

  protected itemPerPage: number = 3;
  protected currentPage: number = 1;

  protected selectedDay: string = '';
  protected entranceEvent: EmployeeEvent = {} as EmployeeEvent;
  protected exitEvent: EmployeeEvent = {} as EmployeeEvent;

  private sortDirection: 'asc' | 'desc' = 'asc';

  constructor(private employeeEventService: EmployeeEventService,
              public dialogRef: MatDialogRef<EmployeeEventsPopupComponent>,
              @Inject(MAT_DIALOG_DATA) public data: Employee,
              private dialog: MatDialog) {
    this.employee = data;
    this.fetchEmployeeEvents();
  }

  private fetchEmployeeEvents(): void {
    this.employeeEventService.getAllEmployeeEvents(this.employee.id).subscribe(
      (data: EmployeeEvent[]) => {
        this.employeeEvents = data;
      },
      (error: HttpErrorResponse) => {
        console.error(error.message);
      }
    );
  }

  public onChangeItemsPerPage() {
    this.currentPage = 1;
  }

  protected onSortEvents(key: keyof EmployeeEvent): void{
    this.employeeEvents = this.sortEvents(this.employeeEvents, key);
  }

  private sortEvents(employeeEvents: EmployeeEvent[], key: keyof EmployeeEvent): EmployeeEvent[] {
    const sortedEvents = this.performSort(employeeEvents, key);
    this.toggleSortDirection();
    return sortedEvents;
  }

  private performSort(employeeEvents: EmployeeEvent[], key: keyof EmployeeEvent): EmployeeEvent[] {
    return employeeEvents.sort((a: EmployeeEvent, b: EmployeeEvent) => {
      const valueA = String(a[key]).toLowerCase();
      const valueB = String(b[key]).toLowerCase();
      const comparison = valueA.localeCompare(valueB);
      return this.sortDirection === 'asc' ? comparison : -comparison;
    });
  }

  private toggleSortDirection(): void {
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
  }

  public closeDialog(): void {
    this.dialogRef.close();
  }

  public onEntranceExitInfo(selectedDay: string): void {
    if (selectedDay === null) {
      return;
    }
    this.selectedDay = selectedDay;

    this.employeeEventService.getEntranceExitEvents(this.employee.id, selectedDay).subscribe(
      (data: EmployeeEvent[]) => {
        if (data.length > 0) {
          this.entranceEvent = data[0]; // First event is entrance
          this.exitEvent = data[1]; // Second event is exit

          // Format the time for entrance and exit events
          this.entranceEvent.eventTime = this.formatTime(this.entranceEvent.eventTime);
          this.exitEvent.eventTime = this.formatTime(this.exitEvent.eventTime);
        }
      },
      (error: HttpErrorResponse) => {
        console.error(error.message);
      }
    );
  }

  private formatTime(timeValue: string): string {
    if (timeValue.length < 6) {
      return timeValue;
    }

    const hours = timeValue.substring(0, 2);
    const minutes = timeValue.substring(2, 4);

    return `${hours}:${minutes}`;
  }

  public resetEvents(): void {
    this.entranceEvent = {} as EmployeeEvent;
    this.exitEvent = {} as EmployeeEvent;
  }
}
