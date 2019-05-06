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

import zone.blob.domain.DataSourceColumn;
import zone.blob.domain.*; // for static metamodels
import zone.blob.repository.DataSourceColumnRepository;
import zone.blob.service.dto.DataSourceColumnCriteria;
import zone.blob.service.dto.DataSourceColumnDTO;
import zone.blob.service.mapper.DataSourceColumnMapper;

/**
 * Service for executing complex queries for {@link DataSourceColumn} entities in the database.
 * The main input is a {@link DataSourceColumnCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DataSourceColumnDTO} or a {@link Page} of {@link DataSourceColumnDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DataSourceColumnQueryService extends QueryService<DataSourceColumn> {

    private final Logger log = LoggerFactory.getLogger(DataSourceColumnQueryService.class);

    private final DataSourceColumnRepository dataSourceColumnRepository;

    private final DataSourceColumnMapper dataSourceColumnMapper;

    public DataSourceColumnQueryService(DataSourceColumnRepository dataSourceColumnRepository, DataSourceColumnMapper dataSourceColumnMapper) {
        this.dataSourceColumnRepository = dataSourceColumnRepository;
        this.dataSourceColumnMapper = dataSourceColumnMapper;
    }

    /**
     * Return a {@link List} of {@link DataSourceColumnDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DataSourceColumnDTO> findByCriteria(DataSourceColumnCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DataSourceColumn> specification = createSpecification(criteria);
        return dataSourceColumnMapper.toDto(dataSourceColumnRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DataSourceColumnDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DataSourceColumnDTO> findByCriteria(DataSourceColumnCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DataSourceColumn> specification = createSpecification(criteria);
        return dataSourceColumnRepository.findAll(specification, page)
            .map(dataSourceColumnMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DataSourceColumnCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DataSourceColumn> specification = createSpecification(criteria);
        return dataSourceColumnRepository.count(specification);
    }

    /**
     * Function to convert DataSourceColumnCriteria to a {@link Specification}.
     */
    private Specification<DataSourceColumn> createSpecification(DataSourceColumnCriteria criteria) {
        Specification<DataSourceColumn> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DataSourceColumn_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DataSourceColumn_.name));
            }
            if (criteria.getDataType() != null) {
                specification = specification.and(buildSpecification(criteria.getDataType(), DataSourceColumn_.dataType));
            }
            if (criteria.getDataSourceId() != null) {
                specification = specification.and(buildSpecification(criteria.getDataSourceId(),
                    root -> root.join(DataSourceColumn_.dataSource, JoinType.LEFT).get(DataSource_.id)));
            }
        }
        return specification;
    }
}
