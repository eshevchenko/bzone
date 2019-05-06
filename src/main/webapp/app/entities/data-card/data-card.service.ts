import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDataCard } from 'app/shared/model/data-card.model';

type EntityResponseType = HttpResponse<IDataCard>;
type EntityArrayResponseType = HttpResponse<IDataCard[]>;

@Injectable({ providedIn: 'root' })
export class DataCardService {
  public resourceUrl = SERVER_API_URL + 'api/data-cards';

  constructor(protected http: HttpClient) {}

  create(dataCard: IDataCard): Observable<EntityResponseType> {
    return this.http.post<IDataCard>(this.resourceUrl, dataCard, { observe: 'response' });
  }

  update(dataCard: IDataCard): Observable<EntityResponseType> {
    return this.http.put<IDataCard>(this.resourceUrl, dataCard, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IDataCard>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IDataCard[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
