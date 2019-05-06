package zone.blob.web.rest;

import zone.blob.service.DataSourceFileService;
import zone.blob.web.rest.errors.BadRequestAlertException;
import zone.blob.service.dto.DataSourceFileDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link zone.blob.domain.DataSourceFile}.
 */
@RestController
@RequestMapping("/api")
public class DataSourceFileResource {

    private final Logger log = LoggerFactory.getLogger(DataSourceFileResource.class);

    private static final String ENTITY_NAME = "dataSourceFile";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataSourceFileService dataSourceFileService;

    public DataSourceFileResource(DataSourceFileService dataSourceFileService) {
        this.dataSourceFileService = dataSourceFileService;
    }

    /**
     * {@code POST  /data-source-files} : Create a new dataSourceFile.
     *
     * @param dataSourceFileDTO the dataSourceFileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataSourceFileDTO, or with status {@code 400 (Bad Request)} if the dataSourceFile has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-source-files")
    public ResponseEntity<DataSourceFileDTO> createDataSourceFile(@Valid @RequestBody DataSourceFileDTO dataSourceFileDTO) throws URISyntaxException {
        log.debug("REST request to save DataSourceFile : {}", dataSourceFileDTO);
        if (dataSourceFileDTO.getId() != null) {
            throw new BadRequestAlertException("A new dataSourceFile cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataSourceFileDTO result = dataSourceFileService.save(dataSourceFileDTO);
        return ResponseEntity.created(new URI("/api/data-source-files/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-source-files} : Updates an existing dataSourceFile.
     *
     * @param dataSourceFileDTO the dataSourceFileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataSourceFileDTO,
     * or with status {@code 400 (Bad Request)} if the dataSourceFileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataSourceFileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-source-files")
    public ResponseEntity<DataSourceFileDTO> updateDataSourceFile(@Valid @RequestBody DataSourceFileDTO dataSourceFileDTO) throws URISyntaxException {
        log.debug("REST request to update DataSourceFile : {}", dataSourceFileDTO);
        if (dataSourceFileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataSourceFileDTO result = dataSourceFileService.save(dataSourceFileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataSourceFileDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /data-source-files} : get all the dataSourceFiles.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataSourceFiles in body.
     */
    @GetMapping("/data-source-files")
    public List<DataSourceFileDTO> getAllDataSourceFiles() {
        log.debug("REST request to get all DataSourceFiles");
        return dataSourceFileService.findAll();
    }

    /**
     * {@code GET  /data-source-files/:id} : get the "id" dataSourceFile.
     *
     * @param id the id of the dataSourceFileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataSourceFileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-source-files/{id}")
    public ResponseEntity<DataSourceFileDTO> getDataSourceFile(@PathVariable Long id) {
        log.debug("REST request to get DataSourceFile : {}", id);
        Optional<DataSourceFileDTO> dataSourceFileDTO = dataSourceFileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataSourceFileDTO);
    }

    /**
     * {@code DELETE  /data-source-files/:id} : delete the "id" dataSourceFile.
     *
     * @param id the id of the dataSourceFileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-source-files/{id}")
    public ResponseEntity<Void> deleteDataSourceFile(@PathVariable Long id) {
        log.debug("REST request to delete DataSourceFile : {}", id);
        dataSourceFileService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
