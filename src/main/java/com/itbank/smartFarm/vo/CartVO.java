 package com.itbank.smartFarm.vo;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class CartVO {
	
	private int order_id, member_id, delivery_id, orderitems_id, member_phone, total_price, unit_price, count, oiorderid;
	private Date order_date;
	private String status, member_name, member_email, member_address, product_name, order_status, address, delivery_status;
                                
    // 기본 생성자
    public CartVO() {}
	
}