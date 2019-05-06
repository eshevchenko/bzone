import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDataCardColumn } from 'app/shared/model/data-card-column.model';

type EntityResponseType = HttpResponse<IDataCardColumn>;
type EntityArrayResponseType = HttpResponse<IDataCardColumn[]>;

@Injectable({ providedIn: 'root' })
export class DataCardColumnService {
  public resourceUrl = SERVER_API_URL + 'api/data-card-columns';

  constructor(protected http: HttpClient) {}

  create(dataCardColumn: IDataCardColumn): Observable<EntityResponseType> {
    return this.http.post<IDataCardColumn>(this.resourceUrl, dataCardColumn, { observe: 'response' });
  }

  update(dataCardColumn: IDataCardColumn): Observable<EntityResponseType> {
    return this.http.put<IDataCardColumn>(this.resourceUrl, dataCardColumn, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDataCardColumn>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDataCardColumn[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
