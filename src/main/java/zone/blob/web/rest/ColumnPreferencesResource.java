package zone.blob.web.rest;

import zone.blob.service.ColumnPreferencesService;
import zone.blob.web.rest.errors.BadRequestAlertException;
import zone.blob.service.dto.ColumnPreferencesDTO;

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
 * REST controller for managing {@link zone.blob.domain.ColumnPreferences}.
 */
@RestController
@RequestMapping("/api")
public class ColumnPreferencesResource {

    private final Logger log = LoggerFactory.getLogger(ColumnPreferencesResource.class);

    private static final String ENTITY_NAME = "columnPreferences";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ColumnPreferencesService columnPreferencesService;

    public ColumnPreferencesResource(ColumnPreferencesService columnPreferencesService) {
        this.columnPreferencesService = columnPreferencesService;
    }

    /**
     * {@code POST  /column-preferences} : Create a new columnPreferences.
     *
     * @param columnPreferencesDTO the columnPreferencesDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new columnPreferencesDTO, or with status {@code 400 (Bad Request)} if the columnPreferences has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/column-preferences")
    public ResponseEntity<ColumnPreferencesDTO> createColumnPreferences(@Valid @RequestBody ColumnPreferencesDTO columnPreferencesDTO) throws URISyntaxException {
        log.debug("REST request to save ColumnPreferences : {}", columnPreferencesDTO);
        if (columnPreferencesDTO.getId() != null) {
            throw new BadRequestAlertException("A new columnPreferences cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ColumnPreferencesDTO result = columnPreferencesService.save(columnPreferencesDTO);
        return ResponseEntity.created(new URI("/api/column-preferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /column-preferences} : Updates an existing columnPreferences.
     *
     * @param columnPreferencesDTO the columnPreferencesDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated columnPreferencesDTO,
     * or with status {@code 400 (Bad Request)} if the columnPreferencesDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the columnPreferencesDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/column-preferences")
    public ResponseEntity<ColumnPreferencesDTO> updateColumnPreferences(@Valid @RequestBody ColumnPreferencesDTO columnPreferencesDTO) throws URISyntaxException {
        log.debug("REST request to update ColumnPreferences : {}", columnPreferencesDTO);
        if (columnPreferencesDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ColumnPreferencesDTO result = columnPreferencesService.save(columnPreferencesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, columnPreferencesDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /column-preferences} : get all the columnPreferences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of columnPreferences in body.
     */
    @GetMapping("/column-preferences")
    public List<ColumnPreferencesDTO> getAllColumnPreferences() {
        log.debug("REST request to get all ColumnPreferences");
        return columnPreferencesService.findAll();
    }

    /**
     * {@code GET  /column-preferences/:id} : get the "id" columnPreferences.
     *
     * @param id the id of the columnPreferencesDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the columnPreferencesDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/column-preferences/{id}")
    public ResponseEntity<ColumnPreferencesDTO> getColumnPreferences(@PathVariable Long id) {
        log.debug("REST request to get ColumnPreferences : {}", id);
        Optional<ColumnPreferencesDTO> columnPreferencesDTO = columnPreferencesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(columnPreferencesDTO);
    }

    /**
     * {@code DELETE  /column-preferences/:id} : delete the "id" columnPreferences.
     *
     * @param id the id of the columnPreferencesDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/column-preferences/{id}")
    public ResponseEntity<Void> deleteColumnPreferences(@PathVariable Long id) {
        log.debug("REST request to delete ColumnPreferences : {}", id);
        columnPreferencesService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
