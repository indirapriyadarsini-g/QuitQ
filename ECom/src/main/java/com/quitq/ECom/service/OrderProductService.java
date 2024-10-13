package com.quitq.ECom.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.quitq.ECom.Exception.InvalidIdException;
import com.quitq.ECom.dto.MostOrderedProductDto;
import com.quitq.ECom.dto.OrderProductDetailDto;
import com.quitq.ECom.dto.OrderProductStatsDto;
import com.quitq.ECom.dto.ProductStatsDto;
import com.quitq.ECom.dto.TopSellingProductDto;
import com.quitq.ECom.model.Exchange;
import com.quitq.ECom.model.OrderProduct;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.Return;
import com.quitq.ECom.repository.OrderProductRepository;

@Service
public class OrderProductService {
@Autowired
private OrderProductRepository orderProductRepository;
	public Page<Product> findAllOrderedProducts(String userName, Pageable pageable) throws InvalidIdException {
		// TODO Auto-generated method stub
		Page<Product> product=orderProductRepository.findByUserNameAndPageAble(userName,pageable);
	
		for(Product p:product) {
			System.out.println(p.getTitle());
	
		}
		if(product.isEmpty())
		{
			throw new InvalidIdException("No products have been ordered");
		}
		return product;
	}
	public List<Product> findAllUnOrderedProducts(String userName) throws InvalidIdException {
		// TODO Auto-generated method stub
		List<Product> product=orderProductRepository.findByUsernameUnordered(userName);
		if(product.isEmpty())
		{
			throw new InvalidIdException("No products have been unordered");
		}
		return product;	}
	public List<OrderProduct> getOrderedProductDetails(String userName) throws InvalidIdException {
		// TODO Auto-generated method stub
		List<OrderProduct> obj=orderProductRepository.getOrderedProductDetails(userName);
		List<OrderProductDetailDto> list=new ArrayList<>();
	/*	for(Object o[]:obj)
		{
			OrderProductDetailDto dto=new OrderProductDetailDto();
			dto.setOrderId(Integer.parseInt(o[0].toString()));
			dto.setOrderStatus(o[1].toString());
			dto.setOrderQuantity(Integer.parseInt(o[2].toString()));
			dto.setAmountPayable(Double.parseDouble(o[3].toString()));
			dto.setTotalAmount(Double.parseDouble(o[4].toString()));
			dto.setDiscountAmount(Double.parseDouble(o[5].toString()));
			dto.setTitle(o[6].toString());
			dto.setPrice(Double.parseDouble(o[7].toString()));
			dto.setDiscount(Double.parseDouble(o[8].toString()));
			dto.setOrderPlacedTime(LocalDate.parse(o[9].toString().split("T")[0]));
			list.add(dto);
		}
		if(list.isEmpty())
		{
			throw new InvalidIdException("No products ordered for details");
		}*/
		return obj;
		
	}
	/*
	public List<OrderProduct> getOrderedProductDetailsFilterStatus(String userName, String status) throws InvalidIdException {
	List<OrderProductDetailDto> dto=this.getOrderedProductDetails(userName);
	List<OrderProductDetailDto> streamList=dto.stream().filter(p->p.getOrderStatus().equalsIgnoreCase(status)).toList();
	if(streamList.isEmpty())
	{
		throw new InvalidIdException("No order with this status");
	}
	return streamList;
		}
	public Integer getOrderedReciveByMonth(String userName,int month) throws InvalidIdException {
		// TODO Auto-generated method stub
		Integer dto=orderProductRepository.getNumberOfOrdersReceivedByMonth(userName,month);
		if(dto==null)
		{
			throw new InvalidIdException("No orders placed for this month");
		}
		return dto;
		
	}*/
	public Integer getOrderedReciveByDate(String userName, String date) throws InvalidIdException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateTime = LocalDate.parse(date, formatter);
		/*
		Integer dto=orderProductRepository.getNumberOfOrdersReceivedByDate(userName,dateTime);
		if(dto==null)
		{
			throw new InvalidIdException("No orders placed for this date");
		}
		return dto;
		*/
		return null;
	}
	public Integer getNoOfProductOrderedMonth(String userName, int month) throws InvalidIdException {
		// TODO Auto-generated method stub
		Integer dto=orderProductRepository.noOfProductOrderedMonth(userName,month);
		if(dto==null)
		{
			throw new InvalidIdException("No products ordered this month");
		}
		return dto;
	}
	public Integer getNoOfProductOrderedDate(String userName, String date) throws InvalidIdException {
		// TODO Auto-generated method stub
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate dateTime = LocalDate.parse(date, formatter);
		Integer dto=orderProductRepository.noOfProductOrderedDate(userName,dateTime);
		if(dto==null)
		{
			throw new InvalidIdException("No product ordered on this date");
		}
		return dto;
	}
	public List<TopSellingProductDto> topSellingProductOfMonth(String userName, int month) throws InvalidIdException {
		// TODO Auto-generated method stub
		List<Object[]> dto=orderProductRepository.getTopSellingProductOfMonth(userName,month);
		if(dto.isEmpty())
		{
			throw new InvalidIdException("No products ordered uptill now");
		}
		List<TopSellingProductDto> list=new ArrayList<>();
		for(Object[] o:dto)
		{
			TopSellingProductDto product=new TopSellingProductDto();
			product.setId(Integer.parseInt(o[0].toString()));
			product.setTitle(o[1].toString());
			product.setPrice(Double.parseDouble(o[2].toString()));
			product.setDiscount(Double.parseDouble(o[3].toString()));
			product.setStatus(o[4].toString());
			product.setNoOfQuantitySold(Double.parseDouble(o[5].toString()));
		list.add(product);

		}
		return list;
	}
	public List<MostOrderedProductDto> topOrderedProductOfMonth(String userName, int month) throws InvalidIdException {
		// TODO Auto-generated method stub
		List<Object[]> dto=orderProductRepository.getMostOrderedProductOfMonth(userName,month);
		if(dto.isEmpty())
		{
			throw new InvalidIdException("No products ordered uptill now");
		}
		List<MostOrderedProductDto> list=new ArrayList<>();
		for(Object[] o:dto)
		{
			MostOrderedProductDto product=new MostOrderedProductDto();
			product.setId(Integer.parseInt(o[0].toString()));
			product.setTitle(o[1].toString());
			product.setPrice(Double.parseDouble(o[2].toString()));
			product.setDiscount(Double.parseDouble(o[3].toString()));
			product.setStatus(o[4].toString());
			product.setNoOfTimeOrdered(Double.parseDouble(o[5].toString()));
		list.add(product);
	}
		return list;
	}
	public List<ProductStatsDto> productStats(String userName, int month) throws InvalidIdException {
		// TODO Auto-generated method stub
		List<Object[]> dto=orderProductRepository.productStas(userName,month);
		if(dto.isEmpty())
		{
			throw new InvalidIdException("No product stats avaialble ");
		}
		List<ProductStatsDto> list=new ArrayList<>();
		for(Object[] o:dto)
		{
			ProductStatsDto product=new ProductStatsDto();
			product.setId(Integer.parseInt(o[0].toString()));
			product.setTitle(o[1].toString());
			product.setPrice(Double.parseDouble(o[2].toString()));
			product.setDiscount(Double.parseDouble(o[3].toString()));
			product.setStatus(o[4].toString());
			product.setNoOfQuantitySold(Double.parseDouble(o[5].toString()));
			product.setNoOfTimesOrdered(Double.parseDouble(o[6].toString()));
			if(o[7]==null)
			{
				product.setAverageRating(Double.parseDouble("0.00"));
			}
			else
			{
				product.setAverageRating(Double.parseDouble(o[7].toString()));

			}
		list.add(product);
	}
		return list;
	}
	public List<Return> getReturnedOrder(String userName) throws InvalidIdException {
		// TODO Auto-generated method stub
		List<Return> returnList=orderProductRepository.getReturnedOrder(userName);
		if(returnList.isEmpty())
		{
			throw new InvalidIdException("No orders returned uptill now");
		}
		return returnList;
	}
	public List<Exchange> getExchangedOrder(String userName) throws InvalidIdException {
		// TODO Auto-generated method stub
		List<Exchange> exchangeList=orderProductRepository.getExchangedOrder(userName);
		if(exchangeList.isEmpty())
		{
			throw new InvalidIdException("No orders returned uptill now");
		}
		return exchangeList;
	}
	public List<OrderProductStatsDto> orderProductStats(String username){
		List<Object[]> obj=orderProductRepository.getOrderProductStats(username);
		List<OrderProductStatsDto> list=new ArrayList<>();
		for(Object o[]:obj) {
			OrderProductStatsDto dto=new OrderProductStatsDto();
			dto.setStatus(o[0].toString());
			dto.setNumber(Integer.parseInt(o[1].toString()));
			list.add(dto);
			
		}
		 obj=orderProductRepository.getReturnProductStats(username);
		for(Object o[]:obj) {
			OrderProductStatsDto dto=new OrderProductStatsDto();
			dto.setStatus("Returned");
			dto.setNumber(Integer.parseInt(o[0].toString()));
			list.add(dto);
			
		}	
		 obj=orderProductRepository.getExchangeProductStats(username);
			for(Object o[]:obj) {
				OrderProductStatsDto dto=new OrderProductStatsDto();
				dto.setStatus("Exchanged");
				dto.setNumber(Integer.parseInt(o[0].toString()));
				list.add(dto);
				
			}
			return list;
		
		
	}
	public List<OrderProductStatsDto> orderProductStatsMonth(String username) {
		// TODO Auto-generated method stub
		List<Object[]> obj=orderProductRepository.getOrderProductStatsMonth(username);
		List<OrderProductStatsDto> list=new ArrayList<>();
		for(Object o[]:obj) {
			OrderProductStatsDto dto=new OrderProductStatsDto();
			dto.setStatus(o[0].toString());
			dto.setNumber(Integer.parseInt(o[1].toString()));
			list.add(dto);
			
		}
		 obj=orderProductRepository.getReturnProductStatsMonth(username);
		for(Object o[]:obj) {
			OrderProductStatsDto dto=new OrderProductStatsDto();
			dto.setStatus("Returned");
			dto.setNumber(Integer.parseInt(o[0].toString()));
			list.add(dto);
			
		}	
		 obj=orderProductRepository.getExchangeProductStatsMonth(username);
			for(Object o[]:obj) {
				OrderProductStatsDto dto=new OrderProductStatsDto();
				dto.setStatus("Exchanged");
				dto.setNumber(Integer.parseInt(o[0].toString()));
				list.add(dto);
				
			}
			return list;
	}
	public List<OrderProductStatsDto> orderProductStatsWeek(String username, String fromDateS, String toDateS) {
		// TODO Auto-generated method stub
	/*	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate fromDate = LocalDate.parse(fromDateS, formatter);
		LocalDate toDate = LocalDate.parse(toDateS, formatter);

		List<Object[]> obj=orderProductRepository.getOrderProductStatsWeek(username,fromDate,toDate);
		List<OrderProductStatsDto> list=new ArrayList<>();
		for(Object o[]:obj) {
			OrderProductStatsDto dto=new OrderProductStatsDto();
			dto.setStatus(o[0].toString());
			dto.setNumber(Integer.parseInt(o[1].toString()));
			list.add(dto);
			
		}
		 obj=orderProductRepository.getReturnProductStatsMonth(username);
		for(Object o[]:obj) {
			OrderProductStatsDto dto=new OrderProductStatsDto();
			dto.setStatus("Returned");
			dto.setNumber(Integer.parseInt(o[0].toString()));
			list.add(dto);
			
		}	
		 obj=orderProductRepository.getExchangeProductStatsMonth(username);
			for(Object o[]:obj) {
				OrderProductStatsDto dto=new OrderProductStatsDto();
				dto.setStatus("Exchanged");
				dto.setNumber(Integer.parseInt(o[0].toString()));
				list.add(dto);
				
			}
			return list;
			*/
		return null;
		
	}
	public List<OrderProductStatsDto> getNoOfOrdersReceived(String username) {
		// TODO Auto-generated method stub
		List<Object[]> obj=orderProductRepository.getNumberOfOrdersReceivedByMonth(username,2024);
		List<OrderProductStatsDto> list=new ArrayList<>();
		for(Object[]o:obj) {
			OrderProductStatsDto dto=new OrderProductStatsDto();
			dto.setStatus(o[0].toString());
			dto.setNumber(Integer.parseInt(o[1].toString()));
			list.add(dto);
			
		}
		return list;
	}
	public List<OrderProductStatsDto> getNoOfOrderReceivedDate(String username) {
		// TODO Auto-generated method stub
		List<Object[]> obj=orderProductRepository.getNumberOfOrdersReceivedByDate(username,9);
		List<OrderProductStatsDto> list=new ArrayList<>();
		for(Object[]o:obj) {
			OrderProductStatsDto dto=new OrderProductStatsDto();
			dto.setStatus(o[0].toString());
			dto.setNumber(Integer.parseInt(o[1].toString()));
			list.add(dto);
			
		}
		return list;
	}
	public List<OrderProductStatsDto> getSalesByMonth(String username) {
		// TODO Auto-generated method stub
		List<Object[]> obj=orderProductRepository.getSalesByMonth(username,9);
		List<OrderProductStatsDto> list=new ArrayList<>();
		for(Object[]o:obj) {
			OrderProductStatsDto dto=new OrderProductStatsDto();
			dto.setStatus(o[0].toString());
			dto.setNumber(Integer.parseInt(o[1].toString()));
			list.add(dto);
			
		}
		return list;
	}
	public List<OrderProductStatsDto> getSalesByDate(String username) {
		// TODO Auto-generated method stub
		List<Object[]> obj=orderProductRepository.getSalesByDate(username,9);
		List<OrderProductStatsDto> list=new ArrayList<>();
		for(Object[]o:obj) {
			OrderProductStatsDto dto=new OrderProductStatsDto();
			dto.setStatus(o[0].toString());
			dto.setNumber(Integer.parseInt(o[1].toString()));
			list.add(dto);
			
		}
		return list;
	}
	
	
}
