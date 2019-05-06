package zone.blob.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import zone.blob.domain.DataCard;
import zone.blob.domain.*; // for static metamodels
import zone.blob.repository.DataCardRepository;
import zone.blob.service.dto.DataCardCriteria;
import zone.blob.service.dto.DataCardDTO;
import zone.blob.service.mapper.DataCardMapper;

/**
 * Service for executing complex queries for {@link DataCard} entities in the database.
 * The main input is a {@link DataCardCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DataCardDTO} or a {@link Page} of {@link DataCardDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DataCardQueryService extends QueryService<DataCard> {

    private final Logger log = LoggerFactory.getLogger(DataCardQueryService.class);

    private final DataCardRepository dataCardRepository;

    private final DataCardMapper dataCardMapper;

    public DataCardQueryService(DataCardRepository dataCardRepository, DataCardMapper dataCardMapper) {
        this.dataCardRepository = dataCardRepository;
        this.dataCardMapper = dataCardMapper;
    }

    /**
     * Return a {@link List} of {@link DataCardDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DataCardDTO> findByCriteria(DataCardCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DataCard> specification = createSpecification(criteria);
        return dataCardMapper.toDto(dataCardRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DataCardDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DataCardDTO> findByCriteria(DataCardCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DataCard> specification = createSpecification(criteria);
        return dataCardRepository.findAll(specification, page)
            .map(dataCardMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DataCardCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DataCard> specification = createSpecification(criteria);
        return dataCardRepository.count(specification);
    }

    /**
     * Function to convert DataCardCriteria to a {@link Specification}.
     */
    private Specification<DataCard> createSpecification(DataCardCriteria criteria) {
        Specification<DataCard> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DataCard_.id));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildSpecification(criteria.getType(), DataCard_.type));
            }
            if (criteria.getStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getStatus(), DataCard_.status));
            }
            if (criteria.getDataSourceId() != null) {
                specification = specification.and(buildSpecification(criteria.getDataSourceId(),
                    root -> root.join(DataCard_.dataSource, JoinType.LEFT).get(DataSource_.id)));
            }
        }
        return specification;
    }
}
