import { Component, OnInit } from '@angular/core';
import { ReportService, ReporteCaja } from '../../../core/services/report.service';
import { SessionService, WorkSession } from '../../../core/services/session.service';

@Component({
  selector: 'app-informe-caja',
  standalone: false,
  templateUrl: './informe-caja.component.html',
  styleUrls: ['./informe-caja.component.css']
})
export class InformeCajaComponent implements OnInit {
  sessions: WorkSession[] = [];
  selectedSessionId = '';
  report: ReporteCaja | null = null;

  constructor(
    private sessionService: SessionService,
    private reportService: ReportService
  ) {}

  ngOnInit(): void {
    this.sessionService.getSessions().subscribe(s => {
      this.sessions = s.filter(x => x.status === 'Cerrada');
    });
  }

  consultReport(): void {
    if (this.selectedSessionId) {
      this.reportService.getReport(this.selectedSessionId)
        .subscribe(r => this.report = r);
    }
  }
}
