package com.gmail.dmytro.backend;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import com.gmail.dmytro.backend.data.OrderState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.gmail.dmytro.backend.data.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Override
	@EntityGraph(value = "Order.allData", type = EntityGraphType.LOAD)
	Order findOne(Long id);

	@EntityGraph(value = "Order.gridData", type = EntityGraphType.LOAD)
	Page<Order> findBycreateDateAfter(LocalDate filterDate, Pageable pageable);

	@Override
	@EntityGraph(value = "Order.gridData", type = EntityGraphType.LOAD)
	Page<Order> findAll(Pageable pageable);

	@EntityGraph(value = "Order.gridData", type = EntityGraphType.LOAD)
	Page<Order> findByCustomerFullNameContainingIgnoreCase(String searchQuery, Pageable pageable);

	@EntityGraph(value = "Order.gridData", type = EntityGraphType.LOAD)
	Page<Order> findByCustomerFullNameContainingIgnoreCaseAndCreateDateAfter(String searchQuery, LocalDate createDate,
			Pageable pageable);

	long countBycreateDateAfterAndStateIn(LocalDate createDate, Collection<OrderState> states);

	long countBycreateDateAfter(LocalDate createDate);

	long countByCustomerFullNameContainingIgnoreCase(String searchQuery);

	long countByCustomerFullNameContainingIgnoreCaseAndCreateDateAfter(String searchQuery, LocalDate createDate);

	long countBycreateDate(LocalDate createDate);

	long countBycreateDateAndStateIn(LocalDate createDate, Collection<OrderState> state);

	long countByState(OrderState state);

	@Query("SELECT month(createDate) as month, count(*) as deliveries FROM OrderInfo o where o.state=?1 and year(createDate)=?2 group by month(createDate)")
	List<Object[]> countPerMonth(OrderState orderState, int year);

	//@Query("SELECT year(o.createDate) as y, month(o.createDate) as m, sum(oi.quantity*p.price) as deliveries FROM OrderInfo o JOIN o.items oi JOIN oi.product p where o.state=?1 and year(o.createDate)<=?2 AND year(o.createDate)>=(?2-3) group by year(o.createDate),month(o.createDate) order by y desc,month(o.createDate)")
	@Query("SELECT month(createDate) as month, count(*) as deliveries FROM OrderInfo o where o.state=?1 and year(createDate)=?2 group by month(createDate)")
	List<Object[]> sumPerMonthLastThreeYears(OrderState orderState, int year);

	@Query("SELECT day(createDate) as day, count(*) as deliveries FROM OrderInfo o where o.state=?1 and year(createDate)=?2 and month(createDate)=?3 group by day(createDate)")
	List<Object[]> countPerDay(OrderState orderState, int year, int month);

	//@Query("SELECT sum(oi.quantity),p FROM OrderInfo o JOIN o.items oi JOIN oi.product p WHERE o.state=?1 AND year(o.createDate)=?2 AND month(o.createDate)=?3 GROUP BY p.id ORDER BY p.id")
	@Query("SELECT day(createDate) as day, count(*) as deliveries FROM OrderInfo o where o.state=?1 and year(createDate)=?2 and month(createDate)=?3 group by day(createDate)")
	List<Object[]> countPerProduct(OrderState orderState, int year, int month);

}
