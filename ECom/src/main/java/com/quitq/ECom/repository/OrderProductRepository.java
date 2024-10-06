package com.quitq.ECom.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.quitq.ECom.model.Exchange;
import com.quitq.ECom.model.Order;
import com.quitq.ECom.model.OrderProduct;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.Return;

import jakarta.transaction.Transactional;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Integer> {
	
	@Transactional
	@Modifying
	@Query("update OrderProduct op set op.quantity = op.quantity+1 where op=?1")
	int addProductCount(OrderProduct orderProduct);
	
@Query("select p from OrderProduct op join op.product p where p.v.user.username=?1")
	List<Product> findByUserName(String userName);
@Query("select p from Product p where p.id not in(select pro.id from OrderProduct op join op.product pro where pro.v.user.username=?1) and p.v.user.username=?1")
List<Product> findByUsernameUnordered(String userName);
@Query("select o.id,o.status,op.quantity,op.amountPayable,op.totalAmount,op.discount,p.title,p.price,p.discount,o.orderPlacedTime from OrderProduct op join op.product p join op.order o where p.v.user.username=?1")
List<Object[]> getOrderedProductDetails(String userName);
@Query("select count(o.id) from OrderProduct op join op.order o join op.product p where p.v.user.username=?1 and month(o.orderPlacedTime)=?2 group by month(o.orderPlacedTime)")
Integer getNumberOfOrdersReceivedByMonth(String userName, int month);
@Query("select count(o.id) from OrderProduct op join op.order o join op.product p where p.v.user.username=?1 and date(o.orderPlacedTime)=?2 group by date(o.orderPlacedTime)")

Integer getNumberOfOrdersReceivedByDate(String userName, LocalDate dateTime);
@Query("select sum(op.quantity) from OrderProduct op join op.order o join op.product p where p.v.user.username=?1 and month(o.orderPlacedTime)=?2 group by month(o.orderPlacedTime)")

Integer noOfProductOrderedMonth(String userName, int month);
@Query("select sum(op.quantity) from OrderProduct op join op.order o join op.product p where p.v.user.username=?1 and date(o.orderPlacedTime)=?2 group by date(o.orderPlacedTime)")

Integer noOfProductOrderedDate(String userName, LocalDate dateTime);
@Query("select p.id,p.title,p.price,p.discount,p.status,sum(op.quantity) from OrderProduct op join op.product p join op.order o where month(o.orderPlacedTime)=?2 and p.v.user.username=?1 group by op.product.id order by sum(op.quantity) desc limit 3 ")
List<Object[]> getTopSellingProductOfMonth(String userName, int month);
@Query("select p.id,p.title,p.price,p.discount,p.status,count(*) from OrderProduct op join op.product p join op.order o where month(o.orderPlacedTime)=?2 and p.v.user.username=?1 group by op.product.id order by count(*) desc  limit 3 ")

List<Object[]> getMostOrderedProductOfMonth(String userName, int month);
@Query("select p.id,p.title,p.price,p.discount,p.status,sum(op.quantity),count(*),avg(r.stars) from OrderProduct op join op.product p join op.order o left join op.review r where p.v.user.username=?1 and month(o.orderPlacedTime)=?2 group by op.product.id")
List<Object[]> productStas(String userName, int month);
@Query("select r from Return r join r.orderProduct op where op.product.v.user.username=?1")
List<Return> getReturnedOrder(String userName);
@Query("select e from Exchange e join e.orderProduct op where op.product.v.user.username=?1")

List<Exchange> getExchangedOrder(String userName);

@Transactional
@Modifying
@Query("update OrderProduct op set op.quantity = op.quantity-1 where op=?1")
int subProductCount(OrderProduct orderProduct);

@Transactional
@Modifying
@Query("delete from OrderProduct op where op.order = ?1")
int deleteOrderProductsByOrder(Order order);
@Query("select p from OrderProduct op join op.product p where p.v.user.username=?1")

Page<Product> findByUserNameAndPageAble(String userName, Pageable pageAble);

@Query("select op from OrderProduct op where op.order = ?1")
Optional<OrderProduct> getOrderProductByOrder(Order order);

@Transactional
@Modifying
@Query("delete from OrderProduct op where op.id = ?1")
int deleteOrderProductsByOrderId(int opId);


}
