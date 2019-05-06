import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDataSourceColumn } from 'app/shared/model/data-source-column.model';

type EntityResponseType = HttpResponse<IDataSourceColumn>;
type EntityArrayResponseType = HttpResponse<IDataSourceColumn[]>;

@Injectable({ providedIn: 'root' })
export class DataSourceColumnService {
  public resourceUrl = SERVER_API_URL + 'api/data-source-columns';

  constructor(protected http: HttpClient) {}

  create(dataSourceColumn: IDataSourceColumn): Observable<EntityResponseType> {
    return this.http.post<IDataSourceColumn>(this.resourceUrl, dataSourceColumn, { observe: 'response' });
  }

  update(dataSourceColumn: IDataSourceColumn): Observable<EntityResponseType> {
    return this.http.put<IDataSourceColumn>(this.resourceUrl, dataSourceColumn, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDataSourceColumn>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDataSourceColumn[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
