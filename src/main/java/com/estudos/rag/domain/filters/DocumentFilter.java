package com.estudos.rag.domain.filters;

import com.estudos.rag.domain.entity.Document;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import lombok.*;
import org.springframework.data.jpa.domain.Specification;



@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Builder
public class DocumentFilter {
  private Long userId;

  public Specification<Document> get() {
    return (root, query, cb) -> {

      Predicate userFilter = filterByUser(cb, root, userId);

      return cb.and(userFilter);
    };
  }

  public Predicate filterByUser(CriteriaBuilder cb, From<?, ?> root, Long userId) {
    return equal(cb, root.get("userId"), userId);
  }

  protected Predicate equal(CriteriaBuilder cb, Expression<?> x, Object y) {
    return y == null ? dontFilter(cb) : cb.equal(x, y);
  }

  protected Predicate dontFilter(CriteriaBuilder cb) {
    return cb.isTrue(cb.literal(true));
  }
}
