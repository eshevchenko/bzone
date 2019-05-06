package zone.blob.web.rest;

import zone.blob.service.DataCardColumnService;
import zone.blob.web.rest.errors.BadRequestAlertException;
import zone.blob.service.dto.DataCardColumnDTO;

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
 * REST controller for managing {@link zone.blob.domain.DataCardColumn}.
 */
@RestController
@RequestMapping("/api")
public class DataCardColumnResource {

    private final Logger log = LoggerFactory.getLogger(DataCardColumnResource.class);

    private static final String ENTITY_NAME = "dataCardColumn";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataCardColumnService dataCardColumnService;

    public DataCardColumnResource(DataCardColumnService dataCardColumnService) {
        this.dataCardColumnService = dataCardColumnService;
    }

    /**
     * {@code POST  /data-card-columns} : Create a new dataCardColumn.
     *
     * @param dataCardColumnDTO the dataCardColumnDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataCardColumnDTO, or with status {@code 400 (Bad Request)} if the dataCardColumn has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-card-columns")
    public ResponseEntity<DataCardColumnDTO> createDataCardColumn(@Valid @RequestBody DataCardColumnDTO dataCardColumnDTO) throws URISyntaxException {
        log.debug("REST request to save DataCardColumn : {}", dataCardColumnDTO);
        if (dataCardColumnDTO.getId() != null) {
            throw new BadRequestAlertException("A new dataCardColumn cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataCardColumnDTO result = dataCardColumnService.save(dataCardColumnDTO);
        return ResponseEntity.created(new URI("/api/data-card-columns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-card-columns} : Updates an existing dataCardColumn.
     *
     * @param dataCardColumnDTO the dataCardColumnDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataCardColumnDTO,
     * or with status {@code 400 (Bad Request)} if the dataCardColumnDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataCardColumnDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-card-columns")
    public ResponseEntity<DataCardColumnDTO> updateDataCardColumn(@Valid @RequestBody DataCardColumnDTO dataCardColumnDTO) throws URISyntaxException {
        log.debug("REST request to update DataCardColumn : {}", dataCardColumnDTO);
        if (dataCardColumnDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataCardColumnDTO result = dataCardColumnService.save(dataCardColumnDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataCardColumnDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /data-card-columns} : get all the dataCardColumns.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataCardColumns in body.
     */
    @GetMapping("/data-card-columns")
    public List<DataCardColumnDTO> getAllDataCardColumns() {
        log.debug("REST request to get all DataCardColumns");
        return dataCardColumnService.findAll();
    }

    /**
     * {@code GET  /data-card-columns/:id} : get the "id" dataCardColumn.
     *
     * @param id the id of the dataCardColumnDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataCardColumnDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-card-columns/{id}")
    public ResponseEntity<DataCardColumnDTO> getDataCardColumn(@PathVariable Long id) {
        log.debug("REST request to get DataCardColumn : {}", id);
        Optional<DataCardColumnDTO> dataCardColumnDTO = dataCardColumnService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataCardColumnDTO);
    }

    /**
     * {@code DELETE  /data-card-columns/:id} : delete the "id" dataCardColumn.
     *
     * @param id the id of the dataCardColumnDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-card-columns/{id}")
    public ResponseEntity<Void> deleteDataCardColumn(@PathVariable Long id) {
        log.debug("REST request to delete DataCardColumn : {}", id);
        dataCardColumnService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
