package zone.blob.web.rest;

import zone.blob.service.DataSourceColumnService;
import zone.blob.web.rest.errors.BadRequestAlertException;
import zone.blob.service.dto.DataSourceColumnDTO;
import zone.blob.service.dto.DataSourceColumnCriteria;
import zone.blob.service.DataSourceColumnQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link zone.blob.domain.DataSourceColumn}.
 */
@RestController
@RequestMapping("/api")
public class DataSourceColumnResource {

    private final Logger log = LoggerFactory.getLogger(DataSourceColumnResource.class);

    private static final String ENTITY_NAME = "dataSourceColumn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataSourceColumnService dataSourceColumnService;

    private final DataSourceColumnQueryService dataSourceColumnQueryService;

    public DataSourceColumnResource(DataSourceColumnService dataSourceColumnService, DataSourceColumnQueryService dataSourceColumnQueryService) {
        this.dataSourceColumnService = dataSourceColumnService;
        this.dataSourceColumnQueryService = dataSourceColumnQueryService;
    }

    /**
     * {@code POST  /data-source-columns} : Create a new dataSourceColumn.
     *
     * @param dataSourceColumnDTO the dataSourceColumnDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataSourceColumnDTO, or with status {@code 400 (Bad Request)} if the dataSourceColumn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-source-columns")
    public ResponseEntity<DataSourceColumnDTO> createDataSourceColumn(@Valid @RequestBody DataSourceColumnDTO dataSourceColumnDTO) throws URISyntaxException {
        log.debug("REST request to save DataSourceColumn : {}", dataSourceColumnDTO);
        if (dataSourceColumnDTO.getId() != null) {
            throw new BadRequestAlertException("A new dataSourceColumn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataSourceColumnDTO result = dataSourceColumnService.save(dataSourceColumnDTO);
        return ResponseEntity.created(new URI("/api/data-source-columns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-source-columns} : Updates an existing dataSourceColumn.
     *
     * @param dataSourceColumnDTO the dataSourceColumnDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataSourceColumnDTO,
     * or with status {@code 400 (Bad Request)} if the dataSourceColumnDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataSourceColumnDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-source-columns")
    public ResponseEntity<DataSourceColumnDTO> updateDataSourceColumn(@Valid @RequestBody DataSourceColumnDTO dataSourceColumnDTO) throws URISyntaxException {
        log.debug("REST request to update DataSourceColumn : {}", dataSourceColumnDTO);
        if (dataSourceColumnDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataSourceColumnDTO result = dataSourceColumnService.save(dataSourceColumnDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataSourceColumnDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /data-source-columns} : get all the dataSourceColumns.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataSourceColumns in body.
     */
    @GetMapping("/data-source-columns")
    public ResponseEntity<List<DataSourceColumnDTO>> getAllDataSourceColumns(DataSourceColumnCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get DataSourceColumns by criteria: {}", criteria);
        Page<DataSourceColumnDTO> page = dataSourceColumnQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /data-source-columns/count} : count all the dataSourceColumns.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/data-source-columns/count")
    public ResponseEntity<Long> countDataSourceColumns(DataSourceColumnCriteria criteria) {
        log.debug("REST request to count DataSourceColumns by criteria: {}", criteria);
        return ResponseEntity.ok().body(dataSourceColumnQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /data-source-columns/:id} : get the "id" dataSourceColumn.
     *
     * @param id the id of the dataSourceColumnDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataSourceColumnDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-source-columns/{id}")
    public ResponseEntity<DataSourceColumnDTO> getDataSourceColumn(@PathVariable Long id) {
        log.debug("REST request to get DataSourceColumn : {}", id);
        Optional<DataSourceColumnDTO> dataSourceColumnDTO = dataSourceColumnService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataSourceColumnDTO);
    }

    /**
     * {@code DELETE  /data-source-columns/:id} : delete the "id" dataSourceColumn.
     *
     * @param id the id of the dataSourceColumnDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-source-columns/{id}")
    public ResponseEntity<Void> deleteDataSourceColumn(@PathVariable Long id) {
        log.debug("REST request to delete DataSourceColumn : {}", id);
        dataSourceColumnService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
