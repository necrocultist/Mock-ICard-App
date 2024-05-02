import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmployeeEventsPopupComponent } from './employee-events-popup.component';

describe('EmployeeEventsPopupComponent', () => {
  let component: EmployeeEventsPopupComponent;
  let fixture: ComponentFixture<EmployeeEventsPopupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EmployeeEventsPopupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EmployeeEventsPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
