import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EmployeeEvent } from '../models/employee-event';
import {environment} from "../environments/environment";

@Injectable({
  providedIn: 'root'
})
export class EmployeeEventService {

  private baseUrl = environment.apiBaseUrl + '/employee/';

  constructor(private http: HttpClient) { }

  getAllEmployeeEvents(employeeId: string): Observable<EmployeeEvent[]> {
    return this.http.get<EmployeeEvent[]>(`${this.baseUrl}${employeeId}/events`);
  }

  getEmployeeEventById(eventId: string): Observable<EmployeeEvent> {
    return this.http.get<EmployeeEvent>(`${this.baseUrl}events/${eventId}`);
  }

  createEmployeeEvent(employeeId: string, employeeEvent: EmployeeEvent): Observable<EmployeeEvent> {
    return this.http.post<EmployeeEvent>(`${this.baseUrl}${employeeId}/events`, employeeEvent);
  }

  deleteEmployeeEvent(eventId: string): Observable<void> {
    return this.http.delete<void>(`${this.baseUrl}events/${eventId}`);
  }
}
