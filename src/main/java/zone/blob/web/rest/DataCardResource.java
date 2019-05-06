package zone.blob.web.rest;

import zone.blob.service.DataCardService;
import zone.blob.web.rest.errors.BadRequestAlertException;
import zone.blob.service.dto.DataCardDTO;
import zone.blob.service.dto.DataCardCriteria;
import zone.blob.service.DataCardQueryService;

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
 * REST controller for managing {@link zone.blob.domain.DataCard}.
 */
@RestController
@RequestMapping("/api")
public class DataCardResource {

    private final Logger log = LoggerFactory.getLogger(DataCardResource.class);

    private static final String ENTITY_NAME = "dataCard";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DataCardService dataCardService;

    private final DataCardQueryService dataCardQueryService;

    public DataCardResource(DataCardService dataCardService, DataCardQueryService dataCardQueryService) {
        this.dataCardService = dataCardService;
        this.dataCardQueryService = dataCardQueryService;
    }

    /**
     * {@code POST  /data-cards} : Create a new dataCard.
     *
     * @param dataCardDTO the dataCardDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dataCardDTO, or with status {@code 400 (Bad Request)} if the dataCard has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/data-cards")
    public ResponseEntity<DataCardDTO> createDataCard(@Valid @RequestBody DataCardDTO dataCardDTO) throws URISyntaxException {
        log.debug("REST request to save DataCard : {}", dataCardDTO);
        if (dataCardDTO.getId() != null) {
            throw new BadRequestAlertException("A new dataCard cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DataCardDTO result = dataCardService.save(dataCardDTO);
        return ResponseEntity.created(new URI("/api/data-cards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /data-cards} : Updates an existing dataCard.
     *
     * @param dataCardDTO the dataCardDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dataCardDTO,
     * or with status {@code 400 (Bad Request)} if the dataCardDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dataCardDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/data-cards")
    public ResponseEntity<DataCardDTO> updateDataCard(@Valid @RequestBody DataCardDTO dataCardDTO) throws URISyntaxException {
        log.debug("REST request to update DataCard : {}", dataCardDTO);
        if (dataCardDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DataCardDTO result = dataCardService.save(dataCardDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dataCardDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /data-cards} : get all the dataCards.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dataCards in body.
     */
    @GetMapping("/data-cards")
    public ResponseEntity<List<DataCardDTO>> getAllDataCards(DataCardCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get DataCards by criteria: {}", criteria);
        Page<DataCardDTO> page = dataCardQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /data-cards/count} : count all the dataCards.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/data-cards/count")
    public ResponseEntity<Long> countDataCards(DataCardCriteria criteria) {
        log.debug("REST request to count DataCards by criteria: {}", criteria);
        return ResponseEntity.ok().body(dataCardQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /data-cards/:id} : get the "id" dataCard.
     *
     * @param id the id of the dataCardDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dataCardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/data-cards/{id}")
    public ResponseEntity<DataCardDTO> getDataCard(@PathVariable Long id) {
        log.debug("REST request to get DataCard : {}", id);
        Optional<DataCardDTO> dataCardDTO = dataCardService.findOne(id);
        return ResponseUtil.wrapOrNotFound(dataCardDTO);
    }

    /**
     * {@code DELETE  /data-cards/:id} : delete the "id" dataCard.
     *
     * @param id the id of the dataCardDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/data-cards/{id}")
    public ResponseEntity<Void> deleteDataCard(@PathVariable Long id) {
        log.debug("REST request to delete DataCard : {}", id);
        dataCardService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
