package filters;

import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class Filter implements Specification {
    List<Condition> conditions;
    public Filter() {
        conditions = new ArrayList<>();
    }

    public boolean isEmpty(){
        return conditions.isEmpty();
    }
    public void addCondition(Condition condition) {
        this.conditions.add(condition);
    }

    @Override
    public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = buildPredicates(root, criteriaQuery, criteriaBuilder);
        return predicates.size() > 1
                ? criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]))
                : predicates.get(0);
    }

    private List<Predicate> buildPredicates(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        conditions.forEach(condition -> predicates.add(buildPredicate(condition, root, criteriaQuery, criteriaBuilder)));
        return predicates;
    }

    public Predicate buildPredicate(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        switch (condition.comparison) {
            case eq:
                return buildEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
            case gt:
                break;
            case lt:
                break;
            case ne:
                break;
            case isnull:
                break;
            case in:
                break;
            default:
                return buildEqualsPredicateToCriteria(condition, root, criteriaQuery, criteriaBuilder);
        }
        throw new RuntimeException();
    }

    private Predicate buildEqualsPredicateToCriteria(Condition condition, Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.equal(root.get(condition.field), condition.value);
    }
}