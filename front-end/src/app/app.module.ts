import {NgModule} from "@angular/core";
import {BrowserModule} from "@angular/platform-browser";
import {HttpClientModule} from "@angular/common/http";
import {AppComponent} from "./app.component";
import {AppRoutingModule} from "./app-routing.module";
import {CommonModule} from "@angular/common";
import {EmployeeCrud} from "./employee/employee-crud";

@NgModule({
  declarations: [
  ],
  imports: [
    CommonModule,
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    AppComponent
  ],
  providers: [],
    bootstrap: []
})export class AppModule {
}
