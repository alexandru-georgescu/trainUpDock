import { Component, Input, ChangeDetectionStrategy } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'slide-panel',
  templateUrl: './slide-panel.component.html',
  styleUrls: ['./slide-panel.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
  animations: [
    trigger('slide', [
      state('top', style({ transform: 'translateY(-50%)' })),
      state('bottom', style({ transform: 'translateY(0%)' })),
      transition('* => *', animate(300))
  ])]
})

export class SlidePanelComponent {
  @Input() activePane: PaneType = 'top';
  }

type PaneType = 'top' | 'bottom';