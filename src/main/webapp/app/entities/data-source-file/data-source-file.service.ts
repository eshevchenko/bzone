import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDataSourceFile } from 'app/shared/model/data-source-file.model';

type EntityResponseType = HttpResponse<IDataSourceFile>;
type EntityArrayResponseType = HttpResponse<IDataSourceFile[]>;

@Injectable({ providedIn: 'root' })
export class DataSourceFileService {
  public resourceUrl = SERVER_API_URL + 'api/data-source-files';

  constructor(protected http: HttpClient) {}

  create(dataSourceFile: IDataSourceFile): Observable<EntityResponseType> {
    return this.http.post<IDataSourceFile>(this.resourceUrl, dataSourceFile, { observe: 'response' });
  }

  update(dataSourceFile: IDataSourceFile): Observable<EntityResponseType> {
    return this.http.put<IDataSourceFile>(this.resourceUrl, dataSourceFile, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDataSourceFile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDataSourceFile[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
