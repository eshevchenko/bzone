package zone.blob.web.rest;

import zone.blob.service.DataSourceService;
import zone.blob.web.rest.errors.BadRequestAlertException;
import zone.blob.service.dto.DataSourceDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link zone.blob.domain.DataSource}.
 */
@RestController
@RequestMapping("/api")
public class DataSourceResource {

    private final Logger log = LoggerFactory.getLogger(DataSourceResource.class);

    private static final String ENTITY_NAME = "dataSource";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataSourceService dataSourceService;

    public DataSourceResource(DataSourceService dataSourceService) {
        this.dataSourceService = dataSourceService;
    }

    /**
     * {@code POST  /data-sources} : Create a new dataSource.
     *
     * @param dataSourceDTO the dataSourceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataSourceDTO, or with status {@code 400 (Bad Request)} if the dataSource has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-sources")
    public ResponseEntity<DataSourceDTO> createDataSource(@RequestBody DataSourceDTO dataSourceDTO) throws URISyntaxException {
        log.debug("REST request to save DataSource : {}", dataSourceDTO);
        if (dataSourceDTO.getId() != null) {
            throw new BadRequestAlertException("A new dataSource cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataSourceDTO result = dataSourceService.save(dataSourceDTO);
        return ResponseEntity.created(new URI("/api/data-sources/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-sources} : Updates an existing dataSource.
     *
     * @param dataSourceDTO the dataSourceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataSourceDTO,
     * or with status {@code 400 (Bad Request)} if the dataSourceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataSourceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-sources")
    public ResponseEntity<DataSourceDTO> updateDataSource(@RequestBody DataSourceDTO dataSourceDTO) throws URISyntaxException {
        log.debug("REST request to update DataSource : {}", dataSourceDTO);
        if (dataSourceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataSourceDTO result = dataSourceService.save(dataSourceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataSourceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /data-sources} : get all the dataSources.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataSources in body.
     */
    @GetMapping("/data-sources")
    public List<DataSourceDTO> getAllDataSources() {
        log.debug("REST request to get all DataSources");
        return dataSourceService.findAll();
    }

    /**
     * {@code GET  /data-sources/:id} : get the "id" dataSource.
     *
     * @param id the id of the dataSourceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataSourceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-sources/{id}")
    public ResponseEntity<DataSourceDTO> getDataSource(@PathVariable Long id) {
        log.debug("REST request to get DataSource : {}", id);
        Optional<DataSourceDTO> dataSourceDTO = dataSourceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataSourceDTO);
    }

    /**
     * {@code DELETE  /data-sources/:id} : delete the "id" dataSource.
     *
     * @param id the id of the dataSourceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-sources/{id}")
    public ResponseEntity<Void> deleteDataSource(@PathVariable Long id) {
        log.debug("REST request to delete DataSource : {}", id);
        dataSourceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
