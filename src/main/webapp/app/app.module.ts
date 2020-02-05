import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { Task1HalfSharedModule } from 'app/shared/shared.module';
import { Task1HalfCoreModule } from 'app/core/core.module';
import { Task1HalfAppRoutingModule } from './app-routing.module';
import { Task1HalfHomeModule } from './home/home.module';
import { Task1HalfEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    Task1HalfSharedModule,
    Task1HalfCoreModule,
    Task1HalfHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    Task1HalfEntityModule,
    Task1HalfAppRoutingModule
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent]
})
export class Task1HalfAppModule {}
