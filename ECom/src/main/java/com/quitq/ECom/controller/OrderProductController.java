package com.quitq.ECom.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.quitq.ECom.Exception.InvalidIdException;
import com.quitq.ECom.dto.MessageDto;
import com.quitq.ECom.dto.MostOrderedProductDto;
import com.quitq.ECom.dto.OrderProductStatsDto;
import com.quitq.ECom.dto.ProductStatsDto;
import com.quitq.ECom.dto.TopSellingProductDto;
import com.quitq.ECom.model.Exchange;
import com.quitq.ECom.model.OrderProduct;
import com.quitq.ECom.model.Product;
import com.quitq.ECom.model.Return;
import com.quitq.ECom.service.OrderProductService;

@RestController
@RequestMapping("/orderproduct")
@CrossOrigin(origins={"http://localhost:4200"})

public class OrderProductController {
	@Autowired
	OrderProductService orderProductService;
	@GetMapping("/vendor/getAll")
	public ResponseEntity<?> getAllOrderedProducts(Principal p,	@RequestParam(defaultValue = "0", required = false)Integer page, 
			@RequestParam(defaultValue = "1000", required = false)Integer size,MessageDto messageDto
			)
	{
		String userName=p.getName();
	try {
		Pageable pageable =PageRequest.of(page,size);

		Page<Product> product=orderProductService.findAllOrderedProducts(userName,pageable);

		return ResponseEntity.ok(product);
	} catch (InvalidIdException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		messageDto.setMsg(e.getMessage());
		return ResponseEntity.badRequest().body(messageDto);
	}
	}
	@GetMapping("/vendor/getAllUnordered")
	public ResponseEntity<?> getAllUnorderedProducts(Principal p,MessageDto messageDto)
	{
		String userName=p.getName();
		try {
			List<Product> product=orderProductService.findAllUnOrderedProducts(userName);

			return ResponseEntity.ok(product);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messageDto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(messageDto);
		}
	}
	@GetMapping("/vendor/product/details")
	public ResponseEntity<?> getAllOrderedProductDetails(Principal p,MessageDto messageDto)
	{
		String userName=p.getName();
		try {
			List<OrderProduct> dto=orderProductService.getOrderedProductDetails(userName);
			return ResponseEntity.ok(dto);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messageDto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(messageDto);
		}
	}/*
	@GetMapping("/vendor/product/details/{status}")
	public ResponseEntity<?> getAllOrderedProductDetailsFilterStatus(@PathVariable String status,Principal p,MessageDto messageDto)
	{
		String userName=p.getName();
		try {
			List<OrderProductDetailDto> dto=orderProductService.getOrderedProductDetailsFilterStatus(userName,status);
			return ResponseEntity.ok(dto);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messageDto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(messageDto);
		}
	}
	@GetMapping("/vendor/orderReceive/{month}")
	public ResponseEntity<?> getOrderedReceivedByMonth(@PathVariable int month,Principal p,MessageDto messageDto)
	{
		String userName=p.getName();
		try {
			Integer dto=orderProductService.getOrderedReciveByMonth(userName,month);
			return ResponseEntity.ok(dto);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messageDto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(messageDto);
		}
	}*/
	@GetMapping("/vendor/orderReceiveDate/{date}")
	public ResponseEntity<?> getOrderedReceivedByDate(@PathVariable String date,Principal p,MessageDto messageDto)
	{
		String userName=p.getName();
		try {
			Integer dto=orderProductService.getOrderedReciveByDate(userName,date);
			return ResponseEntity.ok(dto);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messageDto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(messageDto);
		}
	}
	@GetMapping("/vendor/noOfPoductOrdered/{month}")
	public ResponseEntity<?> noOfProductOrderedMonth(@PathVariable int month,Principal p,MessageDto messageDto)
	{
		String userName=p.getName();
		try {
			Integer dto=orderProductService.getNoOfProductOrderedMonth(userName,month);
			return ResponseEntity.ok(dto);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messageDto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(messageDto);
		}
	} 
	@GetMapping("/vendor/productOrdered/{date}")
	public ResponseEntity<?> noOfProductOrderedDate(@PathVariable String date,Principal p,MessageDto messageDto)
	{
		String userName=p.getName();
		try {
			Integer dto=orderProductService.getNoOfProductOrderedDate(userName,date);
			return ResponseEntity.ok(dto);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messageDto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(messageDto);
		}
	}
	@GetMapping("/vendor/topSellingProduct/{month}")
	public ResponseEntity<?> topSellingProductOfMonth(@PathVariable int month,Principal p,MessageDto messageDto)
	{
		String userName=p.getName();
		try {
			List<TopSellingProductDto> dto=orderProductService.topSellingProductOfMonth(userName,month);
			return ResponseEntity.ok(dto);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messageDto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(messageDto);
		}
	} 
	@GetMapping("/vendor/topOrdered/{month}")
	public ResponseEntity<?> topOrderedProductOfMonth(@PathVariable int month,Principal p,MessageDto messageDto)
	{
		String userName=p.getName();
		try {
			List<MostOrderedProductDto> dto=orderProductService.topOrderedProductOfMonth(userName,month);
			return ResponseEntity.ok(dto);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messageDto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(messageDto);
		}
	} 
	@GetMapping("/vendor/productStats/{month}")
	public ResponseEntity<?> productStats(@PathVariable int month,Principal p,MessageDto messageDto)
	{
		String userName=p.getName();
		try {
			List<ProductStatsDto> dto=orderProductService.productStats(userName,month);
			return ResponseEntity.ok(dto);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			messageDto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(messageDto);
		}
	}
	@GetMapping("/vendor/returnOrder")
	public ResponseEntity<?> getReturnOrder(Principal p,MessageDto dto)
	{
		String userName=p.getName();
		try {
			List<Return> returnList=orderProductService.getReturnedOrder(userName);
			return ResponseEntity.ok(returnList);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
	}
	
	@GetMapping("/vendor/exchangeOrder")
	public ResponseEntity<?> getExchangeOrder(Principal p,MessageDto dto)
	{
		String userName=p.getName();
		try {
			List<Exchange> returnList=orderProductService.getExchangedOrder(userName);
			return ResponseEntity.ok(returnList);
		} catch (InvalidIdException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dto.setMsg(e.getMessage());
			return ResponseEntity.badRequest().body(dto);
		}
		
	}
	@GetMapping("/vendor/orderProductStats")
	public List<OrderProductStatsDto> getOrderProductStats(Principal p){
		String username=p.getName();
		List<OrderProductStatsDto> dto=orderProductService.orderProductStats(username);
		return dto;
	}
	@GetMapping("/vendor/orderProductStats/month")
	public List<OrderProductStatsDto> getOrderProductStatsMonth(Principal p){
		String username=p.getName();
		List<OrderProductStatsDto> dto=orderProductService.orderProductStatsMonth(username);
		return dto;
	}
	@GetMapping("/vendor/orderProductStats/{fromDate}/{toDate}")
	public List<OrderProductStatsDto> getOrderProductStatsDto(Principal p,@PathVariable String fromDate,@PathVariable String toDate){
		String username=p.getName();
		List<OrderProductStatsDto> dto=orderProductService.orderProductStatsWeek(username,fromDate,toDate);
		return dto;
	}
	
	

}
