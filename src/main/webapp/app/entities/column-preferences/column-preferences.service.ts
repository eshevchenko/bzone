import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IColumnPreferences } from 'app/shared/model/column-preferences.model';

type EntityResponseType = HttpResponse<IColumnPreferences>;
type EntityArrayResponseType = HttpResponse<IColumnPreferences[]>;

@Injectable({ providedIn: 'root' })
export class ColumnPreferencesService {
  public resourceUrl = SERVER_API_URL + 'api/column-preferences';

  constructor(protected http: HttpClient) {}

  create(columnPreferences: IColumnPreferences): Observable<EntityResponseType> {
    return this.http.post<IColumnPreferences>(this.resourceUrl, columnPreferences, { observe: 'response' });
  }

  update(columnPreferences: IColumnPreferences): Observable<EntityResponseType> {
    return this.http.put<IColumnPreferences>(this.resourceUrl, columnPreferences, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IColumnPreferences>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IColumnPreferences[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
