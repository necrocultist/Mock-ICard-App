import { ApplicationConfig } from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {EmployeeService} from "../services/employee.service";
import {EmployeeEventService} from "../services/employee-event.service";
import {provideHttpClient} from "@angular/common/http";

export const appConfig: ApplicationConfig = {
  providers: [provideRouter(routes), EmployeeService, EmployeeEventService, provideHttpClient()]
};
