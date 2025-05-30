import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SessionService } from '../../../core/services/session.service';
import { Session } from '../../../../mockData/sessions';

@Component({
  selector: 'app-session-report',
  standalone: false,
  templateUrl: './session-report.component.html',
  styleUrls: ['./session-report.component.css']
})
export class SessionReportComponent implements OnInit {
  sessionId: string | null = null;
  session?: Session;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private sessionService: SessionService
  ) {}

  ngOnInit() {
    this.sessionId = this.route.snapshot.paramMap.get('sessionId');
    if (this.sessionId) {
      this.sessionService.getById(this.sessionId).subscribe(s => {
        if (s) this.session = s;
        else this.router.navigate(['/dashboard']);
      });
    }
  }

  downloadReport() {
    alert('Descargando informe de sesión ' + this.sessionId);
  }
}