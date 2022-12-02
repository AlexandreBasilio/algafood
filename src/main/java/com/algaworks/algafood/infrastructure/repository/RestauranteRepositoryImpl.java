package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepositoryQueries;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantesSpecs.comFreteGratis;
import static com.algaworks.algafood.infrastructure.repository.spec.RestaurantesSpecs.comNomeSemelhante;

@Repository
public class RestauranteRepositoryImpl implements RestauranteRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Autowired @Lazy
    private RestauranteRepository restauranteRepository;

    // o nome do metodo nao tem relevancia
//    @Override
//    public List<Restaurante> find(String nome,
//                                  BigDecimal taxaFreteInicial,
//                                  BigDecimal taxaFreteFinal) {
//
//        // java 10 .. para declarar variavel
//        var jpql = " from Restaurante where nome like :nome " +
//                " and taxaFrete between :taxaFreteInicial and :taxaFreteFinal";
//
//        return manager.createQuery(jpql, Restaurante.class)
//                .setParameter("nome", "%" + nome + "%")
//                .setParameter("taxaFreteInicial", taxaFreteInicial)
//                .setParameter("taxaFreteFinal", taxaFreteFinal).getResultList();
//    }

    // consulta dinamica JPQL
//    @Override
//    public List<Restaurante> find(String nome,
//                                  BigDecimal taxaFreteInicial,
//                                  BigDecimal taxaFreteFinal) {
//
//        System.out.println("nome=" + nome +  " taxaFreteInicial=" + taxaFreteInicial + " taxaFreteFinal=" + taxaFreteFinal);
//
//        // java 10 .. para declarar variavel
//        var jpql = new StringBuilder();
//        var parametros = new HashMap<String, Object>();
//
//        jpql.append(" from Restaurante where 0 = 0 ");
//        if  (StringUtils.hasLength(nome)) {
//            jpql.append(" and nome like :nome " );
//            parametros.put("nome", "%" + nome + "%");
//        }
//        if  (taxaFreteInicial != null) {
//            jpql.append(" and taxaFrete >=  :taxaFreteInicial "  );
//            parametros.put("taxaFreteInicial", taxaFreteInicial);
//        }
//        if  (taxaFreteFinal != null) {
//            jpql.append(" and taxaFrete <= :taxaFreteFinal "  );
//            parametros.put("taxaFreteFinal", taxaFreteFinal);
//        }
//        System.out.println("jpql="+ jpql);
//        System.out.println("parametros="+ parametros);
//
//        TypedQuery<Restaurante> query = manager
//                .createQuery(jpql.toString(), Restaurante.class);
//
//        parametros.forEach((chave, valor) -> query.setParameter(chave, valor)); //LAMBDA
////        for(Map.Entry<String, Object> entry : parametros.entrySet()) {
////            // entry.getKey();
////            // entry.getValue();
////
////            query.setParameter(entry.getKey(), entry.getValue());
////        }
//
//        return query.getResultList();
//
//    }

    // usando criteria
    // consultas complexas e dinamicas, complicando usando o JPQL
    // usando java para montar uma consulta..query SQL gerada
    public List<Restaurante> find(String nome,
                                 BigDecimal taxaFreteInicial,
                                  BigDecimal taxaFreteFinal) {

        var predicates = new ArrayList<Predicate>();

        CriteriaBuilder builder = manager.getCriteriaBuilder();

        // cria a estrutura de uma query (a composicao das clasulas)
        // para conseguir uma instancia de CriteriaQuery precisa do CriteriaBuilder que eh um factory de CriteriaQuery
        CriteriaQuery<Restaurante> criteria = builder.createQuery(Restaurante.class);
        Root<Restaurante> root = criteria.from(Restaurante.class);

        if (StringUtils.hasText(nome)) {
            predicates.add(builder.like(root.get("nome") , "%" + nome + "%"));
        }

        if (taxaFreteInicial != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteInicial));
        }

        if (taxaFreteFinal != null) {
            predicates.add(builder.greaterThanOrEqualTo(root.get("taxaFrete"), taxaFreteFinal));
        }

        //String[] itemsArray = new String[predicates.size()];
        //itemsArray = predicates.toArray(new String[predicates.size()]);
        criteria.where(predicates.toArray(new Predicate[predicates.size()]));

        //criteria.where(predicates.toArray(new Predicate[0]));

        TypedQuery<Restaurante> query =  manager.createQuery(criteria);

        return query.getResultList();
    }

    @Override
    public List<Restaurante> findComFreteGratis(String nome) {
        return restauranteRepository.findAll(comFreteGratis()
                .and(comNomeSemelhante(nome)));
    }


}
